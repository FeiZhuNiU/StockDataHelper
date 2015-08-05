package com.nb.stock;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/5/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

public class Stock
{
    private String name;
    private String code;
    private CompanyInfo companyInfo;
    private Field field;

    public Stock(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
