package org.hispindia.bidtrackerreports.ui.fragment.hischvaccinereport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.event.HIEvent;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResSchvaccine;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterSchvaccineReport;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewSchvaccineReport;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterSchvaccineReport;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Sourabh on 1/30/2016.
 */
public class HIFragmentSchvaccineReport extends HICFragmentBase implements HIIViewSchvaccineReport {
    public final static String TAG = HIFragmentSchvaccineReport.class.getSimpleName();
    private static final String ORG_UNIT_ID = "extra:orgUnitId";
    private static final String PROGRAM_ID = "extra:programId";
    private static final String ORG_UNIT_MODE = "extra:orgUnitMode";
    private static final String FROM_DAY = "extra:fromDay";
    private static final String TO_DAY = "extra:toDay";

    @Bind(R.id.vReport)
    RecyclerView vReport;

    @Inject
    HIPresenterSchvaccineReport flow;
    @Inject
    HIAdapterSchvaccineReport adapter;

    private String orgUnitId;
    private String orgUnitMode;
    private String programId;
    private String fromDay;
    private String toDay;

    public static HIFragmentSchvaccineReport newInstance(String orgUnitId, String orgUnitMode, String programId, String fromDay, String toDay) {
        HIFragmentSchvaccineReport fragment = new HIFragmentSchvaccineReport();
        Bundle args = new Bundle();
        args.putString(ORG_UNIT_ID, orgUnitId);
        args.putString(ORG_UNIT_MODE, orgUnitMode);
        args.putString(PROGRAM_ID, programId);
        args.putString(FROM_DAY, fromDay);
        args.putString(TO_DAY, toDay);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle fragmentArguments = getArguments();
        this.orgUnitId = fragmentArguments.getString(ORG_UNIT_ID);
        this.orgUnitMode = fragmentArguments.getString(ORG_UNIT_MODE);
        this.programId = fragmentArguments.getString(PROGRAM_ID);
        this.fromDay = fragmentArguments.getString(FROM_DAY);
        this.toDay = fragmentArguments.getString(TO_DAY);
        getActivity().setTitle(getString(R.string.btn_schedule_vaccine_report));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hifragment_schvaccine_report, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HIEvent.register(this);
    }

    @Override
    public void onPause() {
        HIEvent.unregister(this);
        flow.onStop();
        super.onPause();
    }

    @Override
    protected void injectDependencies() {
        HIIComponentUi uiComponent = ((HIActivityMain) getActivity()).getUiComponent();
        if (uiComponent != null) {
            uiComponent.inject(this);
        }
    }

    @Override
    protected void onInjected() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        vReport.setHasFixedSize(true);
        vReport.setLayoutManager(llm);
        vReport.setAdapter(adapter);
//        vReport.getItemAnimator().setSupportsChangeAnimations(true);

        RecyclerView.ItemAnimator animator = vReport.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(true);
        }

        vReport.setItemAnimator(new DefaultItemAnimator());

        if (flow != null) {
            adapter.setHiList(new ArrayList<>());
            adapter.setLoadDone(false);
            flow.getSchvaccineReport(this, orgUnitId, orgUnitMode, programId, fromDay, toDay);
        }
    }

    @Override
    public void updateRow(HIResSchvaccine rows) {
        if (rows != null) {
            adapter.setHiList(rows.eventRows);
            adapter.setLoadDone(true);
        }

    }
}
