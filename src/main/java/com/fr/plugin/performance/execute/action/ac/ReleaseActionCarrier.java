package com.fr.plugin.performance.execute.action.ac;

import com.fr.plugin.performance.deploy.carrier.FormCarrier;
import com.fr.plugin.performance.deploy.unit.form.BookUnit;
import com.fr.plugin.performance.deploy.unit.form.OptionUnit;
import com.fr.plugin.performance.deploy.unit.form.UserUnit;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/1/15
 * Description:更新发布需要模板信息，用户信息，额外选项信息
 */
public class ReleaseActionCarrier extends FormCarrier {
    public ReleaseActionCarrier(){ this.addUnit(new UserUnit()).addUnit(new BookUnit()).addUnit(new OptionUnit()); }
}
