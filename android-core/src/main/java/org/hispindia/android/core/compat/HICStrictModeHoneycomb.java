package org.hispindia.android.core.compat;
/**
 * Created by NhanCao on 13-Sep-15.
 */

import android.os.StrictMode;

/**
 * Implementation that supports the Strict Mode functionality
 * available Honeycomb.
 */
public class HICStrictModeHoneycomb implements HICIStrictMode {
    protected static String TAG = "HoneycombStrictMode";

    public void enableStrictMode() {
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder()
                        .detectDiskReads()
                        .detectDiskWrites()
                        .detectNetwork()
                        .penaltyLog()
                        .penaltyFlashScreen()
                        .build()
        );
    }
}
