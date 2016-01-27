package org.hispindia.bidtrackerreports.ui.fragment.histockreport;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import org.hisp.dhis.android.sdk.controllers.metadata.MetaDataController;
import org.hisp.dhis.android.sdk.persistence.models.OrganisationUnit;

import static org.hisp.dhis.android.sdk.utils.Preconditions.isNull;

/**
 * Created by nhancao on 1/21/16.
 */
public class HIFragmentStockSelectProgramPreferences {
    private static final String PROGRAM_FRAGMENT_PREFERENCES = "preferences:HIFragmentStockSelectProgramPreferences";

    private static final String ORG_UNIT_ID = "key:orgUnitId";
    private static final String ORG_UNIT_LABEL = "key:orgUnitLabel";

    private static final String ORG_UNIT_MODE_ID = "key:orgUnitModeId";
    private static final String ORG_UNIT_MODE_LABEL = "key:orgUnitModeLabel";

    private final SharedPreferences mPrefs;

    public HIFragmentStockSelectProgramPreferences(Context context) {
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