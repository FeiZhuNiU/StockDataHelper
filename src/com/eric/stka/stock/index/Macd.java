package com.eric.stka.stock.index;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/7/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.eric.stka.db.DBConstants;
import com.eric.stka.db.table.ColInfo;
import com.eric.stka.stock.Stock;
import com.eric.stka.stock.StockMetaData;

import java.sql.Date;
import java.util.*;

public class Macd extends AbstractIndex {

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

    public Macd(Stock stock, Date date) {
        this(stock, date, 12, 26, 9);
    }

    public Macd(Stock stock, Date date, int param1, int param2, int param3) {
        super(stock, date);
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        setDBColInfo();
    }

    @Override
    public String toString() {
        return "Macd{" +
                "param1=" + param1 +
                ", param2=" + param2 +
                ", param3=" + param3 +
                ", ema12=" + ema12 +
                ", ema26=" + ema26 +
                ", diff=" + diff +
                ", dea=" + dea +
                ", bar=" + bar +
                '}';
    }

    public static Macd calMacd(Macd macd_yesterday, StockMetaData metaData_today) {
        return calMacd(macd_yesterday, metaData_today, 12, 26, 9);
    }

    public static Macd calMacd(Macd macd_yesterday, StockMetaData metaData_today, int param1, int param2, int param3) {

        Macd ret = null;

        if (metaData_today != null) {
            ret = new Macd(metaData_today.getStock(), metaData_today.getDate(), param1, param2, param3);
            if (macd_yesterday == null) {
                ret.ema12 = Calculator.cutDecimal(metaData_today.getAdjust_close(), 5);
                ret.ema26 = Calculator.cutDecimal(metaData_today.getAdjust_close(), 5);
                ret.diff = 0;
                ret.dea = 0;
                ret.bar = 0;
            } else {
                ret.ema12 = Calculator.cutDecimal(macd_yesterday.getEma12() + (metaData_today.getAdjust_close() - macd_yesterday.getEma12()) * 2.0 / (param1 + 1.0), 5);
                ret.ema26 = Calculator.cutDecimal(macd_yesterday.getEma26() + (metaData_today.getAdjust_close() - macd_yesterday.getEma26()) * 2.0 / (param2 + 1.0), 5);
                ret.diff = Calculator.cutDecimal(ret.ema26 - ret.ema12, 5);
                ret.dea = Calculator.cutDecimal(macd_yesterday.getDea() + (ret.diff - macd_yesterday.getDea()) * 2.0 / (param3 + 1.0), 5);
                ret.bar = Calculator.cutDecimal(2.0 * (ret.diff - ret.dea), 5);
            }
        }
        return ret;
    }

    public void setMacd(Macd macd_yesterday, StockMetaData metaData_today) {
        Macd macd = calMacd(macd_yesterday, metaData_today, param1, param2, param3);
        ema12 = macd.getEma12();
        ema26 = macd.getEma26();
        diff = macd.getDiff();
        dea = macd.getDea();
        bar = macd.getBar();
    }


    @Override
    void setDBColInfo() {
        dbColInfos.add(new ColInfo(DBConstants.MACD_COL_EMA12, DBConstants.MACD_COL_EMA12_TYPE));
        dbColInfos.add(new ColInfo(DBConstants.MACD_COL_EMA26, DBConstants.MACD_COL_EMA26_TYPE));
        dbColInfos.add(new ColInfo(DBConstants.MACD_COL_DIFF, DBConstants.MACD_COL_DIFF_TYPE));
        dbColInfos.add(new ColInfo(DBConstants.MACD_COL_DEA, DBConstants.MACD_COL_DEA_TYPE));
        dbColInfos.add(new ColInfo(DBConstants.MACD_COL_BAR, DBConstants.MACD_COL_BAR_TYPE));
    }

    @Override
    public Map<String, Double> getIndexValues() {
        Map<String, Double> ret = new LinkedHashMap<>();
        ret.put(DBConstants.MACD_COL_EMA12, ema12);
        ret.put(DBConstants.MACD_COL_EMA26, ema26);
        ret.put(DBConstants.MACD_COL_DIFF, diff);
        ret.put(DBConstants.MACD_COL_DEA, dea);
        ret.put(DBConstants.MACD_COL_BAR, bar);
        return ret;
    }

    @Override
    List<AbstractIndex> calHistoryIndex(List<StockMetaData> metaDatas) {
        List<AbstractIndex> ret = new ArrayList<>();

        if (metaDatas != null && metaDatas.size() > 0) {
            int size = metaDatas.size();

            StockMetaData metaData_fisrtDay = metaDatas.get(size - 1);
            Macd macd = new Macd(metaData_fisrtDay.getStock(), metaData_fisrtDay.getDate(), param1, param2, param3);
            macd.setMacd(null, metaData_fisrtDay);
            ret.add(macd);

            for (int i = 1; i < size; ++i) {
                StockMetaData metaDataToday = metaDatas.get(size - i - 1);
                Macd yesterday = (Macd) ret.get(ret.size() - 1);

                Macd macd_today = new Macd(metaDataToday.getStock(), metaDataToday.getDate(), param1, param2, param3);
                macd_today.setMacd(yesterday, metaDataToday);
                ret.add(macd_today);
            }
        }
        return ret;
    }
}
