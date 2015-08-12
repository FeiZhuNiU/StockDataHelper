package com.eric.stockhelper.stock.index;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.eric.stockhelper.db.DBConstants;
import com.eric.stockhelper.db.table.ColInfo;
import com.eric.stockhelper.stock.Stock;
import com.eric.stockhelper.stock.StockMetaData;

import java.sql.Date;
import java.util.*;

public class Ma extends AbstractIndex {

    private int days;
    private double maValue;

    public Ma(Stock stock, Date date, int days) {
        super(stock,date);
        this.days = days;
        setDBColInfo();
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

    @Override
    void setDBColInfo() {
        dbColInfos.add(new ColInfo(DBConstants.MA_COL_PREFIX + days, DBConstants.MA_COL_TYPE));
    }

    @Override
    public Map<String, Double> getIndexValues() {

        Map<String, Double> ret = new LinkedHashMap<>();
        ret.put(DBConstants.MA_COL_PREFIX + days, maValue);
        return ret;

    }

    @Override
    List<AbstractIndex> calHistoryIndex(List<StockMetaData> metaDatas) {
        List<AbstractIndex> ret = new ArrayList<>();

        if (metaDatas != null && metaDatas.size() >= days) {
//            double sum = 0;
//            for (int i = 0; i < days; ++i) {
//                sum += metaDatas.get(i).getAdjust_close();
//            }
//            for (int i = 0; i <= metaDatas.size() - days; ++i) {
//
//                StockMetaData metaData = metaDatas.get(i);
//                double ma = sum / (double) days;
//                ret.put(metaData.getDate(), cutDecimal(ma, 3));
//                sum -= metaData.getAdjust_close();
//                if (i + days < metaDatas.size())
//                    sum += metaDatas.get(i + days).getAdjust_close();
//            }
            for(int i = 0 ; i <=metaDatas.size()-days;++i){
                StockMetaData metaData = metaDatas.get(i);
                Ma ma = new Ma(metaData.getStock(),metaData.getDate(),days);
                List<StockMetaData> metaDataList = new ArrayList<>();
                for(int j = 0 ; j < days; ++j){
                    metaDataList.add(metaDatas.get(i+j));
                }
                ma.setMA(metaDataList);
                ret.add(ma);
            }
        }
        return ret;
    }
}
