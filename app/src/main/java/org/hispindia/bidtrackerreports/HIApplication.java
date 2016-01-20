package org.hispindia.bidtrackerreports;

import android.app.Activity;

import com.cvnhan.core.compat.NCMCPlatformImpSpecificFactory;
import com.cvnhan.core.dagger.module.NCMCModuleApplication;

import org.hisp.dhis.android.sdk.persistence.Dhis2Application;
import org.hispindia.bidtrackerreports.dagger.DaggerHIIComponentSingleton;
import org.hispindia.bidtrackerreports.dagger.HIIComponentSingleton;
import org.hispindia.bidtrackerreports.dagger.module.HIModuleModel;
import org.hispindia.bidtrackerreports.dagger.module.HIModulePresenter;
import org.hispindia.bidtrackerreports.dagger.module.HIModuleUtils;
import org.hispindia.bidtrackerreports.mvp.model.HIDBMigration;
import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIApiEnvConfig;

import java.io.File;

import io.realm.RealmConfiguration;

/**
 * Created by nhancao on 1/18/16.
 */
public class HIApplication extends Dhis2Application {
    private HIIComponentSingleton component;
    private RealmConfiguration config0;

    @Override
    public Class<? extends Activity> getMainActivity() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (this.isDebug()) {
            NCMCPlatformImpSpecificFactory.getStrictMode().enableStrictMode();
        }
        component = DaggerHIIComponentSingleton.builder()
                .nCMCModuleApplication(new NCMCModuleApplication(this))
                .hIModuleModel(new HIModuleModel())
                .hIModulePresenter(new HIModulePresenter())
                .hIModuleUtils(new HIModuleUtils())
                .build();
        new File(getFilesDir(), "default0");
        config0 = new RealmConfiguration.Builder(this)
                .name("default0")
                .schemaVersion(HIDBMigration.DBVERSION)
                .migration(new HIDBMigration())
                .build();
        HIApiEnvConfig.configEnv(HIApiEnvConfig.APIURL.BID);

    }

    public HIIComponentSingleton getComponent() {
        return component;
    }

    public RealmConfiguration getConfig0() {
        return config0;
    }

    protected boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
