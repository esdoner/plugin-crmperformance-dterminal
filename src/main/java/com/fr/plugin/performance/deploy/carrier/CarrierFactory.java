package com.fr.plugin.performance.deploy.carrier;

import com.fr.plugin.performance.exception.ValidityException;
import com.fr.plugin.performance.util.read.ClassInfoReader;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/01/21
 * Description:none
 */
public class CarrierFactory {

    /**
    * @params [type]
    * @return com.fr.plugin.performance.executor.carrier.FormCarrier
    * @description: 在包内查找是否有对应的carrier,type不分大小写
    */
    public FormCarrier getCarrier(String type) throws ValidityException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(type == null) {
            throw new ValidityException("FormCarrier Type Required");
        }
        StringBuilder prefix = new StringBuilder();
        prefix.append(ClassInfoReader.getPackName(this.getClass(), false))
                .append(".")
                .append(type.substring(0, 1).toUpperCase())
                .append(type.substring(1).toLowerCase())
                .append("FormCarrier");
        Class clazz;
        Object carrier;

        clazz = Class.forName(prefix.toString());
        carrier =clazz.newInstance();

        return (FormCarrier)carrier;
    }
}
