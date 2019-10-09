package com.fr.plugin.performance.analysis.mysql.lang;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/29
 * Description:none
 */
public class Table {
    private String name;
    private String alias;
    private String charset;
    private Schema Schema;
    private Field[] fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public com.fr.plugin.performance.analysis.mysql.lang.Schema getSchema() {
        return Schema;
    }

    public void setSchema(com.fr.plugin.performance.analysis.mysql.lang.Schema schema) {
        Schema = schema;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }
}
