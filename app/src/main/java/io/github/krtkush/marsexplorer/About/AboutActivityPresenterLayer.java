package io.github.krtkush.marsexplorer.About;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.widget.Toast;

import io.github.krtkush.marsexplorer.About.Credits.CreditsActivity;
import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.UtilityMethods;

/**
 * Created by kartikeykushwaha on 21/10/16.
 */

public class AboutActivityPresenterLayer implements AboutActivityPresenterInteractor {

    private AboutActivity activity;
    private CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
    private String EXTRA_REFERRER;
    private int URI_ANDROID_APP_SCHEME;

    public AboutActivityPresenterLayer(AboutActivity activity) {
        this.activity = activity;
        builder.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary));

        // Prepare for SDK compatibility problems.
        final int version = Build.VERSION.SDK_INT;

        if(version < 17)
            EXTRA_REFERRER = "android.intent.extra.REFERRER";
        else
            EXTRA_REFERRER = Intent.EXTRA_REFERRER;

        if(version < 22)
            URI_ANDROID_APP_SCHEME = 1<<1;
        else
            URI_ANDROID_APP_SCHEME = Intent.URI_ANDROID_APP_SCHEME;
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
        CustomTabsIntent openDeveloperPageTab = builder.build();
        openDeveloperPageTab.intent.putExtra(EXTRA_REFERRER,
                Uri.parse(URI_ANDROID_APP_SCHEME + "//" + activity.getPackageName()));

        openDeveloperPageTab.launchUrl(activity, Uri.parse("https://krtkush.github.io"));
    }

    @Override
    public void goToGithubPage() {
        CustomTabsIntent openGithubPageTab = builder.build();
        openGithubPageTab.intent.putExtra(EXTRA_REFERRER,
                Uri.parse(URI_ANDROID_APP_SCHEME + "//" + activity.getPackageName()));

        openGithubPageTab.launchUrl(activity, Uri.parse("https://github.com/krtkush/MarsExplorer"));
    }
}
