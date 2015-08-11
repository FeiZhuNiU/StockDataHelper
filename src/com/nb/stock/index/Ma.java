package com.nb.stock.index;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.nb.stock.StockMetaData;

import java.util.List;

public class Ma {

    private int days;
    private double ma;

    public Ma(int days) {
        this.days = days;
    }

    public void setMA(List<StockMetaData> list) {
        if (list == null || list.size() != days) {
            System.out.println("wrong days");
        }
        double sum = 0.0;
        for (int i = 0; i < days; ++i) {
            sum += list.get(i).getAdjust_close();
        }
        ma = sum /= days;
    }

}
