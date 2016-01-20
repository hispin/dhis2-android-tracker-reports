package org.hispindia.bidtrackerreports.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cvnhan.core.dagger.module.NCMCModuleActivity;
import com.cvnhan.core.ui.fragment.NCMCFragmentNavigator;
import com.squareup.otto.Bus;

import org.hispindia.bidtrackerreports.HIApplication;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.DaggerHIIComponentUi;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.ui.fragment.HIFragmentSelectProgram;

import javax.inject.Inject;

public class HIActivityMain extends AppCompatActivity {

    @Inject
    Bus bus;
    private NCMCFragmentNavigator fragmentNavigator;
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



    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
        loadInitialData();
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
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
        fragmentNavigator = NCMCFragmentNavigator.create(this, R.id.container);
        uiComponent = DaggerHIIComponentUi.builder()
                .hIIComponentSingleton(((HIApplication) getApplication()).getComponent())
                .nCMCModuleActivity(new NCMCModuleActivity(this))
                .build();
        uiComponent.inject(this);
    }

    public HIIComponentUi getUiComponent() {
        return uiComponent;
    }

    public void loadInitialData() {


    }
}
