package com.nb.db;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/5/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;

public class DBUtils {
    public static String dbUrl = "jdbc:mysql://localhost:3306/testdb?user=lliyu&password=2222";

    private static Connection connection = null;
    private static Statement statement = null;

    public final static String TABLE_STOCK = "Stock";

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
        dropTable(table);
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

    public static void importDataFromCsv(int stockNum, String tableName, String fileName) {
        File csvFile = new File(fileName);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String line = null;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] datas = line.split(",");
                if (datas.length != 7)
                    continue;
                String sql = "INSERT INTO " + tableName + " VALUES (";
                for(int i = 0 ; i < 7 ; ++i){
                    sql += ("'" + datas[i] + "'");
                    if(i != 6)
                        sql+=",";
                }
                sql+=");";
                DBUtils.excute(sql);

                sql = "INSERT INTO demotable VALUES (" + stockNum + ",";
                for(int i = 0 ; i < 7 ; ++i){
                    sql += ("'" + datas[i] + "'");
                    if(i != 6)
                        sql+=",";
                }
                sql+=");";
                DBUtils.excute(sql);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
