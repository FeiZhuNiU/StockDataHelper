package com.eric.stockhelper.stock.index;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/6/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.eric.stockhelper.stock.StockMetaData;

import java.util.List;

public class Calculator {

    public static List<AbstractIndex> calHistoryIndex(List<StockMetaData> metaDatas, AbstractIndex index){
        return index.calHistoryIndex(metaDatas);

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
