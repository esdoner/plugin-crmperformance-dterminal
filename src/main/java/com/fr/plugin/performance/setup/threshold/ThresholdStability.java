package com.fr.plugin.performance.setup.threshold;

import com.fr.plugin.performance.util.read.PropertiesCacheReader;

/**
 * Created by yuwh on 2018/12/4
 * Description:none
 */
public final class ThresholdStability extends PrimaryThreshold {
    public ThresholdStability(){
        onoffKey = "Switch4StabilityThreshold";
        onoffSwitch = Boolean.parseBoolean(PropertiesCacheReader.getInstance().readProperties(PATH,onoffKey));
        thd = createThresholdCalculator();
    }
}
