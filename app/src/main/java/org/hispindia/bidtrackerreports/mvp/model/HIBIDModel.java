package org.hispindia.bidtrackerreports.mvp.model;

import android.app.Application;
import android.util.Log;

import org.hisp.dhis.android.sdk.controllers.metadata.MetaDataController;
import org.hisp.dhis.android.sdk.persistence.models.DataValue;
import org.hisp.dhis.android.sdk.persistence.models.Enrollment;
import org.hisp.dhis.android.sdk.persistence.models.Event;
import org.hisp.dhis.android.sdk.persistence.models.Program;
import org.hisp.dhis.android.sdk.persistence.models.ProgramIndicator;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRule;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRuleAction;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRuleVariable;
import org.hisp.dhis.android.sdk.persistence.models.ProgramStage;
import org.hisp.dhis.android.sdk.persistence.models.ProgramStageDataElement;
import org.hisp.dhis.android.sdk.persistence.models.ProgramTrackedEntityAttribute;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityAttributeValue;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityInstance;
import org.hisp.dhis.android.sdk.utils.comparators.ProgramRulePriorityComparator;
import org.hisp.dhis.android.sdk.utils.services.ProgramIndicatorService;
import org.hisp.dhis.android.sdk.utils.services.ProgramRuleService;
import org.hisp.dhis.android.sdk.utils.services.VariableService;
import org.hispindia.bidtrackerreports.event.HIEvent;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDRow;
import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIIApiDhis2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.BIDEvents;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

import static org.hispindia.android.core.utils.HICUtilRxHelper.onNext;

/**
 * Created by nhancao on 1/22/16.
 */
public class HIBIDModel {
    public final static String TAG = HIBIDModel.class.getSimpleName();

    private final Application application;
    private final HIIApiDhis2 apiModel;

    private HIBIDRow headerRow;
    private Program program;
    private Event event;
    private Enrollment enrollment;
    private ProgramStage programStage;

    private Map<String, List<ProgramRule>> programRulesForDataElements;
    private Map<String, List<ProgramIndicator>> programIndicatorsForDataElements;
    private List<String> affectedFieldsWithValue;

    public HIBIDModel(Application application, HIIApiDhis2 apiModel) {
        this.application = application;
        this.apiModel = apiModel;
    }

    /**
     * Initial db for report
     *
     * @param programId
     * @param programStageUid
     * @return bid header row report
     */
    public HIBIDRow initialDBReport(String programId, String programStageUid) {
        this.program = MetaDataController.getProgram(programId);
        this.programStage = MetaDataController.getProgramStage(programStageUid);
        headerRow = new HIBIDRow();
        headerRow.setTEAKeyList(MetaDataController.getProgramTrackedEntityAttributes(programId));
        headerRow.setDEKeyList(MetaDataController.getProgramStageDataElements(MetaDataController.getProgramStage(programStageUid)));
        return headerRow;
    }

    public void initial(Event event, Enrollment enrollment) {
        this.event = event;
        this.enrollment = enrollment;
        VariableService.reset();
    }

    public void evaluateAndApplyProgramRules() {
        VariableService.initialize(enrollment, event);
        mapFieldsToRulesAndIndicators();
        affectedFieldsWithValue = new ArrayList<>();
        List<ProgramRule> programRules = program.getProgramRules();
        Collections.sort(programRules, new ProgramRulePriorityComparator());
        for (ProgramRule programRule : programRules) {
            boolean evaluatedTrue = ProgramRuleService.evaluate(programRule.getCondition());
            for (ProgramRuleAction action : programRule.getProgramRuleActions()) {
                if (evaluatedTrue) {
                    applyProgramRuleAction(action, affectedFieldsWithValue);
                }
            }
        }
    }

    public void mapFieldsToRulesAndIndicators() {
        setProgramRulesForDataElements(new HashMap<>());
        setProgramIndicatorsForDataElements(new HashMap<>());
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

    protected void applyProgramRuleAction(ProgramRuleAction programRuleAction, List<String> affectedFieldsWithValue) {

        switch (programRuleAction.getProgramRuleActionType()) {
            case HIDEFIELD: {
                if (programRuleAction.getDataElement() != null) {
                    affectedFieldsWithValue.add(programRuleAction.getDataElement());
                    Log.e(TAG, "applyHideFieldRuleAction: " + MetaDataController.getDataElement(programRuleAction.getDataElement()).getName());
                }
                break;
            }
            case ASSIGN: {
                applyAssignRuleAction(programRuleAction);
                break;
            }
        }
    }

    protected void applyAssignRuleAction(ProgramRuleAction programRuleAction) {
        String stringResult = ProgramRuleService.getCalculatedConditionValue(programRuleAction.getData());
        String programRuleVariableName = programRuleAction.getContent();
        ProgramRuleVariable programRuleVariable;
        if (programRuleVariableName != null) {
            programRuleVariableName = programRuleVariableName.substring(2, programRuleVariableName.length() - 1);
            programRuleVariable = VariableService.getInstance().getProgramRuleVariableMap().get(programRuleVariableName);
            programRuleVariable.setVariableValue(stringResult);
            programRuleVariable.setHasValue(true);
        }
    }

    public void getEventBIDRow(Subscriber<? super Object> subscriber, List<Event> eventBIDList) {
        int count = 0;
        for (Event eventItem : eventBIDList) {
            if (eventItem.getStatus().equals("SCHEDULE")) {
                TrackedEntityInstance trackedEntityInstance = getTrackedEntityInstancebyUid(eventItem.getTrackedEntityInstance());
                Enrollment enrollment = getEnrollmentbyUid(eventItem.getEnrollment());
                BIDEvents eventTrackedEntityInstance = getEvents(eventItem.getTrackedEntityInstance());
                enrollment.setEvents(eventTrackedEntityInstance.getEventList());
                Log.e(TAG, "getAllBIDEvent: " + (++count));

                Log.e(TAG, "getAllBIDEvent: due day=" + eventItem.getDueDate());
                for (ProgramTrackedEntityAttribute attRoot : headerRow.getTEAKeyList()) {
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
                            for (ProgramStageDataElement deRoot : headerRow.getDEKeyList()) {
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

                initial(eventItem, enrollment);
                evaluateAndApplyProgramRules();
                onNext(subscriber, new HIBIDRow());

                if (count == 5)
                    break;
            }
        }
    }

    public Observable<BIDEvents> getEvents(String orgUnitUid, String ouModeUid, String programStageUid) {
        return Observable.defer(() -> apiModel.getEvents(orgUnitUid, ouModeUid, programStageUid));
    }

    public TrackedEntityInstance getTrackedEntityInstancebyUid(String trackedEntityInstanceUid) {
        return apiModel.getTrackedEntityInstancebyUid(trackedEntityInstanceUid);
    }

    public Enrollment getEnrollmentbyUid(String enrollmentUid) {
        return apiModel.getEnrollmentbyUid(enrollmentUid);
    }

    public BIDEvents getEvents(String trackedEntityInstanceUid) {
        return apiModel.getEvents(trackedEntityInstanceUid);
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
}
