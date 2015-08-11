package com.nb.db.table;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.nb.db.DBConstants;

import java.util.LinkedHashMap;

public class MarketTable extends AbstractTable{

    public MarketTable() {
        this.tableName = DBConstants.TABLE_MARKET_NAME;
        if(colInfo == null){
            colInfo = new LinkedHashMap<>();
        }
        colInfo.put("stock_code","INT");
        colInfo.put("stock_name","VARCHAR(20)");
    }

}
