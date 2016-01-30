package org.hispindia.bidtrackerreports.mvp.model;

import android.app.Application;

import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIIApiDhis2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResSchvaccine;

import rx.Observable;

/**
 * Created by nhancao on 1/22/16.
 */
public class HISchvaccineModel {
    public final static String TAG = HISchvaccineModel.class.getSimpleName();

    private final Application application;
    private final HIIApiDhis2 apiModel;

    public HISchvaccineModel(Application application, HIIApiDhis2 apiModel) {
        this.application = application;
        this.apiModel = apiModel;
    }

    public Observable<HIResSchvaccine> getSchvaccineReport(String orgUnitId, String ouMode, String programId, String startDate, String endDate) {
        return Observable.defer(() -> getApiModel().getSchvaccineReport(orgUnitId, ouMode, programId, startDate, endDate));
    }

    public HIIApiDhis2 getApiModel() {
        return apiModel;
    }

    public Application getApplication() {
        return application;
    }
}
