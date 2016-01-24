package org.hispindia.bidtrackerreports.dagger.module;

import android.app.Application;
import android.util.Log;

import com.squareup.okhttp.HttpUrl;

import org.hisp.dhis.android.sdk.controllers.DhisController;
import org.hisp.dhis.android.sdk.network.APIException;
import org.hisp.dhis.android.sdk.network.Credentials;
import org.hisp.dhis.android.sdk.network.RepoManager;
import org.hisp.dhis.android.sdk.utils.StringConverter;
import org.hispindia.bidtrackerreports.mvp.model.HIBIDModel;
import org.hispindia.bidtrackerreports.mvp.model.remote.api.HIIApiDhis2;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.converter.ConversionException;
import retrofit.converter.JacksonConverter;

/**
 * Created by nhancao on 1/18/16.
 */
@Module
public class HIModuleModel {

    @Provides
    @Singleton
    public HIIApiDhis2 provideHIIApiDhis2() {
        return getRestAdapter(DhisController.getInstance().getSession().getServerUrl(), DhisController.getInstance().getSession().getCredentials()).create(HIIApiDhis2.class);
    }

    @Provides
    @Singleton
    public HIBIDModel provideHIBIDModel(Application application) {
        return new HIBIDModel(application, provideHIIApiDhis2());
    }


    private RestAdapter getRestAdapter(HttpUrl serverUrl, Credentials credentials) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(serverUrl.newBuilder().addPathSegment("api").build().toString())
                .setConverter(new JacksonConverter(DhisController.getInstance().getObjectMapper()))
                .setClient(new OkClient(RepoManager.provideOkHttpClient(credentials)))
                .setErrorHandler(new RetrofitErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();
        return restAdapter;
    }

    private static class RetrofitErrorHandler implements ErrorHandler {

        @Override
        public Throwable handleError(RetrofitError cause) {
            Log.d("RepoManager", "there was an error.." + cause.getKind().name());
            try {
                String body = new StringConverter().fromBody(cause.getResponse().getBody(), String.class);
                Log.e("RepoManager", body);
            } catch (ConversionException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return APIException.fromRetrofitError(cause);
        }
    }


}
