package com.example.hisp_ws11.myapplication;

/**
 * Created by HISP-WS11 on 12/31/2015.
 */


import com.example.hisp_ws11.myapplication.fragments.selectprogram.SelectProgramFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import org.hisp.dhis.android.sdk.controllers.DhisController;
import org.hisp.dhis.android.sdk.controllers.DhisService;
import org.hisp.dhis.android.sdk.controllers.LoadingController;
import org.hisp.dhis.android.sdk.controllers.PeriodicSynchronizerController;
import org.hisp.dhis.android.sdk.persistence.Dhis2Application;
import org.hisp.dhis.android.sdk.persistence.preferences.ResourceType;
import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hisp.dhis.android.sdk.ui.activities.OnBackPressedListener;
import org.hisp.dhis.android.sdk.ui.fragments.loading.LoadingFragment;
import org.hisp.dhis.android.sdk.utils.UiUtils;

public class BIDreports extends AppCompatActivity implements INavigationHandler {

    public final static String TAG = MainActivity.class.getSimpleName();
    private OnBackPressedListener mBackPressedListener;
    public Spinner s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocreportsmain);

        s1=(Spinner)findViewById(R.id.spinner2);

        //  s1.setOnItemSelectedListener(this);
        final String[] contents={
                "CHILDREN",
                "SELECTED ",
                "DESCENDANTS"

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, contents);

        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = s1.getSelectedItemPosition();
                        Toast.makeText(getApplicationContext(),"You have selected "+contents[+position],Toast.LENGTH_LONG).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                }
        );
        LoadingController.enableLoading(this, ResourceType.ASSIGNEDPROGRAMS);
        LoadingController.enableLoading(this, ResourceType.OPTIONSETS);
        LoadingController.enableLoading(this, ResourceType.PROGRAMS);
        LoadingController.enableLoading(this, ResourceType.CONSTANTS);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULES);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULEVARIABLES);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULEACTIONS);
        LoadingController.enableLoading(this, ResourceType.RELATIONSHIPTYPES);
        Dhis2Application.bus.register(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PeriodicSynchronizerController.activatePeriodicSynchronizer(this);
        showSelectProgramFragment();
    }
    public void loadInitialData() {
        String message = getString(org.hisp.dhis.android.sdk.R.string.finishing_up);
        UiUtils.postProgressMessage(message);
        DhisService.loadInitialData(BIDreports.this);
    }

    public void showLoadingFragment() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setTitle("Loading initial data");
            }
        });
        switchFragment(new LoadingFragment(), LoadingFragment.TAG, false);
    }

    public void showSelectProgramFragment() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setTitle("BID Reports");
            }
        });
        switchFragment(new SelectProgramFragment(), SelectProgramFragment.TAG, true);
    }

    @Override
    public void onBackPressed() {
        if (mBackPressedListener != null) {
            mBackPressedListener.doBack();
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    public void setBackPressedListener(OnBackPressedListener listener) {
        mBackPressedListener = listener;
    }

    @Override
    public void onPause() {
        super.onPause();
        Dhis2Application.getEventBus().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Dhis2Application.getEventBus().register(this);
        loadInitialData();
    }

    @Override
    public void switchFragment(Fragment fragment, String fragmentTag, boolean addToBackStack) {
        if (fragment != null) {
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();

            transaction
                    .setCustomAnimations(R.anim.open_enter, R.anim.open_exit)
                    .replace(R.id.fragment_container, fragment);
            if (addToBackStack) {
                transaction = transaction
                        .addToBackStack(fragmentTag);
            }

            transaction.commitAllowingStateLoss();
        }
    }

}
