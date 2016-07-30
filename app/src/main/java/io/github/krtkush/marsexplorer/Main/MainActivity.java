package io.github.krtkush.marsexplorer.Main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.krtkush.marsexplorer.GeneralConstants;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.maxMarsTemperatureTextView) TextView maxTemperatureTextView;
    @BindView(R.id.minMarsTemperatureTextView) TextView minTemperatureTextView;
    @BindView(R.id.currentSol) TextView currentSolTextView;

    private MainActivityPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise butterknife, timber and the presenter layer
        ButterKnife.bind(MainActivity.this);
        Timber.tag(MainActivity.this.getClass().getSimpleName());
        presenterInteractor = new MainActivityPresenterLayer(this);

        // Send request to fetch Mars weather data
        presenterInteractor.getMarsWeather();

        // Send request to get max SOL for each rover
        presenterInteractor.getMaxSol(GeneralConstants.Curiosity);
        presenterInteractor.getMaxSol(GeneralConstants.Spirit);
        presenterInteractor.getMaxSol(GeneralConstants.Opportunity);
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
     * @param maxMarsTemperature max temperature of the SOL
     * @param minMarsTemperature min temperature of the SOL
     */
    protected void setMarsTemperature(String maxMarsTemperature,
                                      String minMarsTemperature,
                                      String currentSol) {

        currentSolTextView.setText(getResources().getString(R.string.main_sol) + " " + currentSol);
        maxTemperatureTextView.setText(getResources().getString(R.string.maximum_temperature)
                + " " + maxMarsTemperature + "\u00B0C");
        minTemperatureTextView.setText(getResources().getString(R.string.minimum_temperature)
                + " " + minMarsTemperature + "\u00B0C");
    }

    @OnClick(R.id.goToCuriosity)
    public void goToCuriosity(View view) {
        presenterInteractor.goToRoverSection(GeneralConstants.Curiosity);
    }
}
