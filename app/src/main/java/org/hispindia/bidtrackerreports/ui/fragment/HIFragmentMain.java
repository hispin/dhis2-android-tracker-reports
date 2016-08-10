package org.hispindia.bidtrackerreports.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.raizlabs.android.dbflow.sql.language.Select;

import org.hisp.dhis.android.sdk.persistence.models.OrganisationUnit;
import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.activity.MainActivity;
import org.hispindia.bidtrackerreports.ui.fragment.global.selectprogram.SelectProgramFragment;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hitodayschedule.HIFragmentTodayScheduleReport;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hivaccinestatus.HIFragmentVaccineStatusReport;
import org.hispindia.bidtrackerreports.ui.fragment.hischvaccinereport.HIFragmentSchvaccineSelectProgram;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.HIFragmentStockSelectProgram;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockdemand.HIFragmentStockDemandReport;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockdemanddistrict.HIFragmentStockDistrictDemandReport;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockdistrict.HIFragmentDistrictStockInHandReport;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhand.HIFragmentStockInHandReport;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhandvsdemand.HIFragmentStockInHandvsDemandReport;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhandvsdemanddistrict.HIFragmentStockDistrictInHandvsDemandReport;

import butterknife.OnClick;

/**
 * Created by nhancao on 1/27/16.
 */
public class HIFragmentMain extends HICFragmentBase {

    Button btnstockdistrict;
    public final static String TAG = HIFragmentMain.class.getSimpleName();
    protected INavigationHandler mNavigationHandler;

//    @Bind(R.id.btnstockdistrict1)
//    Button btnstockdistrict1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hifragment_main, container, false);
//        btnstockdistrict = (Button) view.findViewById(R.id.btnstockdistrict1);

        super.onCreate(savedInstanceState);
//        btnstockdistrict=(Button)getView().findViewById(R.id.btnstockdistrict1);
//        View view = inflater.inflate(R.layout.hifragment_main, container, false);
//
        return view;
    }

//    @Bind(R.id.buttonswitch)
//    Button buttonswitch;

//

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

//    @OnClick(R.id.btnBirthNotificationsReport)
//    @SuppressWarnings("unused")
//    public void btnBirthNotificationsReport() {
//        mNavigationHandler.switchFragment(new HIFragmentBirthNotificationsReport(), HIFragmentBirthNotificationsReport.TAG, true);
//    }

    @OnClick(R.id.btnVaccineStatus)
    @SuppressWarnings("unused")
    public void btnVaccineStatus() {
        mNavigationHandler.switchFragment(new HIFragmentVaccineStatusReport(), HIFragmentVaccineStatusReport.TAG, true);
    }

    @OnClick(R.id.buttonswitch)
    @SuppressWarnings("unused")
    public void buttonswitch() {

        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
               startActivity(intent);

        //mNavigationHandler.switchFragment(new HIFragmentVaccineStatusReport(), HIFragmentVaccineStatusReport.TAG, true);
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


        if(new Select().from(OrganisationUnit.class).querySingle().getId().equalsIgnoreCase("GUhbn1R8q6w") )
        {
            mNavigationHandler.switchFragment(new HIFragmentDistrictStockInHandReport(), HIFragmentDistrictStockInHandReport.TAG, true);
            Log.e(TAG,"ID Success "+new Select().from(OrganisationUnit.class).querySingle().getId() );
        }

        else
        {
            mNavigationHandler.switchFragment(new HIFragmentStockInHandReport(), HIFragmentStockInHandReport.TAG, true);
        }
    }

    @OnClick(R.id.btnStockInHandvsDemandReport)
    @SuppressWarnings("unused")
    public void btnStockInHandvsDemandReport() {

        if(new Select().from(OrganisationUnit.class).querySingle().getId().equalsIgnoreCase("GUhbn1R8q6w") )
        {
            mNavigationHandler.switchFragment(new HIFragmentStockDistrictInHandvsDemandReport(), HIFragmentStockDistrictInHandvsDemandReport.TAG, true);
            Log.e(TAG,"ID Success "+new Select().from(OrganisationUnit.class).querySingle().getId() );
        }

        else
        {
            mNavigationHandler.switchFragment(new HIFragmentStockInHandvsDemandReport(), HIFragmentStockInHandvsDemandReport.TAG, true);

        }
        }


    @OnClick(R.id.btnStockInDemandReport)
    @SuppressWarnings("unused")

    public void btnStockInDemandReport() {
        if(new Select().from(OrganisationUnit.class).querySingle().getId().equalsIgnoreCase("GUhbn1R8q6w") )
        {
            mNavigationHandler.switchFragment(new HIFragmentStockDistrictDemandReport(), HIFragmentStockDistrictDemandReport.TAG, true);
            Log.e(TAG,"ID Success "+new Select().from(OrganisationUnit.class).querySingle().getId() );
        }

        else
        {
            mNavigationHandler.switchFragment(new HIFragmentStockDemandReport(), HIFragmentStockDemandReport.TAG, true);

        }


    }

//    @OnClick(R.id.btnStockDistrictDemand)
//    @SuppressWarnings("unused")
//
//    public void btnStockDistrictDemand() {
//
//        mNavigationHandler.switchFragment(new HIFragmentStockDistrictDemandReport(), HIFragmentStockDistrictDemandReport.TAG, true);
//
//    }

//    @OnClick(R.id.btnStockvsdemandDistrictDemand)
//    @SuppressWarnings("unused")
//
//    public void btnStockvsdemandDistrictDemand() {
//
//        mNavigationHandler.switchFragment(new HIFragmentStockDistrictDemandReport(), HIFragmentStockDistrictDemandReport.TAG, true);
//
//    }


//         @OnClick(R.id.btnstockdistrict1)
//         @SuppressWarnings("unused")
//
//         public void btnstockdistrict1() {
//
//       //mNavigationHandler.switchFragment(new HIFragmentDistrictStockInHandReport(), HIFragmentDistrictStockInHandReport.TAG, true);
//        if(new Select().from(OrganisationUnit.class).querySingle().getId().equalsIgnoreCase("GUhbn1R8q6w") )
//        {
//            mNavigationHandler.switchFragment(new HIFragmentDistrictStockInHandReport(), HIFragmentDistrictStockInHandReport.TAG, true);
//            Log.e(TAG,"ID Success "+new Select().from(OrganisationUnit.class).querySingle().getId() );
//        }
//
//        else
//        {
//           String dname=new Select().from(UserAccount.class).querySingle().getDisplayName();
//                       Log.e(TAG,"DNAME:  "+dname );
//
//            Toast.makeText(getActivity().getApplicationContext(), "For District Level Users only", Toast.LENGTH_SHORT).show();
//            Log.e(TAG,"ID Failure "+new Select().from(OrganisationUnit.class).querySingle().getId() );
//        }
//
//
//    }

}
