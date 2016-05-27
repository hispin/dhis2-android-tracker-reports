package org.hispindia.bidtrackerreports.dagger.module;

import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterBirthNotificationReport;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterDemandOnlyReport;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterDemandOnlyReport1;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterDemandOnlyReport2;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterDemandOnlyReport3;
import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterDemandOnlyReport4;
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
    public HIAdapterDemandOnlyReport provideHIAdapterDemandOnlyReport() {
        return new HIAdapterDemandOnlyReport();
    }

    @Provides
    @Singleton
    public HIAdapterDemandOnlyReport1 provideHIAdapterDemandOnlyReport1() {
        return new HIAdapterDemandOnlyReport1();
    }
    @Provides
    @Singleton
    public HIAdapterDemandOnlyReport2 provideHIAdapterDemandOnlyReport2() {
        return new HIAdapterDemandOnlyReport2();
    }
    @Provides
    @Singleton
    public HIAdapterDemandOnlyReport3 provideHIAdapterDemandOnlyReport3() {
        return new HIAdapterDemandOnlyReport3();
    }
    @Provides
    @Singleton
    public HIAdapterDemandOnlyReport4 provideHIAdapterDemandOnlyReport4() {
        return new HIAdapterDemandOnlyReport4();
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
    public HIAdapterBirthNotificationReport provideHIAdapterBirthNotificationReport() {
        return new HIAdapterBirthNotificationReport();
    }

    @Provides
    @Singleton
    public HIAdapterOverdueReport provideHIAdapterOverdueReport() {
        return new HIAdapterOverdueReport();
    }

}
