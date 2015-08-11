package com.nb.stock.index;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/7/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.nb.stock.StockMetaData;

public class Macd {

    private double ema12;
    private double ema26;

    private double diff;
    private double dea;
    private double bar;

    public double getEma12() {
        return ema12;
    }

    public double getEma26() {
        return ema26;
    }

    public double getDiff() {
        return diff;
    }

    public double getDea() {
        return dea;
    }

    public double getBar() {
        return bar;
    }

    public void setEma12(double ema12) {
        this.ema12 = ema12;
    }

    public void setEma26(double ema26) {
        this.ema26 = ema26;
    }

    public void setDiff(double diff) {
        this.diff = diff;
    }

    public void setDea(double dea) {
        this.dea = dea;
    }

    public void setBar(double bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "Macd{" +
                "ema12=" + ema12 +
                ", ema26=" + ema26 +
                ", diff=" + diff +
                ", dea=" + dea +
                ", bar=" + bar +
                '}';
    }


    public void setMacd(Macd macd_yesterday, StockMetaData metaData_today) {
        if (metaData_today != null) {
            if (macd_yesterday == null) {
                ema12 = Calculator.cutDecimal(metaData_today.getAdjust_close(), 5);
                ema26 = Calculator.cutDecimal(metaData_today.getAdjust_close(), 5);
                diff = 0;
                dea = 0;
                bar = 0;
            } else {
                ema12 = Calculator.cutDecimal(macd_yesterday.getEma12() + (metaData_today.getAdjust_close() - macd_yesterday.getEma12()) * 2.0 / 13.0, 5);
                ema26 = Calculator.cutDecimal(macd_yesterday.getEma26() + (metaData_today.getAdjust_close() - macd_yesterday.getEma26()) * 2.0 / 27.0, 5);
                diff = Calculator.cutDecimal(ema26 - ema12, 5);
                dea = Calculator.cutDecimal(macd_yesterday.getDea() + (diff - macd_yesterday.getDea()) * 2.0 / 10.0, 5);
                bar = Calculator.cutDecimal(2.0 * (diff - dea), 5);
            }
        }
    }
}
