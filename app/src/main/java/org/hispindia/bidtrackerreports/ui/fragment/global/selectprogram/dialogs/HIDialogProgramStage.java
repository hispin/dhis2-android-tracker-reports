package org.hispindia.bidtrackerreports.ui.fragment.global.selectprogram.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;

import com.raizlabs.android.dbflow.structure.Model;

import org.hisp.dhis.android.sdk.controllers.metadata.MetaDataController;
import org.hisp.dhis.android.sdk.persistence.loaders.DbLoader;
import org.hisp.dhis.android.sdk.persistence.loaders.Query;
import org.hisp.dhis.android.sdk.persistence.models.Program;
import org.hisp.dhis.android.sdk.persistence.models.ProgramStage;
import org.hisp.dhis.android.sdk.ui.dialogs.AutoCompleteDialogAdapter;
import org.hisp.dhis.android.sdk.ui.dialogs.AutoCompleteDialogFragment;
import org.hispindia.bidtrackerreports.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nhancao on 1/21/16.
 */
public class HIDialogProgramStage extends AutoCompleteDialogFragment
        implements LoaderManager.LoaderCallbacks<List<AutoCompleteDialogAdapter.OptionAdapterValue>> {
    public static final int ID = 1000001;
    private static final int LOADER_ID = 1;
    private static final String PROGRAMID = "programId";

    public static HIDialogProgramStage newInstance(OnOptionSelectedListener listener, String programId) {
        HIDialogProgramStage fragment = new HIDialogProgramStage();
        Bundle args = new Bundle();
        args.putString(PROGRAMID, programId);
        fragment.setArguments(args);
        fragment.setOnOptionSetListener(listener);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDialogLabel(R.string.dialog_programs_stage);
        setDialogId(ID);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, getArguments(), this);
    }

    @Override
    public Loader<List<AutoCompleteDialogAdapter.OptionAdapterValue>> onCreateLoader(int id, Bundle args) {
        if (LOADER_ID == id && isAdded()) {
            String programId = args.getString(PROGRAMID);
            List<Class<? extends Model>> modelsToTrack = new ArrayList<>();
            modelsToTrack.add(Program.class);
            modelsToTrack.add(ProgramStage.class);
            return new DbLoader<>(
                    getActivity().getBaseContext(), modelsToTrack, new ProgramStageQuery(programId)
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<AutoCompleteDialogAdapter.OptionAdapterValue>> loader,
                               List<AutoCompleteDialogAdapter.OptionAdapterValue> data) {
        if (LOADER_ID == loader.getId()) {
            getAdapter().swapData(data);

            if (MetaDataController.isDataLoaded(getActivity()))
                mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<AutoCompleteDialogAdapter.OptionAdapterValue>> loader) {
        getAdapter().swapData(null);
    }

    static class ProgramStageQuery implements Query<List<AutoCompleteDialogAdapter.OptionAdapterValue>> {
        private final String programId;

        public ProgramStageQuery(String programId) {
            this.programId = programId;
        }

        @Override
        public List<AutoCompleteDialogAdapter.OptionAdapterValue> query(Context context) {
            List<ProgramStage> programStages = MetaDataController
                    .getProgramStages(programId);
            List<AutoCompleteDialogAdapter.OptionAdapterValue> values = new ArrayList<>();
            if (programStages != null && !programStages.isEmpty()) {
                for (ProgramStage programStage : programStages) {
                    values.add(new AutoCompleteDialogAdapter.OptionAdapterValue(programStage.getUid(), programStage.getName()));
                }
            }
            Collections.sort(values);
            return values;
        }
    }
}