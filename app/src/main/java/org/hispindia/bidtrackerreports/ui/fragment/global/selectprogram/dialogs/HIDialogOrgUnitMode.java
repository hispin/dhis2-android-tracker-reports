package org.hispindia.bidtrackerreports.ui.fragment.global.selectprogram.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    public static final int BID_MODE = 0;
    public static final int STOCK_MODE = 1;
    private static final String ORG_UNIT_MODE = "extra:orgUnitMode";
    private int unitMod = 0;

    public static HIDialogOrgUnitMode newInstance(OnOptionSelectedListener listener) {
        HIDialogOrgUnitMode fragment = new HIDialogOrgUnitMode();
        fragment.setOnOptionSetListener(listener);
        return fragment;
    }

    public static HIDialogOrgUnitMode newInstance(OnOptionSelectedListener listener, int modeProgram) {
        HIDialogOrgUnitMode fragment = new HIDialogOrgUnitMode();
        Bundle args = new Bundle();
        args.putInt(ORG_UNIT_MODE, modeProgram);
        fragment.setArguments(args);
        fragment.setOnOptionSetListener(listener);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle fragmentArguments = getArguments();
        this.unitMod = fragmentArguments.getInt(ORG_UNIT_MODE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDialogLabel(R.string.dialog_organisation_units_mode);
        setDialogId(ID);
        String[] orgUnitMode = getResources().getStringArray(R.array.organisation_mode);
        String[] orgUnitModeId = getResources().getStringArray(R.array.organisation_mode_id);
        List<AutoCompleteDialogAdapter.OptionAdapterValue> data = new ArrayList<>();
        switch (unitMod) {
            case BID_MODE:
                for (String item : orgUnitMode) {
                    data.add(new AutoCompleteDialogAdapter.OptionAdapterValue(item, item));
                }
                break;
            case STOCK_MODE:
                for (int i = 0; i < orgUnitMode.length; i++) {
                    data.add(new AutoCompleteDialogAdapter.OptionAdapterValue(orgUnitModeId[i], orgUnitMode[i]));
                }
                break;
        }
        getAdapter().swapData(data);
    }


}
