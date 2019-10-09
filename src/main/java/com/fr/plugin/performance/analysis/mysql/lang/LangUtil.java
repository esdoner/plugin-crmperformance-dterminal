package com.fr.plugin.performance.analysis.mysql.lang;

import com.fr.third.alibaba.druid.sql.SQLUtils;
import com.fr.third.alibaba.druid.util.JdbcConstants;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/29
 * Description:none
 */
public class LangUtil {
    public static final String JDBC_CONSTANT= JdbcConstants.MYSQL;

    public static String toUpperCase(String lang){
        return SQLUtils.format(lang, JDBC_CONSTANT, SQLUtils.DEFAULT_FORMAT_OPTION);
    }

    public static String toLowerCase(String lang){
        return SQLUtils.format(lang, JDBC_CONSTANT, SQLUtils.DEFAULT_LCASE_FORMAT_OPTION);
    }

    /**
     * TODO 这个保留注释的还没成功，druid可能有bug
     * @return
     */
    public static String keepCommentCase(String lang, boolean keepUpper){
            return !keepUpper? toLowerCase(lang): toUpperCase(lang);
    }

    /**
     * TODO 美化功能未完成，要不就不用druid，重新做个简单的能满足需求的语法树解析
     * @return
     * 0. 这个美化不需要什么自定义，就是认为最好的美化途径，要使用就遵循就好了
     * 1. 要把 AS 填充出来，别名之前不写 AS ，没有任何益处
     * 2. 使用 UpperCase 转换 MySql 所有保留词
     * 3. 引号：都用单引号—>反斜杠+单引号
     * 4. 外部嵌入公式暂不计划处理
     * 5. 注释都改成\/** something *\/
     */
    public static String beautify(String lang){
        return lang;
    }
}
