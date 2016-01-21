package org.hispindia.bidtrackerreports.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.hisp.dhis.android.sdk.controllers.DhisService;
import org.hisp.dhis.android.sdk.controllers.LoadingController;
import org.hisp.dhis.android.sdk.controllers.PeriodicSynchronizerController;
import org.hisp.dhis.android.sdk.persistence.Dhis2Application;
import org.hisp.dhis.android.sdk.persistence.preferences.ResourceType;
import org.hisp.dhis.android.sdk.utils.UiUtils;
import org.hispindia.android.core.dagger.module.HICModuleActivity;
import org.hispindia.android.core.ui.fragment.HICFragmentNavigator;
import org.hispindia.bidtrackerreports.HIApplication;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.DaggerHIIComponentUi;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.ui.fragment.HIFragmentSelectProgram;

public class HIActivityMain extends AppCompatActivity {

    private HICFragmentNavigator fragmentNavigator;
    private HIIComponentUi uiComponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        setContentView(R.layout.hiactivity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            fragmentNavigator.showScreen(new HIFragmentSelectProgram(), false);
        }

        LoadingController.enableLoading(this, ResourceType.ASSIGNEDPROGRAMS);
        LoadingController.enableLoading(this, ResourceType.OPTIONSETS);
        LoadingController.enableLoading(this, ResourceType.PROGRAMS);
        LoadingController.enableLoading(this, ResourceType.CONSTANTS);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULES);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULEVARIABLES);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULEACTIONS);
        LoadingController.enableLoading(this, ResourceType.RELATIONSHIPTYPES);
        LoadingController.enableLoading(this, ResourceType.EVENTS);
        PeriodicSynchronizerController.activatePeriodicSynchronizer(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Dhis2Application.getEventBus().register(this);
        loadInitialData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Dhis2Application.getEventBus().unregister(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!fragmentNavigator.navigateBack())
            super.onBackPressed();
    }

    //Implement method

    private void injectDependencies() {
        fragmentNavigator = HICFragmentNavigator.create(this, R.id.container);
        uiComponent = DaggerHIIComponentUi.builder()
                .hIIComponentSingleton(((HIApplication) getApplication()).getComponent())
                .hICModuleActivity(new HICModuleActivity(this))
                .build();
        uiComponent.inject(this);
    }

    public HIIComponentUi getUiComponent() {
        return uiComponent;
    }

    public void loadInitialData() {
        String message = getString(org.hisp.dhis.android.sdk.R.string.finishing_up);
        UiUtils.postProgressMessage(message);
        DhisService.loadInitialData(HIActivityMain.this);
    }
}
