package org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhand;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    @Bind(R.id.vReport1)
    RecyclerView vReport1;
    @Bind(R.id.vChart1)
    BarChart vChart1;

    @Bind(R.id.vReport2)
    RecyclerView vReport2;
    @Bind(R.id.vChart2)
    BarChart vChart2;

    @Bind(R.id.vReport3)
    RecyclerView vReport3;
    @Bind(R.id.vChart3)
    BarChart vChart3;

    @Bind(R.id.vReport4)
    RecyclerView vReport4;
    @Bind(R.id.vChart4)
    BarChart vChart4;

    @Bind(R.id.vReport5)
    RecyclerView vReport5;
    @Bind(R.id.vChart5)
    BarChart vChart5;

    @Inject
    HIPresenterStockReport flow;
    @Inject
    HIAdapterStockReport adapter;

    @Inject
    HIPresenterStockReport flow1;
    @Inject
    HIAdapterStockReport adapter1;

    @Inject
    HIPresenterStockReport flow2;
    @Inject
    HIPresenterStockReport flow3;
    @Inject
    HIPresenterStockReport flow4;
    @Inject
    HIPresenterStockReport flow5;

    @Inject
    HIAdapterStockReport adapter2;
    @Inject
    HIAdapterStockReport adapter3;
    @Inject
    HIAdapterStockReport adapter4;
    @Inject
    HIAdapterStockReport adapter5;


    private String orgUnitId;
    private String orgUnitMode;
    private String orgUnitIdC1;
    private String orgUnitIdC2;
    private String orgUnitIdC3;
    private String orgUnitIdC4;
    private String orgUnitIdC5;

    public static HIFragmentStockInHandReport newInstance(String orgUnitId, String orgUnitIdC1,String orgUnitIdC2,String orgUnitIdC3,String orgUnitIdC4, String orgUnitIdC5,String orgUnitMode) {
        HIFragmentStockInHandReport fragment = new HIFragmentStockInHandReport();

        Bundle args = new Bundle();

        args.putString(HIParamBIDHardcode.ORGUNITID, orgUnitId);
        args.putString(HIParamBIDHardcode.ORGUNITID1, orgUnitIdC1);
        args.putString(HIParamBIDHardcode.ORGUNITID2, orgUnitIdC2);
        args.putString(HIParamBIDHardcode.ORGUNITID3, orgUnitIdC3);
        args.putString(HIParamBIDHardcode.ORGUNITID4, orgUnitIdC4);
        args.putString(HIParamBIDHardcode.ORGUNITID5, orgUnitIdC5);
        args.putString(HIParamBIDHardcode.OUMODEID, orgUnitMode);
        fragment.setArguments(args);
        return fragment;


    }

//
//    public static HIFragmentStockInHandReport newInstance1(String orgUnitIdC1, String orgUnitMode) {
//        HIFragmentStockInHandReport fragment1 = new HIFragmentStockInHandReport();
//        Bundle args1 = new Bundle();
//        args1.putString(orgUnitIdC1, orgUnitIdC1);
//
//        args1.putString(orgUnitMode, orgUnitMode);
//        fragment1.setArguments(args1);
//        return fragment1;
//    }
//    public static HIFragmentStockInHandReport newInstance2(String orgUnitIdC2, String orgUnitMode) {
//        HIFragmentStockInHandReport fragment2 = new HIFragmentStockInHandReport();
//        Bundle args2 = new Bundle();
//        args2.putString(orgUnitIdC2, orgUnitIdC2);
//
//        args2.putString(orgUnitMode, orgUnitMode);
//        fragment2.setArguments(args2);
//        return fragment2;
//    }
//    public static HIFragmentStockInHandReport newInstance3(String orgUnitIdC3, String orgUnitMode) {
//        HIFragmentStockInHandReport fragment3 = new HIFragmentStockInHandReport();
//        Bundle args3 = new Bundle();
//        args3.putString(orgUnitIdC3, orgUnitIdC3);
//
//        args3.putString(orgUnitMode, orgUnitMode);
//        fragment3.setArguments(args3);
//        return fragment3;
//    }
//    public static HIFragmentStockInHandReport newInstance4(String orgUnitIdC4, String orgUnitMode) {
//        HIFragmentStockInHandReport fragment4 = new HIFragmentStockInHandReport();
//        Bundle args4 = new Bundle();
//        args4.putString(orgUnitIdC4, orgUnitIdC4);
//
//        args4.putString(orgUnitMode, orgUnitMode);
//        fragment4.setArguments(args4);
//        return fragment4;
//    }
//    public static HIFragmentStockInHandReport newInstance5(String orgUnitIdC5, String orgUnitMode) {
//        HIFragmentStockInHandReport fragment5 = new HIFragmentStockInHandReport();
//        Bundle args5 = new Bundle();
//        args5.putString(orgUnitIdC5, orgUnitIdC5);
//
//        args5.putString(orgUnitMode, orgUnitMode);
//        fragment5.setArguments(args5);
//        return fragment5;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Log.e(TAG, "onCreate: " + bundle.get(HIParamBIDHardcode.ORGUNITID));
            Log.e(TAG, "onCreate: " + bundle.get(HIParamBIDHardcode.ORGUNITID1));
            Log.e(TAG, "onCreate: " + bundle.get(HIParamBIDHardcode.ORGUNITID2));
            Log.e(TAG, "onCreate: " + bundle.get(HIParamBIDHardcode.ORGUNITID3));
            Log.e(TAG, "onCreate: " + bundle.get(HIParamBIDHardcode.ORGUNITID4));
            Log.e(TAG, "onCreate: " + bundle.get(HIParamBIDHardcode.ORGUNITID5));

             this.orgUnitId=bundle.getString(HIParamBIDHardcode.ORGUNITID5) ;
            this.orgUnitId = "GUhbn1R8q6w";
               this.orgUnitIdC1 = "DQjaNvP9ulw";

