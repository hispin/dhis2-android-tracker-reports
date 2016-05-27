package org.hispindia.bidtrackerreports.mvp.model.remote.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hisp.dhis.android.sdk.persistence.models.Event;

import java.util.List;

/**
 * Created by nhancao on 1/22/16.
 */
public class HIResBIDBirthEvents {
    @JsonProperty("events")
    List<Event> eventList;

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
