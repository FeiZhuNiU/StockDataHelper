package com.nb.db.table;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.nb.stock.Stock;

import java.util.ArrayList;
import java.util.List;

public class BasicStockTable extends AbstractTable{

    public static final List<ColInfo> originCols;

    static {
        originCols = new ArrayList<>();
        originCols.add(new ColInfo("date", "DATE"));
        originCols.add(new ColInfo("open", "VARCHAR(20)"));
        originCols.add(new ColInfo("high", "VARCHAR(20)"));
        originCols.add(new ColInfo("low", "VARCHAR(20)"));
        originCols.add(new ColInfo("close", "VARCHAR(20)"));
        originCols.add(new ColInfo("volume", "VARCHAR(20)"));
        originCols.add(new ColInfo("adjust_close", "VARCHAR(20)"));
    }

    private Stock stock;

    public BasicStockTable(Stock stock) {

        this.tableName = stock.getTableName();
        this.stock = stock;

        colInfos = new ArrayList<>(originCols);
    }

    public Stock getStock() {
        return stock;
    }
}
