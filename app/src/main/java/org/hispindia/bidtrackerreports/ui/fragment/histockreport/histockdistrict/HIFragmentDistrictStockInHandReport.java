package org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockdistrict;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.hisp.dhis.android.sdk.persistence.models.OrganisationUnit;
import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.event.HIEvent;
import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow;
import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow1;
import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow2;
import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow3;
import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow4;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock1;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock3;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock4;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock5;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport1;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport2;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport3;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport4;
import org.hispindia.bidtrackerreports.mvp.view.HIViewStockInHandReport;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport1;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport2;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport3;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport4;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.HIParamBIDHardcode;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhand.HIFragmentStockInHandReport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Sourabh on 2/6/2016.
 */
public class HIFragmentDistrictStockInHandReport extends HICFragmentBase implements HIViewStockInHandReport {

    public final static String TAG = HIFragmentDistrictStockInHandReport.class.getSimpleName();
    private static final String ORG_UNIT_LV = "extra:orgUnitLv";
    public static String olabel = new Select().from(OrganisationUnit.class).querySingle().getLabel();
    public static int ORGUNITI = new Select().from(OrganisationUnit.class).querySingle().getLevel();
    //    @Bind(R.id.vReport)
//    RecyclerView vReport;
//    @Bind(R.id.tvdistrict)
//    TextView Label;
    public static String ORGUN = new Select().from(OrganisationUnit.class).querySingle().getLabel();
    protected INavigationHandler mNavigationHandler;
    @Bind(R.id.tvchart2)
    TextView tvchart2;
    @Bind(R.id.tvchart3)
    TextView tvchart3;
    @Bind(R.id.tvchart4)
    TextView tvchart4;
    @Bind(R.id.tvchart1)
    TextView tvchart1;
    @Bind(R.id.vChart)
    BarChart vChart;
    //    @Bind(R.id.vReport1)
//    RecyclerView vReport1;
    @Bind(R.id.vChart1)
    BarChart vChart1;

    //    @Bind(R.id.vReport4)
//    RecyclerView vReport4;
//    @Bind(R.id.vChart4)
//    BarChart vChart4;

//    @Bind(R.id.vReport5)
//    RecyclerView vReport5;
//    @Bind(R.id.vChart5)
//    BarChart vChart5;
//    @Bind(R.id.vReport2)
//    RecyclerView vReport2;
@Bind(R.id.vChart2)
BarChart vChart2;
    //    @Bind(R.id.vReport3)
//    RecyclerView vReport3;
    @Bind(R.id.vChart3)
    BarChart vChart3;
    @Inject
    HIPresenterStockReport flow;
    @Inject
    HIAdapterStockReport adapter;
    @Inject
    HIPresenterStockReport1 flow1;
    @Inject
    HIAdapterStockReport1 adapter1;
    @Inject
    HIPresenterStockReport2 flow2;
    @Inject
    HIAdapterStockReport2 adapter2;
    @Inject
    HIPresenterStockReport3 flow3;
    @Inject
    HIAdapterStockReport3 adapter3;

//    @Inject
//    HIPresenterStockReport5 flow5;
//    @Inject
//    HIAdapterStockReport5 adapter5;
@Inject
HIPresenterStockReport4 flow4;
    @Inject
    HIAdapterStockReport4 adapter4;
    private String orgUnitId;
    private String orgUnitMode;
    private String orgUnitIdC1;
    private String orgUnitIdC2;
    private String orgUnitIdC3;
    private String orgUnitIdC4;
    private String orgUnitIdC5;

