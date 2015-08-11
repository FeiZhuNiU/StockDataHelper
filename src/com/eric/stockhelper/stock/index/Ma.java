package com.eric.stockhelper.stock.index;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.eric.stockhelper.stock.StockMetaData;

import java.util.List;

public class Ma {

    private int days;
    private double maValue;

    public Ma(int days) {
        this.days = days;
    }

    public void setMA(List<StockMetaData> list) {
        if (list == null || list.size() != days) {
            System.out.println("history data provided for calculating MA is not correct!");
            return;
        }
        double sum = 0.0;
        for (int i = 0; i < days; ++i) {
            sum += list.get(i).getAdjust_close();
        }
        maValue = Calculator.cutDecimal(sum / days, 3);
    }

    public double getMaValue() {
        return maValue;
    }

    public int getDays() {
        return days;
    }

    @Override
    public String toString() {
        return "Ma{" +
                "days=" + days +
                ", maValue=" + maValue +
                '}';
    }
}
