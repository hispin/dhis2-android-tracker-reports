package org.hispindia.bidtrackerreports.mvp.model.local;

import org.hisp.dhis.android.sdk.persistence.models.ProgramStageDataElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhancao on 1/24/16.
 */
public class HIBIDBIRTHRow {

    private List<HIBIDBIRTHRowItem> dataElementList;



    public List<HIBIDBIRTHRowItem> getDataElementList() {
        return dataElementList;
    }

    public void setDataElementList(List<HIBIDBIRTHRowItem> dataElementList) {
        this.dataElementList = dataElementList;
    }


    public void setDataElementListMapping(List<ProgramStageDataElement> dataElementList) {
        setDataElementList(new ArrayList<>());
        for (ProgramStageDataElement item : dataElementList) {
            getDataElementList().add(new HIBIDBIRTHRowItem(item.getDataelement(), item.getDataElement().getDisplayName(), null));
        }
    }
}
