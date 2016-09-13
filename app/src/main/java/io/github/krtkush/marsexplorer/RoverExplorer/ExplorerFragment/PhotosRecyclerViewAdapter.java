package io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.krtkush.marsexplorer.PicturesJsonDataModels.Photo;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 25/08/16.
 */
public class PhotosRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Photo> photos;

    public PhotosRecyclerViewAdapter(Context context, List<Photo> photos) {
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

        // Populate the views with the data
        Picasso
                .with(context)
                .load(photos.get(position).getImgSrc()).config(Bitmap.Config.RGB_565)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.square_placeholder)
                .fit()
                .centerCrop()
                .into(photosViewHolder.photoHolder);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class PhotosViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.photoHolder) ImageView photoHolder;

        public PhotosViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.photoHolderLayout)
        public void expandPhoto() {

            Timber.i("Photo clicked");
        }
    }
}
