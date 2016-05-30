package org.hispindia.bidtrackerreports.ui.fragment.hibidreport.birthnotifications;

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
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIbidbirthrow;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterBIDBirthReport;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewBirthNotificationReport;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterBirthNotificationReport;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.HIParamBIDHardcode;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by nhancao on 1/20/16.
 */
public class HIFragmentBirthNotificationsReport extends HICFragmentBase implements HIIViewBirthNotificationReport {
    public final static String TAG = HIFragmentBirthNotificationsReport.class.getSimpleName();

    @Bind(R.id.vReport)
    RecyclerView vReport;


    @Inject
    HIPresenterBIDBirthReport flow;
    HIAdapterBirthNotificationReport adapter;

    private String orgUnitId;
    private String orgUnitMode;
    private String programBirthId;
    private String programStageBirthId;

    public static HIFragmentBirthNotificationsReport newInstance() {
        HIFragmentBirthNotificationsReport fragment = new HIFragmentBirthNotificationsReport();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.orgUnitId = HIParamBIDHardcode.ORGUNITID;
        this.orgUnitMode = HIParamBIDHardcode.OUMODE;
        this.programBirthId = HIParamBIDHardcode.PROGRAMBIRTHID;
        this.programStageBirthId = HIParamBIDHardcode.PROGRAMBIRTHSTAGEID;
        this.adapter = new HIAdapterBirthNotificationReport();
        getActivity().setTitle(getString(R.string.btn_birth_report));

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
        View view = inflater.inflate(R.layout.hifragment_birth_notifications_report, container, false);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar myCalendar = Calendar.getInstance();

    }

//    @OnClick(R.id.btnFilter)
//    public void btnFilterOnClick() {
//        adapter.filter(etStartDate.getText().toString(), etEndDate.getText().toString());
//        //adapter.setDemandList(filterDemandbydate(listTemp, etStartDate.getText().toString(), etEndDate.getText().toString()));
//
//    }

    @Override
    protected void onInjected() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        vReport.setHasFixedSize(true);
        vReport.setLayoutManager(llm);
        vReport.setAdapter(adapter);
//        vReport.getItemAnimator().setSupportsChangeAnimations(true);
        vReport.setItemAnimator(new DefaultItemAnimator());
        if (flow != null) {
            adapter.setHibidbirthRowList(new ArrayList<>());
            adapter.setLoadDone(false);
            flow.getBirthNotificationReport(this, orgUnitId, orgUnitMode, programBirthId, programStageBirthId);

        }
    }

    @Override
    public void updateRow(HIbidbirthrow row) {
        if (row == null) {
            adapter.setLoadDone(true);
        } else {
            adapter.updateRow(row);
        }
    }

}
