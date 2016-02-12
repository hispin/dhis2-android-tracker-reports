package org.hispindia.bidtrackerreports.mvp.model;

import android.app.Application;

import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIIApiDhis2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResBIDEvents;

import rx.Observable;


/**
 * Created by nhancao on 1/22/16.
 */
public class HIBIDModel {
    public final static String TAG = HIBIDModel.class.getSimpleName();

    private final Application application;
    private final HIIApiDhis2 apiModel;
    public HIBIDModel(Application application, HIIApiDhis2 apiModel) {
        this.application = application;
        this.apiModel = apiModel;
    }

    public Observable<HIResBIDEvents> getEvents(String orgUnitUid, String ouModeUid, String programStageUid) {
        return Observable.defer(() -> getApiModel().getEvents(orgUnitUid, ouModeUid, programStageUid));
    }

    public HIIApiDhis2 getApiModel() {
        return apiModel;
    }

    public Application getApplication() {
        return application;
    }

}
