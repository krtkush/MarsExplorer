package io.github.krtkush.marsexplorer.RoverExplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

public class RoverExplorerActivity extends AppCompatActivity {

    @BindView(R.id.photosRecyclerView) RecyclerView photosRecyclerView;

    private RoverExplorerPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_explorer);

        // Initialise butterknife, timber and the presenter layer
        ButterKnife.bind(this);
        Timber.tag(RoverExplorerActivity.this.getClass().getSimpleName());
        presenterInteractor = new RoverExplorerPresenterLayer(this);

        // Initialize the RecyclerView
        photosRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(RoverExplorerActivity.this, 2);
        presenterInteractor.prepareRecyclerViewAndAddData(photosRecyclerView, gridLayoutManager);

        // Get the rover name and its respective max SOL via the intent
        presenterInteractor.getRoverNameFromIntent();
        presenterInteractor.getRoverSolFromIntent();

        // Request for rover's photos
        presenterInteractor.getRoverPhotos();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check internet connectivity
        presenterInteractor.checkInternetConnectivity();
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenterInteractor.unsubscribeRoverPhotosRequest();
    }

    /**
     * Method to make toast on this activity
     * @param toastMessage The message to be displayed
     * @param toastDuration Duration for the toast to be visible
     */
    protected void showToast(String toastMessage, int toastDuration) {
        Toast.makeText(this, toastMessage, toastDuration).show();
    }
}
