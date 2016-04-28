package org.hispindia.bidtrackerreports.mvp.model;

import android.app.Application;

import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIIApiDhis2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResOverdue;

import rx.Observable;

/**
 * Created by Sourabh on 2/2/2016.
 */
public class HIOverdueModel {

    public final static String TAG = HIOverdueModel.class.getSimpleName();

    private final Application application;
    private final HIIApiDhis2 apiModel;

    public HIOverdueModel(Application application, HIIApiDhis2 apiModel) {
        this.application = application;
        this.apiModel = apiModel;
    }

    public Observable<HIResOverdue> getOverdueReport(String orgUnitId, String ouMode, String programId) {
        return Observable.defer(() -> getApiModel().getOverdueReport(orgUnitId, ouMode, programId));
        //return Observable.defer(() -> getApiModel().getOverdueReport());

    }



    public HIIApiDhis2 getApiModel() {
        return apiModel;
    }

    public Application getApplication() {
        return application;
    }
}
