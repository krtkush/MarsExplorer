package io.github.krtkush.marsexplorer.About;

/**
 * Created by kartikeykushwaha on 21/10/16.
 */

public interface AboutActivityPresenterInteractor {

    /**
     * Method to check if the device is connected to the internet or not and show a warning toast
     * in case of latter.
     */
    void checkInternetConnectivity();

    /**
     * Method to populate the version number from the gradle file.
     */
    void populateVersionNumber();
}
