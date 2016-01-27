package org.hispindia.bidtrackerreports.ui.fragment.bidprogram;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hispindia.android.core.ui.fragment.HICFragmentBase;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.event.HIEvent;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDRow;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterBIDReport;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewBIDReport;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterBIDReport;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by nhancao on 1/20/16.
 */
public class HIFragmentBIDReport extends HICFragmentBase implements HIIViewBIDReport {

    public final static String TAG = HIFragmentBIDReport.class.getSimpleName();
    private static final String ORG_UNIT_ID = "extra:orgUnitId";
    private static final String ORG_UNIT_MODE = "extra:orgUnitMode";
    private static final String PROGRAM_ID = "extra:programId";
    private static final String PROGRAM_STAGE_ID = "extra:programStageId";

    @Bind(R.id.vReport)
    RecyclerView vReport;

    @Inject
    HIPresenterBIDReport flow;
    @Inject
    HIAdapterBIDReport adapter;

    private String orgUnitId;
    private String orgUnitMode;
    private String programId;
    private String programStageId;

    public static HIFragmentBIDReport newInstance(String orgUnitId, String orgUnitMode, String programId, String programStageId) {
        HIFragmentBIDReport fragment = new HIFragmentBIDReport();
        Bundle args = new Bundle();
        args.putString(ORG_UNIT_ID, orgUnitId);
        args.putString(ORG_UNIT_MODE, orgUnitMode);
        args.putString(PROGRAM_ID, programId);
        args.putString(PROGRAM_STAGE_ID, programStageId);
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
        this.programStageId = fragmentArguments.getString(PROGRAM_STAGE_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hifragment_bidreport, container, false);
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
        Log.e(TAG, "onPause: ");
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
        Log.e(TAG, "onInjected: ");
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        vReport.setHasFixedSize(true);
        vReport.setLayoutManager(llm);
        vReport.setAdapter(adapter);
        vReport.getItemAnimator().setSupportsChangeAnimations(true);
        vReport.setItemAnimator(new DefaultItemAnimator());
        if (flow != null) {
            adapter.setHibidRowList(new ArrayList<>());
            adapter.setLoadDone(false);
            flow.getBIDEventReport(this, orgUnitId, orgUnitMode, programId, programStageId);
        }
    }

    @Override
    public void updateHeaderRow(HIBIDRow headerRow) {


    }

    @Override
    public void updateRow(HIBIDRow row) {
        if (row == null) {
            adapter.setLoadDone(true);
        } else {
            adapter.updateRow(row);
        }
    }

}
