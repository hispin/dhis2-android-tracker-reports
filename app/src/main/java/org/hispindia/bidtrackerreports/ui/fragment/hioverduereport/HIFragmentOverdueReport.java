package org.hispindia.bidtrackerreports.ui.fragment.hioverduereport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.event.HIEvent;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResOverdue;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterOverdueReport;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewOverdueReport;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterOverdueReport;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Sourabh on 2/2/2016.
 */
public class HIFragmentOverdueReport extends HICFragmentBase implements HIIViewOverdueReport {


    public final static String TAG = HIFragmentOverdueReport.class.getSimpleName();
    private static final String ORG_UNIT_ID = "extra:orgUnitId";
    private static final String PROGRAM_ID = "extra:programId";
    private static final String ORG_UNIT_MODE = "extra:orgUnitMode";
    private static final String FROM_DAY = "extra:fromDay";
    private static final String TO_DAY = "extra:toDay";

    @Bind(R.id.vReport)
    RecyclerView vReport;

    @Inject
    HIPresenterOverdueReport flow;
    @Inject
    HIAdapterOverdueReport adapter;

    private String orgUnitId;
    private String orgUnitMode;
    private String programId;

    public static HIFragmentOverdueReport newInstance(String orgUnitId, String orgUnitMode, String programId) {
        HIFragmentOverdueReport fragment = new HIFragmentOverdueReport();
        Bundle args = new Bundle();
        args.putString(ORG_UNIT_ID, orgUnitId);
        args.putString(ORG_UNIT_MODE, orgUnitMode);
        args.putString(PROGRAM_ID, programId);
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
        getActivity().setTitle(getString(R.string.btn_overdue_report));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hifragment_overdue_report, container, false);
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
        vReport.getItemAnimator().setSupportsChangeAnimations(true);
        vReport.setItemAnimator(new DefaultItemAnimator());

        if (flow != null) {
            adapter.setHiList(new ArrayList<>());
            adapter.setLoadDone(false);
            flow.getOverdueReport(this, orgUnitId, orgUnitMode, programId);
        }
    }

    @Override
    public void updateRow(HIResOverdue rows) {
        if (rows != null) {
            adapter.setHiList(rows.eventRows);
            adapter.setLoadDone(true);
        }

    }
}
