package com.eric.stockhelper;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/3/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.eric.stockhelper.internet.NetUtils;
import com.eric.stockhelper.stock.Stock;
import com.eric.stockhelper.db.DBUtils;

public class StockHelper {

    public static void main(String[] args) {

        DBUtils.connectDB();
        DBUtils.resetTables();

        for (int stockCode = 600000; stockCode < 600001; ++stockCode) {

            System.out.println("Process stock: " + stockCode);

            Stock stock = new Stock(stockCode);
            downloadDataToDB(stock);


//            DBUtils.addAndUpdateMA(stock, 5, "ma5", "VARCHAR(20)");
//            DBUtils.addAndUpdateMA(stock, 10, "ma10", "VARCHAR(20)");
//            DBUtils.addAndUpdateMA(stock, 20, "ma20", "VARCHAR(20)");
//            DBUtils.addAndUpdateMA(stock, 60, "ma60", "VARCHAR(20)");
//            DBUtils.addAndUpdateMA(stock, 120, "ma120", "VARCHAR(20)");
//            DBUtils.addAndUpdateMACD(stock);
        }

        DBUtils.disConnectDB();

    }

    private static void downloadDataToDB(Stock stock) {

        if (NetUtils.downloadData(stock.getCode())) {
            DBUtils.importStockDataToDB(stock);

        } else {
            System.out.println("no such stock");
        }
    }

}
