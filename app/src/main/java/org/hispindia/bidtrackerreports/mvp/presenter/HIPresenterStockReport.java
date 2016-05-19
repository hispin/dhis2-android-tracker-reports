package org.hispindia.bidtrackerreports.mvp.presenter;

import android.util.Log;

import org.hispindia.bidtrackerreports.mvp.model.HIStockModel;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewStockReport;
import org.hispindia.bidtrackerreports.mvp.view.HIViewStockInHandReport;
import org.hispindia.bidtrackerreports.utils.HICUtilRxHelper;

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

    public void getStockReport(HIIViewStockReport view, String orgUnitMode, int orgUnitLevel, String orgUnitId) {
        onStop();
        subscription = model.getStockReport(orgUnitMode, orgUnitLevel, orgUnitId)
                .compose(HICUtilRxHelper.applySchedulers())
                .subscribe((rows) -> {
                    view.updateRow(rows);
                }, e -> {
                    Log.e(TAG, "getStockReport: " + e.toString());
                    e.printStackTrace();
                });
    }

    public void getStockInHandReport(HIViewStockInHandReport view, String orgUnitMode, String orgUnitId) {
        onStop();

        subscription = model.getStockInHandReport(orgUnitMode, orgUnitId)
                .compose(HICUtilRxHelper.applySchedulers())
                .subscribe((rows) -> {
                    view.updateRow(rows);
                }, e -> {
                    e.printStackTrace();
                });
    }
//
//    public void getStockInHandReport2(HIViewStockInHandReport view, String orgUnitMode, String orgUnitId) {
//        onStop();
//
//        subscription = model.getStockInHandReport2(orgUnitMode, orgUnitId)
//            .compose(HICUtilRxHelper.applySchedulers())
//            .subscribe((rows1) -> {
//                view.updateRow1(rows1);
//            }, e -> {
//                e.printStackTrace();
//            });
//    }


    @Override
    public void onStop() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
