package com.nb.stock.index;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/6/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.nb.stock.StockMetaData;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator {

    public static Map<Date, Double> calMA(List<StockMetaData> metaDatas, int days) {

        Map<Date, Double> ret = new HashMap<>();

        if (metaDatas != null && metaDatas.size() >= days) {
            double sum = 0;
            for (int i = 0; i < days; ++i) {
                sum += metaDatas.get(i).getAdjust_close();
            }
            for (int i = 0; i <= metaDatas.size() - days; ++i) {

                StockMetaData metaData = metaDatas.get(i);
                double ma = sum / (double) days;
                ret.put(metaData.getDate(), cutDecimal(ma, 3));
                sum -= metaData.getAdjust_close();
                if (i + days < metaDatas.size())
                    sum += metaDatas.get(i + days).getAdjust_close();
            }
//            for(int i = 0 ; i <=metaDatas.size()-days;++i){
//                double sum = 0;
//                for(int j = 0 ; j < days; ++j){
//                    sum += metaDatas.get(i+j).getAdjust_close();
//                }
//                ret.put(metaDatas.get(i).getDate(),sum/(double)days);
//            }

        }
        return ret;
    }

    public static Map<Date,Macd> calMacd(List<StockMetaData> metaDatas){
        Map<Date,Macd> ret = new HashMap<>();
        if(metaDatas!=null && metaDatas.size()>0){
            int size = metaDatas.size();

            StockMetaData metaData = metaDatas.get(size - 1);
            Macd macd = new Macd();
            macd.setDiff(0.0000);
            macd.setDea(0.0000);
            macd.setBar(0.0000);
            macd.setEma12(metaData.getAdjust_close());
            macd.setEma26(metaData.getAdjust_close());
            ret.put(metaData.getDate(),macd);

            for(int i = 1; i < size ; ++i){
                StockMetaData metaDataToday = metaDatas.get(size-i-1);
                Macd yesterday = ret.get(metaDatas.get(size-i).getDate());

                Macd macd_today = new Macd();
                macd_today.setEma12(cutDecimal(yesterday.getEma12()*11.0/13.0 + metaDataToday.getAdjust_close()*2.0/13.0,4));
                macd_today.setEma26(cutDecimal(yesterday.getEma26()*25.0/27.0 + metaDataToday.getAdjust_close()*2.0/17.0,4));
                macd_today.setDiff(cutDecimal(macd_today.getEma12()-macd_today.getEma26(),4));
                macd_today.setDea(cutDecimal(yesterday.getDea()*8.0/10.0 + macd_today.getDiff()*2.0/10.0,4));
                macd_today.setBar(cutDecimal(2.0*(macd_today.getDiff()-macd_today.getDea()),4));

                ret.put(metaDataToday.getDate(),macd_today);
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
