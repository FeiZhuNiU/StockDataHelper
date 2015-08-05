package com.eric;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/5/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/


import java.sql.*;

public class DBUtils {
    public static String dbUrl = "jdbc:mysql://localhost:3306/testdb?user=lliyu&password=2222";

    private static Connection connection = null;
    private static Statement statement = null;

    public static boolean connectDB() {
        boolean ret = true;
        try {
            connection = DriverManager.getConnection(dbUrl);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }

//    public static ResultSet executeQuery(String sql) {
//
//        ResultSet result = null;
//        try {
//            result = statement.executeQuery(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
    /**
     * 执行任何sql语句，也就是前两者之一。
     * 返回值是第一个结果的表现形式。
     * 当第一个执行结果是查询语句时，返回true，可以通过getResultSet方法获取结果；
     * 当第一个执行结果是更新语句或DDL语句时，返回false，可以通过getUpdateCount方法获取更新的记录数量
     * */
    public static boolean excute(String sql){
        boolean ret;
        try {
            ret=statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            ret =false;
        }
        return ret;
    }


    public static boolean disConnectDB() {
        boolean ret = true;
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                ret = false;
            }
        }
        return ret;
    }

    public static boolean createTable(String table, String... colInfos){
        String sql = "CREATE TABLE "+ table + " (";
        int size = colInfos.length;
        for(int i = 0 ; i < size; ++i){
            sql += colInfos[i];
            if(i!=size-1)
                sql+=",";
        }
        sql += ");";
        return excute(sql);
    }

    public static boolean dropTable(String table)
    {
        String sql = "DROP TABLE "+ table+";";
        return excute(sql);
    }

}
