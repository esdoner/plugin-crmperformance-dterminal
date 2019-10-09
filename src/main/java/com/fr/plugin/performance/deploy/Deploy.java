package com.fr.plugin.performance.deploy;

import com.fr.plugin.performance.deploy.unit.Unit;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019.08.01
 * Description: 装配, 部署，为各模型分配资源单元
 */
public interface Deploy {
    /**
     * 添加单元
     * @return
     * @param unit
     */
    Deploy addUnit(Unit unit);

    /**
     * 删除单元
     * @return
     * @param unit
     */
    Deploy removeUnit(Unit unit);

    /**
     * 单元数量获取
     * @return
     */
    int getUnitNumber();

    /**
     * 单元质量获取
     * @return
     */
    int getQuality();

    /**
     * 根据UnitID获取Unit
     * @param id
     * @return
     */
    Unit getUnit(String id);
}
