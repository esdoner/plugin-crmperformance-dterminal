package com.fr.plugin.performance.analysis.mysql.core.ep;

import java.util.HashMap;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/10/9
 * Description:none
 */
public class SelectFactory {
    public static AbstractSelect buildSelect(HashMap var){
        return new AbstractSelect(var) {
        };
    }
}
