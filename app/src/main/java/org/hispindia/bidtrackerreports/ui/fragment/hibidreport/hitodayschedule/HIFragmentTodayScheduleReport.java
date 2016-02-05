package org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hitodayschedule;

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
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterBIDReport;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewTodayScheduleReport;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterTodayScheduleReport;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by nhancao on 1/20/16.
 */
public class HIFragmentTodayScheduleReport extends HICFragmentBase implements HIIViewTodayScheduleReport {

    public final static String TAG = HIFragmentTodayScheduleReport.class.getSimpleName();

    @Bind(R.id.vReport)
    RecyclerView vReport;

    @Inject
    HIPresenterBIDReport flow;
    HIAdapterTodayScheduleReport adapter;

    private String orgUnitId;
    private String orgUnitMode;
    private String programId;
    private String programStageId;

    public static HIFragmentTodayScheduleReport newInstance() {
        HIFragmentTodayScheduleReport fragment = new HIFragmentTodayScheduleReport();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.orgUnitId = HIParamTodaySchedule.ORGUNITID;
        this.orgUnitMode = HIParamTodaySchedule.OUMODE;
        this.programId = HIParamTodaySchedule.PROGRAMID;
        this.programStageId = HIParamTodaySchedule.PROGRAMSTAGEID;
        adapter = new HIAdapterTodayScheduleReport();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hifragment_today_schedule_report, container, false);
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
        vReport.getItemAnimator().setSupportsChangeAnimations(true);
        vReport.setItemAnimator(new DefaultItemAnimator());
        if (flow != null) {
            adapter.setHibidRowList(new ArrayList<>());
            adapter.setLoadDone(false);
            flow.getTodayScheduleEventReport(this, orgUnitId, orgUnitMode, programId, programStageId);
        }
    }

    @Override
    public void updateList(List<HIDBbidrow> localList) {
        if (localList != null) {
            adapter.setHibidRowList(localList);
        }
    }

    @Override
    public void updateRow(HIDBbidrow row) {
        if (row == null) {
            Log.e(TAG, "updateRow: DONE");
            adapter.setLoadDone(true);
        } else {
//            Log.e(TAG, "updateRow: "+row.getOrder());
//            adapter.updateRow(row);
        }
    }

}
