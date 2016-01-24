package org.hispindia.bidtrackerreports.mvp.model;

/**
 * Created by nhancao on 1/24/16.
 */
public class HIDataResult<Data> {

    public final boolean success;
    public final Data data;
    public final Object error;

    public HIDataResult(boolean success, Data data, Object error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }
}
