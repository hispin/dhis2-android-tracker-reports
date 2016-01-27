package org.hispindia.bidtrackerreports.mvp.presenter;

import android.util.Log;

import org.hispindia.android.core.utils.HICUtilRxHelper;
import org.hispindia.bidtrackerreports.mvp.model.HIStockModel;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewStockReport;

import rx.Subscription;

/**
 * Created by nhancao on 1/24/16.
 */
public class HIPresenterStockReport implements HIIPresenterBase<HIIViewStockReport> {
    public final static String TAG = HIPresenterStockReport.class.getSimpleName();

    private final HIStockModel model;
    private Subscription subscription;

    public HIPresenterStockReport(HIStockModel model) {
        this.model = model;
    }

    @Override
    public void onStart(HIIViewStockReport view) {

    }

    public void getStockReport(HIIViewStockReport view, String orgUnitModeId, int orgUnitLevel, String orgUnitId) {
        onStop();
        subscription = model.getStockReport(orgUnitModeId, orgUnitLevel, orgUnitId)
                .compose(HICUtilRxHelper.applySchedulers())
                .subscribe((rows) -> {
                    view.updateRow(rows);
                }, e -> {
                    Log.e(TAG, "getStockReport: " + e.toString());
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
