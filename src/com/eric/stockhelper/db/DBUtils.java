package com.eric.stockhelper.db;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/5/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/


import com.eric.stockhelper.db.table.*;
import com.eric.stockhelper.internet.NetUtils;
import com.eric.stockhelper.stock.Stock;
import com.eric.stockhelper.stock.index.AbstractIndex;
import com.eric.stockhelper.stock.index.Calculator;
import com.eric.stockhelper.stock.index.Ma;
import com.eric.stockhelper.stock.index.Macd;
import com.eric.stockhelper.stock.StockMetaData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
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

        List<ColInfo> colInfos = table.getColInfos();
        for (ColInfo colInfo : colInfos) {
            sql += (colInfo.getColName() + " " + colInfo.getColType() + ",");
        }
        sql = sql.substring(0, sql.length() - 1);

//        for (int i = 0; i < size; ++i) {
//            sql +=
//            if (i != size - 1)
//                sql += ",";
//        }

        sql += ");";
        return excute(sql);
    }

    private static boolean dropTable(String table) {
        String sql = "DROP TABLE " + table + ";";
        return excute(sql);
    }

    public static boolean insertData(String table, Map<String, String> dataMap) {
        if (dataMap != null && dataMap.size() != 0) {
            Set<Map.Entry<String, String>> entries = dataMap.entrySet();
            String sql = "INSERT INTO " + table + " (";
            for (Map.Entry<String, String> entry : entries) {
                sql += (entry.getKey() + ",");
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ") VALUES (";
            for (Map.Entry<String, String> entry : entries) {
                sql += (entry.getValue() + ",");
            }
            sql += ");";

            return excute(sql);
        }
        return false;
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
                excute(sql);

//                sql = "INSERT INTO " + TABLE_BIG_NAME + " VALUES (" + stockCode + ",";
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
        createTable(new MarketTable(DBConstants.TABLE_MARKET_NAME));
        createTable(new BigTable(DBConstants.TABLE_BIG_NAME));
//        createTable(DBConstants.TABLE_BIG_NAME, "code INT", "date DATE", "open VARCHAR(20)", "high VARCHAR(20)", "low VARCHAR(20)", "close VARCHAR(20)", "volume VARCHAR(20)", "adjust_close VARCHAR(20)");

    }

    public static void addColumn(String table, ColInfo colInfo) {
        dropColumn(table, colInfo.getColName());
        String sql = "ALTER TABLE " + table + " ADD " + colInfo.getColName() + " " + colInfo.getColType();
        excute(sql);
    }

    public static void dropColumn(String table, String col) {
        String sql = "ALTER TABLE " + table + " DROP COLUMN " + col;
        excute(sql);
    }

    public static void importStockDataToDB(Stock stock) {

        long startTime = System.currentTimeMillis();

        excute("INSERT INTO " + DBConstants.TABLE_MARKET_NAME + " (stock_code) VALUES (" + stock.getCode() + ");");
        createTable(new BasicStockTable(stock));
        importDataFromCsv(stock);

        long endTime = System.currentTimeMillis();

        System.out.println("Time consumed (insert data into db from " + stock.getCode() + ".scv): " + (endTime - startTime) / 1000.0 + "s");
    }


    public static List<StockMetaData> getHistory(Stock stock) {

        List<StockMetaData> ret = new ArrayList<>();
        String sql = "SELECT * FROM " + stock.getTableName();

        if (excute(sql)) {
            try {
                ResultSet resultSet = getStatement().getResultSet();
                while (resultSet.next()) {
                    Date date = resultSet.getDate(1);
                    double open = Double.parseDouble(resultSet.getString(2));
                    double high = Double.parseDouble(resultSet.getString(3));
                    double low = Double.parseDouble(resultSet.getString(4));
                    double close = Double.parseDouble(resultSet.getString(5));
                    long volume = Long.parseLong(resultSet.getString(6));
                    double adjust_close = Double.parseDouble(resultSet.getString(7));

                    StockMetaData metaData = new StockMetaData(stock, date, high, low, open, close, volume, adjust_close);
                    ret.add(metaData);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static void addAndUpdateIndex(AbstractIndex index){
        long startTime = System.currentTimeMillis();

        List<ColInfo> colInfos = index.getDbColInfos();
        for(ColInfo colInfo : colInfos){
            addColumn(index.getStock().getTableName(), colInfo);
        }
        List<AbstractIndex> historyIndex = Calculator.calHistoryIndex(getHistory(index.getStock()), index);

        updateTable(historyIndex);

        long endTime = System.currentTimeMillis();
        System.out.println("Time consumed (add and update "+index.getClass().getSimpleName() +" of " + index.getStock().getCode() + "): " + (endTime - startTime) / 1000.0 + "s");

    }

    private static void updateTable(List<AbstractIndex> historyIndex) {
        for(AbstractIndex index : historyIndex){
            Map<String,Double> indexValues = index.getIndexValues();
            String sql = "UPDATE " + index.getStock().getTableName() + " SET ";
            Set<Map.Entry<String,Double>> indexSet = indexValues.entrySet();
            for(Map.Entry<String,Double> entry : indexSet) {
                sql += (entry.getKey() + " = " + entry.getValue() + ", ");
            }
            sql = sql.substring(0,sql.length()-2);
            sql += " WHERE date = '" + index.getDate() + "'" ;
            excute(sql);
        }
    }
    public static void downloadDataToDB(Stock stock) {

        if (NetUtils.downloadData(stock.getCode())) {
            importStockDataToDB(stock);

        } else {
            System.out.println("no such stock");
        }
    }
}
