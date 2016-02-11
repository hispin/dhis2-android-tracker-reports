package org.hispindia.bidtrackerreports.mvp.presenter;

import android.support.v4.app.Fragment;
import android.util.Log;

import org.hisp.dhis.android.sdk.controllers.DhisController;
import org.hisp.dhis.android.sdk.controllers.metadata.MetaDataController;
import org.hisp.dhis.android.sdk.controllers.tracker.TrackerController;
import org.hisp.dhis.android.sdk.persistence.models.DataValue;
import org.hisp.dhis.android.sdk.persistence.models.Enrollment;
import org.hisp.dhis.android.sdk.persistence.models.Event;
import org.hisp.dhis.android.sdk.persistence.models.OrganisationUnit;
import org.hisp.dhis.android.sdk.persistence.models.Program;
import org.hisp.dhis.android.sdk.persistence.models.ProgramIndicator;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRule;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRuleAction;
import org.hisp.dhis.android.sdk.persistence.models.ProgramStage;
import org.hisp.dhis.android.sdk.persistence.models.ProgramStageDataElement;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityAttributeValue;
import org.hisp.dhis.android.sdk.ui.fragments.common.IProgramRuleFragmentHelper;
import org.hisp.dhis.android.sdk.utils.services.ProgramIndicatorService;
import org.hisp.dhis.android.sdk.utils.services.ProgramRuleService;
import org.hisp.dhis.android.sdk.utils.services.VariableService;
import org.hisp.dhis.android.sdk.utils.support.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nhancao on 2/12/16.
 */
public class ApplyRuleHelper implements IProgramRuleFragmentHelper {

    public final static String TAG = ApplyRuleHelper.class.getSimpleName();

    public ApplyRuleLogic applyRuleLogic;
    private ArrayList<String> programRuleValidationErrors;
    private Map<String, List<ProgramRule>> programRulesForDataElements;
    private Map<String, List<ProgramIndicator>> programIndicatorsForDataElements;
    private Map<String, DataValue> dataValues;
    private Map<String, TrackedEntityAttributeValue> trackedEntityAttributeValues;
    private Map<String, String> dataElementNames;
    String unitId;
    String programId;
    String programStageId;

    ProgramStage programStage;
    Program program;
    OrganisationUnit organisationUnit;
    Enrollment enrollment;
    Event event;

    public ApplyRuleHelper(String orgUnitId, String programId, String programStageId, long eventId, long enrollmentId) {
        this.unitId = orgUnitId;
        this.programId = programId;
        this.programStageId = programStageId;
        this.programRuleValidationErrors = new ArrayList<>();

        this.programStage = MetaDataController.getProgramStage(programStageId);
        this.program = MetaDataController.getProgram(programId);
        this.organisationUnit = MetaDataController.getOrganisationUnit(unitId);


        final String username = DhisController.getInstance().getSession().getCredentials().getUsername();
        event = generateEvent(
                orgUnitId, programId, eventId, enrollmentId, programStage, username
        );
        if (this.event.getEventDate() == null) {
            this.event.setEventDate(DateTime.now().toString());
            Log.e(TAG, "ApplyRuleHelper: " + this.event.getDueDate());
        }
        if (enrollmentId > 0) {
            this.enrollment = TrackerController.getEnrollment(enrollmentId);
            enrollment.getEvents(true);
        }

        this.applyRuleLogic = new ApplyRuleLogic();
        this.applyRuleLogic.setProgramRuleFragmentHelper(this);

        this.dataValues = new HashMap<>();
        this.trackedEntityAttributeValues = new HashMap<>();
        this.dataElementNames = new HashMap<>();
    }


    @Override
    public ArrayList<String> getProgramRuleValidationErrors() {
        return programRuleValidationErrors;
    }

    @Override
    public void recycle() {

    }

    @Override
    public void initiateEvaluateProgramRules() {
        VariableService.reset();
        programRuleValidationErrors.clear();
        applyRuleLogic.evaluateAndApplyProgramRules();

    }

