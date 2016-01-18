package org.hispindia.bidtrackerreports.dagger;

import android.app.Application;

import com.cvnhan.core.dagger.module.NCMCModuleApplication;
import com.squareup.otto.Bus;

import org.hispindia.bidtrackerreports.dagger.module.HIModuleModel;
import org.hispindia.bidtrackerreports.dagger.module.HIModulePresenter;
import org.hispindia.bidtrackerreports.dagger.module.HIModuleUtils;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nhancao on 1/18/16.
 */
@Singleton
@Component(modules = {HIModuleUtils.class, NCMCModuleApplication.class, HIModuleModel.class, HIModulePresenter.class})
public interface HIIComponentSingleton {
    Application application();

    Bus bus();

    //Declare presenter

}
