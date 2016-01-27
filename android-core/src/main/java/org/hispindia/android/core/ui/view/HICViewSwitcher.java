package org.hispindia.android.core.ui.view;
/**
 * Created by NhanCao on 13-Sep-15.
 */

import android.view.View;

public class HICViewSwitcher {

    private View mainView;
    private View alterView;
    private boolean isMainShowing = true;

    public void showMainView(boolean show) {
        if (isMainShowing == show) {
            return;
        }
        isMainShowing = show;
        if (show) {
            if (mainView != null) {
                mainView.setVisibility(View.VISIBLE);
            }
            if (alterView != null) {
                alterView.setVisibility(View.GONE);
            }
        } else {
            if (alterView != null) {
                alterView.setVisibility(View.VISIBLE);
            }
            if (mainView != null) {
                mainView.setVisibility(View.GONE);
            }
        }

    }

    public void setMainView(View mainView) {
        this.mainView = mainView;
    }

    public void setAlterView(View alterView) {
        this.alterView = alterView;
    }
}
