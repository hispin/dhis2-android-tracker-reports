package org.hispindia.bidtrackerreports.mvp.model.local.db;

/**
 * Created by nhancao on 2/5/16.
 */

public class HIbidbirthrow {

    //attr
    public String firstName;
    public String lastname;
    public String dob;
    //de
    public String gender;
    public String village;
    public String zone;


    public HIbidbirthrow() {
    }

    public HIbidbirthrow(String firstName, String lastname, String dob, String gender, String village, String zone) {

        this.firstName = firstName;
        this.lastname = lastname;
        this.dob = dob;
        this.gender = gender;
        this.village = village;
        this.zone = zone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getVillage() {
        return village;
    }

    public String getZone() {
        return zone;
    }


}
