package org.hispindia.bidtrackerreports.mvp.model.local;

import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hitodayschedule.HIParamTodaySchedule;

/**
 * Created by nhancao on 2/5/16.
 */
public class HIDBMapping {

    public static HIDBbidrow clone(HIDBbidrow row) {
        if (row == null) return null;
        HIDBbidrow local = new HIDBbidrow();
        local.setOrder(row.getOrder());
        local.setDueDate(row.getDueDate());
        local.setFirstName(row.getFirstName());
        local.setChilName(row.getChilName());
        local.setBcg(row.getBcg());
        local.setBcgScar(row.getBcgScar());
        local.setBcgRepeatDose(row.getBcgRepeatDose());
        local.setOpv0(row.getOpv0());
        local.setOpv1(row.getOpv1());
        local.setDptHepBHib1(row.getDptHepBHib1());
        local.setPcv1(row.getPcv1());
        local.setRv1(row.getRv1());
        local.setOpv2(row.getOpv2());
        local.setPcv2(row.getPcv2());
        local.setRv2(row.getRv2());
        local.setOpv3(row.getOpv3());
        local.setDptHepBHib3(row.getDptHepBHib3());
        local.setPcv3(row.getPcv3());
        local.setOpv4(row.getOpv4());
        local.setMeasles1(row.getMeasles1());
        local.setMeasles2(row.getMeasles2());
        local.setIsOverdue(row.getIsOverdue());
        return local;
    }

    public static HIDBbidrow fromRemote(HIBIDRow remote) {
        if (remote == null) return null;
        HIDBbidrow local = new HIDBbidrow();
        local.setOrder(remote.getOrder());
        local.setDueDate(remote.getDueDate());
        for (HIBIDRowItem item : remote.getTrackedEntityAttributeList()) {
            if (item.getId().equals(HIParamTodaySchedule.FIRSTNAMEID)) {
                local.setFirstName(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.CHILDNAMEID)) {
                local.setChilName(item.getValue());
            }
        }
        for (HIBIDRowItem item : remote.getDataElementList()) {
            if (item.getId().equals(HIParamTodaySchedule.DE.get("bpBUOvqy1Jn").getId())) {
                local.setBcg(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("EMcT5j5zR81").getId())) {
                local.setBcgScar(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("KRF40x6EILp").getId())) {
                local.setBcgRepeatDose(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("no7SkAxepi7").getId())) {
                local.setOpv0(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("CfPM8lsEMzH").getId())) {
                local.setOpv1(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("K3TcJM1ySQA").getId())) {
                local.setDptHepBHib1(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("fmXCCPENnwR").getId())) {
                local.setPcv1(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("nIqQYeSwU9E").getId())) {
                local.setRv1(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("sDORmAKh32v").getId())) {
                local.setOpv2(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("PvHUllrtPiy").getId())) {
                local.setPcv2(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("wYg2gOWSyJG").getId())) {
                local.setRv2(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("nQeUfqPjK5o").getId())) {
                local.setOpv3(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("pxCZNoqDVJC").getId())) {
                local.setDptHepBHib3(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("B4eJCy6LFLZ").getId())) {
                local.setPcv3(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("cNA9EmFaiAa").getId())) {
                local.setOpv4(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("g8dMiUOTFla").getId())) {
                local.setMeasles1(item.getValue());
            } else if (item.getId().equals(HIParamTodaySchedule.DE.get("Bxh1xgIY9nA").getId())) {
                local.setMeasles2(item.getValue());
            }
        }
        local.setIsOverdue(remote.isOverDue());
        return local;
    }


}
