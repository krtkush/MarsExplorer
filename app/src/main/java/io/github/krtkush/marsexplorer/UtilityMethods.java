package io.github.krtkush.marsexplorer;

import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.TypedValue;

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
                        .getSystemService(MarsExplorerApplication.getApplicationInstance().CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    /**
     * Method to convert given DP value into respective pixels
     * @param valueInDp
     * @return
     */
    public static int convertDpIntoPixels(int valueInDp) {

        int pixels;

        Resources r = MarsExplorerApplication.getApplicationInstance().getResources();
        pixels = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp,
                r.getDisplayMetrics()));

        return pixels;
    }
}
