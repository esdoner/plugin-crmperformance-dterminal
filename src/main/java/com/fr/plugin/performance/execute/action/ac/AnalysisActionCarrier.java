package com.fr.plugin.performance.execute.action.ac;

import com.fr.plugin.performance.deploy.carrier.FormCarrier;
import com.fr.plugin.performance.deploy.unit.form.BookUnit;
import com.fr.plugin.performance.deploy.unit.form.UserUnit;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/1/15
 * Description:分析需要模板信息和用户信息
 */
public class AnalysisActionCarrier extends FormCarrier {
    public AnalysisActionCarrier(){ this.addUnit(new BookUnit()).addUnit(new UserUnit()); }
}
