package org.hispindia.bidtrackerreports.mvp.presenter;

import org.hisp.dhis.android.sdk.persistence.models.ProgramRule;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRuleAction;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRuleVariable;
import org.hisp.dhis.android.sdk.utils.comparators.ProgramRulePriorityComparator;
import org.hisp.dhis.android.sdk.utils.services.ProgramRuleService;
import org.hisp.dhis.android.sdk.utils.services.VariableService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nhancao on 1/23/16.
 */
public class HIProgramRule {

    public final static String TAG = HIProgramRule.class.getSimpleName();
    protected HIRulesHelper.HIIProgramRuleHelper programRuleHelper;

    public void setProgramRuleHelper(HIRulesHelper.HIIProgramRuleHelper programRuleHelper) {
        this.programRuleHelper = programRuleHelper;
    }

    public void evaluateAndApplyProgramRules() {
        VariableService.initialize(programRuleHelper.getEnrollment(), programRuleHelper.getEvent());
        programRuleHelper.mapFieldsToRulesAndIndicators();
        ArrayList<String> affectedFieldsWithValue = new ArrayList<>();
        List<ProgramRule> programRules = programRuleHelper.getProgramRules();
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


    protected void applyProgramRuleAction(ProgramRuleAction programRuleAction, List<String> affectedFieldsWithValue) {

        switch (programRuleAction.getProgramRuleActionType()) {
            case HIDEFIELD: {
                programRuleHelper.applyHideFieldRuleAction(programRuleAction, affectedFieldsWithValue);
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
}
