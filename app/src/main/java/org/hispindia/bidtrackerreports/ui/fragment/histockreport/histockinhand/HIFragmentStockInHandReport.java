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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.event.HIEvent;
import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport;
import org.hispindia.bidtrackerreports.mvp.view.HIViewStockInHandReport;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.HIParamBIDHardcode;

import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.vChart)
    BarChart vChart;

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
        vChart.setTouchEnabled(false);
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
        getActivity().setTitle(getString(R.string.btn_stockinhand_report));

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
    public void updateRow(HIResStock resStock) {
        if (resStock != null) {
            adapter.setHiStockRowList(resStock.rows);
            adapter.setLoadDone(true);
            createChart(resStock.rows);
        }
    }

    public void createChart(List<HIStockRow> rows) {

        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            String name = rows.get(i).getName();
            try {
                xVals.add(name.substring(0, name.indexOf(" ")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            try {
                yVals1.add(new BarEntry(Integer.parseInt(rows.get(i).getValue()), i));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        BarDataSet set1 = new BarDataSet(yVals1, "value");
        set1.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        vChart.setData(data);
        vChart.animateXY(500, 1000);
    }

}
