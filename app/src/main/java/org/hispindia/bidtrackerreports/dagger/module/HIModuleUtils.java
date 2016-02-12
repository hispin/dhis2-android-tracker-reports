package org.hispindia.bidtrackerreports.dagger.module;

import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterOverdueReport;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterSchvaccineReport;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterStockReport;

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
    public HIAdapterSchvaccineReport provideHIAdapterSchvaccineReport() {
        return new HIAdapterSchvaccineReport();
    }

    @Provides
    @Singleton
    public HIAdapterOverdueReport provideHIAdapterOverdueReport() {
        return new HIAdapterOverdueReport();
    }

}
