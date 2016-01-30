package org.hispindia.bidtrackerreports.mvp.model.remote.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hispindia.bidtrackerreports.mvp.model.local.HISchvaccineRow;

import java.util.List;

/**
 * Created by Sourabh on 1/30/2016.
 */
public class HIResSchvaccine {
    @JsonProperty("eventRows")
    public List<HISchvaccineRow> eventRows;
}
