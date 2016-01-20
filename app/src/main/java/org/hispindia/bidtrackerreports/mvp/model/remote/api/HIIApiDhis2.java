package org.hispindia.bidtrackerreports.mvp.model.remote.api;

import org.hisp.dhis.android.sdk.persistence.models.SystemInfo;

import retrofit.http.GET;

/**
 * Created by nhancao on 1/20/16.
 */
public interface HIIApiDhis2 {

    @GET("/system/info/")
    SystemInfo getSystemInfo();




}
