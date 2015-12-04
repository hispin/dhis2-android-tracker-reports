package com.example.hisp_ws11.myapplication;

import org.hisp.dhis.android.sdk.persistence.Dhis2Application;

import android.app.Activity;



public class Firstact extends Dhis2Application{

    @Override
    public Class<? extends Activity> getMainActivity() {
        return new MainActivity().getClass();
    }

}
