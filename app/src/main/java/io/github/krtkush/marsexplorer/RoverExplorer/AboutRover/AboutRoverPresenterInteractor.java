package io.github.krtkush.marsexplorer.RoverExplorer.AboutRover;

/**
 * Created by kartikeykushwaha on 01/11/16.
 */

public interface AboutRoverPresenterInteractor {

    /**
     * Method to check if the device is connected to the internet or not and show a warning toast
     * in case of latter.
     */
    void checkInternetConnectivity();
}
