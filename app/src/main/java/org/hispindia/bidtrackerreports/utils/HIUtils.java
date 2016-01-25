package org.hispindia.bidtrackerreports.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nhancao on 1/25/16.
 */
public class HIUtils {
    public static void setViewAndChildrenEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }
}
