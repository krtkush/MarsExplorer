package io.github.krtkush.marsexplorer.RoverExplorer;

/**
 * Created by kartikeykushwaha on 17/07/16.
 */
public interface RoverExplorerPresenterInteractor {

    /**
     * Method to retrieve the rover name passed from the previous activity
     */
    void getRoverNameFromIntent();

    /**
     * Method to retrieve the rover's max SOL passed from the previous activity
     */
    void getRoverSolFromIntent();

    /**
     * Method to get all photos for a particular rover
     */
    void getRoverPhotos();

    void unsubscribeRoverPhotosRequest();

    /**
     * Method to get the basic details of given rover
     */
    void getRoverBasicDetails();
}
