package com.fr.plugin.performance.deploy.unit.form;

import com.fr.plugin.performance.deploy.unit.DeployUnit;
import com.fr.plugin.performance.deploy.unit.Unit;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/6
 * Description:none
 */
@DeployUnit(type= "StandardForm")
@Form(name= "UserInformation", size= 2)
public final class UserUnit extends Unit {
    @Input(name= "UserID", accessClazz= String.class)
    private String userName= null;
    @Input(name= "UserPassword", accessClazz= String.class)
    private String password= null;

    public String getUserName(){ return userName; }

    public void setUserName(String var){ userName= var; }

    public String getPassword(){ return password; }

    public void setPassword(String var){ password= var; }
}
