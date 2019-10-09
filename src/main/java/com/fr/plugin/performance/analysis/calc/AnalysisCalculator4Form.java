package com.fr.plugin.performance.analysis.calc;

import com.fr.form.main.Form;
import com.fr.form.main.FormIO;
import com.fr.plugin.performance.deploy.carrier.FormCarrier;
import com.fr.stable.core.UUID;
import com.fr.web.core.FormSessionIDInfor;
import com.fr.web.core.WebContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/4/11
 * Description:表单分析计算
 */
public class AnalysisCalculator4Form extends AnalysisCalculator {
    private FormSessionIDInfor IDInformation;
    private Form form;

    public AnalysisCalculator4Form(FormCarrier FC){ super(FC); }

    @Override
    public void buildFileBook() {
        try {
            form= FormIO.readForm(String.valueOf(getFC().find("BookInformation", "BookPath")));
            IDInformation= new FormSessionIDInfor(form, String.valueOf(getFC().find("BookInformation", "BookPath")), new HashMap<>());
            IDInformation.buildWebContext(new WebContext("", "", ""));
            IDInformation.setSessionID(UUID.randomUUID().toString());
            super.setBook(form);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeBook() {
        long t1 = System.currentTimeMillis();
        form.executeElementCases(IDInformation, (Map)getFC().find("BookInformation", "BookParasHolder"));
        long t2= System.currentTimeMillis();
        super.setCostTime(t2- t1);
    }
}
