package com.fr.plugin.performance.report;

import com.fr.plugin.performance.file.release.FileRelease;
import com.fr.plugin.performance.util.read.PropertiesCacheReader;
import com.fr.plugin.performance.setup.threshold.Threshold;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuwh on 2018/10/18
 * Description:none
 */
public class Result4Release extends ResultHandle {
    private final String CHECKFILE = "更新许可检查";
    private final String VERIFYPATH = "目录检查";
    private final String BACKUPFILE = "备份文件";
    private final String PREPAREFILE = "更新文件";
    private final String RELEASEFILE = "提交更新";
    private final String RESULTGATHER = "记录更新";
    private final String SUCCESS = "成功";
    private final String FAIL = "失败";

    public Result4Release(HashMap var1){
        super(var1);
        beforeHandle();
    }

    @Override
    public void beforeHandle(){ }

    @Override
    public String objectHandle(){
        return object2Html();
    }

    private String object2Html(){
        Set<Map.Entry> multiSet = multinput.entrySet();
        try {
            consum2Html(multiSet);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this.multioutput;
    }

    private void consum2Html(Set<Map.Entry> multiSet) throws NoSuchFieldException, IllegalAccessException {
        multioutput = "<div><ul>";
        for(Map.Entry<String,String[][]> var1:multiSet){
            String var2 = var1.getKey();
            String var3 = var1.getValue()[0][0];
            String var4 = var3 == "success" ? SUCCESS : FAIL;
            String var5 = var3 == "success" ? "release_sub_success" : "release_sub_fail" ;
            String[] var6 = var1.getValue()[1];
            String var7 = "";
            String var8;
            String var9;
            if(var3 != "success") {
                for (int i = 0; i < var6.length; i++) {
                    var8 = PropertiesCacheReader.getInstance().readProperties(Threshold.PATH, var6[i]);
                    var9 = PropertiesCacheReader.getInstance().readProperties(FileRelease.RELEASE_PROPERTIES, var6[i]);
                    if( !(var8 == null && var9 == null) ) {
                        var7 = var7 =="" ? var7 : var7 + " , ";
                        var7 = var7 + (var8 == null? var9 :var8) + FAIL;
                    } else {
                        continue;
                    }

                }
            }
            if(! var7.isEmpty()) var7="<p>——"+var7+"</p>";
            multioutput = multioutput + "<li class='"+var5+"'><span>"+this.getClass().getDeclaredField(var2.toUpperCase()).get(this)+"</span><span>"+var4+"</span>"+var7+"</li>";
        }
        multioutput = multioutput + "</ul></div>";
    }
}
