package org.hispindia.bidtrackerreports.dagger.module;

import org.hispindia.bidtrackerreports.mvp.model.HIBIDModel;
import org.hispindia.bidtrackerreports.mvp.model.HIOverdueModel;
import org.hispindia.bidtrackerreports.mvp.model.HISchvaccineModel;
import org.hispindia.bidtrackerreports.mvp.model.HIStockModel;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterBIDReport;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterOverdueReport;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterSchvaccineReport;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport1;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport2;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport3;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport4;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport5;

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

    @Provides
    @Singleton
    public HIPresenterStockReport provideHIPresenterStockReport(HIStockModel model) {
        return new HIPresenterStockReport(model);
    }

    @Provides
    @Singleton
    public HIPresenterStockReport1 provideHIPresenterStockReport1(HIStockModel model) {
        return new HIPresenterStockReport1(model);
    }

    @Provides
    @Singleton
    public HIPresenterStockReport2 provideHIPresenterStockReport2(HIStockModel model) {
        return new HIPresenterStockReport2(model);
    }

    @Provides
    @Singleton
    public HIPresenterStockReport3 provideHIPresenterStockReport3(HIStockModel model) {
        return new HIPresenterStockReport3(model);
    }

    @Provides
    @Singleton
    public HIPresenterStockReport4 provideHIPresenterStockReport4(HIStockModel model) {
        return new HIPresenterStockReport4(model);
    }

    @Provides
    @Singleton
    public HIPresenterStockReport5 provideHIPresenterStockReport5(HIStockModel model) {
        return new HIPresenterStockReport5(model);
    }

    @Provides
    @Singleton
    public HIPresenterSchvaccineReport provideHIPresenterSchvaccineReport(HISchvaccineModel model) {
        return new HIPresenterSchvaccineReport(model);
    }

    @Provides
    @Singleton
    public HIPresenterOverdueReport provideHIPresenterOverdueReport(HIOverdueModel model) {
        return new HIPresenterOverdueReport(model);
    }

}
