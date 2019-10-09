package com.fr.plugin.performance.execute.action;

/**
 * @author yuwh
 * time:2019/8/14
 * Description:none
 */
public interface Executable {
    /**
     * 直接得到执行结果
     * @param objects
     * @return
     */
    String fetchResult(Object[] objects);
}