    public static HIFragmentStockInHandReport newInstance(String orgUnitId, String orgUnitMode, String orgUnitIdC1, String orgUnitIdC2,String orgUnitIdC3,String orgUnitIdC4,String orgUnitIdC5) {
        HIFragmentStockInHandReport fragment = new HIFragmentStockInHandReport();
        Bundle args = new Bundle();
        args.putString(orgUnitId, orgUnitId);
        args.putString(orgUnitIdC1, orgUnitIdC1);
        args.putString(orgUnitIdC2, orgUnitIdC2);
        args.putString(orgUnitIdC3, orgUnitIdC3);
        args.putString(orgUnitIdC4, orgUnitIdC4);
        args.putString(orgUnitIdC5, orgUnitIdC5);
        args.putString(orgUnitMode, orgUnitMode);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bundle fragmentArguments = getArguments();
        this.orgUnitId = HIParamBIDHardcode.ORGUNITID1;
        this.orgUnitIdC1 = HIParamBIDHardcode.ORGUNITID2;
        this.orgUnitIdC2 = HIParamBIDHardcode.ORGUNITID3;
        this.orgUnitIdC3 = HIParamBIDHardcode.ORGUNITID4;
        this.orgUnitIdC4 = HIParamBIDHardcode.ORGUNITID5;
        //  this.orgUnitIdC5 = HIParamBIDHardcode.ORGUNITID;
        this.orgUnitMode = HIParamBIDHardcode.OUMODEID;

        Log.e(TAG,"ouid:"+ HIParamBIDHardcode.ORGUNITID);
        Log.e(TAG,"omodeuid:"+ HIParamBIDHardcode.OUMODEID);
        Log.e(TAG,"od1:"+ HIParamBIDHardcode.ORGUNITID1);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.hifragment_district_stock_in_hand_report, container, false);
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
//        LinearLayoutManager l6m = new LinearLayoutManager(getActivity());
//        l6m.setOrientation(LinearLayoutManager.VERTICAL);

//        Label.setText(olabel );

        tvchart1.setText("Linda Clinic");
        tvchart2.setText("Simoonga Clinic");
        tvchart3.setText("Victoria Falls Clinic");
        tvchart4.setText("Maramba Clinic");

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

//        vChart4.setDrawBarShadow(false);
//        vChart4.setDrawValueAboveBar(true);
//        vChart4.setDescription("");
//        vChart4.setPinchZoom(false);
//        vChart4.setDrawGridBackground(false);

//        vChart5.setDrawBarShadow(false);
//        vChart5.setDrawValueAboveBar(true);
//        vChart5.setDescription("");
//        vChart5.setPinchZoom(false);
//        vChart5.setDrawGridBackground(false);


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
//
//        XAxis xAxis4 = vChart4.getXAxis();
//        xAxis4.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis4.setDrawGridLines(false);
//        xAxis4.setSpaceBetweenLabels(2);

//        XAxis xAxis5 = vChart5.getXAxis();
//        xAxis5.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis5.setDrawGridLines(false);
//        xAxis5.setSpaceBetweenLabels(2);

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
//
//        YAxis leftAxis4 = vChart4.getAxisLeft();
//        leftAxis4.setLabelCount(8, false);
//        leftAxis4.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis4.setSpaceTop(15f);

//        YAxis leftAxis5 = vChart4.getAxisLeft();
//        leftAxis5.setLabelCount(8, false);
//        leftAxis5.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis5.setSpaceTop(15f);

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

//        YAxis rightAxis4 = vChart4.getAxisRight();
//        rightAxis4.setDrawGridLines(false);
//        rightAxis4.setLabelCount(8, false);
//        rightAxis4.setSpaceTop(15f);

//        YAxis rightAxis5 = vChart5.getAxisRight();
//        rightAxis5.setDrawGridLines(false);
//        rightAxis5.setLabelCount(8, false);
//        rightAxis5.setSpaceTop(15f);

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

//        Legend l4 = vChart4.getLegend();
//        l4.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l4.setForm(Legend.LegendForm.SQUARE);
//        l4.setFormSize(9f);
//        l4.setTextSize(11f);
//        l4.setXEntrySpace(4f);

//        Legend l5 = vChart5.getLegend();
//        l5.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l5.setForm(Legend.LegendForm.SQUARE);
//        l5.setFormSize(9f);
//        l5.setTextSize(11f);
//        l5.setXEntrySpace(4f);

        vChart.setTouchEnabled(false);
//        vReport.setHasFixedSize(true);
//        vReport.setLayoutManager(llm);
//        vReport.setAdapter(adapter);
//        vReport.getItemAnimator().setSupportsChangeAnimations(true);
//        vReport.setItemAnimator(new DefaultItemAnimator());

