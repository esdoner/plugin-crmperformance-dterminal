package com.fr.plugin.performance.deploy.unit.form;

import com.fr.plugin.performance.deploy.unit.*;

import java.util.Map;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/6
 * Description:none
 */
@DeployUnit(type= "StandardForm")
@Form(name= "BookInformation", size= 3)
public class BookUnit extends Unit {
    @Input(name= "BookPath", accessClazz= String.class)
    private String path= null;
    @Input(name= "BookMainOption", accessClazz= String.class)
    private String option= null;
    @Input(name= "BookParasHolder", accessClazz= Map.class)
    private OptionUnit parameters= new OptionUnit();

    public String getPath(){ return path; }

    public void setPath(String var){ path= var; }

    public String getOption(){ return option; }

    public void setOption(String var){ option= var; }

    public Map getParameters(){ return parameters.getOptions(); }

    public void setParameters(Map var){ parameters.setOptions(var); }
}
