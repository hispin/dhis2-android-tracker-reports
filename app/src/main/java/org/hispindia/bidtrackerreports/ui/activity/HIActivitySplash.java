package org.hispindia.bidtrackerreports.ui.activity;

import android.os.Bundle;

import org.hisp.dhis.android.sdk.ui.activities.SplashActivity;
import org.hispindia.bidtrackerreports.R;

/**
 * Created by nhancao on 2/5/16.
 */
public class HIActivitySplash extends SplashActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hiactivity_splash);
    }
}
