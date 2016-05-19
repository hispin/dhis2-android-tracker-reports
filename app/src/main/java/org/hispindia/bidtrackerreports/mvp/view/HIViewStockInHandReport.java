package org.hispindia.bidtrackerreports.mvp.view;

import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock1;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock3;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock4;

/**
 * Created by Sourabh on 2/6/2016.
 */
public interface HIViewStockInHandReport {

    void updateRow(HIResStock rows);

    void updateRow1(HIResStock1 rows1);

    void updateRow2(HIResStock2 rows2);

    void updateRow3(HIResStock3 rows3);

    void updateRow4(HIResStock4 rows4);

//    void updateRow5(HIResStock5 rows5);

}
