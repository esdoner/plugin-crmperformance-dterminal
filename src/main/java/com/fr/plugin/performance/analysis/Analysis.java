package com.fr.plugin.performance.analysis;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019.08.01
 * Description:分析单元需要尽量实现这个接口
 */
public interface Analysis {
    /**
     * 开始
     */
    void start();

    /**
     * 中止
     * @param var1
     */
    void Interrupt(int var1);

    /**
     * 结束
     */
    void stop();
}