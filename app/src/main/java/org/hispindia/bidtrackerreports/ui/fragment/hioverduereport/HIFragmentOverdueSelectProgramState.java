package org.hispindia.bidtrackerreports.ui.fragment.hioverduereport;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sourabh on 2/2/2016.
 */
public class HIFragmentOverdueSelectProgramState implements Parcelable {

    public final static String TAG = HIFragmentOverdueSelectProgramState.class.getSimpleName();
    public static final Parcelable.Creator<HIFragmentOverdueSelectProgramState> CREATOR
            = new Parcelable.Creator<HIFragmentOverdueSelectProgramState>() {

        public HIFragmentOverdueSelectProgramState createFromParcel(Parcel in) {
            return new HIFragmentOverdueSelectProgramState(in);
        }

        public HIFragmentOverdueSelectProgramState[] newArray(int size) {
            return new HIFragmentOverdueSelectProgramState[size];
        }
    };

    private boolean syncInProcess;

    private String orgUnitLabel;
    private String orgUnitId;

    private String programLabel;
    private String programId;

    private String orgUnitModeLabel;
    private String orgUnitModeId;

    public HIFragmentOverdueSelectProgramState() {
    }

    public HIFragmentOverdueSelectProgramState(HIFragmentOverdueSelectProgramState state) {
        if (state != null) {
            setSyncInProcess(state.isSyncInProcess());
            setOrgUnit(state.getOrgUnitId(), state.getOrgUnitLabel());
            setProgram(state.getProgramId(), state.getProgramLabel());
            setOrgUnitMode(state.getOrgUnitModeId(), state.getOrgUnitModeLabel());
        }
    }

    private HIFragmentOverdueSelectProgramState(Parcel in) {
        syncInProcess = in.readInt() == 1;

        orgUnitLabel = in.readString();
        orgUnitId = in.readString();

        programLabel = in.readString();
        programId = in.readString();

        orgUnitModeLabel = in.readString();
        orgUnitModeId = in.readString();

    }

    @Override
    public int describeContents() {
        return TAG.length();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(syncInProcess ? 1 : 0);

        parcel.writeString(orgUnitLabel);
        parcel.writeString(orgUnitId);

        parcel.writeString(programLabel);
        parcel.writeString(programId);

        parcel.writeString(orgUnitModeLabel);
        parcel.writeString(orgUnitModeId);

    }

    public boolean isSyncInProcess() {
        return syncInProcess;
    }

    public void setSyncInProcess(boolean syncInProcess) {
        this.syncInProcess = syncInProcess;
    }

    public void setOrgUnit(String orgUnitId, String orgUnitLabel) {
        setOrgUnitId(orgUnitId);
        setOrgUnitLabel(orgUnitLabel);
    }

    public void resetOrgUnit() {
        setOrgUnitId(null);
        setOrgUnitLabel(null);
    }

    public boolean isOrgUnitEmpty() {
        return (getOrgUnitId() == null || getOrgUnitLabel() == null);
    }

    public void setOrgUnitMode(String orgUnitModeId, String orgUnitModeLabel) {
        setOrgUnitModeId(orgUnitModeId);
        setOrgUnitModeLabel(orgUnitModeLabel);
    }

    public void resetOrgUnitMode() {
        setOrgUnitModeId(null);
        setOrgUnitModeLabel(null);
    }

    public boolean isOrgUnitModeEmpty() {
        return (getOrgUnitModeId() == null || getOrgUnitModeLabel() == null);
    }

    public String getOrgUnitLabel() {
        return orgUnitLabel;
    }

    public void setOrgUnitLabel(String orgUnitLabel) {
        this.orgUnitLabel = orgUnitLabel;
    }

    public String getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(String orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    public String getOrgUnitModeLabel() {
        return orgUnitModeLabel;
    }

    public void setOrgUnitModeLabel(String orgUnitModeLabel) {
        this.orgUnitModeLabel = orgUnitModeLabel;
    }

    public String getOrgUnitModeId() {
        return orgUnitModeId;
    }

    public void setOrgUnitModeId(String orgUnitModeId) {
        this.orgUnitModeId = orgUnitModeId;
    }


    public void setProgram(String programId, String programLabel) {
        setProgramId(programId);
        setProgramLabel(programLabel);

    }

    public void resetProgram() {
        setProgramId(null);
        setProgramLabel(null);
    }

    public boolean isProgramEmpty() {
        return (getProgramId() == null || getProgramLabel() == null);
    }

    public String getProgramLabel() {
        return programLabel;
    }

    public void setProgramLabel(String programLabel) {
        this.programLabel = programLabel;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

}
