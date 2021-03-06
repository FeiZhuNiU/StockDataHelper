package com.eric.stka.db.table;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/


import com.eric.stka.stock.Stock;

import java.util.ArrayList;
import java.util.List;

public class BigTable extends AbstractTable {

    public static List<ColInfo> originCols = new ArrayList<>();

    static {
        originCols.add(new ColInfo("code", "INT"));
        originCols.addAll(new BasicStockTable(new Stock(0)).getColInfos());
    }

    public BigTable(String table_name) {
        tableName = table_name;
        colInfos = new ArrayList<>(originCols);
    }
}
