package com.eric.stockhelper.stock.index;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/6/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.eric.stockhelper.stock.StockMetaData;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator {

    public static Map<Date, Double> calMA(List<StockMetaData> metaDatas, int days) {

        Map<Date, Double> ret = new HashMap<>();

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

                Ma ma = new Ma(days);
                List<StockMetaData> metaDataList = new ArrayList<>();
                for(int j = 0 ; j < days; ++j){
                    metaDataList.add(metaDatas.get(i+j));
                }
                ma.setMA(metaDataList);

                ret.put(metaDatas.get(i).getDate(),ma.getMaValue());
            }

        }
        return ret;
    }

    public static Map<Date, Macd> calMacd(List<StockMetaData> metaDatas) {
        Map<Date, Macd> ret = new HashMap<>();
        if (metaDatas != null && metaDatas.size() > 0) {
            int size = metaDatas.size();

            StockMetaData metaData = metaDatas.get(size - 1);
            Macd macd = new Macd();
            macd.setMacd(null, metaData);
            ret.put(metaData.getDate(), macd);

            for (int i = 1; i < size; ++i) {
                StockMetaData metaDataToday = metaDatas.get(size - i - 1);
                Macd yesterday = ret.get(metaDatas.get(size - i).getDate());

                Macd macd_today = new Macd();
                macd_today.setMacd(yesterday, metaDataToday);
                ret.put(metaDataToday.getDate(), macd_today);
            }
        }
        return ret;
    }

    /**
     * @param number
     * @param digit  保留的小数位数
     * @return
     */
    public static double cutDecimal(double number, int digit) {
        if (digit < 1) {
            return number;
        }
        int a = 1;
        for (int i = 0; i < digit; ++i) {
            a *= 10;
        }
        String str = String.valueOf(a) + ".";
        for (int i = 0; i < digit; ++i) {
            str += "0";
        }
        double b = Double.valueOf(str);

        return Math.round(number * a) / b;
    }
}
