package org.hispindia.bidtrackerreports.mvp.model;


import org.hispindia.bidtrackerreports.utils.HIConstant;

/**
 * Created by nhancao on 1/24/16.
 */
public class HIPublishData<T> {

    public final HIConstant.DataSrc source;
    public final T response;

    public HIPublishData(HIConstant.DataSrc source, T response) {
        this.source = source;
        this.response = response;
    }
}
