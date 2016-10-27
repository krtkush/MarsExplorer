package io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.RESTClients.DataModels.PhotosJsonDataModels.Photos;
import io.github.krtkush.marsexplorer.RoverExplorer.ExpandedPhoto.ExpandedPhotosConstants;
import io.github.krtkush.marsexplorer.RoverExplorer.ExpandedPhoto.PhotoExpandedViewActivity;

/**
 * Created by kartikeykushwaha on 25/08/16.
 */
public class PhotosRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Photos> photos;
    private int lastPosition;
    private Activity activity;

    public PhotosRecyclerViewAdapter(Context context, List<Photos> photos) {
        this.context = context;
        this.photos = photos;
        this.activity = (Activity) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photos_grid_item_layout, parent, false);
        viewHolder = new PhotosViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        final PhotosViewHolder photosViewHolder = (PhotosViewHolder) viewHolder;

        // Animate the view
        if(viewHolder.getAdapterPosition() >= lastPosition) {
            setAnimation(viewHolder.itemView);
            lastPosition = viewHolder.getAdapterPosition();
        }

        // Populate the views with the data
        // Photo
        Picasso
                .with(context)
                .load(photos.get(position).imgSource()).config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.square_placeholder)
                .fit()
                .centerCrop()
                .into(photosViewHolder.photoHolder, new Callback() {
                    @Override
                    public void onSuccess() {
                        photosViewHolder.photoHolder.setTag(R.integer.hasImageLoaded,
                                activity.getString(R.string.imageHasLoaded));
                    }

                    @Override
                    public void onError() {
                        photosViewHolder.photoHolder.setTag(R.integer.hasImageLoaded, null);
                    }
                });

        // Photo Id
        photosViewHolder.photoId
                .setText(String.valueOf(photos.get(viewHolder.getAdapterPosition()).id()));

        // Camera Initials
        photosViewHolder.cameraInitial
                .setText(photos.get(viewHolder.getAdapterPosition()).camera().name());

        // On click action
        photosViewHolder.photoHolderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToPhotoExpandedActivity =
                        new Intent(context, PhotoExpandedViewActivity.class);
                goToPhotoExpandedActivity.putExtra(ExpandedPhotosConstants.imageUrl,
                        photos.get(viewHolder.getAdapterPosition()).imgSource());

                // If the image has not yet been loaded by Picasso.
                // Open the expanded activity without shared element transition animation.
                if(photosViewHolder.photoHolder.getTag(R.integer.hasImageLoaded) == null) {
                    activity.startActivity(goToPhotoExpandedActivity);
                } else {
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(activity, view,
                                    activity.getString(R.string.expandImageTransition));

                    context.startActivity(goToPhotoExpandedActivity,
                            activityOptionsCompat.toBundle());
                }
            }
        });
    }

    /**
     * Method to add -
     * 1. Fade in effect
     * 2. Scroll up animation
     * @param view item to which the affects are to be applied.
     */
    private void setAnimation(View view) {

        // Prepare the fade in effect
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(1500);

        // Prepare the scroll effect
        TranslateAnimation scrollAnimation = new TranslateAnimation(0.0f, 0.0f, 1000.0f, 0.0f);
        scrollAnimation.setDuration(700);

        // Consolidate all the animations
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scrollAnimation);

        view.startAnimation(animationSet);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        holder.itemView.clearAnimation();
    }

    public class PhotosViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.photoHolderLayout) RelativeLayout photoHolderLayout;
        @BindView(R.id.photoHolder) ImageView photoHolder;
        @BindView(R.id.cameraInitial) TextView cameraInitial;
        @BindView(R.id.photoId) TextView photoId;

        public PhotosViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
