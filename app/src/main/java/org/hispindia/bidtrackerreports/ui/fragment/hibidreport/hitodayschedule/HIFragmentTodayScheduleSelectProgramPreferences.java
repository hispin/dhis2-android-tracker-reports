package org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hitodayschedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.hisp.dhis.android.sdk.controllers.metadata.MetaDataController;
import org.hisp.dhis.android.sdk.persistence.models.OrganisationUnit;
import org.hisp.dhis.android.sdk.persistence.models.OrganisationUnitProgramRelationship;
import org.hisp.dhis.android.sdk.persistence.models.OrganisationUnitProgramRelationship$Table;

import static org.hisp.dhis.android.sdk.utils.Preconditions.isNull;

/**
 * Created by nhancao on 1/21/16.
 */
public class HIFragmentTodayScheduleSelectProgramPreferences {
    private static final String PROGRAM_FRAGMENT_PREFERENCES = "preferences:HIFragmentSchvaccineSelectProgramPreferences";

    private static final String ORG_UNIT_ID = "key:orgUnitId";
    private static final String ORG_UNIT_LABEL = "key:orgUnitLabel";

    private static final String PROGRAM_ID = "key:programId";
    private static final String PROGRAM_LABEL = "key:programLabel";

    private static final String ORG_UNIT_MODE_ID = "key:orgUnitModeId";
    private static final String ORG_UNIT_MODE_LABEL = "key:orgUnitModeLabel";

    private static final String FROM_DAY = "key:fromDay";
    private static final String TO_DAY = "key:toDay";

    private final SharedPreferences mPrefs;

    public HIFragmentTodayScheduleSelectProgramPreferences(Context context) {
        isNull(context, "Context object must not be null");
        mPrefs = context.getSharedPreferences(
                PROGRAM_FRAGMENT_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void putOrgUnit(Pair<String, String> orgUnit) {
        if (orgUnit != null) {
            put(ORG_UNIT_ID, orgUnit.first);
            put(ORG_UNIT_LABEL, orgUnit.second);
        } else {
            remove(ORG_UNIT_ID);
            remove(ORG_UNIT_LABEL);
        }
    }

    public Pair<String, String> getOrgUnit() {
        String orgUnitId = get(ORG_UNIT_ID);
        String orgUnitLabel = get(ORG_UNIT_LABEL);

        // we need to make sure that last selected
        // organisation unit still exists in database
        OrganisationUnit unit = MetaDataController.getOrganisationUnit(orgUnitId);
        if (unit != null) {
            return new Pair<>(orgUnitId, orgUnitLabel);
        } else {
            putOrgUnit(null);
            putProgram(null);
            return null;
        }
    }

    public void putProgram(Pair<String, String> program) {
        if (program != null) {
            put(PROGRAM_ID, program.first);
            put(PROGRAM_LABEL, program.second);
        } else {
            remove(PROGRAM_ID);
            remove(PROGRAM_LABEL);
        }
    }

    public Pair<String, String> getProgram() {
        String orgUnitId = get(ORG_UNIT_ID);
        String programId = get(PROGRAM_ID);
        String programLabel = get(PROGRAM_LABEL);

        // we need to make sure that last selected program for particular
        // organisation unit is still in database and assigned to selected organisation unit
        long count = new Select().count().from(OrganisationUnitProgramRelationship.class).where(
                Condition.column(OrganisationUnitProgramRelationship$Table.ORGANISATIONUNITID).is(orgUnitId),
                Condition.column(OrganisationUnitProgramRelationship$Table.PROGRAMID).is(programId)).count();
        if (count > 0) {
            return new Pair<>(programId, programLabel);
        } else {
            putProgram(null);
            return null;
        }
    }

    public void putOrgUnitMode(Pair<String, String> orgUnitMode) {
        if (orgUnitMode != null) {
            put(ORG_UNIT_MODE_ID, orgUnitMode.first);
            put(ORG_UNIT_MODE_LABEL, orgUnitMode.second);
        } else {
            remove(ORG_UNIT_MODE_ID);
            remove(ORG_UNIT_MODE_LABEL);
        }
    }

    public Pair<String, String> getOrgUnitMode() {
        String orgUnitModeId = get(ORG_UNIT_MODE_ID);
        String orgUnitModeLabel = get(ORG_UNIT_MODE_LABEL);
        return new Pair<>(orgUnitModeId, orgUnitModeLabel);
    }

    public String getFromDay() {
        return get(FROM_DAY);
    }

    public void putFromDay(String fromDay) {
        if (fromDay != null) {
            put(FROM_DAY, fromDay);
        } else {
            remove(FROM_DAY);
        }
    }

    public String getToDay() {
        return get(TO_DAY);
    }

    public void putToDay(String toDay) {
        if (toDay != null) {
            put(TO_DAY, toDay);
        } else {
            remove(TO_DAY);
        }
    }

    private void put(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
    }

    private String get(String key) {
        return mPrefs.getString(key, null);
    }

    private void delete() {
        mPrefs.edit().clear().apply();
    }

    private void remove(String key) {
        mPrefs.edit().remove(key).apply();
    }
}