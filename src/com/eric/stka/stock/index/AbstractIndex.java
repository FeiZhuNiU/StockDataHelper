package com.eric.stka.stock.index;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/12/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.eric.stka.db.table.ColInfo;
import com.eric.stka.stock.Stock;
import com.eric.stka.stock.StockMetaData;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractIndex{

    private Date date;
    private Stock stock;
    protected List<ColInfo> dbColInfos;


    /**
     * set ColInfos related to the concrete index
     * must be called in constructor of child classes
     */
    abstract void setDBColInfo();

    /**
     * @return get all index values
     * the key of map is the col name in related table
     */
    public abstract Map<String,Double> getIndexValues();

    abstract List<AbstractIndex> calHistoryIndex(List<StockMetaData> metaDatas);

    public AbstractIndex(Stock stock, Date date) {
        this.stock = stock;
        this.date = date;
        dbColInfos = new ArrayList<>();

    }

    public List<ColInfo> getDbColInfos() {
        return dbColInfos;
    }

    public Date getDate() {
        return date;
    }

    public Stock getStock() {
        return stock;
    }
}
