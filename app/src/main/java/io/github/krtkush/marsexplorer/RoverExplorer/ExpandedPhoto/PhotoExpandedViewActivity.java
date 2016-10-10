package io.github.krtkush.marsexplorer.RoverExplorer.ExpandedPhoto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

public class PhotoExpandedViewActivity extends AppCompatActivity {

    @BindView(R.id.expandedPhotoHolder) ImageView expandedPhotoHolder;

    private PhotoExpandedViewInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_expanded_view);

        // Initialise butterknife, timber and the presenter layer
        ButterKnife.bind(PhotoExpandedViewActivity.this);
        Timber.tag(PhotoExpandedViewActivity.this.getClass().getSimpleName());
        presenterInteractor = new PhotoExpandedViewPresenterLayer(this);

        // Fetch the url of the image to be displayed and show that image.
        presenterInteractor.getImageUrl();
    }

    /**
     * Set the images.
     * @param imagePath
     */
    protected void setImage(String imagePath) {

        Picasso
                .with(this)
                .load(imagePath)
                .fit()
                .centerCrop()
                .into(expandedPhotoHolder);
    }
}
