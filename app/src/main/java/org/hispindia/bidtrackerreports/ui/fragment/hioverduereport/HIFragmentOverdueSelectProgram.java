package org.hispindia.bidtrackerreports.ui.fragment.hioverduereport;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.hisp.dhis.android.sdk.controllers.DhisService;
import org.hisp.dhis.android.sdk.events.UiEvent;
import org.hisp.dhis.android.sdk.persistence.Dhis2Application;
import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hisp.dhis.android.sdk.ui.dialogs.AutoCompleteDialogFragment;
import org.hisp.dhis.android.sdk.ui.dialogs.OrgUnitDialogFragment;
import org.hisp.dhis.android.sdk.ui.dialogs.ProgramDialogFragment;
import org.hisp.dhis.android.sdk.ui.fragments.settings.SettingsFragment;
import org.hisp.dhis.android.sdk.ui.views.CardTextViewButton;
import org.hisp.dhis.android.sdk.utils.api.ProgramType;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;
import org.hispindia.bidtrackerreports.ui.fragment.global.selectprogram.dialogs.HIDialogOrgUnitMode;
import org.hispindia.bidtrackerreports.utils.HICUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sourabh on 2/2/2016.
 */
public class HIFragmentOverdueSelectProgram extends HICFragmentBase implements View.OnClickListener, AutoCompleteDialogFragment.OnOptionSelectedListener,
        SwipeRefreshLayout.OnRefreshListener,
        MenuItemCompat.OnActionExpandListener {
    public final static String TAG = HIFragmentOverdueSelectProgram.class.getSimpleName();
    protected final String STATE;
    protected ListView mListView;
    protected HIFragmentOverdueSelectProgramState mState;
    protected HIFragmentOverdueSelectProgramPreferences mPrefs;
    protected INavigationHandler mNavigationHandler;
    @Bind(R.id.btnGenerateReport)
    Button btnGenerateReport;
    @Bind(R.id.vSelectOrgUnit)
    CardTextViewButton vSelectOrgUnit;
    @Bind(R.id.vSelectProgram)
    CardTextViewButton vSelectProgram;
    @Bind(R.id.vSelectOrgMode)
    CardTextViewButton vSelectOrgMode;


    @Bind(R.id.vSwipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefreshLayout;


    public HIFragmentOverdueSelectProgram() {
        this("state:HIFragmentOverdueSelectProgram");
    }

    @SuppressLint("ValidFragment")
    private HIFragmentOverdueSelectProgram(String stateName) {
        STATE = stateName;
    }


    protected void injectDependencies() {
        HIIComponentUi uiComponent = ((HIActivityMain) getActivity()).getUiComponent();
        if (uiComponent != null) {
            uiComponent.inject(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hifragment_overdue_select_program, container, false);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        setUpView(view, savedInstanceState);
        mPrefs = new HIFragmentOverdueSelectProgramPreferences(
                getActivity().getApplicationContext());

        if (savedInstanceState != null &&
                savedInstanceState.getParcelable(STATE) != null) {
            mState = savedInstanceState.getParcelable(STATE);
        }

        if (mState == null) {
            // restoring last selection of program
            Pair<String, String> orgUnit = mPrefs.getOrgUnit();
            Pair<String, String> program = mPrefs.getProgram();
            Pair<String, String> orgUnitMode = mPrefs.getOrgUnitMode();
            mState = new HIFragmentOverdueSelectProgramState();
            if (orgUnit != null) {
                mState.setOrgUnit(orgUnit.first, orgUnit.second);
                if (program != null) {
                    mState.setProgram(program.first, program.second);
                }
            }

            if (orgUnitMode != null) {
                mState.setOrgUnitMode(orgUnitMode.first, orgUnitMode.second);
            }

        }

        onRestoreState(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof INavigationHandler) {
            mNavigationHandler = (INavigationHandler) activity;
        } else {
            throw new IllegalArgumentException("Activity must " +
                    "implement INavigationHandler interface");
        }
        getActivity().setTitle(getString(R.string.btn_overdue_report));

    }

    @Override
    public void onResume() {
        super.onResume();
        Dhis2Application.getEventBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Dhis2Application.getEventBus().unregister(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // we need to nullify reference
        // to parent activity in order not to leak it
        mNavigationHandler = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(org.hisp.dhis.android.sdk.R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == org.hisp.dhis.android.sdk.R.id.action_settings) {
            mNavigationHandler.switchFragment(
                    new SettingsFragment(), SettingsFragment.TAG, true);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true; //return true to expand
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        out.putParcelable(STATE, mState);
        super.onSaveInstanceState(out);
    }


    @Override

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.vSelectOrgUnit:
                OrgUnitDialogFragment fragmentOrg = OrgUnitDialogFragment
                        .newInstance(HIFragmentOverdueSelectProgram.this,
                                getProgramTypes());
                fragmentOrg.show(getChildFragmentManager());
                break;
            case R.id.vSelectProgram:
                ProgramDialogFragment fragmentPro = ProgramDialogFragment
                        .newInstance(HIFragmentOverdueSelectProgram.this, mState.getOrgUnitId(),
                                getProgramTypes());
                fragmentPro.show(getChildFragmentManager());
                break;
            case R.id.vSelectOrgMode:
                HIDialogOrgUnitMode fragmentOrgMode = HIDialogOrgUnitMode
                        .newInstance(HIFragmentOverdueSelectProgram.this, HIDialogOrgUnitMode.BID_MODE);
                fragmentOrgMode.show(getChildFragmentManager());
                break;

            case R.id.btnGenerateReport:

                mNavigationHandler.switchFragment(HIFragmentOverdueReport.newInstance(mState.getOrgUnitId(), mState.getOrgUnitModeId(), mState.getProgramId()), HIFragmentOverdueReport.TAG, true);
                break;
        }
    }

    @Override
    public void onOptionSelected(int dialogId, int position, String id, String name) {
        switch (dialogId) {
            case OrgUnitDialogFragment.ID:
                onUnitSelected(id, name);
                break;
            case ProgramDialogFragment.ID:
                onProgramSelected(id, name);
                break;
            case HIDialogOrgUnitMode.ID:
                onOrgUnitModeSelected(id, name);
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (isAdded()) {
            Context context = getActivity().getBaseContext();
            Toast.makeText(context, getString(org.hisp.dhis.android.sdk.R.string.syncing), Toast.LENGTH_SHORT).show();
            DhisService.synchronize(context);
        }
    }

    @Subscribe
    @SuppressLint("unused")
    public void onReceivedUiEvent(UiEvent uiEvent) {
        if (uiEvent.getEventType().equals(UiEvent.UiEventType.SYNCING_START)) {
            setRefreshing(true);
        } else if (uiEvent.getEventType().equals(UiEvent.UiEventType.SYNCING_END)) {
            setRefreshing(false);
        }
    }

    private void setUpView(View view, Bundle savedInstanceState) {

        vSwipeRefreshLayout.setColorSchemeResources(org.hisp.dhis.android.sdk.R.color.Green, org.hisp.dhis.android.sdk.R.color.Blue, org.hisp.dhis.android.sdk.R.color.orange);
        vSwipeRefreshLayout.setOnRefreshListener(this);
        vSelectOrgUnit.setOnClickListener(this);
        vSelectProgram.setOnClickListener(this);
        vSelectOrgMode.setOnClickListener(this);
        btnGenerateReport.setOnClickListener(this);

        HICUtils.setEnabledViews(true, vSelectOrgUnit);
        HICUtils.setEnabledViews(false, vSelectProgram);
        HICUtils.setShowViews(false, btnGenerateReport);
    }

    protected ProgramType[] getProgramTypes() {
        return new ProgramType[]{
                ProgramType.WITH_REGISTRATION
        };
    }

    protected void setRefreshing(final boolean refreshing) {
        /* workaround for bug in android support v4 library */
        if (vSwipeRefreshLayout.isRefreshing() != refreshing) {
            vSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    vSwipeRefreshLayout.setRefreshing(refreshing);
                }
            });
        }
    }


    public void onRestoreState(boolean hasUnits) {
        vSelectOrgUnit.setEnabled(hasUnits);
        if (!hasUnits) {
            return;
        }

        HIFragmentOverdueSelectProgramState backedUpState = new HIFragmentOverdueSelectProgramState(mState);
        if (!backedUpState.isOrgUnitEmpty()) {
            onUnitSelected(
                    backedUpState.getOrgUnitId(),
                    backedUpState.getOrgUnitLabel()
            );

            if (!backedUpState.isProgramEmpty()) {
                onProgramSelected(
                        backedUpState.getProgramId(),
                        backedUpState.getProgramLabel()
                );

            }
        }
        if (!backedUpState.isOrgUnitModeEmpty()) {
            onOrgUnitModeSelected(
                    backedUpState.getOrgUnitModeId(),
                    backedUpState.getOrgUnitModeLabel()
            );
        }
    }

    public void onUnitSelected(String orgUnitId, String orgUnitLabel) {
        vSelectOrgUnit.setText(orgUnitLabel);
        vSelectProgram.setEnabled(true);

        mState.setOrgUnit(orgUnitId, orgUnitLabel);
        if (mState.isOrgUnitModeEmpty()) {
            String selectedModed = getResources().getStringArray(R.array.organisation_mode)[0];
            onOrgUnitModeSelected(selectedModed, selectedModed);
        }
        mState.resetProgram();
        mPrefs.putOrgUnit(new Pair<>(orgUnitId, orgUnitLabel));
        mPrefs.putProgram(null);

        handleViews(0);
    }

    public void onProgramSelected(String programId, String programName) {
        vSelectProgram.setText(programName);
        mState.setProgram(programId, programName);
        mPrefs.putProgram(new Pair<>(programId, programName));
        handleViews(1);
    }

    public void onOrgUnitModeSelected(String orgUnitModeId, String orgUnitModeName) {
        vSelectOrgMode.setText(orgUnitModeName);
        mState.setOrgUnitMode(orgUnitModeId, orgUnitModeName);
        mPrefs.putOrgUnitMode(new Pair<>(orgUnitModeId, orgUnitModeName));
        handleViews(2);
    }


    protected void handleViews(int level) {
        switch (level) {
            case 0:
                //step 1 (choose org unit done)
                break;
            case 1:
                //step 2 (choose program done)
                break;
            case 2:
                HICUtils.setShowViews(true, btnGenerateReport);
                break;
        }
    }
}
