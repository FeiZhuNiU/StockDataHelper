package com.nb.stock;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/5/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.nb.db.DBUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockUtils {

    public static List<StockMetaData> getHistory(Stock stock) {


        List<StockMetaData> ret = new ArrayList<>();
        DBUtils.connectDB();
        String sql = "SELECT * FROM " + stock.getTableName();

        if(DBUtils.excute(sql))
        {
            try {
                ResultSet resultSet = DBUtils.getStatement().getResultSet();
                while(resultSet.next()){
                    Date date = resultSet.getDate(1);
                    double open = Double.parseDouble(resultSet.getString(2));
                    double high = Double.parseDouble(resultSet.getString(3));
                    double low = Double.parseDouble(resultSet.getString(4));
                    double close = Double.parseDouble(resultSet.getString(5));
                    long volume = Long.parseLong(resultSet.getString(6));
                    double adjust_close = Double.parseDouble(resultSet.getString(7));

                    StockMetaData metaData = new StockMetaData(stock,date,high,low,open,close,volume,adjust_close);
                    ret.add(metaData);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
