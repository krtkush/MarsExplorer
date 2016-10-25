package io.github.krtkush.marsexplorer.About.Credits;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;

/**
 * Created by kartikeykushwaha on 23/10/16.
 */

public class CreditsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;
    private CreditsListDataStructure creditsListDataStructure;
    private Activity activity;

    // Variables for CustomTabs.
    private CustomTabsIntent customTabsIntent;
    private CustomTabsClient customTabsClient;
    private CustomTabsSession customTabsSession;
    private int URI_ANDROID_APP_SCHEME;
    private String EXTRA_REFERRER;

    public CreditsRecyclerViewAdapter(Context context,
                                      CreditsListDataStructure creditsListDataStructure) {

        this.context = context;
        this.creditsListDataStructure = creditsListDataStructure;
        this.activity = (Activity) context;

        prepareIntentValues();
        prepareCustomTabs();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.credits_list_item_layout, parent, false);
        viewHolder = new CreditsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        CreditsViewHolder creditsViewHolder = (CreditsViewHolder) viewHolder;

        creditsViewHolder.title.setText(creditsListDataStructure.getTitle().get(position));
        creditsViewHolder.subTitle.setText(creditsListDataStructure.getSubTitle().get(position));

        creditsViewHolder.creditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = creditsListDataStructure.getSubTitle()
                        .get(viewHolder.getAdapterPosition());
                customTabsIntent.launchUrl(activity, Uri.parse(url));
            }
        });
    }

    @Override
    public int getItemCount() {
        return creditsListDataStructure.getTitle().size();
    }

    public class CreditsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title) TextView title;
        @BindView(R.id.subTitle) TextView subTitle;
        @BindView(R.id.creditItem) RelativeLayout creditItem;

        public CreditsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * Method to prepare for SDK version compatibility problems.
     */
    private void prepareIntentValues() {
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

    /**
     * Method to prepare the CustomTabs.
     */
    private void prepareCustomTabs() {
        final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";

        CustomTabsServiceConnection customTabsServiceConnection =
                new CustomTabsServiceConnection() {
                    @Override
                    public void onCustomTabsServiceConnected(ComponentName name,
                                                             CustomTabsClient client) {
                        customTabsClient = client;
                        customTabsClient.warmup(0L);
                        customTabsSession = customTabsClient.newSession(null);
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        customTabsClient = null;
                    }
                };

        CustomTabsClient.bindCustomTabsService(activity,
                CUSTOM_TAB_PACKAGE_NAME, customTabsServiceConnection);

        customTabsIntent = new CustomTabsIntent.Builder(customTabsSession)
                .setShowTitle(true)
                .setStartAnimations(activity, R.anim.slide_up_enter, R.anim.stay)
                .setExitAnimations(activity, R.anim.stay, R.anim.slide_down_exit)
                .build();

        customTabsIntent.intent.putExtra(EXTRA_REFERRER,
                Uri.parse(URI_ANDROID_APP_SCHEME + "//" + activity.getPackageName()));
    }
}
