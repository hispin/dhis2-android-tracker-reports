package org.hispindia.bidtrackerreports.mvp.model.remote.api;

import com.fasterxml.jackson.databind.JsonNode;

import org.hisp.dhis.android.sdk.controllers.ApiEndpointContainer;
import org.hisp.dhis.android.sdk.persistence.models.Enrollment;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityInstance;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResBIDEvents;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResOverdue;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResSchvaccine;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by nhancao on 1/20/16.
 */
public interface HIIApiDhis2 {

    @GET("/" + ApiEndpointContainer.EVENTS + "?skipPaging=true")
    Observable<HIResBIDEvents> getEvents(@Query("orgUnit") String orgUnitUid, @Query("ouMode") String ouModeUid, @Query("programStage") String programStageUid);

    @GET("/" + ApiEndpointContainer.TRACKED_ENTITY_INSTANCES + "/{trackedEntityInstanceUid}")
    TrackedEntityInstance getTrackedEntityInstancebyUid(@Path("trackedEntityInstanceUid") String trackedEntityInstanceUid);

    @GET("/" + ApiEndpointContainer.ENROLLMENTS + "/{enrollmentUid}")
    Enrollment getEnrollmentbyUid(@Path("enrollmentUid") String enrollmentUid);

    @GET("/" + ApiEndpointContainer.EVENTS + "?skipPaging=true")
    HIResBIDEvents getEvents(@Query("trackedEntityInstance") String trackedEntityInstanceUid);

    @GET("/sqlViews/{orgUnitModeId}/data")
    Observable<JsonNode> getStockReport(@Path("orgUnitModeId") String orgUnitModeId, @Query("var") String orgUnitLevel, @Query("var") String orgUnitId);

    //startDate=2016-01-30&endDate=2016-03-31
    @GET("/events/eventRows?skipPaging=true&programStatus=ACTIVE&eventStatus=SCHEDULE")
    Observable<HIResSchvaccine> getSchvaccineReport(@Query("orgUnit") String orgUnitId, @Query("ouMode") String ouMode, @Query("program") String programId, @Query("startDate") String startDate, @Query("endDate") String endDate);

    @GET("/events/eventRows?skipPaging=true&programStatus=ACTIVE&eventStatus=OVERDUE")
    Observable<HIResOverdue> getOverdueReport(@Query("orgUnit") String orgUnitId, @Query("ouMode") String ouMode, @Query("program") String programId);

}
