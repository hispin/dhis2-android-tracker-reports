package org.hispindia.bidtrackerreports.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.hisp.dhis.android.sdk.controllers.DhisService;
import org.hisp.dhis.android.sdk.controllers.LoadingController;
import org.hisp.dhis.android.sdk.controllers.PeriodicSynchronizerController;
import org.hisp.dhis.android.sdk.controllers.metadata.MetaDataController;
import org.hisp.dhis.android.sdk.persistence.Dhis2Application;
import org.hisp.dhis.android.sdk.persistence.models.UserAccount;
import org.hisp.dhis.android.sdk.persistence.preferences.ResourceType;
import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hisp.dhis.android.sdk.ui.activities.OnBackPressedListener;
import org.hisp.dhis.android.sdk.ui.fragments.selectprogram.SelectProgramFragment;
import org.hisp.dhis.android.sdk.ui.fragments.settings.SettingsFragment;
import org.hisp.dhis.android.sdk.utils.UiUtils;
import org.hisp.dhis.client.sdk.ui.activities.AbsHomeActivity;
import org.hisp.dhis.client.sdk.ui.fragments.WrapperFragment;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;

import static org.hisp.dhis.client.sdk.utils.StringUtils.isEmpty;

public class MainActivity extends AbsHomeActivity implements INavigationHandler {
    public final static String TAG = MainActivity.class.getSimpleName();
    private OnBackPressedListener mBackPressedListener;
    private HICFragmentBase hifragmentbased;

    private Toolbar mToolbar;
//    private FragmentDrawer drawerFragment;
    protected INavigationHandler mNavigationHandler;
    Button buttonlogout;
    TextView tvname;
    //
//     @Bind(R.id.tvdname)
//    TextView tvdname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        LoadingController.enableLoading(this, ResourceType.ASSIGNEDPROGRAMS);
        LoadingController.enableLoading(this, ResourceType.OPTIONSETS);
        LoadingController.enableLoading(this, ResourceType.PROGRAMS);
        LoadingController.enableLoading(this, ResourceType.CONSTANTS);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULES);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULEVARIABLES);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULEACTIONS);
        LoadingController.enableLoading(this, ResourceType.RELATIONSHIPTYPES);
        Dhis2Application.bus.register(this);


        PeriodicSynchronizerController.activatePeriodicSynchronizer(this);
        setUpNavigationView(savedInstanceState);



    }
    private void setUpNavigationView(Bundle savedInstanceState) {
        getNavigationView().getMenu().removeItem(R.id.drawer_item_profile);
        addMenuItem(11, R.drawable.ic_add, R.string.enroll);
        if (savedInstanceState == null) {
            onNavigationItemSelected(getNavigationView().getMenu()
                    .findItem(11));
        }

        UserAccount userAccount = MetaDataController.getUserAccount();
        String name = "";
        if (!isEmpty(userAccount.getFirstName()) &&
                !isEmpty(userAccount.getSurname())) {
            name = String.valueOf(userAccount.getFirstName().charAt(0)) +
                    String.valueOf(userAccount.getSurname().charAt(0));
        } else if (userAccount.getDisplayName() != null &&
                userAccount.getDisplayName().length() > 1) {
            name = String.valueOf(userAccount.getDisplayName().charAt(0)) +
                    String.valueOf(userAccount.getDisplayName().charAt(1));
        }

        getUsernameTextView().setText(userAccount.getDisplayName());
        getUserInfoTextView().setText(userAccount.getEmail());
        getUsernameLetterTextView().setText(name);

    }


    @NonNull
    @Override
    protected Fragment getProfileFragment() {
        return new Fragment();
//        return WrapperFragment.newInstance(ProfileFragment.class,
//                getString(R.string.drawer_item_profile));
    }

    @NonNull
    @Override
    protected Fragment getSettingsFragment() {
        return WrapperFragment.newInstance(SettingsFragment.class,
                getString(R.string.drawer_item_settings));
    }

    @Override
    protected boolean onItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 11) {
          attachFragment(WrapperFragment.newInstance(SelectProgramFragment.class, getString(R.string.app_name)));
            return true;

        }
        return false;
    }

    public void loadInitialData() {
        String message = getString(org.hisp.dhis.android.sdk.R.string.finishing_up);
        UiUtils.postProgressMessage(message);
        DhisService.loadInitialData(MainActivity.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Dhis2Application.getEventBus().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Dhis2Application.getEventBus().register(this);
        loadInitialData();
    }

    @Override
    public void switchFragment(Fragment fragment, String tag, boolean addToBackStack) {
        if (fragment != null) {
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();

            transaction
                    .setCustomAnimations(R.anim.open_enter, R.anim.open_exit)
                    .replace(R.id.content_frame, fragment, tag);
            transaction = transaction
                    .addToBackStack(tag);
            if (!addToBackStack) {
                getSupportFragmentManager().popBackStack();
            }
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void setBackPressedListener(OnBackPressedListener backPressedListener) {
        mBackPressedListener = backPressedListener;
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
//        String lastSynced = DhisController.getInstance().getSyncDateWrapper().getLastSyncedString();
//        setSynchronizedMessage(lastSynced);

    }


}