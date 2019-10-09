package com.fr.plugin.performance.analysis.mysql.core;

import com.fr.third.alibaba.druid.sql.SQLUtils;
import com.fr.third.alibaba.druid.sql.ast.SQLStatement;
import com.fr.third.alibaba.druid.sql.parser.ParserException;
import com.fr.third.alibaba.druid.sql.parser.SQLParserUtils;
import com.fr.third.alibaba.druid.sql.parser.SQLStatementParser;
import com.fr.third.alibaba.druid.util.JdbcConstants;

import java.util.List;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/29
 * Description:none
 */
public class LanguageBuilder {
    LanguageBuilder(){}

    LanguageBuilder(StringBuilder var0){

    }

    public static void main(String[] args) {
        String dbType = JdbcConstants.MYSQL;
        String sql= "#hdauhda\n" +
                "Select * \n" +
                "/**hduahd*/\n" +
                "From cust_company cc\n" +
                "-- huhaujdaijdiajd\n" +
                "Where com_id<>'1'";
        System.out.println(sql);
        try {
            SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType);
            parser.setKeepComments(true);
            List<SQLStatement> statementList = parser.parseStatementList();
            System.out.println(SQLUtils.toSQLString(statementList, dbType, null, null));
        } catch (ParserException var6) {
            System.out.println(sql);
        }
    }
}
