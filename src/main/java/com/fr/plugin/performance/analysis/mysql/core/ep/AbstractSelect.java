package com.fr.plugin.performance.analysis.mysql.core.ep;

import com.fr.plugin.performance.analysis.mysql.lang.Index;
import com.fr.plugin.performance.analysis.mysql.lang.Table;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/30
 * Description:
 * 1. 对于每行 explain 的结果作为一个 select 操作行
 * 2. 此类是 select 操作行的超类
 */
public abstract class AbstractSelect {
    private int treeOrder= -1;
    protected static String SelectType= "";
    private Table table;
    private JoinType joinType= JoinType.NULL;
    private Index[] possibleIndex;
    private Index[] index;
    private int keyLen;
    private String ref;
    private Long rows=0L;
    private ExtraHit[] extra;

}
