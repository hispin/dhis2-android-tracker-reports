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
import org.hisp.dhis.android.sdk.persistence.models.Program;
import org.hisp.dhis.android.sdk.persistence.models.ProgramIndicator;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRule;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRuleAction;
import org.hisp.dhis.android.sdk.persistence.models.ProgramStage;
import org.hisp.dhis.android.sdk.persistence.models.ProgramStageDataElement;
import org.hisp.dhis.android.sdk.persistence.models.ProgramTrackedEntityAttribute;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityAttributeValue;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityInstance;
import org.hisp.dhis.android.sdk.utils.StringConverter;
import org.hisp.dhis.android.sdk.utils.services.ProgramIndicatorService;
import org.hisp.dhis.android.sdk.utils.services.ProgramRuleService;
import org.hisp.dhis.android.sdk.utils.services.VariableService;
import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIIApiDhis2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.BIDEvents;
import org.hispindia.bidtrackerreports.mvp.presenter.HIProgramRule;
import org.hispindia.bidtrackerreports.mvp.presenter.HIRulesHelper;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();
        return restAdapter.create(HIIApiDhis2.class);
    }

    public static HIIApiDhis2 getHIIApiDhis2() {
        return createService(DhisController.getInstance().getSession().getServerUrl(), DhisController.getInstance().getSession().getCredentials());
    }

    public static void getAllBIDEvent(String orgUnitUid, String ouModeUid, String programId, String programStageUid) {

        new HIApplyRule(orgUnitUid, ouModeUid, programId, programStageUid).execute();

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
}

class HIApplyRule extends AsyncTask<Void, Void, Void> implements HIRulesHelper.HIIProgramRuleHelper {
    public final static String TAG = HIApplyRule.class.getSimpleName();
    HIProgramRule programRule;
    Program program;
    Event event;
    Enrollment enrollment;
    ProgramStage programStage;
    Map<String, DataValue> dataValues;
    Map<String, TrackedEntityAttributeValue> trackedEntityAttributeValues;
    String orgUnitUid;
    String ouModeUid;
    String programId;
    String programStageUid;
    private Map<String, List<ProgramRule>> programRulesForDataElements;
    private Map<String, List<ProgramIndicator>> programIndicatorsForDataElements;

    public HIApplyRule(String orgUnitUid, String ouModeUid, String programId, String programStageUid) {
        this.orgUnitUid = orgUnitUid;
        this.ouModeUid = ouModeUid;
        this.programId = programId;
        this.programStageUid = programStageUid;
        programStage = MetaDataController.getProgramStage(programStageUid);
        program = MetaDataController.getProgram(programId);
        programRule = new HIProgramRule();
        programRule.setProgramRuleHelper(this);
    }

    void initial(Event event, Enrollment enrollment) {
        this.event = event;
        this.enrollment = enrollment;
    }

    public Map<String, List<ProgramRule>> getProgramRulesForDataElements() {
        return programRulesForDataElements;
    }

    public void setProgramRulesForDataElements(Map<String, List<ProgramRule>> programRulesForDataElements) {
        this.programRulesForDataElements = programRulesForDataElements;
    }

    public Map<String, List<ProgramIndicator>> getProgramIndicatorsForDataElements() {
        return programIndicatorsForDataElements;
    }

    public void setProgramIndicatorsForDataElements(Map<String, List<ProgramIndicator>> programIndicatorsForDataElements) {
        this.programIndicatorsForDataElements = programIndicatorsForDataElements;
    }

