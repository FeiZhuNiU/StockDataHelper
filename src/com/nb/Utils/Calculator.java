package com.nb.Utils;
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
                ret.put(metaData.getDate(), ma);
                sum -= metaData.getAdjust_close();
                if (i + days < metaDatas.size())
                    sum += metaDatas.get(i + days).getAdjust_close();
            }
        }
        return ret;
    }
}
