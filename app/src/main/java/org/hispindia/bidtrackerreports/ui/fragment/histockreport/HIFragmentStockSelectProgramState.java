package org.hispindia.bidtrackerreports.ui.fragment.histockreport;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nhancao on 1/21/16.
 */
public class HIFragmentStockSelectProgramState implements Parcelable {
    public final static String TAG = HIFragmentStockSelectProgramState.class.getSimpleName();
    public static final Creator<HIFragmentStockSelectProgramState> CREATOR
            = new Creator<HIFragmentStockSelectProgramState>() {

        public HIFragmentStockSelectProgramState createFromParcel(Parcel in) {
            return new HIFragmentStockSelectProgramState(in);
        }

        public HIFragmentStockSelectProgramState[] newArray(int size) {
            return new HIFragmentStockSelectProgramState[size];
        }
    };

    private boolean syncInProcess;

    private String orgUnitLabel;
    private String orgUnitId;

    private String orgUnitModeLabel;
    private String orgUnitModeId;

    public HIFragmentStockSelectProgramState() {
    }

    public HIFragmentStockSelectProgramState(HIFragmentStockSelectProgramState state) {
        if (state != null) {
            setSyncInProcess(state.isSyncInProcess());
            setOrgUnit(state.getOrgUnitId(), state.getOrgUnitLabel());
            setOrgUnitMode(state.getOrgUnitModeId(), state.getOrgUnitModeLabel());
        }
    }

    private HIFragmentStockSelectProgramState(Parcel in) {
        syncInProcess = in.readInt() == 1;

        orgUnitLabel = in.readString();
        orgUnitId = in.readString();

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

}