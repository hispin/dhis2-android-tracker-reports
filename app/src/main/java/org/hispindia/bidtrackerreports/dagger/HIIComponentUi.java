package org.hispindia.bidtrackerreports.dagger;

import org.hispindia.android.core.dagger.HICIPerActivity;
import org.hispindia.android.core.dagger.module.HICModuleActivity;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.fragment.bidprogram.HIFragmentBIDReport;
import org.hispindia.bidtrackerreports.ui.fragment.bidprogram.HIFragmentSelectProgram;

import dagger.Component;

/**
 * Created by nhancao on 1/18/16.
 */
@HICIPerActivity
@Component(modules = HICModuleActivity.class, dependencies = HIIComponentSingleton.class)
public interface HIIComponentUi {

    //inject activity
    void inject(HIActivityMain activityMain);

    //inject fragment
    void inject(HIFragmentSelectProgram fragmentSelectProgram);

    void inject(HIFragmentBIDReport fragmentBIDReport);


}
