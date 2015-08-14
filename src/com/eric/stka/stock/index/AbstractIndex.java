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

/**
 * create new index steps:
 * <p/>
 * 1.  create a index class extends AbstractIndex
 * 2.  override all the abstract method
 * 3.  in the constructor
 * a.  call super(stock,date)
 * b.  self init
 * c.  call setDBColInfo()
 * 4.  you can provide a setXXX method to calculator one day's index
 */

public abstract class AbstractIndex {

    private Date date;
    private Stock stock;
    protected List<ColInfo> dbColInfos;


    /**
     * add ColInfos to dbColInfos
     */
    abstract void setDBColInfo();

    /**
     * @return Map<col_name,index_value>
     */
    public abstract Map<String, Double> getIndexValues();

    /**
     * @param metaDatas history metadatas
     * @return history index
     */
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
