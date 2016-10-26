package io.github.krtkush.marsexplorer.RoverExplorer.ExpandedPhoto;

/**
 * Created by kartikeykushwaha on 10/10/16.
 */

public class PhotoExpandedViewPresenterLayer implements  PhotoExpandedViewInteractor {

    private PhotoExpandedViewActivity activity;

    public PhotoExpandedViewPresenterLayer(PhotoExpandedViewActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getImageUrl() {
        setImage(activity.getIntent().getStringExtra(ExpandedPhotosConstants.imageUrl));
    }

    @Override
    public void setImage(String imageUrl) {
        activity.setImage(imageUrl);
    }
}
