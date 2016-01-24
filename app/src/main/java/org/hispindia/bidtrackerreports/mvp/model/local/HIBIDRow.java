package org.hispindia.bidtrackerreports.mvp.model.local;

import org.hisp.dhis.android.sdk.persistence.models.ProgramStageDataElement;
import org.hisp.dhis.android.sdk.persistence.models.ProgramTrackedEntityAttribute;

import java.util.List;
import java.util.Map;

/**
 * Created by nhancao on 1/24/16.
 */
public class HIBIDRow {
    private String dueDate;
    private boolean isOverDue;
    private List<ProgramTrackedEntityAttribute> TEAKeyList; //tracked entity attribute list for order
    private Map<String, String> trackedEntityAttributeList;
    private List<ProgramStageDataElement> DEKeyList; //data element list for order
    private Map<String, String> dataElementList;

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isOverDue() {
        return isOverDue;
    }

    public void setIsOverDue(boolean isOverDue) {
        this.isOverDue = isOverDue;
    }

    public Map<String, String> getTrackedEntityAttributeList() {
        return trackedEntityAttributeList;
    }

    public void setTrackedEntityAttributeList(Map<String, String> trackedEntityAttributeList) {
        this.trackedEntityAttributeList = trackedEntityAttributeList;
    }

    public Map<String, String> getDataElementList() {
        return dataElementList;
    }

    public void setDataElementList(Map<String, String> dataElementList) {
        this.dataElementList = dataElementList;
    }

    public List<ProgramTrackedEntityAttribute> getTEAKeyList() {
        return TEAKeyList;
    }

    public void setTEAKeyList(List<ProgramTrackedEntityAttribute> TEAKeyList) {
        this.TEAKeyList = TEAKeyList;
    }

    public List<ProgramStageDataElement> getDEKeyList() {
        return DEKeyList;
    }

    public void setDEKeyList(List<ProgramStageDataElement> DEKeyList) {
        this.DEKeyList = DEKeyList;
    }
}
