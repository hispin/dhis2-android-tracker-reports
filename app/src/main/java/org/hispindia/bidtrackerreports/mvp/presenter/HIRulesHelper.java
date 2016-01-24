package org.hispindia.bidtrackerreports.mvp.presenter;

import org.hisp.dhis.android.sdk.persistence.models.Enrollment;
import org.hisp.dhis.android.sdk.persistence.models.Event;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRule;
import org.hisp.dhis.android.sdk.persistence.models.ProgramRuleAction;

import java.util.List;

/**
 * Created by nhancao on 1/24/16.
 */
public class HIRulesHelper {

    public interface HIIProgramRuleHelper {
        void mapFieldsToRulesAndIndicators();

        List<ProgramRule> getProgramRules();

        Enrollment getEnrollment();

        Event getEvent();

        void applyHideFieldRuleAction(ProgramRuleAction programRuleAction, List<String> affectedFieldsWithValue);
    }
}
