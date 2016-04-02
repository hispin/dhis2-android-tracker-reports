package org.hispindia.bidtrackerreports.mvp.model.local;

import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.HIParamBIDHardcode;

/**
 * Created by nhancao on 2/5/16.
 */
public class HIDBMapping {

    public static HIDBbidrow fromRemote(HIBIDRow remote) {
        if (remote == null) return null;
        HIDBbidrow local = new HIDBbidrow();
        local.order = remote.getOrder();
        local.dueDate = remote.getDueDate();
        for (HIBIDRowItem item : remote.getTrackedEntityAttributeList()) {
            if (item.getId().equals(HIParamBIDHardcode.FIRSTNAMEID)) {
                local.firstName = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.CHILDNAMEID)) {
                local.chilName = item.getValue();
            }
            else if (item.getId().equals(HIParamBIDHardcode.DATEOFBIRTH)) {
                local.dob = item.getValue();
            }

        }
        for (HIBIDRowItem item : remote.getDataElementList()) {
            if (item.getId().equals(HIParamBIDHardcode.DE.get("bpBUOvqy1Jn").getId())) {
                local.bcg = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("EMcT5j5zR81").getId())) {
                local.bcgScar = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("KRF40x6EILp").getId())) {
                local.bcgRepeatDose = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("no7SkAxepi7").getId())) {
                local.opv0 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("CfPM8lsEMzH").getId())) {
                local.opv1 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("K3TcJM1ySQA").getId())) {
                local.dptHepBHib1 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("fmXCCPENnwR").getId())) {
                local.pcv1 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("nIqQYeSwU9E").getId())) {
                local.rv1 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("sDORmAKh32v").getId())) {
                local.opv2 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("PvHUllrtPiy").getId())) {
                local.pcv2 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("wYg2gOWSyJG").getId())) {
                local.rv2 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("nQeUfqPjK5o").getId())) {
                local.opv3 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("pxCZNoqDVJC").getId())) {
                local.dptHepBHib3 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("B4eJCy6LFLZ").getId())) {
                local.pcv3 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("cNA9EmFaiAa").getId())) {
                local.opv4 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("g8dMiUOTFla").getId())) {
                local.measles1 = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("Bxh1xgIY9nA").getId())) {
                local.measles2 = item.getValue();
            }
        }
        local.isOverdue = (remote.isOverDue());
        return local;
    }


}
