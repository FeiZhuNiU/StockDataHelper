package com.nb.stock;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/5/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.nb.db.DBConstants;

public class Stock {
    private String name;
    private int code;
    private String table_name;
    private CompanyInfo companyInfo;
    private Sector sector;

    public Stock(int code) {
        this.code = code;
        table_name = DBConstants.TABLE_SH_PRE + code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTableName() {
        return table_name;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", companyInfo=" + companyInfo +
                ", sector=" + sector +
                '}';
    }
}
