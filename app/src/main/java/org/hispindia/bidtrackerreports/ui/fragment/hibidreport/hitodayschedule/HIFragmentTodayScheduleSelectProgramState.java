package org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hitodayschedule;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sourabh on 2/2/2016.
 */
public class HIFragmentTodayScheduleSelectProgramState implements Parcelable {

    public final static String TAG = HIFragmentTodayScheduleSelectProgramState.class.getSimpleName();
    public static final Creator<HIFragmentTodayScheduleSelectProgramState> CREATOR
            = new Creator<HIFragmentTodayScheduleSelectProgramState>() {

        public HIFragmentTodayScheduleSelectProgramState createFromParcel(Parcel in) {
            return new HIFragmentTodayScheduleSelectProgramState(in);
        }

        public HIFragmentTodayScheduleSelectProgramState[] newArray(int size) {
            return new HIFragmentTodayScheduleSelectProgramState[size];
        }
    };

    private boolean syncInProcess;

    private String orgUnitLabel;
    private String orgUnitId;

    private String programLabel;
    private String programId;


    private String fromDate;
    private String toDate;

    private String orgUnitModeLabel;
    private String orgUnitModeId;

    public HIFragmentTodayScheduleSelectProgramState() {
    }

    public HIFragmentTodayScheduleSelectProgramState(HIFragmentTodayScheduleSelectProgramState state) {
        if (state != null) {
            setSyncInProcess(state.isSyncInProcess());
            setOrgUnit(state.getOrgUnitId(), state.getOrgUnitLabel());
            setProgram(state.getProgramId(), state.getProgramLabel());
            setOrgUnitMode(state.getOrgUnitModeId(), state.getOrgUnitModeLabel());
        }
    }

    private HIFragmentTodayScheduleSelectProgramState(Parcel in) {
        syncInProcess = in.readInt() == 1;

        orgUnitLabel = in.readString();
        orgUnitId = in.readString();

        programLabel = in.readString();
        programId = in.readString();

        orgUnitModeLabel = in.readString();
        orgUnitModeId = in.readString();

        fromDate = in.readString();
        toDate = in.readString();
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

        parcel.writeString(fromDate);
        parcel.writeString(toDate);


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
    public String getFromDay() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDay() {
        return toDate;
    }
    public boolean isFromDayEmpty() {
        return getFromDay() == null;
    }

    public boolean isToDayEmpty() {
        return getToDay() == null;
    }
    public void setToDate(String toDate) {
        this.toDate = toDate;
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
