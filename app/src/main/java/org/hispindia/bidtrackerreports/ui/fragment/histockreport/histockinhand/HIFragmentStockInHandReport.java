package org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhand;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hispindia.android.core.ui.fragment.HICFragmentBase;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.event.HIEvent;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport;
import org.hispindia.bidtrackerreports.mvp.view.HIViewStockInHandReport;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.HIParamBIDHardcode;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Sourabh on 2/6/2016.
 */
public class HIFragmentStockInHandReport extends HICFragmentBase implements HIViewStockInHandReport {

    public final static String TAG = HIFragmentStockInHandReport.class.getSimpleName();
    private static final String ORG_UNIT_LV = "extra:orgUnitLv";
    protected INavigationHandler mNavigationHandler;
    @Bind(R.id.vReport)
    RecyclerView vReport;
    @Inject
    HIPresenterStockReport flow;
    @Inject
    HIAdapterStockReport adapter;
    private String orgUnitId;
    private String orgUnitMode;

    public static HIFragmentStockInHandReport newInstance(String orgUnitId, String orgUnitMode) {
        HIFragmentStockInHandReport fragment = new HIFragmentStockInHandReport();
        Bundle args = new Bundle();
        args.putString(orgUnitId, orgUnitId);

        args.putString(orgUnitMode, orgUnitMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle fragmentArguments = getArguments();
        this.orgUnitId = HIParamBIDHardcode.ORGUNITID;
        this.orgUnitMode = HIParamBIDHardcode.OUMODEID;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.hifragment_stock_in_hand_report, container, false);
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
    protected void onInjected() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        vReport.setHasFixedSize(true);
        vReport.setLayoutManager(llm);
        vReport.setAdapter(adapter);
        vReport.getItemAnimator().setSupportsChangeAnimations(true);
        vReport.setItemAnimator(new DefaultItemAnimator());
        if (flow != null) {
            adapter.setHiStockRowList(new ArrayList<>());
            adapter.setLoadDone(false);
            flow.getStockInHandReport(this, orgUnitMode, orgUnitId);
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
    public void onDetach() {
        super.onDetach();
        mNavigationHandler = null;
    }

    @Override
    public void updateRow(HIResStock rows) {
        if (rows != null) {
            adapter.setHiStockRowList(rows.rows);
            adapter.setLoadDone(true);
        }
    }

}
