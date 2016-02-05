package org.hispindia.bidtrackerreports.mvp.view;

import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;

import java.util.List;

/**
 * Created by nhancao on 1/25/16.
 */
public interface HIIViewTodayScheduleReport {

    void updateList(List<HIDBbidrow> localList);

    void updateRow(HIDBbidrow row);
}
