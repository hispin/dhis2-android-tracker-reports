package org.hispindia.bidtrackerreports.dagger.module;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nhancao on 1/18/16.
 */
@Module
public class HIModuleUtils {

    @Singleton
    @Provides
    public Bus provideBus() {
        return new Bus();
    }
}
