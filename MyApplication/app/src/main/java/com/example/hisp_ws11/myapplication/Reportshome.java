package com.example.hisp_ws11.myapplication;

/**
 * Created by HISP-WS11 on 12/31/2015.
 */

import android.widget.Button;

/**
 * Created by HISP-WS11 on 12/30/2015.
 */
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.content.Intent;
import org.hisp.dhis.android.sdk.R;
import org.hisp.dhis.android.sdk.controllers.DhisController;
import org.hisp.dhis.android.sdk.persistence.Dhis2Application;
import org.hisp.dhis.android.sdk.persistence.preferences.AppPreferences;
import org.hisp.dhis.android.sdk.ui.activities.LoginActivity;

public class Reportshome extends Activity{

    public Button prog,stats,upevents,overevents;
    private AppPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_main);

        mPrefs = new AppPreferences(getApplicationContext());
        setupUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        Dhis2Application.bus.unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Dhis2Application.bus.register(this);
    }

    private void setupUI() {

        prog = (Button) findViewById(R.id.but_program);
        stats = (Button) findViewById(R.id.butstats);
        upevents=(Button)findViewById(R.id.butevents);
        overevents=(Button)findViewById(R.id.butoverdueevents);


        prog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        stats.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });



    }


}
