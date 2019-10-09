package com.fr.plugin.performance.handle;

import com.fr.file.DatasourceManager;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by yuwh on 2018/9/27
 * Description:none
 */
public final class Connect2DB {
    private com.fr.data.impl.Connection _conn = null;
    private Connection _con = null;
    private Statement _stmt = null;
    private String _dbname;

    public Connect2DB(String dbname){
        this._dbname = dbname;
    }

    public boolean createCon(){
        _conn = DatasourceManager.getInstance().getConnection(_dbname);
        if(_conn != null){
            try {
                _con = _conn.createConnection();
                _stmt = _con.createStatement();
                return true;
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(_dbname+" conn error");
            return false;
        }
        return false;
    }

    public boolean closeCon(){
        try {
            if (_stmt != null) {
                _stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (_con != null) {
                _con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public ResultSet executeDQL(String sql) {
        try {
            return _stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object executeDML(String sql) {
        try {
            return _stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Object executeDDL(String var1) { return null; }

    public Object executeDCL(String var4) { return null; }

    public Statement getStmt(){ return this._stmt;}

    public String getDriverName(){ return _conn.getDriver(); }
}
