package com.fr.plugin.performance.deploy.unit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/7
 * Description:none
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeployUnit {
    String type();
}
