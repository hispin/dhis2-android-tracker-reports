package com.example.hisp_ws11.myapplication;

/**
 * Created by HISP-WS11 on 12/31/2015.
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

public class Mainscreen extends Activity
{
    public Button reports,dataentry,bidreport;
    private AppPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);

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

        reports = (Button) findViewById(R.id.butreports);
        dataentry = (Button) findViewById(R.id.butregistration);
        bidreport =(Button)findViewById(R.id.butbidreports);

        dataentry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

                // startActivity(new Intent(Mainscreen.this,
                //((Dhis2Application) getApplication()).getMainActivity()));



            }
        });
        bidreport.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BIDreports.class);
                startActivity(i);

                // startActivity(new Intent(Mainscreen.this,
                //((Dhis2Application) getApplication()).getMainActivity()));



            }
        });
        reports.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Reportshome.class);
                startActivity(i);
                //  Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                // startActivity(i);


            }
        });



    }
}
