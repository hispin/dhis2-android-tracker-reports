package org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hidefaulterlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.event.HIEvent;
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterBIDReport;
import org.hispindia.bidtrackerreports.mvp.view.HIIViewTodayScheduleReport;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterDefaulterListReport;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.HIParamBIDHardcode;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Sourabh on 1/20/16.
 */
public class HIFragmentDefaulterListReport extends HICFragmentBase implements HIIViewTodayScheduleReport {

    public final static String TAG = HIFragmentDefaulterListReport.class.getSimpleName();

    @Bind(R.id.vReport)
    RecyclerView vReport;

    @Bind(R.id.textView4)
    TextView textView4;

    @Bind(R.id.etvaccinesmissed)
    EditText etvaccinemissed;

    @Bind(R.id.etdomicile)
    EditText etdomicile;

   @Bind(R.id.etfollowup)
    EditText etfollowup;

    @Bind(R.id.btnFilter)
    Button btnFilter;

    @Inject
    HIPresenterBIDReport flow;
    HIAdapterDefaulterListReport adapter;

    private String orgUnitId;
    private String orgUnitMode;
    private String programId;
    private String programStageId;

    public static HIFragmentDefaulterListReport newInstance() {
        HIFragmentDefaulterListReport fragment = new HIFragmentDefaulterListReport();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.orgUnitId = HIParamBIDHardcode.ORGUNITID;
        this.orgUnitMode = HIParamBIDHardcode.OUMODE;
        this.programId = HIParamBIDHardcode.PROGRAMID;
        this.programStageId = HIParamBIDHardcode.PROGRAMSTAGEID;
        this.adapter = new HIAdapterDefaulterListReport();
        getActivity().setTitle(getString(R.string.btn_defaulter_list));
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
        View view = inflater.inflate(R.layout.hifragment_defaulter_list_report, container, false);
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

    //@Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Calendar myCalendar = Calendar.getInstance();
//        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                String myFormat = "yyyy-MM-dd"; //In which you need put here
//
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                etStartDate.setText(sdf.format(myCalendar.getTime()));
//                Log.e(TAG,"Start Date"+ etStartDate);
//                btnFilter.setVisibility(View.VISIBLE);
//            }
//
//        };
//        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                String myFormat = "yyyy-MM-dd"; //In which you need put here
//
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//                etEndDate.setText(sdf.format(myCalendar.getTime()));
//
//
//            }
//
//        };
//
//        etStartDate.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG, "onClick: ");
//                // TODO Auto-generated method stub
//                new DatePickerDialog(getActivity(), date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        etEndDate.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG, "onClick: ");
//                // TODO Auto-generated method stub
//                new DatePickerDialog(getActivity(), date2, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//    }


    @OnClick(R.id.btnFilter)
    public void btnFilterOnClick() {

//        if(etStartDate.getText().toString().equals("null")||etStartDate.getText().toString().equals("")||etEndDate.getText().toString().equals("")||etEndDate.getText().toString().equals("null")) {
//            Toast.makeText(getActivity().getApplicationContext(), "Please Choose Date First", Toast.LENGTH_SHORT);
//        }
//
//        else {
//
//            adapter.filter(etStartDate.getText().toString(), etEndDate.getText().toString());
//        }

        //adapter.setDemandList(filterDemandbydate(listTemp, etStartDate.getText().toString(), etEndDate.getText().toString()));

    }

    @Override
    protected void onInjected() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        vReport.setHasFixedSize(true);
        vReport.setLayoutManager(llm);

        vReport.setAdapter(adapter);

        //vReport.getItemAnimator().setSupportsChangeAnimations(true);

        vReport.setItemAnimator(new DefaultItemAnimator());
        if (flow != null) {
            adapter.setLoadDone(false);
            flow.getTodayScheduleEventReport(this, orgUnitId, orgUnitMode, programId, programStageId, true);
        }

    }

    @Override
    public void updateRow(HIDBbidrow row) {
        Log.e(TAG,"ROW: "+ row);
        if (row == null) {
            adapter.setLoadDone(true);
        }
        else {
            adapter.updateRow(row);
            Log.e(TAG,"ROW: after"+ row);
        }
    }

}
