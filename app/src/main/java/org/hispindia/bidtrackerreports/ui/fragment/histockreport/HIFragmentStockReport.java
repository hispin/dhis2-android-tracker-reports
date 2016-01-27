package org.hispindia.bidtrackerreports.ui.fragment.histockreport;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hispindia.android.core.ui.fragment.HICFragmentBase;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;

/**
 * Created by nhancao on 1/27/16.
 */
public class HIFragmentStockReport extends HICFragmentBase {

    public final static String TAG = HIFragmentStockReport.class.getSimpleName();

    protected INavigationHandler mNavigationHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.hifragment_stock_report, container, false);
        return view;
    }

    @Override
    protected void injectDependencies() {
        HIIComponentUi uiComponent = ((HIActivityMain) getActivity()).getUiComponent();
        if (uiComponent != null) {
            uiComponent.inject(this);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof INavigationHandler) {
            mNavigationHandler = (INavigationHandler) activity;
        } else {
            throw new IllegalArgumentException("Activity must " +
                    "implement INavigationHandler interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNavigationHandler = null;
    }

}
