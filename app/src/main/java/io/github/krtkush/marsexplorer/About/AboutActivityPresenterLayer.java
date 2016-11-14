package io.github.krtkush.marsexplorer.About;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
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

    // Variables for CustomTabs.
    private CustomTabsIntent customTabsIntent;
    private CustomTabsClient customTabsClient;
    private CustomTabsSession customTabsSession;
    private String DEVELOPER_PAGE = "https://krtkush.github.io";
    private String GITHUB_PAGE = "https://github.com/krtkush/MarsExplorer";
    // Keep track if the CustomTab is up and running. If not, open the links in the browser.
    private boolean isConnectedToCustomTabService;

    public AboutActivityPresenterLayer(AboutActivity activity) {

        this.activity = activity;
        prepareCustomTabs();
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
        if(isConnectedToCustomTabService)
            customTabsIntent.launchUrl(activity, Uri.parse(DEVELOPER_PAGE));
        else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(DEVELOPER_PAGE));
            activity.startActivity(browserIntent);
        }
    }

    @Override
    public void goToGithubPage() {
        if(isConnectedToCustomTabService)
            customTabsIntent.launchUrl(activity, Uri.parse(GITHUB_PAGE));
        else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_PAGE));
            activity.startActivity(browserIntent);
        }
    }

    /**
     * Method to prepare the CustomTabs.
     */
    private void prepareCustomTabs() {
        final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
        final int REQUEST_CODE = 100;

        CustomTabsServiceConnection customTabsServiceConnection =
                new CustomTabsServiceConnection() {
                    @Override
                    public void onCustomTabsServiceConnected(ComponentName name,
                                                             CustomTabsClient client) {
                        customTabsClient = client;
                        customTabsClient.warmup(0L);
                        customTabsSession = customTabsClient.newSession(null);
                        customTabsSession.mayLaunchUrl(Uri.parse(GITHUB_PAGE), null, null);
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        customTabsClient = null;
                    }
                };

        isConnectedToCustomTabService = CustomTabsClient.bindCustomTabsService(activity,
                CUSTOM_TAB_PACKAGE_NAME, customTabsServiceConnection);

        // Define the icon and title for the share option.
        String shareLabel = activity.getString(R.string.share);
        Bitmap icon = BitmapFactory.decodeResource(activity.getResources(),
                R.drawable.ic_share);

        // Create a PendingIntent to the ShareUrlBroadcastReceiver BroadCastReceiver implementation
        Intent shareIntent = new Intent(activity, ShareUrlBroadcastReceiver.class);
        PendingIntent pendingShareIntent =
                PendingIntent.getBroadcast(
                        activity,
                        REQUEST_CODE,
                        shareIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        customTabsIntent = new CustomTabsIntent.Builder(customTabsSession)
                .setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                .setActionButton(icon, shareLabel, pendingShareIntent)
                .setShowTitle(true)
                .setStartAnimations(activity, R.anim.slide_up_enter, R.anim.stay)
                .setExitAnimations(activity, R.anim.stay, R.anim.slide_down_exit)
                .build();

        customTabsIntent.intent.putExtra(UtilityMethods.customTabReferrerKey(),
                UtilityMethods.customTabReferrerString());
    }
}
