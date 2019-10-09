package com.fr.plugin.performance.analysis.pre;

import com.fr.base.ParameterMapNameSpace;
import com.fr.base.TableData;
import com.fr.base.TemplateUtils;
import com.fr.base.io.IOFile;
import com.fr.data.impl.DBTableData;
import com.fr.plugin.performance.analysis.Analysis;
import com.fr.plugin.performance.deploy.carrier.FormCarrier;
import com.fr.plugin.performance.gather.Gather2DB4DS;
import com.fr.plugin.performance.report.Result4DS;
import com.fr.plugin.performance.report.Result4Interrupt;
import com.fr.plugin.performance.report.Result4NotFound;
import com.fr.plugin.performance.util.read.URLReader;
import com.fr.script.Calculator;
import com.fr.stable.ParameterProvider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019.07.18
 * Description:none
 */
public final class BaseAnalysis implements Analysis {
    private IOFile book;
    private Map<String, Object> Paras= new HashMap<>();
    private String bookPath;
    private String user;
    private Map<String, String[]> DSResultMap = new HashMap<>();
    private Map<String, Object> ResultMap = new HashMap<>();
    private Result4DS result4DS;
    public String ResultReport = "";
    public boolean interrupt = false;
    private String problemDS ;

    public BaseAnalysis(IOFile book, FormCarrier fc, long var4) {
        this.book = book;
        bookPath = String.valueOf(fc.find("BookInformation", "BookPath"));
        user = String.valueOf(fc.find("UserInformation", "UserID"));
        Paras.putAll((Map)fc.find("BookInformation", "BookParasHolder"));
        ResultMap.put("cptName", bookPath);
        ResultMap.put("tTime", var4);
    }

    @Override
    public void start(){
        if(book == null) {
            this.Interrupt(1);
        } else {
            prepareSQL();
        }
    }

    /** 处理出 DB 数据集*/
    private void prepareSQL(){
        Iterator iterator = book.getTableDataNameIterator();
        Map<String, Object> parameterMap = new HashMap<>();
        String singleSQL;

        while(iterator.hasNext() && ! interrupt) {
            String dsName = (String) iterator.next();
            TableData tableData = book.getTableData(dsName);
            if (tableData instanceof DBTableData) {
                DBTableData dt = (DBTableData) tableData;
                singleSQL = dt.getQuery();
                String dbName = dt.getDatabase().toString().replace("[","").replace(" Database]","");
                ParameterProvider[] ps = dt.getParameters(Calculator.createCalculator());
                for(ParameterProvider var2 : ps) {
                    parameterMap.put(var2.getName(), var2.getValue());
                }
                parameterMap.putAll(Paras);
                Calculator ca = Calculator.createCalculator();
                ParameterMapNameSpace ns = ParameterMapNameSpace.create(parameterMap);
                ca.pushNameSpace(ns);
                try {
                    String sqlString = TemplateUtils.renderTpl(ca, singleSQL);
                    /** select 才检查*/
                    if(sqlString.trim().substring(0, 6).equalsIgnoreCase("SELECT")) {
                        sqlAnalysis(dbName, dsName, sqlString);
                    }
                }catch(Exception e){
                    problemDS = dsName;
                    this.Interrupt(2);
                    interrupt = true;
                }
            }
            parameterMap.clear();
        }
    }

    private void sqlAnalysis(String dbname, String dsname, String sqlString){
        long t;
        int l;
        long m;
        String e;
        DBQueryAnalysis qA = new DBQueryAnalysis(dbname, sqlString, dsname);
        qA.start();
        t = qA.getTimeConsum();
        l = qA.getLineConsum();
        m = qA.getMemoryConsum();
        e = qA.getQueryExplain();
        qA.stop();
        DSResultMap.put(dsname, new String[]{dbname, e, String.valueOf(t), String.valueOf(l), String.valueOf(m)});
    }

    @Override
    public void stop(){
        if(! this.interrupt) {
            /*consuming report*/
            result4DS = new Result4DS((HashMap) ResultMap, user, (HashMap) DSResultMap);
            ResultReport = result4DS.objectHandle();
            /*result gather*/
            Gather2DB4DS ResultGather = new Gather2DB4DS(ResultMap, result4DS.getStoreHtml(), user, DSResultMap, URLReader.getParasString(Paras));
            if (ResultGather.containerPrepare()) {
                ResultGather.containerGather();
            }
        }
    }

    @Override
    public void Interrupt(int reasonid){
        String[] var = {"cptName","result"};
        switch(reasonid) {
            case 1:
                var[0] = bookPath;
                var[1] = "The specified template path was not found";
                DSResultMap.clear();
                DSResultMap.put("tpl-not-found", var);
                ResultReport = new Result4NotFound((HashMap) DSResultMap).ObjectHandle();
                break;
            case 2:
                var[0] = bookPath;
                var[1] = "Template parameters error,please check the data set named '" + problemDS+"'";
                DSResultMap.clear();
                DSResultMap.put("tpl-paras-err", var);
                ResultReport = new Result4Interrupt((HashMap) DSResultMap).ObjectHandle();
                break;
            default:;
        }
    }
}
