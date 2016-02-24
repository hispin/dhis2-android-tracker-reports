package org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hitodayschedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import org.hisp.dhis.android.sdk.events.UiEvent;
import org.hisp.dhis.android.sdk.persistence.Dhis2Application;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterBIDReport;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewTodayScheduleReport;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterTodayScheduleReport;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.HIParamBIDHardcode;

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
        this.orgUnitId = HIParamBIDHardcode.ORGUNITID;
        this.orgUnitMode = HIParamBIDHardcode.OUMODE;
        this.programId = HIParamBIDHardcode.PROGRAMID;
        this.programStageId = HIParamBIDHardcode.PROGRAMSTAGEID;
        this.adapter = new HIAdapterTodayScheduleReport();
        getActivity().setTitle(getString(R.string.btn_today_report));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (flow != null) {
            flow.onStop();
        }
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
        Dhis2Application.getEventBus().register(this);
    }

    @Override
    public void onPause() {
        Dhis2Application.getEventBus().unregister(this);
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
            flow.getTodayScheduleEventReport(this, orgUnitId, orgUnitMode, programId, programStageId, true);
        }
    }

    @Override
    public void updateRow(HIDBbidrow row) {
        if (row == null) {
//            adapter.sortList();
        } else {
            adapter.updateRow(row);
        }
    }

    @Override
    public void updateProgress(boolean status) {
        adapter.setLoadDone(status);
    }

    @Subscribe
    public void syncNotify(UiEvent uiEvent) {
        if (uiEvent.getEventType() == UiEvent.UiEventType.BID_TEI_SERVERDONE) {
            updateProgress(true);
            if (adapter.getItemCount() == 1) {
                flow.getTodayScheduleEventReport(this, orgUnitId, orgUnitMode, programId, programStageId, true, false);
            }
        } else {
        }
    }
}
