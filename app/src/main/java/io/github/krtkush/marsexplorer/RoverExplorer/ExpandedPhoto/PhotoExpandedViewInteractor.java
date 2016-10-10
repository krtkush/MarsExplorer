package io.github.krtkush.marsexplorer.RoverExplorer.ExpandedPhoto;

/**
 * Created by kartikeykushwaha on 10/10/16.
 */

public interface PhotoExpandedViewInteractor {

    /**
     * Method to retrieve the image URL from the previous activity.
     * @param imageUrl
     */
    void getImageUrl(String imageUrl);

    /**
     * Method to set the image into the ImageView.
     * @param imageUrl
     */
    void setImage(String imageUrl);
}
