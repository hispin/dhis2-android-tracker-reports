package org.hispindia.bidtrackerreports.mvp.presenter;

import org.hisp.dhis.android.sdk.persistence.models.DataValue;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRule;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRuleAction;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRuleVariable;
import org.hisp.dhis.android.sdk.persistence.models.TrackedEntityAttributeValue;
import org.hisp.dhis.android.sdk.ui.fragments.common.IProgramRuleFragmentHelper;
import org.hisp.dhis.android.sdk.utils.comparators.ProgramRulePriorityComparator;
import org.hisp.dhis.android.sdk.utils.services.ProgramRuleService;
import org.hisp.dhis.android.sdk.utils.services.VariableService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nhancao on 2/12/16.
 */
public class ApplyRuleLogic {

    public final static String TAG = ApplyRuleLogic.class.getSimpleName();

    protected IProgramRuleFragmentHelper programRuleFragmentHelper;

    public IProgramRuleFragmentHelper getProgramRuleFragmentHelper() {
        return programRuleFragmentHelper;
    }

    public void setProgramRuleFragmentHelper(IProgramRuleFragmentHelper programRuleFragmentHelper) {
        this.programRuleFragmentHelper = programRuleFragmentHelper;
    }

    public void evaluateAndApplyProgramRules() {
        VariableService.initialize(programRuleFragmentHelper.getEnrollment(), programRuleFragmentHelper.getEvent());
        programRuleFragmentHelper.mapFieldsToRulesAndIndicators();
        ArrayList<String> affectedFieldsWithValue = new ArrayList<>();
        List<ProgramRule> programRules = programRuleFragmentHelper.getProgramRules();
        Collections.sort(programRules, new ProgramRulePriorityComparator());
        for (ProgramRule programRule : programRules) {
            boolean evaluatedTrue = ProgramRuleService.evaluate(programRule.getCondition());
            for (ProgramRuleAction action : programRule.getProgramRuleActions()) {
                if (evaluatedTrue) {
                    applyProgramRuleAction(action, affectedFieldsWithValue);
                }
            }
        }
        if (!affectedFieldsWithValue.isEmpty()) {
            programRuleFragmentHelper.showWarningHiddenValuesDialog(programRuleFragmentHelper.getFragment(), affectedFieldsWithValue);
        }
        programRuleFragmentHelper.updateUi();
    }

    protected void applyProgramRuleAction(ProgramRuleAction programRuleAction, List<String> affectedFieldsWithValue) {

        switch (programRuleAction.getProgramRuleActionType()) {
            case HIDEFIELD: {
                programRuleFragmentHelper.applyHideFieldRuleAction(programRuleAction, affectedFieldsWithValue);
                break;
            }
            case HIDESECTION: {
                programRuleFragmentHelper.applyHideSectionRuleAction(programRuleAction);
                break;
            }
            case SHOWWARNING: {
                programRuleFragmentHelper.applyShowWarningRuleAction(programRuleAction);
                break;
            }
            case SHOWERROR: {
                programRuleFragmentHelper.applyShowErrorRuleAction(programRuleAction);
                break;
            }
            case ASSIGN: {
                applyAssignRuleAction(programRuleAction);
                break;
            }
            case CREATEEVENT: {
                programRuleFragmentHelper.applyCreateEventRuleAction(programRuleAction);
                break;
            }
            case DISPLAYKEYVALUEPAIR: {
                programRuleFragmentHelper.applyDisplayKeyValuePairRuleAction(programRuleAction);
                break;
            }
            case DISPLAYTEXT: {
                programRuleFragmentHelper.applyDisplayTextRuleAction(programRuleAction);
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
        String dataElementId = programRuleAction.getDataElement();
        if (dataElementId != null) {
            DataValue dataValue = programRuleFragmentHelper.getDataElementValue(dataElementId);
            if (dataValue != null) {
                dataValue.setValue(stringResult);
                programRuleFragmentHelper.flagDataChanged(true);
                programRuleFragmentHelper.saveDataElement(dataElementId);
            }
        }
        String trackedEntityAttributeId = programRuleAction.getTrackedEntityAttribute();
        if (trackedEntityAttributeId != null) {
            TrackedEntityAttributeValue trackedEntityAttributeValue = programRuleFragmentHelper.getTrackedEntityAttributeValue(trackedEntityAttributeId);
            if (trackedEntityAttributeValue != null) {
                trackedEntityAttributeValue.setValue(stringResult);
                programRuleFragmentHelper.flagDataChanged(true);
                programRuleFragmentHelper.saveTrackedEntityAttribute(trackedEntityAttributeId);
            }
        }
    }


}