        vChart1.setTouchEnabled(false);
//        vReport1.setHasFixedSize(true);
//        vReport1.setLayoutManager(l2m);
//        vReport1.setAdapter(adapter1);
//        vReport1.getItemAnimator().setSupportsChangeAnimations(true);
//        vReport1.setItemAnimator(new DefaultItemAnimator());

        vChart2.setTouchEnabled(false);
//        vReport2.setHasFixedSize(true);
//        vReport2.setLayoutManager(l3m);
//        vReport2.setAdapter(adapter2);
//        vReport2.getItemAnimator().setSupportsChangeAnimations(true);
//        vReport2.setItemAnimator(new DefaultItemAnimator());

        vChart3.setTouchEnabled(false);
//        vReport3.setHasFixedSize(true);
//        vReport3.setLayoutManager(l4m);
//        vReport3.setAdapter(adapter3);
//        vReport3.getItemAnimator().setSupportsChangeAnimations(true);
//        vReport3.setItemAnimator(new DefaultItemAnimator());

//        vChart4.setTouchEnabled(false);
//        vReport4.setHasFixedSize(true);
//        vReport4.setLayoutManager(l5m);
//        vReport4.setAdapter(adapter4);
//        vReport4.getItemAnimator().setSupportsChangeAnimations(true);
//        vReport4.setItemAnimator(new DefaultItemAnimator());

//    vChart5.setTouchEnabled(false);
//        vReport5.setHasFixedSize(true);
//        vReport5.setLayoutManager(l6m);
//        vReport5.setAdapter(adapter5);
//        vReport5.getItemAnimator().setSupportsChangeAnimations(true);
//        vReport5.setItemAnimator(new DefaultItemAnimator());


        if (flow != null) {
            adapter.setHiStockRowList(new ArrayList<>());
            adapter.setLoadDone(false);
            flow.getStockInHandReport(this, orgUnitMode, orgUnitId);


        }

        if (flow1 != null) {
            adapter1.setHiStockRowList1(new ArrayList<>());
            adapter1.setLoadDone(false);
            flow1.getStockInHandReport1(this, orgUnitMode, orgUnitIdC1);

        }


        if (flow2 != null) {
            adapter2.setHiStockRowList2(new ArrayList<>());
            adapter2.setLoadDone(false);
            flow2.getStockInHandReport2(this, orgUnitMode, orgUnitIdC2);
        }
        if (flow3 != null) {
            adapter3.setHiStockRowList3(new ArrayList<>());
            adapter3.setLoadDone(false);
            flow3.getStockInHandReport3(this, orgUnitMode, orgUnitIdC3);

        }
        if (flow4 != null) {
            adapter4.setHiStockRowList4(new ArrayList<>());
            adapter4.setLoadDone(false);
            flow4.getStockInHandReport4(this, orgUnitMode, orgUnitIdC4);

        }

//        if (flow5 != null) {
//            adapter5.setHiStockRowList5(new ArrayList<>());
//            adapter5.setLoadDone(false);
//            flow5.getStockInHandReport5(this, orgUnitMode, orgUnitIdC5);
//
//        }

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
//        flow5.onStop();
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

    public void updateRow1(HIResStock1 resStock1) {
        if (resStock1 != null) {
            Log.e(TAG,"Restock 1:"+resStock1 );
            adapter1.setHiStockRowList1(resStock1.rows1);
            adapter1.setLoadDone(true);
            createChart1(resStock1.rows1);
        }
    }

    public void updateRow2(HIResStock2 resStock2) {
        if (resStock2 != null) {
            Log.e(TAG,"Restock 1:"+resStock2 );
            adapter2.setHiStockRowList2(resStock2.rows2);
            adapter2.setLoadDone(true);
            createChart2(resStock2.rows2);
        }
    }

   public void updateRow3(HIResStock3 resStock3) {
        if (resStock3 != null) {
            Log.e(TAG,"Restock 1:"+resStock3 );
            adapter3.setHiStockRowList3(resStock3.rows3);
            adapter3.setLoadDone(true);
            createChart3(resStock3.rows3);
        }
    }

