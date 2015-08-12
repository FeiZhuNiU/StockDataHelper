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
import com.eric.stockhelper.stock.index.Ma;
import com.eric.stockhelper.stock.index.Macd;

public class StockHelper {

    public static void main(String[] args) {

        DBUtils.connectDB();
        DBUtils.resetTables();

        for (int stockCode = 600000; stockCode < 600001; ++stockCode) {

            System.out.println("Process stock: " + stockCode);

            Stock stock = new Stock(stockCode);
//            DBUtils.downloadDataToDB(stock);
//            Ma ma = new Ma(stock,null,5);
//            DBUtils.addAndUpdateIndex(ma,stock);
            Macd macd = new Macd(stock,null);
            DBUtils.addAndUpdateIndex(macd,stock);

        }

        DBUtils.disConnectDB();

    }



}
