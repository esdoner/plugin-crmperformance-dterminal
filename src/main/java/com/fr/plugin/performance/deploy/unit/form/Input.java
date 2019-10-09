package com.fr.plugin.performance.deploy.unit.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/5
 * Description:none
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Input {
    String name();

    Class accessClazz();
}
