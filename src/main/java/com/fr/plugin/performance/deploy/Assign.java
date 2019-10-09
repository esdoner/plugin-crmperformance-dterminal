package com.fr.plugin.performance.deploy;

import java.util.Map;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/8
 * Description: 指派，为已有的容纳物分配内容
 */
public interface Assign {
    /**
     * 指派
     * @param parts
     */
    void assign(Map parts);

    /**
     * 查找内容
     * @param alias1
     * @param alias2
     * @return
     */
    Object find(String alias1, String alias2);
}
