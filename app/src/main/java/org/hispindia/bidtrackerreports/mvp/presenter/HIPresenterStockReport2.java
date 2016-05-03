package org.hispindia.bidtrackerreports.mvp.presenter;

import org.hispindia.bidtrackerreports.mvp.model.HIStockModel;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewStockReport;
import org.hispindia.bidtrackerreports.mvp.view.HIViewStockInHandReport;
import org.hispindia.bidtrackerreports.utils.HICUtilRxHelper;

import rx.Subscription;

/**
 * Created by Sourabh on 1/24/16.
 */
public class HIPresenterStockReport2 implements HIIPresenterBase<HIIViewStockReport> {
    public final static String TAG = HIPresenterStockReport2.class.getSimpleName();

    private final HIStockModel model;
    private Subscription subscription;

    public HIPresenterStockReport2(HIStockModel model) {
        this.model = model;
    }

    @Override
    public void onStart(HIIViewStockReport view) {

    }

    public void getStockInHandReport2(HIViewStockInHandReport view, String orgUnitMode, String orgUnitId) {
        onStop();

        subscription = model.getStockInHandReport2(orgUnitMode, orgUnitId)
            .compose(HICUtilRxHelper.applySchedulers())
            .subscribe((rows2) -> {
                view.updateRow2(rows2);
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
