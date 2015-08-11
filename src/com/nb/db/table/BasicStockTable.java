package com.nb.db.table;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.nb.stock.Stock;

import java.util.LinkedHashMap;

public class BasicStockTable extends AbstractTable{

    protected Stock stock;

    public BasicStockTable(Stock stock) {
        this.tableName = stock.getTableName();
        this.stock = stock;
        if(colInfo==null){
            colInfo = new LinkedHashMap<>();
        }
        colInfo.put("date","DATE");
        colInfo.put("open","VARCHAR(20)");
        colInfo.put("high","VARCHAR(20)");
        colInfo.put("low","VARCHAR(20)");
        colInfo.put("close","VARCHAR(20)");
        colInfo.put("volume","VARCHAR(20)");
        colInfo.put("adjust_close","VARCHAR(20)");
    }

}
