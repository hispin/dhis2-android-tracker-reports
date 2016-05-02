package org.hispindia.bidtrackerreports.dagger;

import android.app.Application;

import org.hispindia.bidtrackerreports.dagger.module.HICModuleApplication;
import org.hispindia.bidtrackerreports.dagger.module.HIModuleModel;
import org.hispindia.bidtrackerreports.dagger.module.HIModulePresenter;
import org.hispindia.bidtrackerreports.dagger.module.HIModuleUtils;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterBIDReport;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterOverdueReport;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterSchvaccineReport;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport1;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport2;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport3;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport4;
import org.hispindia.bidtrackerreports.mvp.presenter.HIPresenterStockReport5;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterOverdueReport;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterSchvaccineReport;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport1;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport2;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport3;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport4;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport5;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nhancao on 1/18/16.
 */
@Singleton
@Component(modules = {HIModuleUtils.class, HICModuleApplication.class, HIModuleModel.class, HIModulePresenter.class})
public interface HIIComponentSingleton {
    Application application();

    HIPresenterBIDReport hiPresenterBIDReport();

    HIPresenterStockReport hiPresenterStockReport();

    HIAdapterStockReport hiAdapterStockReport();

    HIPresenterStockReport1 hiPresenterStockReport1();

    HIAdapterStockReport1 hiAdapterStockReport1();

    HIPresenterStockReport2 hiPresenterStockReport2();

    HIAdapterStockReport2 hiAdapterStockReport2();

    HIPresenterStockReport3 hiPresenterStockReport3();

    HIAdapterStockReport3 hiAdapterStockReport3();

    HIPresenterStockReport4 hiPresenterStockReport4();

    HIAdapterStockReport4 hiAdapterStockReport4();

    HIPresenterStockReport5 hiPresenterStockReport5();

    HIAdapterStockReport5 hiAdapterStockReport5();

    HIPresenterSchvaccineReport hiHIPresenterSchvaccineReport();

    HIAdapterSchvaccineReport hiHIAdapterSchvaccineReport();

    HIPresenterOverdueReport hiHIPresenterOverdueReport();

    HIAdapterOverdueReport hiHIAdapterOverdueReport();

}
