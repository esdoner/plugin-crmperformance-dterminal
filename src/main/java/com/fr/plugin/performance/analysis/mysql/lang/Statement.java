package com.fr.plugin.performance.analysis.mysql.lang;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/29
 * Description:none
 */
public class Statement {
    private String statement;
    private Field[] fields;

    Statement(){ }

    public void setStatement(String statement) { this.statement = statement; }

    public String getStatement() { return statement; }

    /**
     * @params
     * @return
     * @description:
     * 1.  通过解析sql获取结果字段的信息
     * 2. 这样获取的字段不带有详细信息
     * 3. 必须是select形式的sql，是最外层的字段信息
     */
    private void generateFields() {
        Field tmpField= null;
        if(statement!= null){

        }
    }

    private enum Type {
        SELECT(),
        SHOW(),
        CALL(),
        EXPLAIN(),
    }
}
