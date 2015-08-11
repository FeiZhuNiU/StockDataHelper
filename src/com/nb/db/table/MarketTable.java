package com.nb.db.table;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.nb.db.DBConstants;

import java.util.ArrayList;
import java.util.List;

public class MarketTable extends AbstractTable{

    public static final List<ColInfo> originCols;

    static {
        originCols = new ArrayList<>();
        originCols.add(new ColInfo("stock_code","INT"));
        originCols.add(new ColInfo("stock_name","VARCHAR(20)"));
    }

    public MarketTable() {

        this.tableName = DBConstants.TABLE_MARKET_NAME;
        colInfos = new ArrayList<>(originCols);

    }

}
