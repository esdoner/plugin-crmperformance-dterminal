package com.fr.plugin.performance.util;

import com.fr.stable.fun.impl.AbstractLocaleFinder;

/**
 * @author yuwh
 * time:2019/8/12
 * Description:none
 */
public class PerformanceLocaleFinder extends AbstractLocaleFinder {
    @Override
    public String find() {
        return "com/fr/plugin/performance/locale/dterminal";
    }
}
