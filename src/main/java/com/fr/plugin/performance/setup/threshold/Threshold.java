package com.fr.plugin.performance.setup.threshold;

import com.fr.plugin.performance.judge.calculator.ThresholdCalculator;

import java.util.Map;

/**
 * Created by yuwh on 2018/11/23
 * Description:none
 * @author yuwh
 */
public interface Threshold {
    String PATH = "static/properties/threshold.properties";

    /** 载入阈值*/
    void loadThresholdIndex ();

    /** 切换开关*/
    void toggleSwitch();

    /** 设置和获取阈值*/
    void setThreshold(Map var1);

    String getThreshold(String var1);

    /** 创建阈值算子，使阈值和判断分离*/
    ThresholdCalculator createThresholdCalculator();
}
