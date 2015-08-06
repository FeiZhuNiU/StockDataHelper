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
import com.nb.stock.Stock;

public class StockHelper {

    public static void main(String[] args) {

        DBUtils.connectDB();
        DBUtils.resetTables();

        for (int stockNum = 600000; stockNum < 600001; ++stockNum) {

            System.out.println("Process stock: " + stockNum);

            Stock stock = new Stock(stockNum);
            processStock(stock);

            DBUtils.addAndUpdateMA(stock, 5, "ma5", "VARCHAR(20)");

        }

        DBUtils.disConnectDB();

    }

    private static void processStock(Stock stock) {

        if (Utils.downloadData(stock.getCode())) {
            DBUtils.insertIntoDB(stock);

        } else {
            System.out.println("no such stock");
        }
    }

}