    @Override
    public void mapFieldsToRulesAndIndicators() {
        setProgramRulesForDataElements(new HashMap<String, List<ProgramRule>>());
        setProgramIndicatorsForDataElements(new HashMap<String, List<ProgramIndicator>>());
        for (ProgramRule programRule : getProgramStage().getProgram().getProgramRules()) {
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

    @Override
    public Fragment getFragment() {
        return null;
    }

    @Override
    public void showWarningHiddenValuesDialog(Fragment fragment, ArrayList<String> affectedValues) {

    }

    @Override
    public void updateUi() {

    }

    @Override
    public List<ProgramRule> getProgramRules() {
        return getProgramStage().getProgram().getProgramRules();
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
    public void applyShowWarningRuleAction(ProgramRuleAction programRuleAction) {

    }

    @Override
    public void applyShowErrorRuleAction(ProgramRuleAction programRuleAction) {

    }

    @Override
    public void applyHideFieldRuleAction(ProgramRuleAction programRuleAction, List<String> affectedFieldsWithValue) {
//        affectedFieldsWithValue.add(programRuleAction.getDataElement());
        if (programRuleAction.getDataElement() != null) {
            Log.e(TAG, "applyHideFieldRuleAction: hidden - " + MetaDataController.getDataElement(programRuleAction.getDataElement()).getName());
        }


    }

    @Override
    public void applyHideSectionRuleAction(ProgramRuleAction programRuleAction) {

    }

    @Override
    public void applyCreateEventRuleAction(ProgramRuleAction programRuleAction) {

    }

    @Override
    public void applyDisplayKeyValuePairRuleAction(ProgramRuleAction programRuleAction) {

    }

    @Override
    public void applyDisplayTextRuleAction(ProgramRuleAction programRuleAction) {

    }

    @Override
    public DataValue getDataElementValue(String uid) {
        return getDataValues().get(uid);
    }

    @Override
    public TrackedEntityAttributeValue getTrackedEntityAttributeValue(String uid) {
        return getTrackedEntityAttributeValues().get(uid);
    }

    @Override
    public void flagDataChanged(boolean dataChanged) {

    }

    @Override
    public void saveDataElement(String uid) {

    }

    @Override
    public void saveTrackedEntityAttribute(String uid) {

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

    public ProgramStage getProgramStage() {
        return programStage;
    }

    public Program getProgram() {
        return program;
    }

    public OrganisationUnit getOrganisationUnit() {
        return organisationUnit;
    }

    public Map<String, DataValue> getDataValues() {
        return dataValues;
    }

    public Map<String, TrackedEntityAttributeValue> getTrackedEntityAttributeValues() {
        return trackedEntityAttributeValues;
    }

    public Map<String, String> getDataElementNames() {
        return dataElementNames;
    }

    private Event generateEvent(String orgUnitId, String programId, long eventId, long enrollmentId,
                                ProgramStage programStage, String username) {
        Event event;
        if (eventId < 0) {
            event = new Event(orgUnitId, Event.STATUS_ACTIVE, programId, programStage, null, null, null);
            if (enrollmentId > 0) {
                Enrollment enrollment = TrackerController.getEnrollment(enrollmentId);
                if (enrollment != null) {
                    event.setLocalEnrollmentId(enrollmentId);
                    event.setEnrollment(enrollment.getEnrollment());
                    event.setTrackedEntityInstance(enrollment.getTrackedEntityInstance());
                    LocalDate dueDate = new LocalDate(DateUtils.parseDate(enrollment.getEnrollmentDate())).plusDays(programStage.getMinDaysFromStart());
                    event.setDueDate(dueDate.toString());
                }
            }

            List<DataValue> dataValues = new ArrayList<>();
            for (ProgramStageDataElement dataElement : programStage.getProgramStageDataElements()) {
                dataValues.add(
                        new DataValue(event, "", dataElement.getDataelement(), false, username)
                );
            }
            event.setDataValues(dataValues);
        } else {
            event = TrackerController.getEvent(eventId);
        }

        return event;
    }

    public static DataValue getDataValue(String dataElement, Event event,
                                         String username) {
        for (DataValue dataValue : event.getDataValues()) {
            if (dataValue.getDataElement().equals(dataElement)) {
                return dataValue;
            }
        }

        // The DataValue didn't exist for some reason. Create a new one.
        DataValue dataValue = new DataValue(
                event, "", dataElement, false, username
        );
        event.getDataValues().add(dataValue);
        return dataValue;
    }
}
