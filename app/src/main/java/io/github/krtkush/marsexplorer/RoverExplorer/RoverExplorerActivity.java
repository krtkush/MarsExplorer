package io.github.krtkush.marsexplorer.RoverExplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

public class RoverExplorerActivity extends AppCompatActivity {

    private RoverExplorerPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_explorer);

        // Initialise butterknife, timber and the presenter layer
        ButterKnife.bind(this);
        Timber.tag(RoverExplorerActivity.this.getClass().getSimpleName());
        presenterInteractor = new RoverExplorerPresenterLayer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get the rover name and its respective max SOL via the intent
        presenterInteractor.getRoverNameFromIntent();
        presenterInteractor.getRoverSolFromIntent();

        // Request for rover's photos
        presenterInteractor.getRoverPhotos();
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenterInteractor.unsubscribeRoverPhotosRequest();
    }
}
