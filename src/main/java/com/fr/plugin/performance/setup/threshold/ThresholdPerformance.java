package com.fr.plugin.performance.setup.threshold;

import com.fr.plugin.performance.util.read.PropertiesCacheReader;

/**
 * Created by yuwh on 2018/11/23
 * Description:唯一属性就是onoffSwicth
 */
public final class ThresholdPerformance extends PrimaryThreshold {
    public ThresholdPerformance(){
        onoffKey = "Switch4PerformanceThreshold";
        onoffSwitch = Boolean.parseBoolean(PropertiesCacheReader.getInstance().readProperties(PATH,onoffKey));
        thd = createThresholdCalculator();
    }
}
