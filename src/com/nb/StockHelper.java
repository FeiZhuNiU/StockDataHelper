package com.nb;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/3/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.nb.db.DBUtils;
import com.nb.internet.Utils;

public class StockHelper {

    public static void main(String[] args) {

        DBUtils.connectDB();
//        DBUtils.dropTable("Stock");
        DBUtils.createTable("Stock", "code INT", "name VARCHAR(20)");
        DBUtils.createTable("demotable","code VARCHAR(10)","date VARCHAR(20)", "open VARCHAR(20)", "high VARCHAR(20)", "low VARCHAR(20)", "close VARCHAR(20)", "volumn VARCHAR(20)", "adjust_close VARCHAR(20)");

        for (int stockNum = 600000; stockNum < 600040; ++stockNum) {

            System.out.println("current stock: " + stockNum);

            processStockNum(stockNum);

        }

        DBUtils.disConnectDB();

    }

    private static void processStockNum(int stockNum) {
        
        long startTime = System.currentTimeMillis();

        String fileName = "D:/stock/" + stockNum + ".csv";

        if (Utils.downloadData(String.valueOf(stockNum), fileName)) {

            DBUtils.excute("INSERT INTO Stock (code) VALUES (" + stockNum + ");");

            String tableName = "SH"+String.valueOf(stockNum);
//            DBUtils.dropTable(tableName);
            DBUtils.createTable(tableName, "date VARCHAR(20)", "open VARCHAR(20)", "high VARCHAR(20)", "low VARCHAR(20)", "close VARCHAR(20)", "volumn VARCHAR(20)", "adjust_close VARCHAR(20)");

            DBUtils.importDataFromCsv(stockNum,tableName, fileName);

            long endTime = System.currentTimeMillis();

            System.out.println("spend " +(endTime-startTime)/1000.0 + "s" );

        }
        else{
            System.out.println("no such stock");
        }
    }


}
