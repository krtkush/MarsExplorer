package io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    // Grid item type
    private final int PHOTO_ITEM = 1;
    private final int PROGRESS_ITEM = 0;

    public PhotosRecyclerViewAdapter(Context context, List<Photos> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {

            case PHOTO_ITEM:
                view = inflater.inflate(R.layout.photos_grid_element_layout, parent, false);
                viewHolder = new PhotosViewHolder(view);
                break;

            case PROGRESS_ITEM:
                view = inflater.inflate(R.layout.pagination_progress_bar, parent, false);
                viewHolder = new ProgressViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof PhotosViewHolder) {

            // Photo view

            PhotosViewHolder photosViewHolder = (PhotosViewHolder) viewHolder;

            // Animate the view
            if(viewHolder.getAdapterPosition() > lastPosition) {
                Animation scrollUpAnimation =
                        AnimationUtils.loadAnimation(context, R.anim.scroll_up_animation);
                viewHolder.itemView.startAnimation(scrollUpAnimation);
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
        } else {

            // Progress bar

            ProgressViewHolder progressViewHolder = (ProgressViewHolder) viewHolder;

            //Show the progress bar
            progressViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return photos.get(position) != null ? PHOTO_ITEM: PROGRESS_ITEM;
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

    /**
     * View holder for the photo item
     */
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

    /**
     * View holder for the progress bar
     */
    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progressBar) ProgressBar progressBar;

        public ProgressViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
