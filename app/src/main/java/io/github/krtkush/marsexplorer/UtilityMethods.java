package io.github.krtkush.marsexplorer;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;

/**
 * Created by kartikeykushwaha on 22/05/16.
 */
public class UtilityMethods {

    /**
     * Method to detect network connection on the device
     * @return
     */
    public static boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) MarsExplorerApplication.getApplicationInstance()
                        .getSystemService(MarsExplorerApplication.getApplicationInstance()
                                .CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    /**
     * It's usually very important for websites to track where their traffic is coming from.
     * We make sure to let them know that we are sending them users by setting the referrer when
     * launching our Custom Tab.
     * @return The value of the Referrer Intent.
     */
    public static String customTabReferrerString() {

        int URI_ANDROID_APP_SCHEME;

        // Prepare for SDK version compatibility problems.
        if(Build.VERSION.SDK_INT < 22)
            URI_ANDROID_APP_SCHEME = 1<<1;
        else
            URI_ANDROID_APP_SCHEME = Intent.URI_ANDROID_APP_SCHEME;

        return URI_ANDROID_APP_SCHEME + "//" + MarsExplorerApplication.getApplicationInstance()
                .getPackageName();
    }

    /**
     * @return The Referrer intent key.
     */
    public static String customTabReferrerKey() {

        String EXTRA_REFERRER;

        // Prepare for SDK version compatibility problems.
        if(Build.VERSION.SDK_INT < 17)
            EXTRA_REFERRER = "android.intent.extra.REFERRER";
        else
            EXTRA_REFERRER = Intent.EXTRA_REFERRER;

        return EXTRA_REFERRER;
    }
}
