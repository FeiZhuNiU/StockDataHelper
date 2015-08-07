package com.nb.stock.index;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/7/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

public class Macd {
    public static final String COL_EMA12 = "macd_ema12";
    public static final String COL_EMA26 = "macd_ema26";
    public static final String COL_DIFF = "macd_diff";
    public static final String COL_DEA = "macd_dea";
    public static final String COL_BAR = "macd_bar";

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
}
