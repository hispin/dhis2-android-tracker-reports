package org.hispindia.bidtrackerreports.dagger.module;

import org.hispindia.bidtrackerreports.ui.adapter.HIAdapterBIDReport;

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
    public HIAdapterBIDReport provideHIAdapterBIDReport() {
        return new HIAdapterBIDReport();
    }

}
