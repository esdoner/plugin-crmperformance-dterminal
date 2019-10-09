package com.fr.plugin.performance.analysis.calc;

import com.fr.io.TemplateWorkBookIO;
import com.fr.main.impl.WorkBook;
import com.fr.main.workbook.ResultWorkBook;
import com.fr.plugin.performance.deploy.carrier.FormCarrier;
import com.fr.report.poly.PolyWorkSheet;
import com.fr.stable.ViewActor;
import com.fr.stable.WriteActor;

import java.util.Map;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/4/11
 * Description:报表分析计算
 */
public class AnalysisCalculator4Report extends AnalysisCalculator {
    private WorkBook workBook;

    public AnalysisCalculator4Report(FormCarrier FC){ super(FC); }

    @Override
    public void buildFileBook() {
        try {
            workBook= (WorkBook) TemplateWorkBookIO.readTemplateWorkBook(String.valueOf(getFC().find("BookInformation", "BookPath")));
            super.setBook(workBook);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeBook() {
        long t1 = System.currentTimeMillis();
        if( workBook.getReport(0) instanceof PolyWorkSheet ) {
            ResultWorkBook exeResult = workBook.execute((Map)getFC().find("BookInformation", "BookParasHolder"), new ViewActor());
        } else {
            ResultWorkBook exeResult = workBook.execute((Map)getFC().find("BookInformation", "BookParasHolder"), new WriteActor());
        }
        long t2 = System.currentTimeMillis();
        super.setCostTime(t2- t1);
    }
}
