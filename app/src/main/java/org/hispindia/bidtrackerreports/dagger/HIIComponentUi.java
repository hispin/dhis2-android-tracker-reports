package org.hispindia.bidtrackerreports.dagger;

import com.cvnhan.core.dagger.NCMCIPerActivity;
import com.cvnhan.core.dagger.module.NCMCModuleActivity;

import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.fragment.HIFragmentBIDReport;
import org.hispindia.bidtrackerreports.ui.fragment.HIFragmentSelectProgram;

import dagger.Component;

/**
 * Created by nhancao on 1/18/16.
 */
@NCMCIPerActivity
@Component(modules = NCMCModuleActivity.class, dependencies = HIIComponentSingleton.class)
public interface HIIComponentUi {

    //inject activity
    void inject(HIActivityMain activityMain);

    //inject fragment
    void inject(HIFragmentSelectProgram fragmentSelectProgram);
    void inject(HIFragmentBIDReport fragmentBIDReport);


}
