package org.hispindia.android.core.ui.fragment;
/**
 * Created by NhanCao on 13-Sep-15.
 */

import android.view.View;

import org.hispindia.android.core.ui.view.HICViewSwitcher;

public class HICFragmentLoading extends HICFragmentBase {

    private HICViewSwitcher loadingViewSwitcher;

    protected void setupLoading(View mainView, View loadingView) {
        loadingViewSwitcher = new HICViewSwitcher();
        loadingViewSwitcher.setMainView(mainView);
        loadingViewSwitcher.setAlterView(loadingView);
    }

    public void setShowLoading(boolean show) {
        loadingViewSwitcher.showMainView(!show);
    }

}
