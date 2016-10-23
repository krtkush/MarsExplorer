package io.github.krtkush.marsexplorer.About;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.versionValue) TextView versionValue;
    @BindView(R.id.librariesDetails) RelativeLayout librariesDetails;
    @BindView(R.id.githubDetails) RelativeLayout githubDetails;
    @BindView(R.id.developerDetails) RelativeLayout developerDetails;

    private AboutActivityPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Initialise butterknife, timber and the presenter layer
        ButterKnife.bind(AboutActivity.this);
        Timber.tag(AboutActivity.this.getClass().getSimpleName());
        presenterInteractor = new AboutActivityPresenterLayer(this);

        // Setup the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenterInteractor.populateVersionNumber();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        presenterInteractor.handleOptionsSelected(item);
        return super.onOptionsItemSelected(item);
    }

    protected void setVersionNumber(String versionNumber) {
        versionValue.setText(versionNumber);
    }

    /**
     * Method to make toast on this activity
     * @param toastMessage The message to be displayed
     * @param toastDuration Duration for the toast to be visible
     */
    protected void showToast(String toastMessage, int toastDuration) {
        Toast.makeText(this, toastMessage, toastDuration).show();
    }

    @OnClick({R.id.librariesDetails, R.id.githubDetails, R.id.developerDetails})
    public void goToGithubPage(View view) {

        switch (view.getId()) {
            case R.id.librariesDetails:
                presenterInteractor.goToCreditsSection();
                break;

            case R.id.githubDetails:
                presenterInteractor.goToGithubPage();
                break;

            case R.id.developerDetails:
                presenterInteractor.goToDeveloperPage();
                break;
        }
    }
}
