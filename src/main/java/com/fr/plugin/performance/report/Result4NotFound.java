package com.fr.plugin.performance.report;

import java.util.HashMap;

public class Result4NotFound extends ResultHandle {

    public Result4NotFound(HashMap var1){
        super(var1);
    }

    public String ObjectHandle(){
        return Object2html();
    }

    private String Object2html(){
       for(Object key:multinput.keySet()){
           String[] var1 = (String[]) multinput.get(key);
           multioutput = "<span class='"+key.toString()+"'>&lt;"+var1[0]+"&gt;</span><span class='"+key.toString()+"'>"+var1[1] +"</span>";
       }
       return multioutput;
    }
}
