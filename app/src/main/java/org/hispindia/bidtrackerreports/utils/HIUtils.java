package org.hispindia.bidtrackerreports.utils;

import android.view.View;
import android.view.ViewGroup;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

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

    public static boolean isOverDue(String dueDateStrYYYYMMDD) {
        DateTime dueDate = DateTime.parse(dueDateStrYYYYMMDD, DateTimeFormat.forPattern("yyyy-MM-dd"));
        return dueDate.isBeforeNow();
    }

    public static String getYYYMMDD(String dueDateStr) {
        DateTime dueDate = DateTime.parse(dueDateStr, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        return dueDate.toString("yyyy-MM-dd");
    }


}
