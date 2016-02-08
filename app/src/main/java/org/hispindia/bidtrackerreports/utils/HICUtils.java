package org.hispindia.bidtrackerreports.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import java.text.Normalizer;

/**
 * Created by NhanCao on 13-Sep-15.
 */
public class HICUtils {

    public static final String ACTIVITY_RECEIVE = "com.cvnhan.core.utils.Activity.RECEIVE";

    public static final String SERVICE_RECEIVE = "com.cvnhan.core.utils.Service.RECEIVE";

    /**
     * @param duration : 302000
     * @return 5:02
     */
    public static String formatDuration(String duration) {
        String result = duration;
        try {
            long du = Integer.parseInt(result) / 1000;
            result = String.format("%02d:%02d", du / 60, du % 60);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String formatDuration(int duration) {
        String result = "00:00";
        try {
            long du = duration / 1000;
            result = String.format("%02d:%02d", du / 60, du % 60);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static CharSequence highlightText(String search, String originalText) {
        if (search != null && !search.equalsIgnoreCase("")) {
            String normalizedText = Normalizer.normalize(originalText, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
            int start = normalizedText.indexOf(search);
            if (start < 0) {
                return originalText;
            } else {
                Spannable highlighted = new SpannableString(originalText);
                while (start >= 0) {
                    int spanStart = Math.min(start, originalText.length());
                    int spanEnd = Math.min(start + search.length(), originalText.length());
                    highlighted.setSpan(new ForegroundColorSpan(Color.BLUE), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    start = normalizedText.indexOf(search, spanEnd);
                }
                return highlighted;
            }
        }
        return originalText;
    }

    public static boolean isContainText(String search, String originalText) {
        if (search != null && !search.equalsIgnoreCase("")) {
            String normalizedText = Normalizer.normalize(originalText, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
            int start = normalizedText.indexOf(search);
            if (start < 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void setEnabledViews(boolean enabled, View... views) {
        for (View v : views) {
            v.setEnabled(enabled);
        }
    }

    public static void setShowViews(boolean show, View... views) {
        for (View v : views) {
            if (show) {
                v.setVisibility(View.VISIBLE);
            } else {
                v.setVisibility(View.GONE);
            }
        }
    }
}
