package com.nb.stock;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/5/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import java.util.Date;

public class StockMetaData
{
    private Stock stock;
    private double high;
    private double low;
    private double open;
    private double close;
    private double volume;
    private double adjust_close;
    private Date date;

    private double m5;

    public StockMetaData(Stock stock, double high, double low, double open, double close, double volume, double adjust_close, Date date) {
        this.stock = stock;
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.volume = volume;
        this.adjust_close = adjust_close;
        this.date = date;
    }

    public Stock getStock() {
        return stock;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {
        return close;
    }

    public double getVolume() {
        return volume;
    }

    public double getAdjust_close() {
        return adjust_close;
    }

    public Date getDate() {
        return date;
    }

    public double getM5() {
        return m5;
    }

    public void setM5(double m5) {
        this.m5 = m5;
    }
}
