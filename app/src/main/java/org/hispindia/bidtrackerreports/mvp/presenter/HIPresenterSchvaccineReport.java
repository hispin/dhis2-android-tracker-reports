package org.hispindia.bidtrackerreports.mvp.presenter;

import android.util.Log;

import org.hispindia.bidtrackerreports.mvp.model.HISchvaccineModel;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewSchvaccineReport;
import org.hispindia.bidtrackerreports.utils.HICUtilRxHelper;

import rx.Subscription;

/**
 * Created by nhancao on 1/24/16.
 */
public class HIPresenterSchvaccineReport implements HIIPresenterBase<HIIViewSchvaccineReport> {
    public final static String TAG = HIPresenterSchvaccineReport.class.getSimpleName();

    private final HISchvaccineModel model;
    private Subscription subscription;

    public HIPresenterSchvaccineReport(HISchvaccineModel model) {
        this.model = model;
    }

    @Override
    public void onStart(HIIViewSchvaccineReport view) {

    }

    public void getSchvaccineReport(HIIViewSchvaccineReport view, String orgUnitId, String ouMode, String programId, String startDate, String endDate) {
        onStop();
        subscription = model.getSchvaccineReport(orgUnitId, ouMode, programId, startDate, endDate)
                .compose(HICUtilRxHelper.applySchedulers())
                .subscribe((rows) -> {
                    view.updateRow(rows);
                }, e -> {
                    Log.e(TAG, "getSchvaccineReport: " + e.toString());
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
