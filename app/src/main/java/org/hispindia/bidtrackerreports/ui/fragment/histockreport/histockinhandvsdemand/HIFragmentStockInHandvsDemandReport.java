package org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhandvsdemand;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.squareup.otto.Subscribe;

import org.hisp.dhis.android.sdk.events.UiEvent;
import org.hisp.dhis.android.sdk.persistence.Dhis2Application;
import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.event.HIEvent;
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterBIDReport;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewTodayScheduleReport;
import org.hispindia.bidtrackerreports.mvp.view.HIViewStockInHandReport;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockDemandReport;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.HIParamBIDHardcode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Sourabh on 2/6/2016.
 */
public class HIFragmentStockInHandvsDemandReport extends HICFragmentBase implements HIViewStockInHandReport, HIIViewTodayScheduleReport, AdapterView.OnItemSelectedListener {

    public final static String TAG = HIFragmentStockInHandvsDemandReport.class.getSimpleName();
    protected INavigationHandler mNavigationHandler;
    @Bind(R.id.vReport)
    RecyclerView vReport;
    @Bind(R.id.vChart)
    BarChart vChart;
    @Bind(R.id.vReportOption)
    Spinner vReportOption;

    @Inject
    HIPresenterStockReport flow;
    @Inject
    HIPresenterBIDReport flowGetDemand;

    HIAdapterStockDemandReport adapter;
    List<HIDBbidrow> listTemp = new ArrayList<>();

    private String orgUnitId;
    private String orgUnitMode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.orgUnitId = HIParamBIDHardcode.ORGUNITID;
        this.orgUnitMode = HIParamBIDHardcode.OUMODEID;
        this.adapter = new HIAdapterStockDemandReport();
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
        vChart.setDrawBarShadow(false);
        vChart.setDrawValueAboveBar(true);
        vChart.setDescription("");
        vChart.setPinchZoom(false);
        vChart.setDrawGridBackground(false);
        XAxis xAxis = vChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxis leftAxis = vChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = vChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);

        Legend l = vChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        vReport.setHasFixedSize(true);
        vReport.setLayoutManager(llm);
        vReport.setAdapter(adapter);
        vReport.getItemAnimator().setSupportsChangeAnimations(true);
        vReport.setItemAnimator(new DefaultItemAnimator());
        vReportOption.setOnItemSelectedListener(this);
        List<String> options = new ArrayList<>();
        options.add("1 Day");
        options.add("3 Day");
        options.add("A Week");
        options.add("2 Weeks");
        options.add("2 Months");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vReportOption.setAdapter(dataAdapter);
        vReportOption.setSelection(0);
        if (flow != null) {
            adapter.setInHandList(new ArrayList<>());
            adapter.setLoadDone(false);
            flow.getStockInHandReport(this, orgUnitMode, orgUnitId);
        }
        if (flowGetDemand != null) {
            listTemp = new ArrayList<>();
            adapter.setLoadDone(false);
            flowGetDemand.getTodayScheduleEventReport(this, orgUnitId, HIParamBIDHardcode.OUMODE, HIParamBIDHardcode.PROGRAMID, HIParamBIDHardcode.PROGRAMSTAGEID, true);
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
        getActivity().setTitle(getString(R.string.btn_stockinhandvsdemand_report));

    }

    @Override
    public void onResume() {
        super.onResume();
        HIEvent.register(this);
        Dhis2Application.getEventBus().register(this);
    }

    @Override
    public void onPause() {
        HIEvent.unregister(this);
        Dhis2Application.getEventBus().unregister(this);
        flow.onStop();
        flowGetDemand.onStop();
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNavigationHandler = null;
    }

    @Override
    public void updateRow(HIResStock resStock) {
        if (resStock != null) {
            adapter.setInHandList(resStock.rows);
            createChart(adapter.hiStockRowList, adapter.inhand, adapter.demand);
        }
    }

    public void createChart(List<String> xAxis, HashMap<String, Integer> inhand, HashMap<String, Integer> demand) {
        vChart.clear();
        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < xAxis.size(); i++) {
            xVals.add(xAxis.get(i));
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        if (inhand != null && inhand.size() > 0) {
            for (int i = 0; i < xAxis.size(); i++) {
                yVals1.add(new BarEntry(inhand.get(xAxis.get(i)), i));
            }
            BarDataSet set1 = new BarDataSet(yVals1, "Inhand");
            set1.setBarSpacePercent(35f);
            dataSets.add(set1);
        }
        if (demand != null && demand.size() > 0) {
            for (int i = 0; i < xAxis.size(); i++) {
                yVals2.add(new BarEntry(demand.get(xAxis.get(i)), i));
            }
            BarDataSet set2 = new BarDataSet(yVals2, "Demand");
            set2.setBarSpacePercent(35f);
            set2.setColor(Color.RED);
            dataSets.add(set2);
        }
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart.setData(data);
        vChart.animateXY(500, 1000);
    }

    @Override
    public void updateRow(HIDBbidrow row) {
        if (row != null) {
            listTemp.add(row);
        } else {
            Log.e(TAG, "updateRow: null");
            adapter.setDemandList(filterDemand(1, 0));
            createChart(adapter.hiStockRowList, adapter.inhand, adapter.demand);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                adapter.setDemandList(filterDemand(1, 0));
                break;
            case 1:
                adapter.setDemandList(filterDemand(3, 0));
                break;
            case 2:
                adapter.setDemandList(filterDemand(7, 0));
                break;
            case 3:
                adapter.setDemandList(filterDemand(14, 0));
                break;
            case 4:
                adapter.setDemandList(filterDemand(0, 1));
                break;
        }
        createChart(adapter.hiStockRowList, adapter.inhand, adapter.demand);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public List<HIDBbidrow> filterDemand(int dayNum, int monthNum) {
        List<HIDBbidrow> result = new ArrayList<>();
        for (HIDBbidrow item : listTemp) {
            DateTime desDate = DateTime.now().plusDays(dayNum).plusMonths(monthNum);
            DateTime dueDate = DateTime.parse(item.getDueDate(), DateTimeFormat.forPattern("yyyy-MM-dd"));
            if (dueDate.isBefore(desDate)) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public void updateProgress(boolean status) {
        adapter.setLoadDone(status);
    }


    @Subscribe
    public void syncNotify(UiEvent uiEvent) {
        if (uiEvent.getEventType() == UiEvent.UiEventType.BID_TEI_SERVERDONE) {
            updateProgress(true);
            if (adapter.getItemCount() == 2) {
                flowGetDemand.getTodayScheduleEventReport(this, orgUnitId, HIParamBIDHardcode.OUMODE, HIParamBIDHardcode.PROGRAMID, HIParamBIDHardcode.PROGRAMSTAGEID, true, false);
            }
        } else {
        }
    }
}