    public void updateRow4(HIResStock4 resStock4) {
        if (resStock4 != null) {
//            Log.e(TAG,"Restock 1:"+resStock4 );
//            adapter4.setHiStockRowList4(resStock4.rows4);
//            adapter4.setLoadDone(true);
//            createChart4(resStock4.rows4);
        }
    }

    public void updateRow5(HIResStock5 resStock5) {
        if (resStock5 != null) {
            Log.e(TAG,"Restock 1:"+resStock5 );
            //adapter5.setHiStockRowList5(resStock5.rows5);
           // adapter5.setLoadDone(true);
            //createChart5(resStock5.rows5);
        }
    }

    public void createChart(List<HIStockRow> rows) {

        Log.e(TAG, "HIStockRow" + rows);
        Log.e(TAG, "HIStockRow size" + rows.size());
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
                Log.e(TAG, "Y Values:" + yVals1);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        BarDataSet set1 = new BarDataSet(yVals1, "Linda Clinic");
        set1.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        vChart.setData(data);
        vChart.animateXY(500, 1000);
    }

    public void createChart1(List<HIStockRow1> rows) {
        Log.e(TAG,"HIStockRow"+rows);   Log.e(TAG,"HIStockRow size"+rows.size());
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

        BarDataSet set2 = new BarDataSet(yVals1, "Simoonga Clinic");
        set2.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set2);
        Log.e(TAG, "Data Set Value:" + dataSets);
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart1.setData(data);
        vChart1.animateXY(500, 1000);
    }

    public void createChart2(List<HIStockRow2> rows) {
        Log.e(TAG,"HIStockRow"+rows);   Log.e(TAG,"HIStockRow size"+rows.size());
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

        BarDataSet set2 = new BarDataSet(yVals1, "Victoria Falls Clinic");
        set2.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set2);
        Log.e(TAG, "Data Set Value:" + dataSets);
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart2.setData(data);
        vChart2.animateXY(500, 1000);
    }

    public void createChart3(List<HIStockRow3> rows) {
        Log.e(TAG,"HIStockRow"+rows);   Log.e(TAG,"HIStockRow size"+rows.size());
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

        BarDataSet set2 = new BarDataSet(yVals1, "Maramba Clinic");
        set2.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart3.setData(data);
        vChart3.animateXY(500, 1000);
    }

    public void createChart4(List<HIStockRow4> rows) {
        Log.e(TAG,"HIStockRow"+rows);   Log.e(TAG,"HIStockRow size"+rows.size());
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

        BarDataSet set2 = new BarDataSet(yVals1, "Maramba Clinic");
        set2.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
//        vChart4.setData(data);
//        vChart4.animateXY(500, 1000);
    }

    public class MyYAxisValueFormatter implements YAxisValueFormatter {

        private DecimalFormat mFormat;

        public MyYAxisValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.00"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            // write your logic here
            // access the YAxis object to get more information
            return mFormat.format(value) + " $"; // e.g. append a dollar-sign
        }
    }

//   public void createChart5(List<HIStockRow5> rows) {
//        Log.e(TAG,"HIStockRow"+rows);   Log.e(TAG,"HIStockRow size"+rows.size());
//        ArrayList<String> xVals = new ArrayList<>();
//        for (int i = 0; i < rows.size(); i++) {
//            String name = rows.get(i).getName();
//            try {
//                xVals.add(name.substring(0, name.indexOf(" ")));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        ArrayList<BarEntry> yVals1 = new ArrayList<>();
//        for (int i = 0; i < rows.size(); i++) {
//            try {
//                yVals1.add(new BarEntry(Integer.parseInt(rows.get(i).getValue()), i));
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
//
//        BarDataSet set2 = new BarDataSet(yVals1, "Clinic 2");
//        set2.setBarSpacePercent(35f);
//
//        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//        dataSets.add(set2);
//
//        BarData data = new BarData(xVals, dataSets);
//        data.setValueTextSize(10f);
//        vChart5.setData(data);
//        vChart5.animateXY(500, 1000);
//    }


}
