package io.github.krtkush.marsexplorer.RoverExplorer.AboutRover;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

public class AboutRoverActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    private AboutRoverPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_rover);

        // Initialise butterknife, timber and the presenter layer
        ButterKnife.bind(AboutRoverActivity.this);
        Timber.tag(AboutRoverActivity.this.getClass().getSimpleName());
        presenterInteractor = new AboutRoverPresenterLayer(this);

        // Setup the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Method to make toast on this activity.
     * @param toastMessage The message to be displayed.
     * @param toastDuration Duration for the toast to be visible.
     */
    protected void showToast(String toastMessage, int toastDuration) {
        Toast.makeText(this, toastMessage, toastDuration).show();
    }
}
