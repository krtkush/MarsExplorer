package io.github.krtkush.marsexplorer.RoverExplorer.AboutRover;

import android.widget.Toast;

import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.UtilityMethods;

/**
 * Created by kartikeykushwaha on 01/11/16.
 */

public class AboutRoverPresenterLayer implements AboutRoverPresenterInteractor {

    private AboutRoverActivity activity;

    public AboutRoverPresenterLayer(AboutRoverActivity activity) {
        this.activity = activity;
    }

    @Override
    public void checkInternetConnectivity() {
        if(!UtilityMethods.isNetworkAvailable())
            activity.showToast(activity.getResources()
                    .getString(R.string.no_internet), Toast.LENGTH_LONG);
    }
}
