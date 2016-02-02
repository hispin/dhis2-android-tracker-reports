package org.hispindia.bidtrackerreports.mvp.model.remote.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hispindia.bidtrackerreports.mvp.model.local.HIOverdueRow;


import java.util.List;

/**
 * Created by Sourabh on 2/2/2016.
 */
public class HIResOverdue {
    @JsonProperty("eventRows")
    public List<HIOverdueRow> eventRows;

}
