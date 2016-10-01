package io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.krtkush.marsexplorer.PhotosJsonDataModels.Photos;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 25/08/16.
 */
public class PhotosRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Photos> photos;
    private int lastPosition;

    public PhotosRecyclerViewAdapter(Context context, List<Photos> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photos_grid_element_layout, parent, false);
        viewHolder = new PhotosViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        PhotosViewHolder photosViewHolder = (PhotosViewHolder) viewHolder;

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
                .into(photosViewHolder.photoHolder);

        // Photo Id
        photosViewHolder.photoId.setText(String.valueOf(photos.get(position).id()));

        // Camera Initials
        photosViewHolder.cameraInitial.setText(photos.get(position).camera().name());
    }

    /**
     * Method to add
     * 1. Fade in effect
     * 2. Scroll up animation
     * @param view item to which the affects are to be applied.
     */
    private void setAnimation(View view) {

        // Prepare the fade in effect
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(1500);

        // Prepare the translate effect
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 1000.0f, 0.0f);
        translateAnimation.setDuration(700);

        // Consolidate all the animations
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);

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

        @BindView(R.id.photoHolder) ImageView photoHolder;
        @BindView(R.id.cameraInitial) TextView cameraInitial;
        @BindView(R.id.photoId) TextView photoId;

        public PhotosViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.photoHolderLayout)
        public void expandPhoto() {

            Timber.i("PhotosResultDM clicked");
        }
    }
}