//            this.orgUnitIdC2 = "DQjaNvP9ulw";
//            this.orgUnitIdC3 = "DQjaNvP9ulw";
//            this.orgUnitIdC4 = "DQjaNvP9ulw";
//            this.orgUnitIdC5 ="GUhbn1R8q6w";

            this.orgUnitMode = HIParamBIDHardcode.OUMODEID;
        }
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
        LinearLayoutManager l2m = new LinearLayoutManager(getActivity());
        l2m.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager l3m = new LinearLayoutManager(getActivity());
        l3m.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager l4m = new LinearLayoutManager(getActivity());
        l4m.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager l5m = new LinearLayoutManager(getActivity());
        l5m.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager l6m = new LinearLayoutManager(getActivity());
        l6m.setOrientation(LinearLayoutManager.VERTICAL);
        vChart.setDrawBarShadow(false);
        vChart.setDrawValueAboveBar(true);
        vChart.setDescription("");
        vChart.setPinchZoom(false);
        vChart.setDrawGridBackground(false);

        vChart1.setDrawBarShadow(false);
        vChart1.setDrawValueAboveBar(true);
        vChart1.setDescription("");
        vChart1.setPinchZoom(false);
        vChart1.setDrawGridBackground(false);

        vChart2.setDrawBarShadow(false);
        vChart2.setDrawValueAboveBar(true);
        vChart2.setDescription("");
        vChart2.setPinchZoom(false);
        vChart2.setDrawGridBackground(false);

        vChart3.setDrawBarShadow(false);
        vChart3.setDrawValueAboveBar(true);
        vChart3.setDescription("");
        vChart3.setPinchZoom(false);
        vChart3.setDrawGridBackground(false);

        vChart4.setDrawBarShadow(false);
        vChart4.setDrawValueAboveBar(true);
        vChart4.setDescription("");
        vChart4.setPinchZoom(false);
        vChart4.setDrawGridBackground(false);

        vChart5.setDrawBarShadow(false);
        vChart5.setDrawValueAboveBar(true);
        vChart5.setDescription("");
        vChart5.setPinchZoom(false);
        vChart5.setDrawGridBackground(false);

        XAxis xAxis = vChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        XAxis xAxis1 = vChart1.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis1.setDrawGridLines(false);
        xAxis1.setSpaceBetweenLabels(2);

        XAxis xAxis2 = vChart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setDrawGridLines(false);
        xAxis2.setSpaceBetweenLabels(2);

        XAxis xAxis3 = vChart3.getXAxis();
        xAxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis3.setDrawGridLines(false);
        xAxis3.setSpaceBetweenLabels(2);


        XAxis xAxis4 = vChart4.getXAxis();
        xAxis4.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis4.setDrawGridLines(false);
        xAxis4.setSpaceBetweenLabels(2);

        XAxis xAxis5 = vChart5.getXAxis();
        xAxis5.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis5.setDrawGridLines(false);
        xAxis5.setSpaceBetweenLabels(2);


        YAxis leftAxis = vChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);


        YAxis leftAxis1 = vChart1.getAxisLeft();
        leftAxis1.setLabelCount(8, false);
        leftAxis1.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis1.setSpaceTop(15f);

        YAxis leftAxis2 = vChart2.getAxisLeft();
        leftAxis2.setLabelCount(8, false);
        leftAxis2.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis2.setSpaceTop(15f);

        YAxis leftAxis3 = vChart3.getAxisLeft();
        leftAxis3.setLabelCount(8, false);
        leftAxis3.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis3.setSpaceTop(15f);

        YAxis leftAxis4 = vChart4.getAxisLeft();
        leftAxis4.setLabelCount(8, false);
        leftAxis4.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis4.setSpaceTop(15f);

        YAxis leftAxis5 = vChart5.getAxisLeft();
        leftAxis5.setLabelCount(8, false);
        leftAxis5.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis5.setSpaceTop(15f);


        YAxis rightAxis = vChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);

        YAxis rightAxis1 = vChart1.getAxisRight();
        rightAxis1.setDrawGridLines(false);
        rightAxis1.setLabelCount(8, false);
        rightAxis1.setSpaceTop(15f);

        YAxis rightAxis2 = vChart2.getAxisRight();
        rightAxis2.setDrawGridLines(false);
        rightAxis2.setLabelCount(8, false);
        rightAxis2.setSpaceTop(15f);

        YAxis rightAxis3 = vChart3.getAxisRight();
        rightAxis3.setDrawGridLines(false);
        rightAxis3.setLabelCount(8, false);
        rightAxis3.setSpaceTop(15f);

        YAxis rightAxis4 = vChart4.getAxisRight();
        rightAxis4.setDrawGridLines(false);
        rightAxis4.setLabelCount(8, false);
        rightAxis4.setSpaceTop(15f);

        YAxis rightAxis5 = vChart5.getAxisRight();
        rightAxis5.setDrawGridLines(false);
        rightAxis5.setLabelCount(8, false);
        rightAxis5.setSpaceTop(15f);


        Legend l = vChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        Legend l1 = vChart1.getLegend();
        l1.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l1.setForm(Legend.LegendForm.SQUARE);
        l1.setFormSize(9f);
        l1.setTextSize(11f);
        l1.setXEntrySpace(4f);
        Legend l2 = vChart2.getLegend();
        l2.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l2.setForm(Legend.LegendForm.SQUARE);
        l2.setFormSize(9f);
        l2.setTextSize(11f);
        l2.setXEntrySpace(4f);

        Legend l3 = vChart3.getLegend();
        l3.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l3.setForm(Legend.LegendForm.SQUARE);
        l3.setFormSize(9f);
        l3.setTextSize(11f);
        l3.setXEntrySpace(4f);

        Legend l4 = vChart4.getLegend();
        l4.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l4.setForm(Legend.LegendForm.SQUARE);
        l4.setFormSize(9f);
        l4.setTextSize(11f);
        l4.setXEntrySpace(4f);

        Legend l5 = vChart5.getLegend();
        l5.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l5.setForm(Legend.LegendForm.SQUARE);
        l5.setFormSize(9f);
        l5.setTextSize(11f);
        l5.setXEntrySpace(4f);

        vChart.setTouchEnabled(false);
        vReport.setHasFixedSize(true);
        vReport.setLayoutManager(llm);
        vReport.setAdapter(adapter);
        vReport.getItemAnimator().setSupportsChangeAnimations(true);
        vReport.setItemAnimator(new DefaultItemAnimator());

        vChart1.setTouchEnabled(false);
        vReport1.setHasFixedSize(true);
        vReport1.setLayoutManager(l2m);
        vReport1.setAdapter(adapter1);
        vReport1.getItemAnimator().setSupportsChangeAnimations(true);
        vReport1.setItemAnimator(new DefaultItemAnimator());

        vChart2.setTouchEnabled(false);
        vReport2.setHasFixedSize(true);
        vReport2.setLayoutManager(l3m);
        vReport2.setAdapter(adapter2);
        vReport2.getItemAnimator().setSupportsChangeAnimations(true);
        vReport2.setItemAnimator(new DefaultItemAnimator());

        vChart3.setTouchEnabled(false);
        vReport3.setHasFixedSize(true);
        vReport3.setLayoutManager(l4m);
        vReport3.setAdapter(adapter3);
        vReport3.getItemAnimator().setSupportsChangeAnimations(true);
        vReport3.setItemAnimator(new DefaultItemAnimator());

        vChart4.setTouchEnabled(false);
        vReport4.setHasFixedSize(true);
        vReport4.setLayoutManager(l5m);
        vReport4.setAdapter(adapter4);
        vReport4.getItemAnimator().setSupportsChangeAnimations(true);
        vReport4.setItemAnimator(new DefaultItemAnimator());

        vChart5.setTouchEnabled(false);
        vReport5.setHasFixedSize(true);
        vReport5.setLayoutManager(l6m);
        vReport5.setAdapter(adapter5);
        vReport5.getItemAnimator().setSupportsChangeAnimations(true);
        vReport5.setItemAnimator(new DefaultItemAnimator());

        if (flow != null) {
            adapter.setHiStockRowList(new ArrayList<>());
            adapter.setLoadDone(false);
            flow.getStockInHandReport(this, orgUnitMode, orgUnitId);
            Log.e(TAG, "flow  " + flow);
        }
        if (flow1 != null) {
            adapter1.setHiStockRowList(new ArrayList<>());
            adapter1.setLoadDone(false);
            flow1.getStockInHandReport2(this, orgUnitMode, orgUnitIdC1);
            Log.e(TAG, "flow1  " + flow1);
            Log.e(TAG, "Adapter:flow  " + adapter);
            Log.e(TAG, "Adapter1 flow : " + adapter1);
        }

        if (flow2 != null) {
            adapter2.setHiStockRowList(new ArrayList<>());
            adapter2.setLoadDone(false);
            flow2.getStockInHandReport2(this, orgUnitMode, orgUnitIdC2);

        }
        if (flow3 != null) {
            adapter3.setHiStockRowList(new ArrayList<>());
            adapter3.setLoadDone(false);
            flow3.getStockInHandReport3(this, orgUnitMode, orgUnitIdC3);
        }
        if (flow4 != null) {
            adapter4.setHiStockRowList(new ArrayList<>());
            adapter4.setLoadDone(false);
            flow4.getStockInHandReport4(this, orgUnitMode, orgUnitIdC4);
        }
        if (flow5 != null) {
            adapter5.setHiStockRowList(new ArrayList<>());
            adapter5.setLoadDone(false);
            flow5.getStockInHandReport(this, orgUnitMode, orgUnitIdC5);
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
        flow1.onStop();

        flow2.onStop();
        flow3.onStop();
        flow4.onStop();
        flow5.onStop();
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
            adapter1.setHiStockRowList(resStock.rows);
            adapter1.setLoadDone(true);

            adapter2.setHiStockRowList(resStock.rows);
            adapter2.setLoadDone(true);

            adapter3.setHiStockRowList(resStock.rows);
            adapter3.setLoadDone(true);

            adapter4.setHiStockRowList(resStock.rows);
            adapter4.setLoadDone(true);

            adapter5.setHiStockRowList(resStock.rows);
            adapter5.setLoadDone(true);

            createChart(resStock.rows);
            createChart1(resStock.rows);
            createChart2(resStock.rows);
            createChart3(resStock.rows);
            createChart4(resStock.rows);
            createChart5(resStock.rows);

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

        BarDataSet set1 = new BarDataSet(yVals1, "Clinic 1");
        set1.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        vChart.setData(data);
        vChart.animateXY(500, 1000);

    }

    public void createChart1(List<HIStockRow> rows) {

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

        BarDataSet set2 = new BarDataSet(yVals1, "Clinic 2");
        set2.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart1.setData(data);
        vChart1.animateXY(500, 1000);
    }

    public void createChart2(List<HIStockRow> rows) {

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

        BarDataSet set2 = new BarDataSet(yVals1, "Clinic 2");
        set2.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart2.setData(data);
        vChart2.animateXY(500, 1000);
    }

    public void createChart3(List<HIStockRow> rows) {

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

        BarDataSet set2 = new BarDataSet(yVals1, "Clinic 3");
        set2.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart3.setData(data);
        vChart3.animateXY(500, 1000);
    }

    public void createChart4(List<HIStockRow> rows) {

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

        BarDataSet set2 = new BarDataSet(yVals1, "Clinic 4");
        set2.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart4.setData(data);
        vChart4.animateXY(500, 1000);
    }

    public void createChart5(List<HIStockRow> rows) {

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

        BarDataSet set2 = new BarDataSet(yVals1, "Clinic 5");
        set2.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart5.setData(data);
        vChart5.animateXY(500, 1000);
    }
}