package org.hispindia.bidtrackerreports.mvp.presenter;

import android.util.Log;

import org.hisp.dhis.android.sdk.controllers.tracker.TrackerController;
import org.hisp.dhis.android.sdk.persistence.models.Event;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityInstance;
import org.hispindia.bidtrackerreports.mvp.model.HIBIDModel;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDRow;
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
        Observable.create(subscriber -> {
            List<Event> eventList = TrackerController.getEvents(orgUnitUid, programId);
            for (Event event : eventList) {
                if(event.getStatus().equals("SCHEDULE")){
                    Log.e(TAG, "getTodayScheduleEventReport: ---------------------begin-----");
                    ApplyRuleHelper applyRuleHelper = new ApplyRuleHelper(orgUnitUid,programId, programStageUid, event.getLocalId(), event.getLocalEnrollmentId());
                    applyRuleHelper.initiateEvaluateProgramRules();
                    Log.e(TAG, "getTodayScheduleEventReport: ---------------------end-----");

                }
            }

        }).compose(HICUtilRxHelper.applySchedulers()).subscribe();


        if(true)
        return ;
        temp = model.getDbLocal();
        if (temp != null && temp.size() > 0) {
            view.updateList(temp);
        }
        temp = new ArrayList<>();
        subscription = model.getEvents(orgUnitUid, ouModeUid, programStageUid)
                .compose(HICUtilRxHelper.applySchedulers())
                .subscribe(
                        bidEvents -> {
                            List<TrackedEntityInstance> trackedEntityInstances = new ArrayList<>();
                            for (int count = 0; count < bidEvents.getEventList().size(); count++) {
                                Event eventItem = bidEvents.getEventList().get(count);
                                if (eventItem.getStatus().equals("SCHEDULE")) {
                                    TrackedEntityInstance tei = new TrackedEntityInstance();
                                    tei.setTrackedEntityInstance(eventItem.getTrackedEntityInstance());
                                    tei.setOrgUnit(eventItem.getOrganisationUnitId());
                                    trackedEntityInstances.add(tei);
                                }
                            }

                            subscription = Observable.create(subscription -> {

//                                TrackerController.getTrackedEntityInstancesDataFromServer(DhisController.getInstance().getDhisApi(), trackedEntityInstances, true);
//                                Log.e(TAG, "getTodayScheduleEventReport: DONE");



//                                model.getEventBIDRow(subscription, bidEvents.getEventList());
                            }).compose(HICUtilRxHelper.applySchedulers()).subscribe(
//                                    hidRow -> {
//                                        HIDBbidrow row = HIDBMapping.fromRemote((HIBIDRow) hidRow);
//                                        if (row != null) {
//                                            temp.add(row);
//                                        } else {
//                                            model.cacheToLocal(temp);
//                                            view.updateList(temp);
//                                            temp = new ArrayList<>();
//                                        }
//                                        view.updateRow(row);
//                                    }
                            );
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
