package com.nb.db;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/5/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/


import com.nb.db.table.BasicStockTable;
import com.nb.db.table.MarketTable;
import com.nb.db.table.Table;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DBUtils {


    private static Connection connection = null;
    private static Statement statement = null;

    public static Statement getStatement() {
        return statement;
    }

    public static boolean connectDB() {
        boolean ret = true;
        try {
            connection = DriverManager.getConnection(DBConstants.DB_URL);
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
     * ִ���κ�sql��䣬Ҳ����ǰ����֮һ��
     * ����ֵ�ǵ�һ������ı�����ʽ��
     * ����һ��ִ�н���ǲ�ѯ���ʱ������true������ͨ��getResultSet������ȡ�����
     * ����һ��ִ�н���Ǹ�������DDL���ʱ������false������ͨ��getUpdateCount������ȡ���µļ�¼����
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

    public static boolean createTable(Table table) {
        dropTable(table.getTableName());
        String sql = "CREATE TABLE " + table.getTableName() + " (";

        Set<Map.Entry<String,String>> entrySet = table.getColInfo().entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            sql += (entry.getKey() + " " + entry.getValue() + ",");
        }
        sql = sql.substring(0,sql.length()-1);

//        for (int i = 0; i < size; ++i) {
//            sql +=
//            if (i != size - 1)
//                sql += ",";
//        }

        sql += ");";
        return excute(sql);
    }

    public static boolean dropTable(String table) {
        String sql = "DROP TABLE " + table + ";";
        return excute(sql);
    }

    public static void importDataFromCsv(Stock stock) {

        String fileName = NetUtils.savePath_pre + stock.getCode() + NetUtils.savePath_post;

        File csvFile = new File(fileName);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String line = null;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] datas = line.split(",");
                if (datas.length != 7)
                    continue;
                String sql = "INSERT INTO " + stock.getTableName() + " VALUES (";
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
        createTable(new MarketTable());
//        createTable(DBConstants.TABLE_BIG, "code INT", "date DATE", "open VARCHAR(20)", "high VARCHAR(20)", "low VARCHAR(20)", "close VARCHAR(20)", "volume VARCHAR(20)", "adjust_close VARCHAR(20)");

    }

    public static void addColumn(String table, String col, String colInfo) {
        dropColumn(table, col);
        String sql = "ALTER TABLE " + table + " ADD " + col + " " + colInfo;
        excute(sql);
    }

    public static void dropColumn(String table, String col) {
        String sql = "ALTER TABLE " + table + " DROP COLUMN " + col;
        excute(sql);
    }

    public static void downloadStockDataToDB(Stock stock) {

        long startTime = System.currentTimeMillis();

        excute("INSERT INTO " + DBConstants.TABLE_MARKET_NAME + " (stock_code) VALUES (" + stock.getCode() + ");");

//        String tableName = DBConstants.TABLE_SH_PRE + String.valueOf(stock.getCode());

//        createTable(stock.getTableName(), "date DATE", "open VARCHAR(20)", "high VARCHAR(20)", "low VARCHAR(20)", "close VARCHAR(20)", "volume VARCHAR(20)", "adjust_close VARCHAR(20)");
        createTable(new BasicStockTable(stock));
        importDataFromCsv(stock);

        long endTime = System.currentTimeMillis();

        System.out.println("Time consumed (insert data into db from " + stock.getCode() + ".scv): " + (endTime - startTime) / 1000.0 + "s");
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

        addColumn(stock.getTableName(), DBConstants.MACD_COL_EMA12, DBConstants.MACD_COL_EMA12_TYPE);
        addColumn(stock.getTableName(), DBConstants.MACD_COL_EMA26, DBConstants.MACD_COL_EMA26_TYPE);
        addColumn(stock.getTableName(), DBConstants.MACD_COL_DIFF, DBConstants.MACD_COL_DIFF_TYPE);
        addColumn(stock.getTableName(), DBConstants.MACD_COL_DEA, DBConstants.MACD_COL_DEA_TYPE);
        addColumn(stock.getTableName(), DBConstants.MACD_COL_BAR, DBConstants.MACD_COL_BAR_TYPE);

        List<StockMetaData> lists = StockUtils.getHistory(stock);
        Map<Date, Macd> map = Calculator.calMacd(lists);

        for (Map.Entry<Date, Macd> entry : map.entrySet()) {

            String sql = "UPDATE " + stock.getTableName() + " SET " +
                    DBConstants.MACD_COL_EMA12 + " = " + entry.getValue().getEma12() + ", " +
                    DBConstants.MACD_COL_EMA26 + " = " + entry.getValue().getEma26() + ", " +
                    DBConstants.MACD_COL_DIFF + " = " + entry.getValue().getDiff() + ", " +
                    DBConstants.MACD_COL_DEA + " = " + entry.getValue().getDea() + ", " +
                    DBConstants.MACD_COL_BAR + " = " + entry.getValue().getBar() +
                    " WHERE date = '" + entry.getKey() + "';";
            excute(sql);
        }


        long endTime = System.currentTimeMillis();
        System.out.println("Time consumed (add and update MACD of " + stock.getCode() + "): " + (endTime - startTime) / 1000.0 + "s");

    }
}
