package org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhandvsdemanddistrict;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

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
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock1;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock2;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock3;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock4;
import org.hispindia.bidtrackerreports.mvp.model.remote.response.HIResStock5;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Sourabh on 2/6/2016.
 */
public class HIFragmentStockDistrictInHandvsDemandReport extends HICFragmentBase implements HIViewStockInHandReport, HIIViewTodayScheduleReport, AdapterView.OnItemSelectedListener {

    public final static String TAG = HIFragmentStockDistrictInHandvsDemandReport.class.getSimpleName();
    protected INavigationHandler mNavigationHandler;
    @Bind(R.id.vReport)
    RecyclerView vReport;
    @Bind(R.id.vChart)
    BarChart vChart;


    @Bind(R.id.vChart1)
    BarChart vChart1;
    @Bind(R.id.vChart2)
    BarChart vChart2;
    @Bind(R.id.vChart3)
    BarChart vChart3;
    @Bind(R.id.vChart4)
    BarChart vChart4;



    @Bind(R.id.vReportOption)
    Spinner vReportOption;

    @Bind(R.id.etStartDate)
    EditText etStartDate;
    @Bind(R.id.etEndDate)
    EditText etEndDate;




    @Inject
    HIPresenterStockReport flow;
    @Inject
    HIPresenterBIDReport flowGetDemand;

