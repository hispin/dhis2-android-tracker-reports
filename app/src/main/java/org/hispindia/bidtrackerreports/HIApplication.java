package org.hispindia.bidtrackerreports;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import org.hisp.dhis.android.sdk.controllers.DhisController;
import org.hisp.dhis.android.sdk.persistence.Dhis2Application;
import org.hisp.dhis.android.sdk.ui.activities.LoginActivity;
import org.hispindia.bidtrackerreports.compat.HICPlatformImpSpecificFactory;
import org.hispindia.bidtrackerreports.dagger.DaggerHIIComponentSingleton;
import org.hispindia.bidtrackerreports.dagger.HIIComponentSingleton;
import org.hispindia.bidtrackerreports.dagger.module.HICModuleApplication;
import org.hispindia.bidtrackerreports.dagger.module.HIModuleModel;
import org.hispindia.bidtrackerreports.dagger.module.HIModulePresenter;
import org.hispindia.bidtrackerreports.dagger.module.HIModuleUtils;
import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIApiEnvConfig;

/**
 * Created by nhancao on 1/18/16.
 */
public class HIApplication extends Dhis2Application {
    private HIIComponentSingleton component;

    @Override
    public Class<? extends Activity> getMainActivity() {
        return getNextActivity();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (this.isDebug()) {
            HICPlatformImpSpecificFactory.getStrictMode().enableStrictMode();
        }
        component = DaggerHIIComponentSingleton.builder()
                .hICModuleApplication(new HICModuleApplication(this))
                .hIModuleModel(new HIModuleModel())
                .hIModulePresenter(new HIModulePresenter())
                .hIModuleUtils(new HIModuleUtils())
                .build();
        HIApiEnvConfig.configEnv(HIApiEnvConfig.APIURL.BID);
    }

    public HIIComponentSingleton getComponent() {
        return component;
    }

    protected boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    private Class<? extends Activity> getNextActivity() {
        Class<? extends Activity> nextClass = LoginActivity.class;

        DhisController.getInstance().init();
        if (DhisController.isUserLoggedIn()) {
            ApplicationInfo ai = null;
            try {
                ai = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return nextClass;
            }
            Bundle bundle = ai.metaData;
            String nextClassName = bundle.getString("nextClassName");

            if (nextClassName != null) {
                try {
                    nextClass = (Class<? extends Activity>) Class.forName(nextClassName);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return LoginActivity.class;
                }
            }
        }

        return nextClass;
    }
}
