package com.eric.stka;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/3/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.eric.stka.stock.Stock;
import com.eric.stka.db.DBUtils;
import com.eric.stka.stock.index.Ma;
import com.eric.stka.stock.index.Macd;

public class StockAnalyzer {

    public static void main(String[] args) throws Exception {

        DBUtils.connectDB();
        DBUtils.resetTables();

        for (int stockCode = 600000; stockCode < 600001; ++stockCode) {

            System.out.println("Process stock: " + stockCode);

            Stock stock = new Stock(stockCode);
            DBUtils.downloadDataToDB(stock);
            Ma ma = new Ma(stock, null, 5);
            DBUtils.addAndUpdateIndex(ma);
            Macd macd = new Macd(stock, null, 12, 26, 9);
            DBUtils.addAndUpdateIndex(macd);

        }

        DBUtils.disConnectDB();
    }
}
