package com.nb;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/3/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.nb.db.DBUtils;
import com.nb.internet.NetUtils;
import com.nb.stock.Stock;

public class StockHelper {

    public static void main(String[] args) {

        DBUtils.connectDB();
        DBUtils.resetTables();

        for (int stockCode = 600000; stockCode < 600001; ++stockCode) {

            System.out.println("Process stock: " + stockCode);

            Stock stock = new Stock(stockCode);
            downloadDataToDB(stock);
            DBUtils.addAndUpdateMACD(stock);

//            DBUtils.addAndUpdateMA(stock, 5, "ma5", "VARCHAR(20)");
//            DBUtils.addAndUpdateMA(stock, 10, "ma10", "VARCHAR(20)");
//            DBUtils.addAndUpdateMA(stock, 20, "ma20", "VARCHAR(20)");
//            DBUtils.addAndUpdateMA(stock, 60, "ma60", "VARCHAR(20)");
//            DBUtils.addAndUpdateMA(stock, 120, "ma120", "VARCHAR(20)");

        }

        DBUtils.disConnectDB();

    }

    private static void downloadDataToDB(Stock stock) {

        if (NetUtils.downloadData(stock.getCode())) {
            DBUtils.insertStockDataToDB(stock);

        } else {
            System.out.println("no such stock");
        }
    }

}
