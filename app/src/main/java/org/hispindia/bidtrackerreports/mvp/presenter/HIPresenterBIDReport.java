package org.hispindia.bidtrackerreports.mvp.presenter;

import android.util.Log;

import org.hispindia.bidtrackerreports.mvp.model.HIBIDModel;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDRow;
import org.hispindia.bidtrackerreports.mvp.model.local.HIDBMapping;
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewBIDReport;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewTodayScheduleReport;
import org.hispindia.bidtrackerreports.utils.HICUtilRxHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by nhancao on 1/24/16.
 */
public class HIPresenterBIDReport implements HIIPresenterBase<HIIViewBIDReport> {
    public final static String TAG = HIPresenterBIDReport.class.getSimpleName();

    private final HIBIDModel model;
    private Subscription subscription;
    private List<HIDBbidrow> temp = new ArrayList<>();

    public HIPresenterBIDReport(HIBIDModel model) {
        this.model = model;
    }

    @Override
    public void onStart(HIIViewBIDReport view) {

    }

    public void getBIDEventReport(HIIViewBIDReport view, String orgUnitUid, String ouModeUid, String programId, String programStageUid) {
        HIBIDRow headerRow = model.initialDBReport(programId, programStageUid);
        view.updateHeaderRow(headerRow);
        onStop();
        subscription = model.getEvents(orgUnitUid, ouModeUid, programStageUid)
                .compose(HICUtilRxHelper.applySchedulers())
                .subscribe(
                        bidEvents -> {
                            subscription = Observable.create(subscription -> {
                                model.getEventBIDRow(subscription, bidEvents.getEventList());
                            }).compose(HICUtilRxHelper.applySchedulers()).subscribe(hidRow -> {
                                view.updateRow((HIBIDRow) hidRow);
                            });
                        },
                        e -> {
                            Log.e(TAG, "getBIDEventReport: " + e.toString());
                            e.printStackTrace();
                        }
                );
    }

    public void getTodayScheduleEventReport(HIIViewTodayScheduleReport view, String orgUnitUid, String ouModeUid, String programId, String programStageUid) {
        model.initialDBReport(programId, programStageUid);
        onStop();
        temp = model.getDbLocal();
        if (temp != null && temp.size() > 0) {
            view.updateList(temp);
        }
        temp = new ArrayList<>();
        subscription = model.getEvents(orgUnitUid, ouModeUid, programStageUid)
                .compose(HICUtilRxHelper.applySchedulers())
                .subscribe(
                        bidEvents -> {
                            subscription = Observable.create(subscription -> {
                                model.getEventBIDRow(subscription, bidEvents.getEventList());
                            }).compose(HICUtilRxHelper.applySchedulers()).subscribe(hidRow -> {
                                HIDBbidrow row = HIDBMapping.fromRemote((HIBIDRow) hidRow);
                                if (row != null) {
                                    temp.add(row);
                                } else {
                                    model.cacheToLocal(temp);
                                    view.updateList(temp);
                                    temp = new ArrayList<>();
                                }
                                view.updateRow(row);
                            });
                        },
                        e -> {
                            view.updateRow(null);
                            Log.e(TAG, "getBIDEventReport: " + e.toString());
                            e.printStackTrace();
                        }
                );

    }

    @Override
    public void onStop() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
