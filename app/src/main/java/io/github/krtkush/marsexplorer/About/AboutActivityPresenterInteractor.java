package io.github.krtkush.marsexplorer.About;

import android.view.MenuItem;

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

    /**
     * Method to handle taps on actionbar items.
     */
    void handleOptionsSelected(MenuItem item);

    /**
     * Method to take user to the Mars Explorer github page.
     */
    void goToGithubPage();

    /**
     * Method to take user to developer personal page.
     */
    void goToDeveloperPage();

    /**
     * Method to open the credits activity.
     */
    void goToCreditsSection();
}
