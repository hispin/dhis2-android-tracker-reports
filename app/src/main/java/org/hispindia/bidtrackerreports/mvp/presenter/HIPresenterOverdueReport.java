package org.hispindia.bidtrackerreports.mvp.presenter;

import android.util.Log;

import org.hispindia.android.core.utils.HICUtilRxHelper;
import org.hispindia.bidtrackerreports.mvp.model.HIOverdueModel;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewOverdueReport;

import rx.Subscription;

/**
 * Created by Sourabh on 2/2/2016.
 */
public class HIPresenterOverdueReport implements HIIPresenterBase<HIIViewOverdueReport> {
    public final static String TAG = HIPresenterOverdueReport.class.getSimpleName();

    private final HIOverdueModel model;
    private Subscription subscription;

    public HIPresenterOverdueReport(HIOverdueModel model) {
        this.model = model;
    }

    @Override
    public void onStart(HIIViewOverdueReport view) {

    }

    public void getOverdueReport(HIIViewOverdueReport view, String orgUnitId, String ouMode, String programId) {
        onStop();
        subscription = model.getOverdueReport(orgUnitId, ouMode, programId)
                .compose(HICUtilRxHelper.applySchedulers())
                .subscribe((rows) -> {
                    view.updateRow(rows);
                }, e -> {
                    Log.e(TAG, "getOverdueReport: " + e.toString());
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
