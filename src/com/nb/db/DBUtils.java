package com.nb.db;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/5/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/


import com.nb.stock.index.Calculator;
import com.nb.internet.NetUtils;
import com.nb.stock.Stock;
import com.nb.stock.StockMetaData;
import com.nb.stock.StockUtils;
import com.nb.stock.index.Macd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DBUtils {
    public static String dbUrl = "jdbc:mysql://localhost:3306/stockdb?user=lliyu&password=2222";
    public final static String TABLE_MARKET = "market";
    public final static String TABLE_SH_PRE = "sh";
    public final static String TABLE_BIG = "big_table";


    private static Connection connection = null;
    private static Statement statement = null;

    public static Statement getStatement() {
        return statement;
    }

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
     */
    public static boolean excute(String sql) {
        boolean ret;
        try {
            ret = statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            ret = false;
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

    public static boolean createTable(String table, String... colInfos) {
        dropTable(table);
        String sql = "CREATE TABLE " + table + " (";
        int size = colInfos.length;
        for (int i = 0; i < size; ++i) {
            sql += colInfos[i];
            if (i != size - 1)
                sql += ",";
        }
        sql += ");";
        return excute(sql);
    }

    public static boolean dropTable(String table) {
        String sql = "DROP TABLE " + table + ";";
        return excute(sql);
    }

    public static void importDataFromCsv(int stockCode, String tableName) {

        String fileName = NetUtils.savePath_pre + stockCode + NetUtils.savePath_post;

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
                for (int i = 0; i < 7; ++i) {
                    sql += ("'" + datas[i] + "'");
                    if (i != 6)
                        sql += ",";
                }
                sql += ");";
                DBUtils.excute(sql);

//                sql = "INSERT INTO " + TABLE_BIG + " VALUES (" + stockCode + ",";
//                for (int i = 0; i < 7; ++i) {
//                    sql += ("'" + datas[i] + "'");
//                    if (i != 6)
//                        sql += ",";
//                }
//                sql += ");";
//                DBUtils.excute(sql);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * reset following tables:
     * market
     * big_table
     */
    public static void resetTables() {
        createTable(TABLE_MARKET, "code INT", "name VARCHAR(20)");
        createTable(TABLE_BIG, "code INT", "date DATE", "open VARCHAR(20)", "high VARCHAR(20)", "low VARCHAR(20)", "close VARCHAR(20)", "volume VARCHAR(20)", "adjust_close VARCHAR(20)");
    }

    public static void insertIntoDB(Stock stock) {

        long startTime = System.currentTimeMillis();

        excute("INSERT INTO " + DBUtils.TABLE_MARKET + " (code) VALUES (" + stock.getCode() + ");");

        String tableName = TABLE_SH_PRE + String.valueOf(stock.getCode());

        createTable(tableName, "date DATE", "open VARCHAR(20)", "high VARCHAR(20)", "low VARCHAR(20)", "close VARCHAR(20)", "volumn VARCHAR(20)", "adjust_close VARCHAR(20)");

        importDataFromCsv(stock.getCode(), tableName);

        long endTime = System.currentTimeMillis();

        System.out.println("Time consumed (insert data into db from " + stock.getCode() + ".scv): " + (endTime - startTime) / 1000.0 + "s");
    }

    public static void addColumn(String table, String col, String colInfo) {
        dropColumn(table, col);
        String sql = "ALTER TABLE " + table + " ADD " + col + " " + colInfo;
        excute(sql);
        //SQL: update table xx set colInfo = value where code = and date = ;
    }

    public static void dropColumn(String table, String col) {
        String sql = "ALTER TABLE " + table + " DROP COLUMN " + col;
        excute(sql);
    }

    public static void addAndUpdateMA(Stock stock, int days, String col, String colInfo) {

        long startTime = System.currentTimeMillis();
        addColumn(stock.getTableName(), col, colInfo);

        List<StockMetaData> lists = StockUtils.getHistory(stock);
        Map<Date, Double> maMap = Calculator.calMA(lists, days);

        for (Map.Entry<Date, Double> entry : maMap.entrySet()) {

            String sql = "UPDATE " + stock.getTableName() + " SET " + col + " = " + entry.getValue() + " WHERE date = '" + entry.getKey() + "';";
            excute(sql);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time consumed (add and update MA" + days + "): " + (endTime - startTime) / 1000.0 + "s");
    }

    public static void addAndUpdateMACD(Stock stock) {
        long startTime = System.currentTimeMillis();

        addColumn(stock.getTableName(),"macd_ema12","VARCHAR(20)");
        addColumn(stock.getTableName(),"macd_ema26","VARCHAR(20)");
        addColumn(stock.getTableName(),"macd_diff","VARCHAR(20)");
        addColumn(stock.getTableName(),"macd_dea","VARCHAR(20)");
        addColumn(stock.getTableName(),"macd_bar","VARCHAR(20)");

        List<StockMetaData> lists = StockUtils.getHistory(stock);
        Map<Date, Macd> map = Calculator.calMacd(lists);
//        Iterator<Map.Entry<Date,Macd>> iterator = map.entrySet().iterator();
//        while(iterator.hasNext()){
//            Map.Entry<Date,Macd> entry = iterator.next();
//            System.out.println(entry.getKey()+ "    " + entry.getValue());
//        }
        for (Map.Entry<Date, Macd> entry : map.entrySet()) {

            String sql = "UPDATE " + stock.getTableName() + " SET " +
                    Macd.COL_EMA12 + " = " + entry.getValue().getEma12() + ", " +
                    Macd.COL_EMA26 + " = " + entry.getValue().getEma26() + ", " +
                    Macd.COL_DIFF + " = " + entry.getValue().getDiff() + ", " +
                    Macd.COL_DEA + " = " + entry.getValue().getDea() + ", " +
                    Macd.COL_BAR + " = " + entry.getValue().getBar() +
                    " WHERE date = '" + entry.getKey() + "';";
            excute(sql);
        }


        long endTime = System.currentTimeMillis();
        System.out.println("Time consumed (add and update MACD of " + stock.getCode() + "): " + (endTime - startTime) / 1000.0 + "s");

    }
}
