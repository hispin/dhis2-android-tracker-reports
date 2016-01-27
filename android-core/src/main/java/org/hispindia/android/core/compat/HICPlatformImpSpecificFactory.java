package org.hispindia.android.core.compat;

/**
 * Created by NhanCao on 13-Sep-15.
 */

public class HICPlatformImpSpecificFactory {
    /**
     * Create a new StrictMode instance.
     *
     * @return StrictMode
     */
    public static HICIStrictMode getStrictMode() {
        if (HICPlatform.SUPPORTS_HONEYCOMB)
            return new HICStrictModeHoneycomb();
        else if (HICPlatform.SUPPORTS_GINGERBREAD)
            return new HICStrictModeLegacy();
        else
            return null;
    }
}
