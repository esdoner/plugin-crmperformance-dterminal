package com.fr.plugin.performance.analysis.calc;

import com.fr.base.io.IOFile;
import com.fr.plugin.performance.analysis.pre.BaseAnalysis;
import com.fr.plugin.performance.deploy.carrier.FormCarrier;

import java.util.Map;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019.08.01
 * Description:none
 */
public abstract class AnalysisCalculator<T extends IOFile>{
    private T book;
    private FormCarrier FC;
    private long costTime;

    public AnalysisCalculator(FormCarrier FC){ this.FC= FC; }
    /**
     * 创建 book 的过程，可能是 IOFile 的子类 Form 或者 WorkBook
     * @throws Exception
     */
    public abstract void buildFileBook();

    /**
     * 执行 book 的过程，分析总以执行一遍开始
     */
    public abstract void executeBook();


    public BaseAnalysis buildAnalysis(){
        BaseAnalysis var1= new BaseAnalysis(this.book, FC, costTime);
        return var1;
    }

    protected void setBook(T var){ this.book= var; }

    protected void setCostTime(long var){ this.costTime= var; }

    protected FormCarrier getFC(){ return FC; }
}
