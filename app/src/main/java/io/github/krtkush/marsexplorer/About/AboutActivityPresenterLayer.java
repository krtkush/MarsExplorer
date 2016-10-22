package io.github.krtkush.marsexplorer.About;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.UtilityMethods;

/**
 * Created by kartikeykushwaha on 21/10/16.
 */

public class AboutActivityPresenterLayer implements AboutActivityPresenterInteractor {

    AboutActivity activity;

    public AboutActivityPresenterLayer(AboutActivity activity) {
        this.activity = activity;
    }

    @Override
    public void checkInternetConnectivity() {
        if(!UtilityMethods.isNetworkAvailable())
            activity.showToast(activity.getResources()
                            .getString(R.string.no_internet),
                    Toast.LENGTH_LONG);
    }

    @Override
    public void populateVersionNumber() {

        try {
            PackageInfo packageInfo = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0);
            activity.setVersionNumber(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
