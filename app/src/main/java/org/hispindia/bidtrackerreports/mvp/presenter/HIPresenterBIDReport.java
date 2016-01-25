package org.hispindia.bidtrackerreports.mvp.presenter;

import android.util.Log;

import org.hispindia.android.core.utils.HICUtilRxHelper;
import org.hispindia.bidtrackerreports.mvp.model.HIBIDModel;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDRow;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewBIDReport;

import rx.Observable;
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

    public void getBIDEventReport(HIIViewBIDReport view, String orgUnitUid, String ouModeUid, String programId, String programStageUid) {
        HIBIDRow headerRow = model.initialDBReport(programId, programStageUid);
        view.updateHeaderRow(headerRow);
        onStop();
        subscription = model.getEvents(orgUnitUid, ouModeUid, programStageUid)
                .compose(HICUtilRxHelper.applySchedulers())
                .subscribe(
                        bidEvents -> {
                            Observable.create(subscriber -> {
                                model.getEventBIDRow(subscriber, bidEvents.getEventList());
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

    @Override
    public void onStop() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
