package com.fr.plugin.performance.execute.action.ac;

import com.fr.plugin.performance.deploy.carrier.FormCarrier;
import com.fr.plugin.performance.deploy.unit.form.OptionUnit;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/6
 * Description:设置需要额外选项信息
 */
public class SetupActionCarrier extends FormCarrier {
    public SetupActionCarrier(){ this.addUnit(new OptionUnit()); }
}
