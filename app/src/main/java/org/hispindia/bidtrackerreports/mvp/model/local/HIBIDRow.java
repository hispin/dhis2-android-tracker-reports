package org.hispindia.bidtrackerreports.mvp.model.local;

import org.hisp.dhis.android.sdk.persistence.models.ProgramStageDataElement;
import org.hisp.dhis.android.sdk.persistence.models.ProgramTrackedEntityAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhancao on 1/24/16.
 */
public class HIBIDRow {
    private int order;
    private String eventId;
    private String dueDate;
    private boolean isOverDue;
    private List<HIBIDRowItem> trackedEntityAttributeList;
    private List<HIBIDRowItem> dataElementList;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

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

    public List<HIBIDRowItem> getTrackedEntityAttributeList() {
        return trackedEntityAttributeList;
    }

    public void setTrackedEntityAttributeList(List<HIBIDRowItem> trackedEntityAttributeList) {
        this.trackedEntityAttributeList = trackedEntityAttributeList;
    }

    public List<HIBIDRowItem> getDataElementList() {
        return dataElementList;
    }

    public void setDataElementList(List<HIBIDRowItem> dataElementList) {
        this.dataElementList = dataElementList;
    }

    public void setTrackedEntityAttributeListMapping(List<ProgramTrackedEntityAttribute> trackedEntityAttributeList) {
        setTrackedEntityAttributeList(new ArrayList<>());
        for (ProgramTrackedEntityAttribute item : trackedEntityAttributeList) {
            getTrackedEntityAttributeList().add(new HIBIDRowItem(item.getTrackedEntityAttributeId(), item.getTrackedEntityAttribute().getDisplayName(), null));
        }
    }

    public void setDataElementListMapping(List<ProgramStageDataElement> dataElementList) {
        setDataElementList(new ArrayList<>());
        for (ProgramStageDataElement item : dataElementList) {
            getDataElementList().add(new HIBIDRowItem(item.getDataelement(), item.getDataElement().getDisplayName(), null));
        }
    }
}
