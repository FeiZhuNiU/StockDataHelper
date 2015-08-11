package com.nb.db.table;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

public class ColInfo {

    private String col_name;
    private String col_type;

    public ColInfo(String col_name, String col_type) {
        this.col_name = col_name;
        this.col_type = col_type;
    }

//    public Map.Entry<String,String> getColEntry(){
//        return new AbstractMap.SimpleEntry<String, String>(col_name,col_type);
//    }

    public String getColName() {
        return col_name;
    }

    public String getColType() {
        return col_type;
    }
}
