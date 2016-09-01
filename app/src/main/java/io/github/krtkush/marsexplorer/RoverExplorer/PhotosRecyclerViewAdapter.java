package io.github.krtkush.marsexplorer.RoverExplorer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.krtkush.marsexplorer.PicturesJsonDataModels.Photo;
import io.github.krtkush.marsexplorer.R;

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
                .load(photos.get(position).getImgSrc())
                .fit()
                .into(photosViewHolder.photoHolder);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class PhotosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView photoHolder;

        public PhotosViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);
            photoHolder = (ImageView) view.findViewById(R.id.photoHolder);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
