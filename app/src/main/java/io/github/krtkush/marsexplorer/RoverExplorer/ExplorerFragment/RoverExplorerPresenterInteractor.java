package io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment;

import android.support.v7.widget.RecyclerView;

/**
 * Created by kartikeykushwaha on 17/07/16.
 */
public interface RoverExplorerPresenterInteractor {

    void getValuesFromIntent();

    /**
     * Method to get all photos for a particular rover
     */
    void getRoverPhotos();

    void unsubscribeRoverPhotosRequest();

    /**
     * Method to initialize the RecyclerView and set the data via the adapter
     */
    void prepareRecyclerViewAndAddData(RecyclerView recyclerView);
}
