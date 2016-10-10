package io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 01/09/16.
 */
public class RoverExplorerFragment extends Fragment {

    @BindView(R.id.photosRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.messageTextHolder) TextView messageTextHolder;

    private RoverExplorerPresenterInteractor roverExplorerPresenterInteractor;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.common_recyclerview_layout, container, false);

        // Initialise Butterknife, Timber and the presenter layer
        unbinder = ButterKnife.bind(this, view);
        Timber.tag(getActivity().getClass().getSimpleName());
        roverExplorerPresenterInteractor = new RoverExplorerPresenterLayer(this);

        // Get the details from the intent
        roverExplorerPresenterInteractor.getValuesFromIntent();

        // Request data for photos from API
        roverExplorerPresenterInteractor.prepareRecyclerViewAndAddData(recyclerView,
                swipeRefreshLayout);
        roverExplorerPresenterInteractor.getRoverPhotos(true);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        roverExplorerPresenterInteractor.unsubscribeRoverPhotosRequest();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    /**
     * Method to pass message for message TextView and toggle visibility.
     * @param message
     */
    protected void viewsVisibilityToggle(String message, boolean showRecyclerView,
                                         boolean showMessage, boolean showProgress)
            throws Exception {

        // Toggle visibility of the message TextView
        if(showMessage) {
            if(messageTextHolder != null) {
                messageTextHolder.setVisibility(View.VISIBLE);
                messageTextHolder.setText(message);
            }
        } else {
            if(messageTextHolder != null)
                messageTextHolder.setVisibility(View.GONE);
        }

        // Toggle the visibility of the RecyclerView
        if (showRecyclerView){
            if(recyclerView != null)
                recyclerView.setVisibility(View.VISIBLE);
        } else {
            if(recyclerView != null)
                recyclerView.setVisibility(View.GONE);
        }

        // Toggle the visibility of the progress bar
        if(showProgress) {
            toggleSwipeRefreshing(true);
        } else {
            toggleSwipeRefreshing(false);
        }
    }

    /**
     * Method to toggle the visibility of the pull down to refresh animation.
     * @param setRefreshingFlag
     */
    protected void toggleSwipeRefreshing(boolean setRefreshingFlag) {
        if(swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(setRefreshingFlag);
    }
}
