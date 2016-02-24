package org.hispindia.bidtrackerreports.mvp.presenter;

import android.util.Log;

import org.hisp.dhis.android.sdk.controllers.DhisController;
import org.hisp.dhis.android.sdk.controllers.metadata.MetaDataController;
import org.hisp.dhis.android.sdk.controllers.tracker.TrackerController;
import org.hisp.dhis.android.sdk.controllers.tracker.TrackerDataLoader;
import org.hisp.dhis.android.sdk.persistence.models.Event;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityAttributeValue;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityInstance;
import org.hispindia.bidtrackerreports.mvp.model.HIBIDModel;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDRow;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDRowItem;
import org.hispindia.bidtrackerreports.mvp.model.local.HIDBMapping;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewBIDReport;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewTodayScheduleReport;
import org.hispindia.bidtrackerreports.utils.HICUtilRxHelper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by nhancao on 1/24/16.
 */
public class HIPresenterBIDReport implements HIIPresenterBase<HIIViewBIDReport> {
    public final static String TAG = HIPresenterBIDReport.class.getSimpleName();

    private final HIBIDModel model;
    private Subscription subscription;

    public HIPresenterBIDReport(HIBIDModel model) {
        this.model = model;
    }

    @Override
    public void onStart(HIIViewBIDReport view) {

    }

    public void getTodayScheduleEventReport(HIIViewTodayScheduleReport view, String orgUnitUid, String ouModeUid, String programId, String programStageUid, boolean getTodaySchedule) {
        getTodayScheduleEventReport(view, orgUnitUid, ouModeUid, programId, programStageUid, getTodaySchedule, true);
    }

    public void getTodayScheduleEventReport(HIIViewTodayScheduleReport view, String orgUnitUid, String ouModeUid, String programId, String programStageUid, boolean getTodaySchedule, boolean fetchNetwork) {
        view.updateProgress(false);
        onStop();
        subscription = Observable.create((Subscriber<? super Object> subscriber) -> {
            List<Event> eventList = new ArrayList<>();
            for (Event event : TrackerController.getEvents(orgUnitUid, programId)) {
                if (event.getStatus().equals(Event.STATUS_FUTURE_VISIT)) {
                    eventList.add(event);
                }
            }
            Collections.sort(eventList, (lhs, rhs) -> {
                DateTime left = DateTime.parse(lhs.getDueDate(), DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
                DateTime right = DateTime.parse(rhs.getDueDate(), DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
                if (left.isAfter(right)) return 1;
                if (left.isEqual(right)) return 0;
                return -1;
            });
            for (int i = 0; i < eventList.size(); i++) {
                Event event = eventList.get(i);
                HIBIDRow row = applyRuleEachEvent(i, event, orgUnitUid, ouModeUid, programId, programStageUid, getTodaySchedule);
                subscriber.onNext(row);
                if (subscription != null && subscription.isUnsubscribed()) {
                    break;
                }
            }
            subscriber.onNext(null);
        }).compose(HICUtilRxHelper.applySchedulers()).subscribe(hidRow -> {
            view.updateRow(HIDBMapping.fromRemote((HIBIDRow) hidRow));
            if (hidRow == null) {
                view.updateProgress(true);
            }
            if (TrackerDataLoader.TEICUSTOMFLAG) {
                return;
            }
            if (hidRow == null && fetchNetwork) {
                Observable.create(subscription -> {
                    TrackerDataLoader.TEICUSTOMFLAG = true;
                    model.getEvents(orgUnitUid, ouModeUid, programStageUid)
                            .subscribe(
                                    bidEvents -> {
                                        List<TrackedEntityInstance> trackedEntityInstances = new ArrayList<>();
                                        for (int count = 0; count < bidEvents.getEventList().size(); count++) {
                                            Event eventItem = bidEvents.getEventList().get(count);
                                            if (eventItem.getStatus().equals(Event.STATUS_FUTURE_VISIT)) {
                                                TrackedEntityInstance tei = new TrackedEntityInstance();
                                                tei.setTrackedEntityInstance(eventItem.getTrackedEntityInstance());
                                                tei.setOrgUnit(eventItem.getOrganisationUnitId());
                                                trackedEntityInstances.add(tei);
                                            }
                                        }
                                        TrackerController.getTrackedEntityInstancesDataFromServerCustom(DhisController.getInstance().getDhisApi(), trackedEntityInstances, true);
                                    }, e -> {
                                        Log.e(TAG, "getBIDEventReport: " + e.toString());
                                        e.printStackTrace();
                                    }
                            );
                }).subscribe();
            }
        }, e -> {
            Log.e(TAG, "getBIDEventReport: " + e.toString());
            e.printStackTrace();
        });
    }

    public HIBIDRow applyRuleEachEvent(int order, Event event, String orgUnitUid, String ouModeUid, String programId, String programStageUid, boolean getTodaySchedule) {

        ApplyRuleHelper applyRuleHelper = new ApplyRuleHelper(orgUnitUid, programId, programStageUid, event.getLocalId(), event.getLocalEnrollmentId());
        if (getTodaySchedule) {
            applyRuleHelper.initiateEvaluateProgramRules();
        } else {
            applyRuleHelper.populateCompletedEvent(orgUnitUid, programId);
        }
        List<HIBIDRowItem> trackedEntityAttributeList = new ArrayList<>();
        List<HIBIDRowItem> dataElementList = new ArrayList<>();
        for (TrackedEntityAttributeValue teiAtt : TrackerController.getTrackedEntityAttributeValues(event.getTrackedEntityInstance())) {
            trackedEntityAttributeList.add(new HIBIDRowItem(teiAtt.getTrackedEntityAttributeId(),
                    MetaDataController.getTrackedEntityAttribute(teiAtt.getTrackedEntityAttributeId()).getDisplayName(), teiAtt.getValue()));
        }
        for (String key : applyRuleHelper.getDataElementNames().keySet()) {
            dataElementList.add(new HIBIDRowItem(key, applyRuleHelper.getDataElementNames().get(key), applyRuleHelper.getDataValues().get(key).getValue()));
        }

        HIBIDRow row = new HIBIDRow();
        row.setOrder(order + 1);
        row.setEventId(event.getEvent());
        DateTime dueDate = DateTime.parse(event.getDueDate(), DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        row.setDueDate(dueDate.toString("yyyy-MM-dd"));
        row.setIsOverDue(dueDate.isBeforeNow());
        row.setTrackedEntityAttributeList(trackedEntityAttributeList);
        row.setDataElementList(dataElementList);
        return row;
    }

    @Override
    public void onStop() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
