package com.fr.plugin.performance.analysis.mysql.lang;

import com.fr.plugin.performance.analysis.mysql.core.IgnoreCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/30
 * Description:none
 */
public class Index {
    private String name;
    private List fields;
    private Type type= Type.NULL;
    private Method method= Method.NULL;;

    public Index(){}

    public Index(String name){ this.name= name; }

    public void initFields(Field[] fields){
        if(fields== null){
            this.fields= new ArrayList();
        }
        Arrays.stream(fields).forEach(f->{
            this.fields.add(f);
        });
    }

    private enum Type implements IgnoreCase<Type> {
        /**
         * 全文索引，带分词的，消耗比较大，特别是为已有记录的表添加新的列索引
         */
        FULLTEXT(),
        /**
         * 常用
         */
        NORMAL(),
        /**
         * TODO 未理解
         */
        SPATIAL(),
        /**
         * 唯一索引，除聚集索引之外的自建唯一性索引
         */
        UNIQUE(),
        /**
         * NULL
         */
        NULL;

        Type(){}
    }

    /**
     * BTree 和 Hash 的区别
     * https://dev.mysql.com/doc/refman/5.7/en/index-btree-hash.html
     */
    private enum Method implements IgnoreCase<Method> {
        /**
         * --
         */
        BTREE(),
        /**
         * --
         */
        HASH(),
        /**
         * --
         */
        NULL;

        Method(){}
    }
}
