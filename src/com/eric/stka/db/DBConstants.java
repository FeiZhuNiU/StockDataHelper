package com.eric.stka.db;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

public class DBConstants {


    public final static String DB_URL = "jdbc:mysql://localhost:3306/stockdb?user=lliyu&password=2222";
    public final static String TABLE_MARKET_NAME = "market";
    public final static String TABLE_SH_PRE = "sh";
    public final static String TABLE_BIG_NAME = "big_table";

    public final static String MA_COL_PREFIX = "ma";
    public final static String MA_COL_TYPE = "VARCHAR(20)";

    public static final String MACD_COL_EMA12 = "macd_ema12";
    public static final String MACD_COL_EMA26 = "macd_ema26";
    public static final String MACD_COL_DIFF = "macd_diff";
    public static final String MACD_COL_DEA = "macd_dea";
    public static final String MACD_COL_BAR = "macd_bar";

    public static final String MACD_COL_EMA12_TYPE = "VARCHAR(20)";
    public static final String MACD_COL_EMA26_TYPE = "VARCHAR(20)";
    public static final String MACD_COL_DIFF_TYPE = "VARCHAR(20)";
    public static final String MACD_COL_DEA_TYPE = "VARCHAR(20)";
    public static final String MACD_COL_BAR_TYPE = "VARCHAR(20)";
}
