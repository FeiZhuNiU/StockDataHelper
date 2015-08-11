package com.eric.stockhelper.stock;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/5/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import java.sql.Date;

public class StockMetaData {
    private Stock stock;
    private Date date;
    private double high;
    private double low;
    private double open;
    private double close;
    private long volume;
    private double adjust_close;


    private double m5;

    public StockMetaData(Stock stock, Date date, double high, double low, double open, double close, long volume, double adjust_close) {
        this.stock = stock;
        this.date = date;
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.volume = volume;
        this.adjust_close = adjust_close;
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

    public long getVolume() {
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

    @Override
    public String toString() {
        return "StockMetaData{" +
                "stock=" + stock +
                ", date=" + date +
                ", high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", close=" + close +
                ", volume=" + volume +
                ", adjust_close=" + adjust_close +
                ", m5=" + m5 +
                '}';
    }
}
