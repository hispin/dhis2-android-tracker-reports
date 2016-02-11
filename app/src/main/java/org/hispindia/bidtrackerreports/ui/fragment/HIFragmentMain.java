package org.hispindia.bidtrackerreports.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.fragment.global.selectprogram.SelectProgramFragment;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hitodayschedule.HIFragmentTodayScheduleReport;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hivaccinestatus.HIFragmentVaccineStatusReport;
import org.hispindia.bidtrackerreports.ui.fragment.hioverduereport.HIFragmentOverdueSelectProgram;
import org.hispindia.bidtrackerreports.ui.fragment.hischvaccinereport.HIFragmentSchvaccineSelectProgram;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.HIFragmentStockSelectProgram;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhand.HIFragmentStockInHandReport;

import butterknife.OnClick;

/**
 * Created by nhancao on 1/27/16.
 */
public class HIFragmentMain extends HICFragmentBase {

    public final static String TAG = HIFragmentMain.class.getSimpleName();

    protected INavigationHandler mNavigationHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.hifragment_main, container, false);
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

    @OnClick(R.id.btnTrackerReport)
    @SuppressWarnings("unused")
    public void btnTrackerReport() {
        mNavigationHandler.switchFragment(new SelectProgramFragment(), SelectProgramFragment.TAG, true);
//        EventDataEntryFragment fragment = EventDataEntryFragment.newInstanceWithEnrollment("DQjaNvP9ulw", "SSLpOM0r1U7", "s53RFfXA75f" , 1, 1);
//        mNavigationHandler.switchFragment(fragment, EventDataEntryFragment.TAG, true);
    }

    @OnClick(R.id.btnTodayReport)
    @SuppressWarnings("unused")
    public void btnBidReport() {
        mNavigationHandler.switchFragment(new HIFragmentTodayScheduleReport(), HIFragmentTodayScheduleReport.TAG, true);
    }

    @OnClick(R.id.btnVaccineStatus)
    @SuppressWarnings("unused")
    public void btnVaccineStatus() {
        mNavigationHandler.switchFragment(new HIFragmentVaccineStatusReport(), HIFragmentVaccineStatusReport.TAG, true);
    }

    @OnClick(R.id.btnStockReport)
    @SuppressWarnings("unused")
    public void btnStockReport() {
        mNavigationHandler.switchFragment(new HIFragmentStockSelectProgram(), HIFragmentStockSelectProgram.TAG, true);
    }

    @OnClick(R.id.btnSchvaccineReport)
    @SuppressWarnings("unused")
    public void btnSchvaccineReport() {
        mNavigationHandler.switchFragment(new HIFragmentSchvaccineSelectProgram(), HIFragmentSchvaccineSelectProgram.TAG, true);
    }

    @OnClick(R.id.btnOverdueReport)
    @SuppressWarnings("unused")
    public void btnOverdueReport() {
        mNavigationHandler.switchFragment(new HIFragmentOverdueSelectProgram(), HIFragmentOverdueSelectProgram.TAG, true);
    }

    @OnClick(R.id.btnStockInHandReport)
    @SuppressWarnings("unused")
    public void btnStockInHandReport() {
        mNavigationHandler.switchFragment(new HIFragmentStockInHandReport(), HIFragmentStockInHandReport.TAG, true);
    }
}
