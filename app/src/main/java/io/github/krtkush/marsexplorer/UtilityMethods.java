package io.github.krtkush.marsexplorer;

import android.net.ConnectivityManager;

/**
 * Created by kartikeykushwaha on 22/05/16.
 */
public class UtilityMethods {

    public static boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) MarsExplorerApplication.getApplicationInstance()
                        .getSystemService(MarsExplorerApplication.getApplicationInstance().CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
