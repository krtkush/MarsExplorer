package io.github.krtkush.marsexplorer.About.Credits;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import io.github.krtkush.marsexplorer.UtilityMethods;

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
    // Keep track if the CustomTab is up and running. If not, open the links in the browser.
    private boolean isConnectedToCustomTabService;

    public CreditsRecyclerViewAdapter(Context context,
                                      CreditsListDataStructure creditsListDataStructure) {

        this.context = context;
        this.creditsListDataStructure = creditsListDataStructure;
        this.activity = (Activity) context;

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

                if(isConnectedToCustomTabService)
                    customTabsIntent.launchUrl(activity, Uri.parse(url));
                else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    activity.startActivity(browserIntent);
                }
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

        isConnectedToCustomTabService = CustomTabsClient.bindCustomTabsService(activity,
                CUSTOM_TAB_PACKAGE_NAME, customTabsServiceConnection);

        customTabsIntent = new CustomTabsIntent.Builder(customTabsSession)
                .setShowTitle(true)
                .setStartAnimations(activity, R.anim.slide_up_enter, R.anim.stay)
                .setExitAnimations(activity, R.anim.stay, R.anim.slide_down_exit)
                .build();

        customTabsIntent.intent.putExtra(UtilityMethods.customTabReferrerKey(),
                UtilityMethods.customTabReferrerString());
    }
}
