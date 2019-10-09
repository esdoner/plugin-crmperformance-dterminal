package com.fr.plugin.performance.deploy.unit;

import java.util.UUID;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/6
 * Description:none
 */
@DeployUnit(type= "Super")
public abstract class Unit {
    private StringBuffer unitID;

    public Unit(){
        unitID= new StringBuffer(this.getClass().getSimpleName());
        String uuid = UUID.randomUUID().toString();
        unitID.append("@").append(uuid.replace("-",""));
    }

    public String getID(){
        return String.valueOf(unitID);
    }
}
