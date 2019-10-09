package com.fr.plugin.performance.analysis.calc;

import com.fr.plugin.performance.deploy.carrier.FormCarrier;
import com.fr.plugin.performance.exception.BadCheckException;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019.04.15
 * Description:
 * 根据文件后缀名判断calculator
 *
 */
public class AnalysisCalculatorFactory {
    public AnalysisCalculatorFactory(){ }

    public static AnalysisCalculator getCreator(FormCarrier FC){
        String ext = FC.find("BookInformation","BookPath").toString().split("\\.")[1];
        switch(ext){
            case "cpt":
                return new AnalysisCalculator4Report(FC);
            case "frm":
                return new AnalysisCalculator4Form(FC);
            default:
                throw new BadCheckException("Wrong file extension:.cpt or .frm is expected");
        }
    }
}
