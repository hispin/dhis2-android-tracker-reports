package org.hispindia.bidtrackerreports.mvp.model.local;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sourabh on 1/30/2016.
 */
public class HIAttribute {
    @JsonProperty("displayName")
    public String displayName;
    @JsonProperty("attribute")
    public String attribute;
    @JsonProperty("created")
    public String created;
    @JsonProperty("lastUpdated")
    public String lastUpdated;
    @JsonProperty("valueType")
    public String valueType;
    @JsonProperty("value")
    public String value;
}
