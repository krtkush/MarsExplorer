package io.github.krtkush.marsexplorer.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.maxMarsTemperatureTextView) TextView maxTemperatureTextView;
    @BindView(R.id.minMarsTemperatureTextView) TextView minTemperatureTextView;

    private MainActivityPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        Timber.tag(MainActivity.this.getClass().getSimpleName());

        presenterInteractor = new MainActivityPresenterLayer(this);

        // Send request to fetch Mars weather data
        presenterInteractor.getMarsWeather();
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
                                      String minMarsTemperature) {

        maxTemperatureTextView.setText("Maximum Temperature: " + maxMarsTemperature);
        minTemperatureTextView.setText("Minimum Temperature: " + minMarsTemperature);
    }
}