package com.fr.plugin.performance.analysis.mysql.core.ep;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/30
 * Description:none
 */
public enum JoinType {
    /**
     * 全表扫描
     */
    ALL("all", 1),
    /**
     * 全表扫描，但 使用某字段索引排序
     */
    INDEX("index", 2),
    /**
     * 范围索引
     */
    RANGE("range", 3),
    /**
     * TODO 未找到例子，但是官方文档里有这个项目
     */
    INDEX_SUBQUERY("index_subquery", 4),
    /**
     * TODO 未找到例子，但是官方文档里有这个项目
     */
    UNIQUE_SUBQUERY("unique_subquery", 5),
    /**
     * 多个可使用索引，使用合并索引
     */
    INDEX_MERGE("index_merge", 6),
    /**
     * 查询索引，非唯一索引
     */
    REF_OR_NULL("ref", 7),
    /**
     * 全文索引，分词索引，并不建议
     */
    FULLTEXT("fulltext", 8),
    /**
     * 查询索引，非唯一索引
     */
    REF("ref", 9),
    /**
     * 查询索引，唯一索引
     */
    EQ_REF("eq_ref", 10),
    /**
     * 使用唯一索引来查找常量
     */
    CONST("const", 11),
    /**
     * 使用唯一索引来查找常量
     */
    SYSTEM("system", 12),
    /**
     * 最乐观类型
     */
    NULL("null", 9999);

    private String name;
    private int priority;

    JoinType(String name, int priority){
        this.name= name;
        this.priority= priority;
    }

    public static JoinType findIgnoreCase(String name){
        return JoinType.valueOf(name.toUpperCase());
    }
}
