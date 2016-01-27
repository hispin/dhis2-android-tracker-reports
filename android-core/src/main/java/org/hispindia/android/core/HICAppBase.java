package org.hispindia.android.core;

import android.app.Application;

import org.hispindia.android.core.compat.HICPlatformImpSpecificFactory;

/**
 * Created by NhanCao on 13-Sep-15.
 */
public abstract class HICAppBase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (isDebug()) {
            // Enable strictMode for development. This will help to keep project in high quality.
            HICPlatformImpSpecificFactory.getStrictMode().enableStrictMode();
        } else {
        }
    }

    protected abstract boolean isDebug();
}
