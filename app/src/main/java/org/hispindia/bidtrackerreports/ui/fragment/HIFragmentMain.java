package org.hispindia.bidtrackerreports.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.sql.language.Select;

import org.hisp.dhis.android.sdk.persistence.models.OrganisationUnit;
import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.fragment.global.selectprogram.SelectProgramFragment;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hitodayschedule.HIFragmentTodayScheduleReport;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hivaccinestatus.HIFragmentVaccineStatusReport;
import org.hispindia.bidtrackerreports.ui.fragment.hischvaccinereport.HIFragmentSchvaccineSelectProgram;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.HIFragmentStockSelectProgram;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockdemand.HIFragmentStockDemandReport;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhand.HIFragmentStockInHandReport;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhandvsdemand.HIFragmentStockInHandvsDemandReport;

import butterknife.OnClick;

/**
 * Created by nhancao on 1/27/16.
 */
public class HIFragmentMain extends HICFragmentBase {

    public final static String TAG = HIFragmentMain.class.getSimpleName();
    public static int ORGUNITI = new Select().from(OrganisationUnit.class).querySingle().getLevel();
    public static String olabel = new Select().from(OrganisationUnit.class).querySingle().getLabel();
    public static String ORGUNITID = new Select().from(OrganisationUnit.class).querySingle().getId();
    public String orglabel = olabel.toString();
    public int orglevel = ORGUNITI;
    protected INavigationHandler mNavigationHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.hifragment_main, container, false);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof INavigationHandler) {
            mNavigationHandler = (INavigationHandler) activity;
        } else {
            throw new IllegalArgumentException("Activity must " +
                    "implement INavigationHandler interface");
        }
        getActivity().setTitle(getString(R.string.app_name));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNavigationHandler = null;
    }

    @SuppressWarnings("unused")

    //public static String ORGUNITID="GUhbn1R8q6w";
    public void btnTrackerReport() {
        mNavigationHandler.switchFragment(new SelectProgramFragment(), SelectProgramFragment.TAG, true);
//        EventDataEntryFragment fragment = EventDataEntryFragment.newInstanceWithEnrollment("DQjaNvP9ulw", "SSLpOM0r1U7", "s53RFfXA75f" , 1, 1);
//        mNavigationHandler.switchFragment(fragment, EventDataEntryFragment.TAG, true);
    }

    @OnClick(R.id.btnTodayReport)
    @SuppressWarnings("unused")
    public void btnBidReport() {
        mNavigationHandler.switchFragment(new HIFragmentTodayScheduleReport(), HIFragmentTodayScheduleReport.TAG, true);
    }

    @OnClick(R.id.btnVaccineStatus)
    @SuppressWarnings("unused")
    public void btnVaccineStatus() {
        mNavigationHandler.switchFragment(new HIFragmentVaccineStatusReport(), HIFragmentVaccineStatusReport.TAG, true);
    }

//    @OnClick(R.id.factodo)
//    @SuppressWarnings("unused")
//    public void factodo() {
//        mNavigationHandler.switchFragment(new HIFragmentSchvaccineSelectProgram(), HIFragmentSchvaccineReport.TAG, true);
//    }


    @SuppressWarnings("unused")
    public void btnStockReport() {
        mNavigationHandler.switchFragment(new HIFragmentStockSelectProgram(), HIFragmentStockSelectProgram.TAG, true);
    }


    @SuppressWarnings("unused")
    public void btnSchvaccineReport() {
        mNavigationHandler.switchFragment(new HIFragmentSchvaccineSelectProgram(), HIFragmentSchvaccineSelectProgram.TAG, true);
    }

