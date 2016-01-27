package org.hispindia.bidtrackerreports.mvp.model;

import android.app.Application;

import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIIApiDhis2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock;

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

    public Observable<HIResStock> getStockReport(String orgUnitModeId, int orgUnitLevel, String orgUnitId) {
        return Observable.defer(() -> getApiModel().getStockReport(orgUnitModeId, "level:" + orgUnitLevel, "ouuid:" + orgUnitId)
                .map(HIResStock::new));
    }

    public HIIApiDhis2 getApiModel() {
        return apiModel;
    }

    public Application getApplication() {
        return application;
    }
}
