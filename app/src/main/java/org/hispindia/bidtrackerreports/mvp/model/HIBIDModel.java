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
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityAttributeValue;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityInstance;
import org.hisp.dhis.android.sdk.utils.comparators.ProgramRulePriorityComparator;
import org.hisp.dhis.android.sdk.utils.services.ProgramIndicatorService;
import org.hisp.dhis.android.sdk.utils.services.ProgramRuleService;
import org.hisp.dhis.android.sdk.utils.services.VariableService;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDRow;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDRowItem;
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
        setProgram(MetaDataController.getProgram(programId));
        setProgramStage(MetaDataController.getProgramStage(programStageUid));
        setHeaderRow(new HIBIDRow());
        getHeaderRow().setTrackedEntityAttributeListMapping(MetaDataController.getProgramTrackedEntityAttributes(programId));
        getHeaderRow().setDataElementListMapping(MetaDataController.getProgramStageDataElements(MetaDataController.getProgramStage(programStageUid)));
        return getHeaderRow();
    }

    public void initial(Event event, Enrollment enrollment) {
        setEvent(event);
        setEnrollment(enrollment);
        VariableService.reset();
    }

    public void evaluateAndApplyProgramRules() {
        VariableService.initialize(getEnrollment(), getEvent());
        mapFieldsToRulesAndIndicators();
        setAffectedFieldsWithValue(new ArrayList<>());
        List<ProgramRule> programRules = getProgram().getProgramRules();
        Collections.sort(programRules, new ProgramRulePriorityComparator());
        for (ProgramRule programRule : programRules) {
            boolean evaluatedTrue = ProgramRuleService.evaluate(programRule.getCondition());
            for (ProgramRuleAction action : programRule.getProgramRuleActions()) {
                if (evaluatedTrue) {
                    applyProgramRuleAction(action, getAffectedFieldsWithValue());
                }
            }
        }
    }

    public void mapFieldsToRulesAndIndicators() {
        setProgramRulesForDataElements(new HashMap<>());
        setProgramIndicatorsForDataElements(new HashMap<>());
        for (ProgramRule programRule : getProgram().getProgramRules()) {
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
        for (ProgramIndicator programIndicator : getProgramStage().getProgramIndicators()) {
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
        int order = 0;
        for (int count = 0; count < eventBIDList.size(); count++) {
            Event eventItem = eventBIDList.get(count);
            if (eventItem.getStatus().equals("SCHEDULE")) {
                List<HIBIDRowItem> trackedEntityAttributeList = createNewAttributeRowList();
                List<HIBIDRowItem> dataElementList = createDataElementRowList();

                TrackedEntityInstance trackedEntityInstance = getTrackedEntityInstancebyUid(eventItem.getTrackedEntityInstance());
                Enrollment enrollment = getEnrollmentbyUid(eventItem.getEnrollment());
                BIDEvents eventTrackedEntityInstance = getEvents(eventItem.getTrackedEntityInstance());
                enrollment.setEvents(eventTrackedEntityInstance.getEventList());
                Log.e(TAG, "getAllBIDEvent: " + (order));

                Log.e(TAG, "getAllBIDEvent: due day=" + eventItem.getDueDate());
                for (HIBIDRowItem attRoot : trackedEntityAttributeList) {
                    for (TrackedEntityAttributeValue att : trackedEntityInstance.getAttributes()) {
                        if (att.getTrackedEntityAttributeId().equals(attRoot.getId())) {
                            attRoot.setValue(att.getValue());
                            Log.e(TAG, "getAllBIDEvent: " + attRoot.getName() + " = " + attRoot.getValue());
                        }
                    }
                }
                Log.e(TAG, "getAllBIDEvent: ");
                for (int i = eventTrackedEntityInstance.getEventList().size() - 1; i >= 0; i--) {
                    List<DataValue> dataValues = eventTrackedEntityInstance.getEventList().get(i).getDataValues();
                    if (dataValues != null) {
                        for (int j = 0; j < dataValues.size(); j++) {
                            String dataElementId = dataValues.get(j).getDataElement();
                            for (HIBIDRowItem deRoot : dataElementList) {
                                if (dataElementId.equals(deRoot.getId())) {
                                    deRoot.setValue(dataValues.get(j).getValue());
                                    Log.e(TAG, "getAllBIDEvent: " + deRoot.getName() + " = " + deRoot.getValue());
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
                for (String item : affectedFieldsWithValue) {
                    for (HIBIDRowItem de : dataElementList) {
                        if (de.getValue() != null) break;
                        if (item.equals(de.getId())) {
                            de.setValue("-*#hidefield#*-");
                            Log.e(TAG, "getEventBIDRow: " + de.getName() + " - " + de.getValue());
                        }
                    }
                }

                HIBIDRow row = new HIBIDRow();
                row.setOrder(++order);
                row.setEventId((order) + "*" + eventItem.getEvent());
                row.setDueDate(eventItem.getDueDate());
                row.setIsOverDue(DateTime.parse(eventItem.getDueDate()).isBeforeNow());
                row.setTrackedEntityAttributeList(trackedEntityAttributeList);
                row.setDataElementList(dataElementList);

                onNext(subscriber, row);

                if (order == 5)
                    break;
            }
            if (count == eventBIDList.size() - 1) {
                onNext(subscriber, null);
            }
        }
    }

    public Observable<BIDEvents> getEvents(String orgUnitUid, String ouModeUid, String programStageUid) {
        return Observable.defer(() -> getApiModel().getEvents(orgUnitUid, ouModeUid, programStageUid));
    }

    public TrackedEntityInstance getTrackedEntityInstancebyUid(String trackedEntityInstanceUid) {
        return getApiModel().getTrackedEntityInstancebyUid(trackedEntityInstanceUid);
    }

    public Enrollment getEnrollmentbyUid(String enrollmentUid) {
        return getApiModel().getEnrollmentbyUid(enrollmentUid);
    }

    public BIDEvents getEvents(String trackedEntityInstanceUid) {
        return getApiModel().getEvents(trackedEntityInstanceUid);
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

    public HIIApiDhis2 getApiModel() {
        return apiModel;
    }

    public HIBIDRow getHeaderRow() {
        return headerRow;
    }

    public void setHeaderRow(HIBIDRow headerRow) {
        this.headerRow = headerRow;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public ProgramStage getProgramStage() {
        return programStage;
    }

    public void setProgramStage(ProgramStage programStage) {
        this.programStage = programStage;
    }

    public List<String> getAffectedFieldsWithValue() {
        return affectedFieldsWithValue;
    }

    public void setAffectedFieldsWithValue(List<String> affectedFieldsWithValue) {
        this.affectedFieldsWithValue = affectedFieldsWithValue;
    }

    public List<HIBIDRowItem> createNewAttributeRowList() {
        List<HIBIDRowItem> rowList = new ArrayList<>();
        for (HIBIDRowItem item : getHeaderRow().getTrackedEntityAttributeList()) {
            HIBIDRowItem i = new HIBIDRowItem(item.getId(), item.getName(), null);
            rowList.add(i);
        }
        return rowList;
    }

    public List<HIBIDRowItem> createDataElementRowList() {
        List<HIBIDRowItem> rowList = new ArrayList<>();
        for (HIBIDRowItem item : getHeaderRow().getDataElementList()) {
            HIBIDRowItem i = new HIBIDRowItem(item.getId(), item.getName(), null);
            rowList.add(i);
        }
        return rowList;
    }

    public Application getApplication() {
        return application;
    }
}
