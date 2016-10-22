package io.github.krtkush.marsexplorer.About;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Toast;

import io.github.krtkush.marsexplorer.About.Credits.CreditsActivity;
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

    @Override
    public void handleOptionsSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(activity);
                break;
        }
    }

    @Override
    public void goToCreditsSection() {
        Intent goToCreditsList = new Intent(activity, CreditsActivity.class);
        activity.startActivity(goToCreditsList);
    }

    @Override
    public void goToDeveloperPage() {
        String url = "https://krtkush.github.io";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(activity, Uri.parse(url));
    }

    @Override
    public void goToGithubPage() {
        String url = "https://github.com/krtkush/MarsExplorer";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(activity, Uri.parse(url));
    }
}
