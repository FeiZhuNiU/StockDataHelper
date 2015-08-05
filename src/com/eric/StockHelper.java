package com.eric;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/3/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class StockHelper {

    public static void main(String[] args) {

        DBUtils.connectDB();
        DBUtils.dropTable("Stock");
        DBUtils.createTable("Stock", "code INT", "name VARCHAR(20)");

        for (int stockNum = 600000; stockNum < 600010; ++stockNum) {

            System.out.println("current stock: " + stockNum);

            processStockNum(stockNum);

        }

        DBUtils.disConnectDB();

    }

    private static void processStockNum(int stockNum) {
        long startTime = System.currentTimeMillis();

        String fileName = "D:/stock/" + stockNum + ".csv";

        if (Utils.downloadData(Utils.url_pre + stockNum + Utils.url_post, fileName)) {
            DBUtils.excute("INSERT INTO Stock (code) VALUES (" + stockNum + ");");
            String tableName = "SH"+String.valueOf(stockNum);
            DBUtils.dropTable(tableName);
            DBUtils.createTable(tableName, "date VARCHAR(20)", "open VARCHAR(20)", "high VARCHAR(20)", "low VARCHAR(20)", "close VARCHAR(20)", "volumn VARCHAR(20)", "adjust_close VARCHAR(20)");
            csvToDB(tableName, fileName);

            long endTime = System.currentTimeMillis();

            System.out.println("spend " +(endTime-startTime)/1000.0 + "s" );

        }
        else{
            System.out.println("no such stock");
        }
    }

    private static void csvToDB(String tableName, String fileName) {
        File csvFile = new File(fileName);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String line = null;
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

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
