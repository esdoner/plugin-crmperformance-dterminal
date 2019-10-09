package com.fr.plugin.performance.gather;

/**
 * Created by yuwh on 2018/9/19
 * Description:none
 */
public interface AnalyzeGatherer {

    void setContainerType(String var1);

    String getContainerType();

    boolean containerPrepare();

    void containerGather();

    String containerFeed();
}