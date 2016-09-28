package io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

/**
 * Created by kartikeykushwaha on 17/07/16.
 */
public interface RoverExplorerPresenterInteractor {

    void getValuesFromIntent();

    /**
     * Method to get all photos for a particular rover
     * @param delayRequest flag to let the method know whether to delay API request
     *                     by few seconds or not. Delay is needed when the
     *                     user is swiping tabs very quickly; helping reduce RAM usage and
     *                     network calls.
     */
    void getRoverPhotos(boolean delayRequest);

    void unsubscribeRoverPhotosRequest();

    /**
     * Method to initialize the RecyclerView and set the data via the adapter.
     */
    void prepareRecyclerViewAndAddData(RecyclerView recyclerView,
                                       SwipeRefreshLayout swipeRefreshLayout);
}
