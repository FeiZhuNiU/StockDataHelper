package com.nb.db.table;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import java.util.Map;

public abstract class AbstractTable implements Table{

    protected String tableName;

    protected Map<String,String> colInfo;

    @Override
    public Map<String, String> getColInfo() {
        return colInfo;
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}
