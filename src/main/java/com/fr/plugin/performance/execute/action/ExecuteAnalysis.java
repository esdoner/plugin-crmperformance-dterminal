package com.fr.plugin.performance.execute.action;

import com.fr.plugin.performance.analysis.calc.AnalysisCalculator;
import com.fr.plugin.performance.analysis.calc.AnalysisCalculatorFactory;
import com.fr.plugin.performance.analysis.pre.BaseAnalysis;
import com.fr.plugin.performance.deploy.carrier.FormCarrier;
import com.fr.plugin.performance.execute.action.ac.AnalysisActionCarrier;
import com.fr.plugin.performance.util.read.URLReader;

import java.util.HashMap;
import java.util.Map;

import static com.fr.plugin.performance.util.read.EscapeStrReader.unescape;

/**
 * @author yuwh
 * @version 1.0.0
 * Created by yuwh on 18.7.29
 * Description: 性能检查模块入口
 */
public class ExecuteAnalysis implements Executable{
    private FormCarrier AAC= new AnalysisActionCarrier();

    /**
     * 1.组织Carrier
     * 2.根据IOFile的类型执行book并且创建分析
     * 3.组织并返回执行以及分析的结果
     * @param objects
     * @return
     */
    @Override
    public synchronized String fetchResult(Object[] objects){
        Map paras= new HashMap();
        paras.put("BookPath", unescape(objects[0].toString()));
        paras.put("BookParasHolder", new URLReader(unescape(objects[1].toString())).getParas());
        paras.put("UserID", unescape(objects[2].toString()));
        AAC.assign(paras);
        AnalysisCalculator AC= AnalysisCalculatorFactory.getCreator(AAC);
        AC.buildFileBook();
        AC.executeBook();
        BaseAnalysis var5 = AC.buildAnalysis();
        var5.start();
        var5.stop();

        return var5.ResultReport;
    }
}
