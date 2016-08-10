package org.hispindia.bidtrackerreports.mvp.presenter;

import android.util.Log;

import org.hisp.dhis.android.sdk.controllers.tracker.TrackerController;
import org.hisp.dhis.android.sdk.persistence.models.Event;
import org.hispindia.bidtrackerreports.mvp.model.HIBIDBIRTHModel;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDBIRTHRow;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDBIRTHRowItem;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIRTHMapping;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewBirthNotificationReport;
import org.hispindia.bidtrackerreports.utils.HICUtilRxHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

import static org.hispindia.bidtrackerreports.utils.HICUtilRxHelper.onNext;


public class HIPresenterBIDBirthReport implements HIIPresenterBase<HIIViewBirthNotificationReport> {
    public final static String TAG = HIPresenterBIDBirthReport.class.getSimpleName();

    private final HIBIDBIRTHModel model;
    private Subscription subscription;
    private Subscription subscriptionUpdated;

    public HIPresenterBIDBirthReport(HIBIDBIRTHModel model) {
        this.model = model;
    }

    @Override
    public void onStart(HIIViewBirthNotificationReport view) {

    }

    public void getBirthNotificationReport(HIIViewBirthNotificationReport view, String orgUnitUid, String ouModeUid, String programBirthId, String programStageUid) {
        onStop();

        subscription = Observable.create(subscriber -> {
            List<Event> eventList = new ArrayList<>();

            for (Event event : TrackerController.getEvents(orgUnitUid, programBirthId)) {
                Log.e(TAG,"Between"+ programBirthId);
                Log.e(TAG,"Success Birth");
                if (event.getStatus().equals(Event.STATUS_ACTIVE)) {
                    eventList.add(event);
                    Log.e(TAG,"Event List Size "+ eventList.size());
                }
            }
            Log.e(TAG,"Before1"+ programBirthId);
            Log.e(TAG,"Before programStageUid1"+ programStageUid);
//            Collections.sort(eventList, (lhs, rhs) -> {
//                DateTime left = DateTime.parse(lhs.getDueDate(), DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
//                DateTime right = DateTime.parse(rhs.getDueDate(), DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
//                if (left.isAfter(right)) return 1;
//                if (left.isEqual(right)) return 0;
//                return -1;
//            });

            for (int i = 0; i < eventList.size(); i++) {
                Log.e(TAG,"Before"+ programBirthId);
                Log.e(TAG,"Before programStageUid"+ programStageUid);
                Log.e(TAG,"Event List:"+eventList );
                Event event = eventList.get(i);
                ApplyRuleHelper applyRuleHelper = new ApplyRuleHelper(orgUnitUid, programBirthId, programStageUid, event.getLocalId(), event.getLocalEnrollmentId());

                applyRuleHelper.initiateEvaluateProgramRules();

                List<HIBIDBIRTHRowItem> dataElementList = new ArrayList<>();

                for (String key : applyRuleHelper.getDataElementNames().keySet()) {
                    dataElementList.add(new HIBIDBIRTHRowItem(key, applyRuleHelper.getDataElementNames().get(key), applyRuleHelper.getDataValues().get(key).getValue()));
                }

                HIBIDBIRTHRow row = new HIBIDBIRTHRow();

                onNext(subscriber, row);
                if (subscriber != null && subscriber.isUnsubscribed()) {
                    break;
                }
                Log.e(TAG,"Event List:"+eventList );
            }
            onNext(subscriber, null);
        }).compose(HICUtilRxHelper.applySchedulers()).subscribe(hidRow -> {
            view.updateRow(HIBIRTHMapping.fromRemote((HIBIDBIRTHRow) hidRow));
            if (hidRow == null) {
                if (subscriptionUpdated != null && !subscriptionUpdated.isUnsubscribed()) return;
                subscriptionUpdated = model.getEventss(orgUnitUid, ouModeUid, programStageUid)
                        .compose(HICUtilRxHelper.applySchedulers())
                        .subscribe(
                                bidEvents -> {

                                    //List<TrackedEntityInstance> trackedEntityInstances = new ArrayList<>();
                                    for (int count = 0; count < bidEvents.getEventList().size(); count++) {
                                        Event eventItem = bidEvents.getEventList().get(count);

//                                            TrackedEntityInstance tei = new TrackedEntityInstance();
//                                            tei.setTrackedEntityInstance(eventItem.getTrackedEntityInstance());
//                                            tei.setOrgUnit(eventItem.getOrganisationUnitId());
                                           // trackedEntityInstances.add(tei);

                                    }

                                    subscriptionUpdated = Observable.create(subscription -> {
                                       // TrackerController.getTrackedEntityInstancesDataFromServer(DhisController.getInstance().getDhisApi(), trackedEntityInstances, true);
                                        subscriptionUpdated.unsubscribe();
                                    }).compose(HICUtilRxHelper.applySchedulers()).subscribe();
                                }, e -> {
                                    view.updateRow(null);
                                    Log.e(TAG, "getBIDEventReport: " + e.toString());
                                    e.printStackTrace();
                                }
                        );
            }
        }, e -> {
            view.updateRow(null);
            Log.e(TAG, "getBIDEventReport: " + e.toString());
            e.printStackTrace();
        });
    }

    @Override
    public void onStop() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
