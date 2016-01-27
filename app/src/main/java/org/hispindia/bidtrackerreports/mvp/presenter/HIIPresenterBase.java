package org.hispindia.bidtrackerreports.mvp.presenter;

/**
 * Created by nhancao on 1/18/16.
 */
public interface HIIPresenterBase<V> {
    void onStart(V view);

    void onStop();
}
