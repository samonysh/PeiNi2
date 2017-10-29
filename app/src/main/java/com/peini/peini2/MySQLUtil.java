package com.peini.peini2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/8/22.
 */

public class MySQLUtil {
    public static Connection openConnection(String url, String user,String password){
        Connection conn=null;
        try {
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(url, user, password);
        }catch (ClassNotFoundException e) {
            conn = null;
        }catch (SQLException e){
            conn = null;
        }
        return conn;
    }
}
