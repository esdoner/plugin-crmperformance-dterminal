package com.fr.plugin.performance.file.release;

import com.fr.plugin.performance.handle.Connect2DB;
import com.fr.plugin.performance.setup.bucket.ThresholdBucket;
import com.fr.plugin.performance.setup.threshold.ThresholdPlate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description: none
 * @author yuwh
 * time: 2018/10/12
 */
public class CptFileChecker {
    private String rPath;
    private BasicFileAttributes aAttr;
    private HashMap<String,String> fileInfo = new HashMap<>();
    private boolean fileExists = false;
    private HashMap<String,String> fileQualifications = new HashMap<>();
    private List<String> failMessage = new ArrayList<>();

    public CptFileChecker(String cptName,String cptDirectory){
        rPath = this.getClass().getResource("/").getPath();
        File  var1 = new File(rPath);
        rPath = var1.getParent();
        fileInfo.put("cptName",cptName);
        fileInfo.put("cptDirectory",cptDirectory);
        initFileInfo();
    }

    private void initFileInfo(){
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf= new SimpleDateFormat(format);

        try {
            Path aPath = Paths.get(rPath+fileInfo.get("cptDirectory")+fileInfo.get("cptName"));
            BasicFileAttributeView aView =  Files.getFileAttributeView(aPath,BasicFileAttributeView.class);
            aAttr = aView.readAttributes();
            if(aAttr != null){ fileExists = true; }
            setFileCreated(sdf.format(new Date(aAttr.creationTime().toMillis())));
            setFileModified(sdf.format(new Date(aAttr.lastModifiedTime().toMillis())));
            setFileRecentAnalysis(String.valueOf(initFileRecentAnalysis()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
    * @params []
    * @return int
    * @description: result 0是明确不通过，1是通过，-1是待定，需要备注并遵从CptFileRelease
    */
    private int initFileRecentAnalysis(){
        int result = 0 ;
        int result_p = 0;
        int result_t = 0;
        //是否已经性能检查
        Connect2DB conn = new Connect2DB("performance2018");
        String queryString = "SELECT test_path,test_time_max,test_index1_max,test_index2_max,test_ttime FROM cptanalysis_record " +
                "WHERE test_valid = 1 AND UNIX_TIMESTAMP(now())-3600 <= test_time AND test_path = '"+fileInfo.get("cptName")+"' ORDER BY test_time DESC LIMIT 1";
        if(conn.createCon()){
            try{
                ResultSet queryRst = conn.executeDQL(queryString);
                if(queryRst.next()){
                    fileQualifications.put("WarningTimeConsume", queryRst.getString(2));
                    fileQualifications.put("MaxTimeConsume",queryRst.getString(2));
                    fileQualifications.put("WarningLineNumber", queryRst.getString(3));
                    fileQualifications.put("MaxLineNumber", queryRst.getString(3));
                    fileQualifications.put("WarningMemoryConsume", queryRst.getString(4));
                    fileQualifications.put("MaxMemoryConsume", queryRst.getString(4));
                    fileQualifications.put("WarningTotalTime", queryRst.getString(5));
                    fileQualifications.put("MaxTotalTime", queryRst.getString(5));
                    fileQualifications.put("WarningDBRetrievalNumber", "0");
                    fileQualifications.put("MaxDBRetrievalNumber", "0");
                    result_p = 1;
                } else {
                    failMessage.add("PerformanceAnalysis");
                }
            }catch(SQLException e){
                e.printStackTrace();
            } finally{
                conn.closeCon();
            }
        }
        //是否已经稳定性检查，大部分还未强制检查，没有的要记录下说明
        Connect2DB conn1 = new Connect2DB("testcase2018");
        //null的需要说明，有0的不过关，全是1才过关
        String queryString1 = "SELECT (1-demand_ifpass) AS demand_ifpass FROM test_case_demand JOIN test_case_cpt ON cpt_id = demand_cpt "
                + "WHERE CONCAT('/',cpt_path,'/',cpt_filename) = '" + fileInfo.get("cptName") + "' ORDER BY demand_ifpass ASC LIMIT 1";
        if(conn1.createCon()){
            try{
                ResultSet queryRst1 = conn1.executeDQL(queryString1);
                if(queryRst1.next()){
                    fileQualifications.put("TestCaseRequire",queryRst1.getString(1));
                    result_t = 1;
                } else {
                    failMessage.add("TestcaseAnalysis");
                }
            }catch(SQLException e){
                e.printStackTrace();
            } finally{
                conn1.closeCon();
            }
        }
        if(result_p * result_t == 1){
            result = 1;
        } else if(result_t < result_p){
            result = 0;
        } else if(result_t > result_p){
            result = -1;
        } else {
            result = -2;
        }
        return result;
        //是否通用
    }

    private void setFileCreated(String var1){
        fileInfo.put("craeted",var1);
    }

    private void setFileModified(String var2){
        fileInfo.put("modified",var2);
    }

    private void setFileRecentAnalysis(String var3) {
        fileInfo.put("analysised",var3);
    }

    public String getRootPath() { return rPath; }

    public HashMap getFileInfo(){
        return fileInfo;
    }

    public String getFileInfo(String key){
        return fileInfo.get(key);
    }

    public boolean judgeHasAnalysed(Boolean reason){
        String var1 = getFileInfo("analysised");
        if(reason){
            fileQualifications.put("TestCaseRequire", "1");
        }
        if(var1.equals("1")){
            return true;
        } else if(var1.equals("0") && reason) {
            failMessage.remove("TestcaseAnalysis");
            return true;
        }else if(var1.equals("-2") && reason) {
            failMessage.remove("TestcaseAnalysis");
            return false;
        } else {
            return false;
        }
    }

    public boolean judgePassThreshold(){
        ThresholdBucket curbucket = ThresholdBucket.getInstance();
        ThresholdPlate curplate = new ThresholdPlate();
        //step 1
        curplate.putLeavings(fileQualifications);
        //step 2
        curbucket.thresholdsWork(curplate);
        //step 3
        curplate.failFilter(failMessage);
        return ! Boolean.valueOf(curplate.getNotes("gameover").toString());
    }

    public boolean judgeExisted(){
        if(! fileExists) {
            failMessage.add("FileExists");
        }
        return fileExists;
    }

    public String[] getFailMessage(){
        return failMessage.toArray(new String[failMessage.size()]);
    }
}
