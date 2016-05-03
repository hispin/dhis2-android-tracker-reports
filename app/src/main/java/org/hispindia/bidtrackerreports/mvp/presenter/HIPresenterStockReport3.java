package org.hispindia.bidtrackerreports.mvp.presenter;

import org.hispindia.bidtrackerreports.mvp.model.HIStockModel;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewStockReport;
import org.hispindia.bidtrackerreports.mvp.view.HIViewStockInHandReport;
import org.hispindia.bidtrackerreports.utils.HICUtilRxHelper;

import rx.Subscription;

/**
 * Created by Sourabh on 1/24/16.
 */
public class HIPresenterStockReport3 implements HIIPresenterBase<HIIViewStockReport> {
    public final static String TAG = HIPresenterStockReport3.class.getSimpleName();

    private final HIStockModel model;
    private Subscription subscription;

    public HIPresenterStockReport3(HIStockModel model) {
        this.model = model;
    }

    @Override
    public void onStart(HIIViewStockReport view) {

    }

    public void getStockInHandReport3(HIViewStockInHandReport view, String orgUnitMode, String orgUnitId) {
        onStop();

        subscription = model.getStockInHandReport3(orgUnitMode, orgUnitId)
            .compose(HICUtilRxHelper.applySchedulers())
            .subscribe((rows3) -> {
                view.updateRow3(rows3);
            }, e -> {
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
