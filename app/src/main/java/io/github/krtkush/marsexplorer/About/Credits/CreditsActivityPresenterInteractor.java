package io.github.krtkush.marsexplorer.About.Credits;

import android.support.v7.widget.RecyclerView;

/**
 * Created by kartikeykushwaha on 22/10/16.
 */

public interface CreditsActivityPresenterInteractor {

    /**
     * Method to check if the device is connected to the internet or not and show a warning toast
     * in case of latter.
     */
    void checkInternetConnectivity();

    /**
     * Method to setup the RecyclerView with the credits data.
     */
    void prepareRecyclerViewAndAddData(RecyclerView recyclerView);
}
