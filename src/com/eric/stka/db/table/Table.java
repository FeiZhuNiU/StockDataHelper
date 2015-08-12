package com.eric.stka.db.table;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 8/11/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import java.util.List;

public interface Table {

    List<ColInfo> getColInfos();

    String getTableName();

    void addCol(ColInfo colInfo);

    void deleteCol(ColInfo colInfo);

}
