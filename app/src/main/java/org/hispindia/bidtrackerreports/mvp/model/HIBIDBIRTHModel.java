package org.hispindia.bidtrackerreports.mvp.model;

import android.app.Application;

import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIIApiDhis2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResBIDBirthEvents;

import rx.Observable;


/**
 * Created by nhancao on 1/22/16.
 */
public class HIBIDBIRTHModel {
    public final static String TAG = HIBIDBIRTHModel.class.getSimpleName();

    private final Application application;
    private final HIIApiDhis2 apiModel;
    public HIBIDBIRTHModel(Application application, HIIApiDhis2 apiModel) {
        this.application = application;
        this.apiModel = apiModel;
    }

    public Observable<HIResBIDBirthEvents> getEventss(String orgUnitUid, String ouModeUid, String programStageUid) {
        return Observable.defer(() -> getApiModel().getEventss(orgUnitUid, ouModeUid, programStageUid));
    }

    public HIIApiDhis2 getApiModel() {
        return apiModel;
    }

    public Application getApplication() {
        return application;
    }

}
