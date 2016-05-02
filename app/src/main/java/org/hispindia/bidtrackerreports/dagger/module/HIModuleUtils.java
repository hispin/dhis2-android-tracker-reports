package org.hispindia.bidtrackerreports.dagger.module;

import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterOverdueReport;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterSchvaccineReport;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport1;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport2;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport3;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport4;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport5;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nhancao on 1/18/16.
 */
@Module
public class HIModuleUtils {

    @Provides
    @Singleton
    public HIAdapterStockReport provideHIAdapterStockReport() {
        return new HIAdapterStockReport();
    }


    @Provides
    @Singleton
    public HIAdapterStockReport1 provideHIAdapterStockReport1() {
        return new HIAdapterStockReport1();
    }

    @Provides
    @Singleton
    public HIAdapterStockReport2 provideHIAdapterStockReport2() {
        return new HIAdapterStockReport2();
    }

    @Provides
    @Singleton
    public HIAdapterStockReport3 provideHIAdapterStockReport3() {
        return new HIAdapterStockReport3();
    }

    @Provides
    @Singleton
    public HIAdapterStockReport4 provideHIAdapterStockReport4() {
        return new HIAdapterStockReport4();
    }

    @Provides
    @Singleton
    public HIAdapterStockReport5 provideHIAdapterStockReport5() {
        return new HIAdapterStockReport5();
    }

    @Provides
    @Singleton
    public HIAdapterSchvaccineReport provideHIAdapterSchvaccineReport() {
        return new HIAdapterSchvaccineReport();
    }

    @Provides
    @Singleton
    public HIAdapterOverdueReport provideHIAdapterOverdueReport() {
        return new HIAdapterOverdueReport();
    }

}
