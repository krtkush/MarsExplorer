package io.github.krtkush.marsexplorer.About.Credits;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
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

    public CreditsRecyclerViewAdapter(Context context,
                                      CreditsListDataStructure creditsListDataStructure) {

        this.context = context;
        this.creditsListDataStructure = creditsListDataStructure;
        this.activity = (Activity) context;
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
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
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
}
