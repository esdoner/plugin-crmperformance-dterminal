package com.fr.plugin.performance.file.release;

import com.fr.plugin.performance.deploy.carrier.FormCarrier;
import com.fr.plugin.performance.gather.Gather2DB4Release;
import com.fr.plugin.performance.handle.FileBasicHandler;
import com.fr.plugin.performance.handle.GitBasicHandler;
import com.fr.plugin.performance.report.Result4Release;
import com.fr.stable.StringUtils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yuwh
 * time:2018/10/12
 * Description:
 * 1  没有的文件路径自动创建
 * 2 备份文件夹下的文件如果不需要可以随便删除，不会影响之后的备份
 * 3 option选项参考FileBasicHandler的方法描述
 */
public class CptFileRelease implements FileRelease {
    private final String REPORTLETS = "/reportlets";
    private String cptName;
    private String userName;
    private String password;
    private String reason;
    private Map option;

    private File cptPath;
    private File desPath;
    private File bakPath;

    private int Step;
    private String releaseType;

    private boolean isInterrupt = true;
    public LinkedHashMap resultMap = new LinkedHashMap<String,String[][]>();
    public Result4Release rst4Release;
    public Gather2DB4Release rstGather;
    public String ResultReport = "";

    public CptFileRelease(FormCarrier fc) {
        this.cptName = String.valueOf(fc.find("BookInformation", "BookPath"));
        this.userName = String.valueOf(fc.find("UserInformation", "UserID"));
        this.password = String.valueOf(fc.find("UserInformation", "UserPassword"));
        this.option=(Map)fc.find("OptionInformation", "OptionMap");
        this.desPath = new File(option.get("SpecifiedCPTReleasePath").toString());
        this.bakPath = new File(option.get("SpecifiedCPTBackUpPath").toString());
        this.reason = option.get("reason").toString();
        Step = 0;
    }

    @Override
    public void checkFile() {
        CptFileChecker cfc = new CptFileChecker(cptName, REPORTLETS);
        cptPath = new File(cfc.getRootPath()+ cfc.getFileInfo("cptDirectory")+ cptName);
        if(cfc.judgeExisted() && cfc.judgeHasAnalysed(!StringUtils.isEmpty(reason)) && cfc.judgePassThreshold()) {
            setSubResult("success");
            Step = 1;
        } else {
            setSubResult("fail",cfc.getFailMessage());
            setIsInterrupt(false);
        }
    }

    @Override
    public void verifyPath() {
        if(desPath != null) {
            if( FileBasicHandler.ensureDirectory(desPath, Boolean.parseBoolean(option.get("mkdir").toString()))){
                setSubResult("success");
                Step = 2;
            } else {
                setSubResult("fail");
                setIsInterrupt(false);
            }
        }
    }

    @Override
    public void backupFile() {
        File des = new File(desPath.getPath()+ REPORTLETS +cptName);
        File bak = new File(bakPath.getPath()+ REPORTLETS +cptName);
        if(FileBasicHandler.flexibleBackUp(des,bak, Boolean.parseBoolean(option.get("bak4bak").toString()))){
            setSubResult("success");
            Step = 3;
        } else {
            setSubResult("fail");
            setIsInterrupt(false);
        }
    }

    @Override
    public void prepareFile() {
        File des = new File(desPath.getPath()+ REPORTLETS +cptName);
        releaseType =des.exists() ? "Modify" : "created";
        if(FileBasicHandler.copyFile(cptPath,des, Boolean.parseBoolean(option.get("onlyIO").toString()))){
            setSubResult("success");
            Step = 4;
        } else {
            setSubResult("fail");
            setIsInterrupt(false);
        }
    }

    @Override
    public void releaseFile() {
        String path = option.get("GitRepositoryRoot").toString()+ "\\.git";
        GitBasicHandler a = new GitBasicHandler(path);
        a.insertAuth(userName,password, option.get("GitRemoteOriginName").toString(), option.get("GitRemoteBranchName").toString());
        /** 有更新才add和commit；push失败之后不会重复提交没有改变的模板*/
        if(a.diffOperation((option.get("DefaultPathPrefix") + cptName).substring(1))){
            a.addOperation((option.get("DefaultPathPrefix") + cptName).substring(1));
            a.commitOperation(releaseType+" "+ cptName);
        }
        if(a.pushOperation()){
            setSubResult("success");
            Step = 5;
        } else {
            setSubResult("fail");
            setIsInterrupt(false);
        }
    }

    @Override
    public void resultGather() {
        rstGather = new Gather2DB4Release(cptName,releaseType, userName, reason);
        if(rstGather.containerPrepare()){
            rstGather.containerGather();
        }
        Step = 6;
        setSubResult("success");
    }

    public void stepShift(int stepNum){
        while(stepNum > 0 && isInterrupt){
            switch(Step){
                case 0:
                    checkFile();
                    break;
                case 1:
                    verifyPath();
                    break;
                case 2:
                    backupFile();
                    break;
                case 3:
                    prepareFile();
                    break;
                case 4:
                    releaseFile();
                    break;
                case 5:
                    resultGather();
                    break;
                default:
                    setIsInterrupt(false);
            }
            stepNum -= 1;
        }
    }

    public void setIsInterrupt(boolean var){ isInterrupt = var; }

    private void setSubResult(String result) {
        String parentMethosName = Thread.currentThread().getStackTrace()[2].getMethodName();
        resultMap.put(parentMethosName.toLowerCase(), new String[][]{{result},{}});
    }

    private void setSubResult(String result, String[] failMessage) {
        String parentMethosName = Thread.currentThread().getStackTrace()[2].getMethodName();
        resultMap.put(parentMethosName.toLowerCase(), new String[][]{{result},failMessage});
    }

    public void releaseStop(){
        rst4Release = new Result4Release(resultMap);
        ResultReport = rst4Release.objectHandle();
    }
}
