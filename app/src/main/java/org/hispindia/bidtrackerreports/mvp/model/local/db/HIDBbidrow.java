package org.hispindia.bidtrackerreports.mvp.model.local.db;

/**
 * Created by nhancao on 2/5/16.
 */

public class HIDBbidrow {

    public int order;
    public String dueDate;
    //attr
    public String firstName;
    public String chilName;
    //de
    public String bcg;
    public String bcgScar;
    public String bcgRepeatDose;
    public String opv0;
    public String opv1;
    public String dptHepBHib1;
    public String pcv1;
    public String rv1;
    public String opv2;
    public String pcv2;
    public String rv2;
    public String opv3;
    public String dptHepBHib3;
    public String pcv3;
    public String opv4;
    public String measles1;
    public String measles2;
    public Boolean isOverdue;

    public HIDBbidrow() {
    }

    public HIDBbidrow(int order, String dueDate, String firstName, String chilName, String bcg, String bcgScar, String bcgRepeatDose, String opv0, String opv1, String dptHepBHib1, String pcv1, String rv1, String opv2, String pcv2, String rv2, String opv3, String dptHepBHib3, String pcv3, String opv4, String measles1, String measles2, Boolean isOverdue) {
        this.order = order;
        this.dueDate = dueDate;
        this.firstName = firstName;
        this.chilName = chilName;
        this.bcg = bcg;
        this.bcgScar = bcgScar;
        this.bcgRepeatDose = bcgRepeatDose;
        this.opv0 = opv0;
        this.opv1 = opv1;
        this.dptHepBHib1 = dptHepBHib1;
        this.pcv1 = pcv1;
        this.rv1 = rv1;
        this.opv2 = opv2;
        this.pcv2 = pcv2;
        this.rv2 = rv2;
        this.opv3 = opv3;
        this.dptHepBHib3 = dptHepBHib3;
        this.pcv3 = pcv3;
        this.opv4 = opv4;
        this.measles1 = measles1;
        this.measles2 = measles2;
        this.isOverdue = isOverdue;
    }

    public int getOrder() {
        return order;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getChilName() {
        return chilName;
    }

    public String getBcg() {
        return bcg;
    }

    public String getBcgScar() {
        return bcgScar;
    }

    public String getBcgRepeatDose() {
        return bcgRepeatDose;
    }

    public String getOpv0() {
        return opv0;
    }

    public String getOpv1() {
        return opv1;
    }

    public String getDptHepBHib1() {
        return dptHepBHib1;
    }

    public String getPcv1() {
        return pcv1;
    }

    public String getRv1() {
        return rv1;
    }

    public String getOpv2() {
        return opv2;
    }

    public String getPcv2() {
        return pcv2;
    }

    public String getRv2() {
        return rv2;
    }

    public String getOpv3() {
        return opv3;
    }

    public String getDptHepBHib3() {
        return dptHepBHib3;
    }

    public String getPcv3() {
        return pcv3;
    }

    public String getOpv4() {
        return opv4;
    }

    public String getMeasles1() {
        return measles1;
    }

    public String getMeasles2() {
        return measles2;
    }

    public Boolean getIsOverdue() {
        return isOverdue;
    }

    public int getBCGCount() {
        int count = 0;
        if (getBcg() != null && getBcg().trim().equals("")) count++;
        if (getBcgScar() != null && getBcgScar().trim().equals("")) count++;
        if (getBcgRepeatDose() != null && getBcgRepeatDose().trim().equals("")) count++;
        return count;
    }

    public int getDPTCount() {
        int count = 0;
        if (getDptHepBHib1() != null && getDptHepBHib1().trim().equals("")) count++;
        if (getDptHepBHib3() != null && getDptHepBHib3().trim().equals("")) count++;
        return count;
    }

    public int getOPVCount() {
        int count = 0;
        if (getOpv0() != null && getOpv0().trim().equals("")) count++;
        if (getOpv1() != null && getOpv1().trim().equals("")) count++;
        if (getOpv2() != null && getOpv2().trim().equals("")) count++;
        if (getOpv3() != null && getOpv3().trim().equals("")) count++;
        if (getOpv4() != null && getOpv4().trim().equals("")) count++;
        return count;
    }

    public int getPCVCount() {
        int count = 0;
        if (getPcv1() != null && getPcv1().trim().equals("")) count++;
        if (getPcv2() != null && getPcv2().trim().equals("")) count++;
        if (getPcv3() != null && getPcv3().trim().equals("")) count++;
        return count;
    }

    public int getRVCount() {
        int count = 0;
        if (getRv1() != null && getRv1().trim().equals("")) count++;
        if (getRv2() != null && getRv2().trim().equals("")) count++;
        return count;
    }

    public int getMeaslesCount() {
        int count = 0;
        if (getMeasles1() != null && getMeasles1().trim().equals("")) count++;
        if (getMeasles2() != null && getMeasles2().trim().equals("")) count++;
        return count;
    }

    public int getVaccineCount() {
        int count = 0;
        if (getBcg() != null && getBcg().trim().equals("")) count++;
        if (getBcgScar() != null && getBcgScar().trim().equals("")) count++;
        if (getBcgRepeatDose() != null && getBcgRepeatDose().trim().equals("")) count++;
        if (getDptHepBHib1() != null && getDptHepBHib1().trim().equals("")) count++;
        if (getDptHepBHib3() != null && getDptHepBHib3().trim().equals("")) count++;
        if (getOpv0() != null && getOpv0().trim().equals("")) count++;
        if (getOpv1() != null && getOpv1().trim().equals("")) count++;
        if (getOpv2() != null && getOpv2().trim().equals("")) count++;
        if (getOpv3() != null && getOpv3().trim().equals("")) count++;
        if (getOpv4() != null && getOpv4().trim().equals("")) count++;
        if (getPcv1() != null && getPcv1().trim().equals("")) count++;
        if (getPcv2() != null && getPcv2().trim().equals("")) count++;
        if (getPcv3() != null && getPcv3().trim().equals("")) count++;
        if (getRv1() != null && getRv1().trim().equals("")) count++;
        if (getRv2() != null && getRv2().trim().equals("")) count++;
        if (getMeasles1() != null && getMeasles1().trim().equals("")) count++;
        if (getMeasles2() != null && getMeasles2().trim().equals("")) count++;

        return count;
    }



}
