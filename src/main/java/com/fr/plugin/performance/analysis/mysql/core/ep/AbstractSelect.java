package com.fr.plugin.performance.analysis.mysql.core.ep;

import com.fr.plugin.performance.analysis.mysql.lang.Index;
import com.fr.plugin.performance.analysis.mysql.lang.Table;

import java.util.HashMap;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/30
 * Description:
 * 1. 对于每行 explain 的结果作为一个 select 操作行
 * 2. 此类是 select 操作行的超类
 */
public class AbstractSelect {
    private int treeOrder= 0;
    private Table table;
    private JoinType joinType= JoinType.NULL;
    private Index[] possibleIndex;
    private Index[] index;
    private int keyLen;
    private String ref;
    private Long rows;
    private ExtraHit[] extra;

    public AbstractSelect(){}

    public AbstractSelect(HashMap var){
        this.treeOrder= Integer.valueOf(var.get("id").toString());
        this.generateTable(var.get("table").toString());
        this.joinType= JoinType.findIgnoreCase(var.get("type").toString());
        /** TODO possibleIndex and index
         * */
        this.keyLen= Integer.valueOf(var.get("key_len").toString());
        this.ref= var.get("ref").toString();
        this.rows= Long.valueOf(var.get("rows").toString());
        /** TODO extra
         * */
    }

    private void generateTable(String var){
        /**
         * TODO something
         */
        this.table= new Table();
        this.table.setName(var);
    }

    public int getTreeOrder(){ return this.treeOrder; }

    public Table getTable(){ return this.table;}
}
