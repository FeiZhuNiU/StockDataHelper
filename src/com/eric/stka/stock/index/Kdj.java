package com.eric.stka.stock.index;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/12/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.eric.stka.stock.Stock;
import com.eric.stka.stock.StockMetaData;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Kdj extends AbstractIndex {


    private int param1;
    private int param2;
    private int param3;

    public int getParam1() {
        return param1;
    }

    public int getParam2() {
        return param2;
    }

    public int getParam3() {
        return param3;
    }

    private double k;
    private double d;
    private double j;

    public double getK() {
        return k;
    }

    public double getD() {
        return d;
    }

    public double getJ() {
        return j;
    }

    public Kdj(Stock stock, Date date, int param1, int param2, int param3) {
        super(stock, date);

    }

    @Override
    void setDBColInfo() {

    }

    @Override
    public Map<String, Double> getIndexValues() {
        return null;
    }

    @Override
    List<AbstractIndex> calHistoryIndex(List<StockMetaData> metaDatas) {
        return null;
    }
}
