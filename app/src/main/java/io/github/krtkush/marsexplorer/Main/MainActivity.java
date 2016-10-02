package io.github.krtkush.marsexplorer.Main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.krtkush.marsexplorer.GeneralConstants;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.maxMarsTemperature) TextView maxTemperatureTextView;
    @BindView(R.id.minMarsTemperature) TextView minTemperatureTextView;
    @BindView(R.id.currentSol) TextView currentSolTextView;
    @BindView(R.id.marsPressure) TextView atmosphericPressureTextView;
    @BindView(R.id.goToCuriosityButtonBackground) ImageView goToCuriosityButtonBackground;
    @BindView(R.id.goToOpportunityButtonBackground) ImageView goToOpportunityButtonBackground;
    @BindView(R.id.goToSpiritButtonBackground) ImageView goToSpiritButtonBackground;

    private MainActivityPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise butterknife, timber and the presenter layer
        ButterKnife.bind(MainActivity.this);
        Timber.tag(MainActivity.this.getClass().getSimpleName());
        presenterInteractor = new MainActivityPresenterLayer(this);

        // Send request to get max SOL for each rover
        presenterInteractor.getMaxSol(GeneralConstants.Curiosity);
        presenterInteractor.getMaxSol(GeneralConstants.Spirit);
        presenterInteractor.getMaxSol(GeneralConstants.Opportunity);

        // Set the buttons background images
        setButtonBackground(R.drawable.curiosity_full, goToCuriosityButtonBackground);
        setButtonBackground(R.drawable.oppertunity_spirit_full, goToOpportunityButtonBackground);
        setButtonBackground(R.drawable.oppertunity_spirit_full, goToSpiritButtonBackground);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check internet connectivity
        presenterInteractor.checkInternetConnectivity();

        // Request mars weather
        presenterInteractor.getMarsWeather();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenterInteractor.unsubscribeMarsWeatherRequest();
        presenterInteractor.unsubscribeMaxSolRequest();
    }

    /**
     * Method to set the temperature as provided by the report
     * @param maxMarsTemperature
     * @param minMarsTemperature
     * @param currentSol
     * @param atmosphericPressure
     */
    protected void setMarsWeather(String maxMarsTemperature,
                                  String minMarsTemperature,
                                  String currentSol,
                                  String atmosphericPressure) {
        currentSolTextView.setText(currentSol);
        maxTemperatureTextView.setText(maxMarsTemperature);
        minTemperatureTextView.setText(minMarsTemperature);
        atmosphericPressureTextView.setText(atmosphericPressure);
    }

    @OnClick({R.id.goToCuriosity, R.id.goToOpportunity, R.id.goToSpirit})
    public void goToRoverExplorer(View view) {

        switch (view.getId()) {

            case R.id.goToCuriosity:
                presenterInteractor.goToRoverSection(GeneralConstants.Curiosity, view);
                break;

            case R.id.goToOpportunity:
                presenterInteractor.goToRoverSection(GeneralConstants.Opportunity, view);
                break;

            case R.id.goToSpirit:
                presenterInteractor.goToRoverSection(GeneralConstants.Spirit, view);
                break;
        }
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
     * Set the background images of rover buttons.
     * @param drawablePath
     * @param imageView
     */
    private void setButtonBackground(int drawablePath, ImageView imageView) {

        Picasso
                .with(this)
                .load(drawablePath)
                .fit()
                .centerCrop()
                .into(imageView);
    }
}
