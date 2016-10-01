package io.github.krtkush.marsexplorer.RoverExplorer.TabHostActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RoverExplorerTabHostActivity extends AppCompatActivity {

    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsibleImage) ImageView collapsibleImage;

    private ExplorerTabHostPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_explorer);

        // Initialise Butterknife, Timber and the presenter layer.
        ButterKnife.bind(this);
        Timber.tag(RoverExplorerTabHostActivity.this.getClass().getSimpleName());
        presenterInteractor = new ExplorerTabHostPresenterLayer(this);

        // Setup the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get all the data via the intent.
        presenterInteractor.getValuesFromIntent();
        // Set the basic UI values
        presenterInteractor.setViewsValue();
        // Prepare the tabs.
        presenterInteractor.prepareAndImplementViewPager(viewPager, tabLayout);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterInteractor.checkInternetConnectivity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenterInteractor.unsubscribeMaxSolRequest();
    }

    /**
     * Method to make toast on this activity
     * @param toastMessage The message to be displayed
     * @param toastDuration Duration for the toast to be visible
     */
    protected void showToast(String toastMessage, int toastDuration) {
        Toast.makeText(this, toastMessage, toastDuration).show();
    }

    /**
     * Method to set the toolbar title (usually the name of the rover)
     * @param title
     */
    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     * Method to set the image of the collapsible toolbar
     * @param drawablePath
     */
    protected void setCollapsibleToolbarImage(int drawablePath) {

        //collapsibleImage.setImageResource(drawablePath);
        //collapsibleImage.setImageDrawable(ActivityCompat.getDrawable(this, drawablePath));

        ActivityCompat.postponeEnterTransition(this);
        Picasso.with(this.getApplicationContext())
                .load(drawablePath)
                .noFade()
                .fit()
                .into(collapsibleImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        ActivityCompat
                                .startPostponedEnterTransition(RoverExplorerTabHostActivity.this);
                    }

                    @Override
                    public void onError() {
                        ActivityCompat
                                .startPostponedEnterTransition(RoverExplorerTabHostActivity.this);
                    }
                });
    }
}
