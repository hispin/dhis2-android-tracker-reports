package org.hispindia.bidtrackerreports.mvp.model;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.HttpUrl;

import org.hisp.dhis.android.sdk.controllers.DhisController;
import org.hisp.dhis.android.sdk.controllers.metadata.MetaDataController;
import org.hisp.dhis.android.sdk.network.APIException;
import org.hisp.dhis.android.sdk.network.Credentials;
import org.hisp.dhis.android.sdk.network.RepoManager;
import org.hisp.dhis.android.sdk.persistence.models.DataValue;
import org.hisp.dhis.android.sdk.persistence.models.Enrollment;
import org.hisp.dhis.android.sdk.persistence.models.Event;
import org.hisp.dhis.android.sdk.persistence.models.ProgramStage;
import org.hisp.dhis.android.sdk.persistence.models.ProgramStageDataElement;
import org.hisp.dhis.android.sdk.persistence.models.ProgramTrackedEntityAttribute;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityAttributeValue;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityInstance;
import org.hisp.dhis.android.sdk.utils.StringConverter;
import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIIApiDhis2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.BIDEvents;

import java.util.List;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.converter.JacksonConverter;

/**
 * Created by nhancao on 1/22/16.
 */
public class HIBIDModel {
    public final static String TAG = HIBIDModel.class.getSimpleName();

    private static String provideServerUrl(HttpUrl httpUrl) {
        return httpUrl.newBuilder()
                .addPathSegment("api")
                .build().toString();
    }

    private static Converter provideJacksonConverter() {
        return new JacksonConverter(DhisController.getInstance().getObjectMapper());
    }

    private static OkClient provideOkClient(Credentials credentials) {
        return new OkClient(RepoManager.provideOkHttpClient(credentials));
    }

    private static HIIApiDhis2 createService(HttpUrl serverUrl, Credentials credentials) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(provideServerUrl(serverUrl))
                .setConverter(provideJacksonConverter())
                .setClient(provideOkClient(credentials))
                .setErrorHandler(new RetrofitErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(HIIApiDhis2.class);
    }

    private static class RetrofitErrorHandler implements ErrorHandler {

        @Override
        public Throwable handleError(RetrofitError cause) {
            Log.d("RepoManager", "there was an error.." + cause.getKind().name());
            try {
                String body = new StringConverter().fromBody(cause.getResponse().getBody(), String.class);
                Log.e("RepoManager", body);
            } catch (ConversionException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return APIException.fromRetrofitError(cause);
        }
    }

    private static HIIApiDhis2 getHIIApiDhis2() {
        return createService(DhisController.getInstance().getSession().getServerUrl(), DhisController.getInstance().getSession().getCredentials());
    }


    public static void getAllBIDEvent(String orgUnitUid, String ouModeUid, String programId, String programStageUid) {

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                //due day, is overdue?, Tracked Entity Attribute, Data element
                List<ProgramTrackedEntityAttribute> programTrackedEntityAttributes = MetaDataController.getProgramTrackedEntityAttributes(programId);
                ProgramStage programStage = new ProgramStage();
                programStage.setUid(programStageUid);
                List<ProgramStageDataElement> programStageDataElements = MetaDataController.getProgramStageDataElements(programStage);
                Log.e(TAG, "doInBackground: att size = " + programTrackedEntityAttributes.size() + "; dataE size = " + programStageDataElements.size());

                BIDEvents bidEvents = getHIIApiDhis2().getEvents(orgUnitUid, ouModeUid, programStageUid);
                List<Event> eventBIDList = bidEvents.getEventList();
                int count = 0;
                for (Event eventItem : eventBIDList) {
                    if (eventItem.getStatus().equals("SCHEDULE")) {
                        TrackedEntityInstance trackedEntityInstance = getHIIApiDhis2().getTrackedEntityInstancebyUid(eventItem.getTrackedEntityInstance());
                        Enrollment enrollment = getHIIApiDhis2().getEnrollmentbyUid(eventItem.getEnrollment());
                        BIDEvents eventTrackedEntityInstance = getHIIApiDhis2().getEvents(eventItem.getTrackedEntityInstance());
                        Log.e(TAG, "getAllBIDEvent: " + (++count));

                        Log.e(TAG, "getAllBIDEvent: due day=" + eventItem.getDueDate());
                        for (ProgramTrackedEntityAttribute attRoot : programTrackedEntityAttributes) {
                            for (TrackedEntityAttributeValue att : trackedEntityInstance.getAttributes()) {
                                if (att.getTrackedEntityAttributeId().equals(attRoot.getTrackedEntityAttributeId())) {
                                    Log.e(TAG, "getAllBIDEvent: " + attRoot.getTrackedEntityAttribute().getDisplayName() + " = " + att.getValue());
                                }
                            }
                        }

                        for (int i = eventTrackedEntityInstance.getEventList().size() - 1; i >= 0; i--) {
                            List<DataValue> dataValues = eventTrackedEntityInstance.getEventList().get(i).getDataValues();
                            if (dataValues != null) {
                                for (int j = 0; j < dataValues.size(); j++) {
                                    String dataElementId = dataValues.get(j).getDataElement();
                                    for (ProgramStageDataElement deRoot : programStageDataElements) {
                                        if (dataElementId.equals(deRoot.getDataelement())) {
                                            Log.e(TAG, "getAllBIDEvent: " + deRoot.getDataElement().getDisplayName() + " = " + dataValues.get(j).getValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return null;
            }
        }.execute();


    }
}
