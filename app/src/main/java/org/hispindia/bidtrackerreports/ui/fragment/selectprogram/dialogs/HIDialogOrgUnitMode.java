package org.hispindia.bidtrackerreports.ui.fragment.selectprogram.dialogs;

import android.os.Bundle;
import android.view.View;

import org.hisp.dhis.android.sdk.ui.dialogs.AutoCompleteDialogAdapter;
import org.hisp.dhis.android.sdk.ui.dialogs.AutoCompleteDialogFragment;
import org.hispindia.bidtrackerreports.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhancao on 1/21/16.
 */
public class HIDialogOrgUnitMode extends AutoCompleteDialogFragment {
    public static final int ID = 1000000;

    public static HIDialogOrgUnitMode newInstance(OnOptionSelectedListener listener) {
        HIDialogOrgUnitMode fragment = new HIDialogOrgUnitMode();
        fragment.setOnOptionSetListener(listener);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDialogLabel(R.string.dialog_organisation_units_mode);
        setDialogId(ID);

        String[] orgUnitMode = getResources().getStringArray(R.array.organisation_mode);
        List<AutoCompleteDialogAdapter.OptionAdapterValue> data = new ArrayList<>();
        for (String item : orgUnitMode) {
            data.add(new AutoCompleteDialogAdapter.OptionAdapterValue(item, item));
        }
        getAdapter().swapData(data);
    }


}
