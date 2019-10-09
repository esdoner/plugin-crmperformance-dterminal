package com.fr.plugin.performance.deploy.unit.form;

import com.fr.plugin.performance.deploy.unit.DeployUnit;
import com.fr.plugin.performance.deploy.unit.Unit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/6
 * Description:none
 */
@DeployUnit(type= "StandardForm")
@Form(name= "OptionInformation", size= 2)
public class OptionUnit extends Unit {
    @Input(name= "OptionName", accessClazz= String.class)
    private String name= null;
    @Input(name= "OptionMap", accessClazz= Map.class)
    private Map options= new HashMap<String, Object>();

    public String getName(){ return name; }

    public void setName(String var){ name= var; }

    public Object getOption(String key){ return options.get(key); }

    public void setOption(String key, Object value){ options.put(key, value); }

    public Map getOptions(){ return options; }

    public void setOptions(Map var){ options= var; }
}
