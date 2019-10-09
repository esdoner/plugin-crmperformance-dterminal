package com.fr.plugin.performance.gather;

import com.fr.plugin.performance.handle.Connect2DB;

import java.sql.Statement;

/**
 * Created by yuwh on 2018/9/19
 * Description:none
 */
public abstract class Gather2DB implements AnalyzeGatherer{
    protected static String containerType;
    protected String sqlString;
    protected Statement stmt;
    protected Connect2DB conn;
    public static final boolean absFinal = true ;

    public Gather2DB(String var1)  {
        conn = new Connect2DB(var1);
        conn.createCon();
        stmt = conn.getStmt();
    }

    @Override
    public void setContainerType(String var1) { containerType = var1; }

    @Override
    public String getContainerType() { return containerType; }

    public  String  htmParser(String var2){
        String var3 =  var2.replaceAll("[\'\"]","\\\\'");
        return var3;
    }

    protected void conFinish(){conn.createCon(); }
}
