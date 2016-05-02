package org.hispindia.bidtrackerreports.mvp.model;

import android.app.Application;

import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIIApiDhis2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock1;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock3;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock4;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock5;

import rx.Observable;

/**
 * Created by nhancao on 1/22/16.
 */
public class HIStockModel {
    public final static String TAG = HIStockModel.class.getSimpleName();

    private final Application application;
    private final HIIApiDhis2 apiModel;

    public HIStockModel(Application application, HIIApiDhis2 apiModel) {
        this.application = application;
        this.apiModel = apiModel;
    }

    public Observable<HIResStock> getStockReport(String orgUnitMode, int orgUnitLevel, String orgUnitId) {
        return Observable.defer(() -> getApiModel().getStockReport(orgUnitMode, "level:" + orgUnitLevel, "ouuid:" + orgUnitId)
                .map(HIResStock::new));
    }

    public Observable<HIResStock> getStockInHandReport(String orgUnitMode, String orgUnitId) {
        return Observable.defer(() -> getApiModel().getStockInHandReport(orgUnitMode, "ouuid:" + orgUnitId)
                .map(HIResStock::new));
    }

    public Observable<HIResStock1> getStockInHandReport1(String orgUnitMode, String orgUnitId) {
        return Observable.defer(() -> getApiModel().getStockInHandReport1(orgUnitMode, "ouuid:" + orgUnitId)
                .map(HIResStock1::new));
    }

    public Observable<HIResStock2> getStockInHandReport2(String orgUnitMode, String orgUnitId) {
        return Observable.defer(() -> getApiModel().getStockInHandReport2(orgUnitMode, "ouuid:" + orgUnitId)
                .map(HIResStock2::new));
    }

    public Observable<HIResStock3> getStockInHandReport3(String orgUnitMode, String orgUnitId) {
        return Observable.defer(() -> getApiModel().getStockInHandReport3(orgUnitMode, "ouuid:" + orgUnitId)
                .map(HIResStock3::new));
    }

    public Observable<HIResStock4> getStockInHandReport4(String orgUnitMode, String orgUnitId) {
        return Observable.defer(() -> getApiModel().getStockInHandReport4(orgUnitMode, "ouuid:" + orgUnitId)
                .map(HIResStock4::new));
    }

    public Observable<HIResStock5> getStockInHandReport5(String orgUnitMode, String orgUnitId) {
        return Observable.defer(() -> getApiModel().getStockInHandReport5(orgUnitMode, "ouuid:" + orgUnitId)
                .map(HIResStock5::new));
    }





    public HIIApiDhis2 getApiModel() {
        return apiModel;
    }

    public Application getApplication() {
        return application;
    }
}
