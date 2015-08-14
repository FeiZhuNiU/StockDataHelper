package com.eric.stka.db.table;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import java.util.ArrayList;
import java.util.List;

public class MarketTable extends AbstractTable {

    public static final List<ColInfo> originCols = new ArrayList<>();

    static {
        originCols.add(new ColInfo("stock_code", "INT"));
        originCols.add(new ColInfo("stock_name", "VARCHAR(20)"));
    }

    public MarketTable(String tableName) {
        this.tableName = tableName;
        colInfos = new ArrayList<>(originCols);
    }

}