    @Override
    protected Void doInBackground(Void... params) {
        //due day, is overdue?, Tracked Entity Attribute, Data element
        List<ProgramTrackedEntityAttribute> programTrackedEntityAttributes = MetaDataController.getProgramTrackedEntityAttributes(programId);
        List<ProgramStageDataElement> programStageDataElements = MetaDataController.getProgramStageDataElements(programStage);
        Log.e(TAG, "doInBackground: att size = " + programTrackedEntityAttributes.size() + "; dataE size = " + programStageDataElements.size());

        BIDEvents bidEvents = HIBIDModel.getHIIApiDhis2().getEvents(orgUnitUid, ouModeUid, programStageUid);
        List<Event> eventBIDList = bidEvents.getEventList();
        int count = 0;
        for (Event eventItem : eventBIDList) {
            if (eventItem.getStatus().equals("SCHEDULE")) {
                TrackedEntityInstance trackedEntityInstance = HIBIDModel.getHIIApiDhis2().getTrackedEntityInstancebyUid(eventItem.getTrackedEntityInstance());
                Enrollment enrollment = HIBIDModel.getHIIApiDhis2().getEnrollmentbyUid(eventItem.getEnrollment());
                BIDEvents eventTrackedEntityInstance = HIBIDModel.getHIIApiDhis2().getEvents(eventItem.getTrackedEntityInstance());
                enrollment.setEvents(eventTrackedEntityInstance.getEventList());
                Log.e(TAG, "getAllBIDEvent: " + (++count));

                Log.e(TAG, "getAllBIDEvent: due day=" + eventItem.getDueDate());
                for (ProgramTrackedEntityAttribute attRoot : programTrackedEntityAttributes) {
                    for (TrackedEntityAttributeValue att : trackedEntityInstance.getAttributes()) {
                        if (att.getTrackedEntityAttributeId().equals(attRoot.getTrackedEntityAttributeId())) {
                            Log.e(TAG, "getAllBIDEvent: " + attRoot.getTrackedEntityAttribute().getDisplayName() + " = " + att.getValue());
                        }
                    }
                }
                Log.e(TAG, "getAllBIDEvent: ");
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

                if (eventItem.getEventDate() == null) {
                    eventItem.setEventDate(DateTime.now().toString());
                }
                VariableService.reset();
                initial(eventItem, enrollment);
                programRule.evaluateAndApplyProgramRules();
                if (count == 5)
                    break;
            }
        }

        return null;
    }

    @Override
    public void mapFieldsToRulesAndIndicators() {
        setProgramRulesForDataElements(new HashMap<String, List<ProgramRule>>());
        setProgramIndicatorsForDataElements(new HashMap<String, List<ProgramIndicator>>());
        for (ProgramRule programRule : program.getProgramRules()) {
            for (String dataElement : ProgramRuleService.getDataElementsInRule(programRule)) {
                List<ProgramRule> rulesForDataElement = getProgramRulesForDataElements().get(dataElement);
                if (rulesForDataElement == null) {
                    rulesForDataElement = new ArrayList<>();
                    rulesForDataElement.add(programRule);
                    getProgramRulesForDataElements().put(dataElement, rulesForDataElement);
                } else {
                    rulesForDataElement.add(programRule);
                }
            }
        }
        for (ProgramIndicator programIndicator : programStage.getProgramIndicators()) {
            for (String dataElement : ProgramIndicatorService.getDataElementsInExpression(programIndicator)) {
                List<ProgramIndicator> programIndicatorsForDataElement = getProgramIndicatorsForDataElements().get(dataElement);
                if (programIndicatorsForDataElement == null) {
                    programIndicatorsForDataElement = new ArrayList<>();
                    programIndicatorsForDataElement.add(programIndicator);
                    getProgramIndicatorsForDataElements().put(dataElement, programIndicatorsForDataElement);
                } else {
                    programIndicatorsForDataElement.add(programIndicator);
                }
            }
        }
    }

    @Override
    public List<ProgramRule> getProgramRules() {
        return program.getProgramRules();
    }

    @Override
    public Enrollment getEnrollment() {
        return enrollment;
    }

    @Override
    public Event getEvent() {
        return event;
    }

    @Override
    public void applyHideFieldRuleAction(ProgramRuleAction programRuleAction, List<String> affectedFieldsWithValue) {
        if (programRuleAction.getDataElement() != null) {
            Log.e(TAG, "applyHideFieldRuleAction: " + MetaDataController.getDataElement(programRuleAction.getDataElement()).getName());
        }

    }

}