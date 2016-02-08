package org.hispindia.bidtrackerreports.dagger.module;
/**
 * Created by NhanCao on 13-Sep-15.
 */

import android.app.Activity;
import android.view.LayoutInflater;

import org.hispindia.bidtrackerreports.dagger.HICIPerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
public class HICModuleActivity {
    private final Activity activity;

    public HICModuleActivity(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @HICIPerActivity
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @HICIPerActivity
    public LayoutInflater provideLayoutInflater() {
        return activity.getLayoutInflater();
    }
}