    HIAdapterStockDemandReport adapter;
    List<HIDBbidrow> listTemp = new ArrayList<>();
    List<HIDBbidrow> listTempFilter = new ArrayList<>();

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
        View view = inflater.inflate(R.layout.hifragment_stock_district_in_handvsdemand_report, container, false);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here

                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                etStartDate.setText(sdf.format(myCalendar.getTime()));

            }

        };
        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //String myFormat = "MM/dd/yy"; //In which you need put here
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                etEndDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        etStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: ");
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        etEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: ");
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
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
        vChart.setDrawBarShadow(false);
        vChart.setDrawValueAboveBar(true);
        vChart.setDescription("");
        vChart.setPinchZoom(false);
        vChart.setDrawGridBackground(false);
        XAxis xAxis = vChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        vChart1.setDrawBarShadow(false);
        vChart1.setDrawValueAboveBar(true);
        vChart1.setDescription("");
        vChart1.setPinchZoom(false);
        vChart1.setDrawGridBackground(false);
        XAxis xAxis1 = vChart.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis1.setDrawGridLines(false);
        xAxis1.setSpaceBetweenLabels(2);

        vChart2.setDrawBarShadow(false);
        vChart2.setDrawValueAboveBar(true);
        vChart2.setDescription("");
        vChart2.setPinchZoom(false);
        vChart2.setDrawGridBackground(false);
        XAxis xAxis2 = vChart.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setDrawGridLines(false);
        xAxis2.setSpaceBetweenLabels(2);

        vChart3.setDrawBarShadow(false);
        vChart3.setDrawValueAboveBar(true);
        vChart3.setDescription("");
        vChart3.setPinchZoom(false);
        vChart3.setDrawGridBackground(false);
        XAxis xAxis3 = vChart.getXAxis();
        xAxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis3.setDrawGridLines(false);
        xAxis3.setSpaceBetweenLabels(2);

        vChart4.setDrawBarShadow(false);
        vChart4.setDrawValueAboveBar(true);
        vChart4.setDescription("");
        vChart4.setPinchZoom(false);
        vChart4.setDrawGridBackground(false);
        XAxis xAxis4 = vChart.getXAxis();
        xAxis4.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis4.setDrawGridLines(false);
        xAxis4.setSpaceBetweenLabels(2);

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

        vChart.setTouchEnabled(false);
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
    }

    @Override
    public void onPause() {
        HIEvent.unregister(this);
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

    public void updateRow1(HIResStock1 resStock) {
        if (resStock != null) {
            // adapter.setInHandList(resStock.rows);
            //createChart(adapter.hiStockRowList, adapter.inhand, adapter.demand);
        }
    }

    public void updateRow2(HIResStock2 resStock2) {
        if (resStock2 != null) {
            // adapter.setInHandList(resStock.rows);
            //createChart(adapter.hiStockRowList, adapter.inhand, adapter.demand);
        }
    }

    public void updateRow3(HIResStock3 resStock3) {
        if (resStock3 != null) {
            // adapter.setInHandList(resStock.rows);
            //createChart(adapter.hiStockRowList, adapter.inhand, adapter.demand);
        }
    }

    public void updateRow4(HIResStock4 resStock4) {
        if (resStock4 != null) {
            // adapter.setInHandList(resStock.rows);
            //createChart(adapter.hiStockRowList, adapter.inhand, adapter.demand);
        }
    }

    public void updateRow5(HIResStock5 resStock5) {
        if (resStock5 != null) {
            // adapter.setInHandList(resStock.rows);
            //createChart(adapter.hiStockRowList, adapter.inhand, adapter.demand);
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
//            for (int i = 0; i < xAxis.size(); i++) {
//                yVals1.add(new BarEntry(inhand.get(xAxis.get(i)), i));
//            }
            for (int i = 0; i < 6; i++) {
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
            set2.setColor(Color.DKGRAY);
            dataSets.add(set2);
        }
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart.setData(data);
        vChart.animateXY(500, 1000);
    }

    public void createChart1(List<String> xAxis, HashMap<String, Integer> inhand, HashMap<String, Integer> demand) {
        vChart1.clear();
        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < xAxis.size(); i++) {
            xVals.add(xAxis.get(i));
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        if (inhand != null && inhand.size() > 0) {
//            for (int i = 0; i < xAxis.size(); i++) {
//                yVals1.add(new BarEntry(inhand.get(xAxis.get(i)), i));
//            }
            for (int i = 0; i < 6; i++) {
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
            set2.setColor(Color.DKGRAY);
            dataSets.add(set2);
        }
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart1.setData(data);
        vChart1.animateXY(500, 1000);
    }
    public void createChart2(List<String> xAxis, HashMap<String, Integer> inhand, HashMap<String, Integer> demand) {
        vChart2.clear();
        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < xAxis.size(); i++) {
            xVals.add(xAxis.get(i));
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        if (inhand != null && inhand.size() > 0) {
//            for (int i = 0; i < xAxis.size(); i++) {
//                yVals1.add(new BarEntry(inhand.get(xAxis.get(i)), i));
//            }
            for (int i = 0; i < 6; i++) {
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
            set2.setColor(Color.DKGRAY);
            dataSets.add(set2);
        }
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart2.setData(data);
        vChart2.animateXY(500, 1000);
    }

    public void createChart3(List<String> xAxis, HashMap<String, Integer> inhand, HashMap<String, Integer> demand) {
        vChart3.clear();
        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < xAxis.size(); i++) {
            xVals.add(xAxis.get(i));
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        if (inhand != null && inhand.size() > 0) {
//            for (int i = 0; i < xAxis.size(); i++) {
//                yVals1.add(new BarEntry(inhand.get(xAxis.get(i)), i));
//            }
            for (int i = 0; i < 6; i++) {
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
            set2.setColor(Color.DKGRAY);
            dataSets.add(set2);
        }
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart3.setData(data);
        vChart3.animateXY(500, 1000);
    }

    public void createChart4(List<String> xAxis, HashMap<String, Integer> inhand, HashMap<String, Integer> demand) {
        vChart4.clear();
        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < xAxis.size(); i++) {
            xVals.add(xAxis.get(i));
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        if (inhand != null && inhand.size() > 0) {
//            for (int i = 0; i < xAxis.size(); i++) {
//                yVals1.add(new BarEntry(inhand.get(xAxis.get(i)), i));
//            }
            for (int i = 0; i < 6; i++) {
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
            set2.setColor(Color.DKGRAY);
            dataSets.add(set2);
        }
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        vChart4.setData(data);
        vChart4.animateXY(500, 1000);
    }


    @Override
    public void updateRow(HIDBbidrow row) {
        if (row != null) {
            listTemp.add(row);
        } else {
            adapter.setDemandList(filterDemand(1, 0));
            createChart(adapter.hiStockRowList, adapter.inhand, adapter.demand);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                listTempFilter = filterDemand(1, 0);
                adapter.setDemandList(listTempFilter);
                break;
            case 1:
                listTempFilter = filterDemand(3, 0);
                adapter.setDemandList(listTempFilter);
                break;
            case 2:
                listTempFilter = filterDemand(7, 0);
                adapter.setDemandList(listTempFilter);
                break;
            case 3:
                listTempFilter = filterDemand(14, 0);
                adapter.setDemandList(listTempFilter);
                break;
            case 4:
                listTempFilter = filterDemand(0, 1);
                adapter.setDemandList(listTempFilter);
                break;
        }
        createChart(adapter.hiStockRowList, adapter.inhand, adapter.demand);
        createChart2(adapter.hiStockRowList, adapter.inhand, adapter.demand);
        createChart3(adapter.hiStockRowList, adapter.inhand, adapter.demand);
        createChart4(adapter.hiStockRowList, adapter.inhand, adapter.demand);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick(R.id.btnFilter)
    public void btnFilterOnClick() {

        adapter.setDemandList(filterDemandbydate(listTemp, etStartDate.getText().toString(), etEndDate.getText().toString()));
        createChart(adapter.hiStockRowList, adapter.inhand, adapter.demand);
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

    public List<HIDBbidrow> filterDemandbydate(List<HIDBbidrow> arrOrigin, String startdate, String enddate) {
        Log.e(TAG, "filterDemandbydate: " + startdate + " - " + enddate + " size = " + arrOrigin.size());
        List<HIDBbidrow> result = new ArrayList<>();
        for (HIDBbidrow item : arrOrigin) {
            DateTime startD = DateTime.parse(startdate, DateTimeFormat.forPattern("yyyy-MM-dd"));
            DateTime endD = DateTime.parse(enddate, DateTimeFormat.forPattern("yyyy-MM-dd"));
            DateTime maxD = ((startD.isAfter(endD) ? startD : endD));
            DateTime dueDate = DateTime.parse(item.getDueDate(), DateTimeFormat.forPattern("yyyy-MM-dd"));
            if (dueDate.isBefore(maxD)) {
                result.add(item);
            }
        }
        return result;
    }
}
