package org.hispindia.bidtrackerreports.mvp.model.local;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sourabh on 2/4/2016.
 */
public class HINotes {

    @JsonProperty("value")
    public String displayName;
    @JsonProperty("storedBy")
    public String attribute;
    @JsonProperty("storedDate")
    public String created;
}
