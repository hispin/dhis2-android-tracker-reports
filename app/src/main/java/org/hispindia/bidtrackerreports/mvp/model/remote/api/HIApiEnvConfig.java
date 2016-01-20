package org.hispindia.bidtrackerreports.mvp.model.remote.api;

/**
 * Created by NhanCao on 26-Nov-15.
 */
public class HIApiEnvConfig {

    private static String hostUrl;
    private static boolean needVerifyHost = false;

    public static void configEnv(APIURL hostEnv) {
        switch (hostEnv) {
            case BID:
                hostUrl="https://bid.dhis2.org/epireg";
                needVerifyHost = true;
                break;
            case LOCAL:
                hostUrl="http://192.168.0.230/dhis";
                needVerifyHost = false;
                break;
            default:
                hostUrl="https://bid.dhis2.org/epireg";
                needVerifyHost = true;
                break;
        }
    }

    public static boolean isNeedVerifyHost() {
        return needVerifyHost;
    }

    public static String getHostUrl() { return hostUrl; }

    public enum APIURL {
        BID,
        LOCAL
    }

}
