package org.hispindia.bidtrackerreports.mvp.model.local.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nhancao on 2/5/16.
 */

public class HIDBbidrow extends RealmObject {

    @PrimaryKey
    private int order;
    private String dueDate;
    //attr
    private String firstName;
    private String chilName;
    //data element: value or true:"check", null:"calendar", "-*#hidefield#*-":hiden rule
    private String bcg;
    private String bcgScar;
    private String bcgRepeatDose;
    private String opv0;
    private String opv1;
    private String dptHepBHib1;
    private String pcv1;
    private String rv1;
    private String opv2;
    private String pcv2;
    private String rv2;
    private String opv3;
    private String dptHepBHib3;
    private String pcv3;
    private String opv4;
    private String measles1;
    private String measles2;
    private Boolean isOverdue;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getChilName() {
        return chilName;
    }

    public void setChilName(String chilName) {
        this.chilName = chilName;
    }

    public String getBcg() {
        return bcg;
    }

    public void setBcg(String bcg) {
        this.bcg = bcg;
    }

    public String getBcgScar() {
        return bcgScar;
    }

    public void setBcgScar(String bcgScar) {
        this.bcgScar = bcgScar;
    }

    public String getBcgRepeatDose() {
        return bcgRepeatDose;
    }

    public void setBcgRepeatDose(String bcgRepeatDose) {
        this.bcgRepeatDose = bcgRepeatDose;
    }

    public String getOpv0() {
        return opv0;
    }

    public void setOpv0(String opv0) {
        this.opv0 = opv0;
    }

    public String getOpv1() {
        return opv1;
    }

    public void setOpv1(String opv1) {
        this.opv1 = opv1;
    }

    public String getDptHepBHib1() {
        return dptHepBHib1;
    }

    public void setDptHepBHib1(String dptHepBHib1) {
        this.dptHepBHib1 = dptHepBHib1;
    }

    public String getPcv1() {
        return pcv1;
    }

    public void setPcv1(String pcv1) {
        this.pcv1 = pcv1;
    }

    public String getRv1() {
        return rv1;
    }

    public void setRv1(String rv1) {
        this.rv1 = rv1;
    }

    public String getOpv2() {
        return opv2;
    }

    public void setOpv2(String opv2) {
        this.opv2 = opv2;
    }

    public String getPcv2() {
        return pcv2;
    }

    public void setPcv2(String pcv2) {
        this.pcv2 = pcv2;
    }

    public String getRv2() {
        return rv2;
    }

    public void setRv2(String rv2) {
        this.rv2 = rv2;
    }

    public String getOpv3() {
        return opv3;
    }

    public void setOpv3(String opv3) {
        this.opv3 = opv3;
    }

    public String getDptHepBHib3() {
        return dptHepBHib3;
    }

    public void setDptHepBHib3(String dptHepBHib3) {
        this.dptHepBHib3 = dptHepBHib3;
    }

    public String getPcv3() {
        return pcv3;
    }

    public void setPcv3(String pcv3) {
        this.pcv3 = pcv3;
    }

    public String getOpv4() {
        return opv4;
    }

    public void setOpv4(String opv4) {
        this.opv4 = opv4;
    }

    public String getMeasles1() {
        return measles1;
    }

    public void setMeasles1(String measles1) {
        this.measles1 = measles1;
    }

    public String getMeasles2() {
        return measles2;
    }

    public void setMeasles2(String measles2) {
        this.measles2 = measles2;
    }

    public Boolean getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(Boolean isOverdue) {
        this.isOverdue = isOverdue;
    }

}
