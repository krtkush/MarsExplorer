package io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private RoverExplorerPresenterInteractor roverExplorerPresenterInteractor;
    private Unbinder unbinder;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    roverExplorerPresenterInteractor.getRoverPhotos();
                }
            }, 1500);
        }
    };

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
        roverExplorerPresenterInteractor.prepareRecyclerViewAndAddData(recyclerView);
        // roverExplorerPresenterInteractor.getRoverPhotos();

        runnable.run();

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
        roverExplorerPresenterInteractor.unsubscribeRoverPhotosRequest();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
