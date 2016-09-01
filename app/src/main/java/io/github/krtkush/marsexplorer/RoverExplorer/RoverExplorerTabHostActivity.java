package io.github.krtkush.marsexplorer.RoverExplorer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

public class RoverExplorerTabHostActivity extends AppCompatActivity {

    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;

    private ExplorerTabHostPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_explorer);

        // Initialise butterknife, timber and the presenter layer
        ButterKnife.bind(this);
        Timber.tag(RoverExplorerTabHostActivity.this.getClass().getSimpleName());
        presenterInteractor = new ExplorerTabHostPresenterLayer(this);

        // Get the rover name and its respective max SOL via the intent
        presenterInteractor.getRoverNameFromIntent();
        presenterInteractor.getRoverSolFromIntent();

        // Prepare the tabs
        presenterInteractor.prepareViewPager(viewPager, tabLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenterInteractor.checkInternetConnectivity();
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
