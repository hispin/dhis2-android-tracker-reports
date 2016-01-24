package org.hispindia.bidtrackerreports.mvp.view;

import org.hispindia.bidtrackerreports.mvp.model.HIDataResult;

/**
 * Created by nhancao on 1/24/16.
 */
public interface HIIViewApiCall<Data> {
    void renderResult(HIDataResult<Data> result);
}