//    @OnClick(R.id.btnOverdueReport)
//    @SuppressWarnings("unused")
//    public void btnOverdueReport() {
//        mNavigationHandler.switchFragment(new HIFragmentOverdueSelectProgram(), HIFragmentOverdueSelectProgram.TAG, true);
//    }

    @OnClick(R.id.btnStockInHandReport)
    @SuppressWarnings("unused")
    public void btnStockInHandReport() {
//        Fragment fragment = HIFragmentStockInHandReport.newInstance("GUhbn1R8q6w", "GUhbn1R8q6w","lTqNF1rWha3");
//        mNavigationHandler.switchFragment(fragment, HIFragmentStockInHandReport.TAG, true);
        mNavigationHandler.switchFragment(new HIFragmentStockInHandReport(), HIFragmentStockInHandReport.TAG, true);
    }

    @OnClick(R.id.btnStockInHandvsDemandReport)
    @SuppressWarnings("unused")
    public void btnStockInHandvsDemandReport() {
        mNavigationHandler.switchFragment(new HIFragmentStockInHandvsDemandReport(), HIFragmentStockInHandvsDemandReport.TAG, true);
    }

    @OnClick(R.id.btnStockInDemandReport)
    @SuppressWarnings("unused")

    public void btnStockInDemandReport() {

        mNavigationHandler.switchFragment(new HIFragmentStockDemandReport(), HIFragmentStockDemandReport.TAG, true);


    }

//    @OnClick(R.id.btnstockdistrict)
//         @SuppressWarnings("unused")
//         public void btnstockdistrict() {
//        Intent intent = new Intent(getActivity(), ListViewMultiChartActivity.class);
//        startActivity(intent);
//        //  mNavigationHandler.switchFragment(new ListViewMultiChartActivity(), ListViewMultiChartActivity.TAG, true);
//    }
//public void checklevel() {
//    if (ORGUNITI == 3){
//
//    //show button
//
//    }
//}
    @OnClick(R.id.btnstockdistrict1)
         @SuppressWarnings("unused")
         public void btnstockdistrict1() {

//        //mNavigationHandler.switchFragment(new HIFragmentDistrictStockInHandReport(), HIFragmentDistrictStockInHandReport.TAG, true);
//        if(ORGUNITID=="GUhbn1R8q6w" || olabel=="Livingstone District"|| ORGUNITID=="" )
//        {
//            Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_SHORT);
//            mNavigationHandler.switchFragment(new HIFragmentDistrictStockInHandReport(), HIFragmentDistrictStockInHandReport.TAG, true);
//        }
//else if(ORGUNITID=="" || orglevel==0 || new Select().from(OrganisationUnit.class).querySingle().getLevel()==3 )
//        {
//
//            Toast.makeText(getActivity().getApplicationContext(), "Empty Results" , Toast.LENGTH_LONG).show();
//        }
//        else
//        {
//            Toast.makeText(getActivity().getApplicationContext(), "Else Nothing " , Toast.LENGTH_LONG).show();
//        }
//        Toast.makeText(getActivity().getApplicationContext(), "Level =="+ new Select().from(OrganisationUnit.class).querySingle().getLevel() , Toast.LENGTH_LONG).show();

        if (new Select().from(OrganisationUnit.class).querySingle().getId().toString() == "GUhbn1R8q6w") {
            Log.e(TAG, "Success for ID Match");
        }
        if (new Select().from(OrganisationUnit.class).querySingle().getLabel() == "Livingstone District") {
            Log.e(TAG, "Success for Label Match");
        }

        Log.e(TAG, "Level==" + new Select().from(OrganisationUnit.class).querySingle().getLevel());
        Log.e(TAG, "ID==" + new Select().from(OrganisationUnit.class).querySingle().getId());
        Log.e(TAG, "Parent==" + new Select().from(OrganisationUnit.class).querySingle().getParent());
        Log.e(TAG, "Label==" + new Select().from(OrganisationUnit.class).querySingle().getLabel());
        Log.e(TAG, "Programs==" + new Select().from(OrganisationUnit.class).querySingle().getPrograms());
        Log.e(TAG, "To string==" + new Select().from(OrganisationUnit.class).querySingle().toString());
    }

}
