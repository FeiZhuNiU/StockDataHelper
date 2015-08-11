package com.eric.stockhelper.db.table;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import java.util.List;

public abstract class AbstractTable implements Table{

    protected String tableName;

    protected List<ColInfo> colInfos;

    @Override
    public List<ColInfo> getColInfos() {
        return colInfos;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

}
