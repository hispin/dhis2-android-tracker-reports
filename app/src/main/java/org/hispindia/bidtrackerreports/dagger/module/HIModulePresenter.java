package org.hispindia.bidtrackerreports.dagger.module;

import org.hispindia.bidtrackerreports.mvp.model.HIBIDModel;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterBIDReport;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nhancao on 1/18/16.
 */
@Module
public class HIModulePresenter {

    @Provides
    @Singleton
    public HIPresenterBIDReport provideHIPresenterBIDReport(HIBIDModel model) {
        return new HIPresenterBIDReport(model);
    }


}
