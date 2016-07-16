package io.github.krtkush.marsexplorer.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.marsTemperatureTextView) TextView temperatureTextView;

    private MainActivityPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        Timber.tag(MainActivity.this.getClass().getSimpleName());

        presenterInteractor = new MainActivityPresenterLayer(this);

        // Send request to fetch mars weather data
        presenterInteractor.getMarsWeather();
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenterInteractor.unsubscribeMarsWeatherRequest();
        presenterInteractor.unsubscribeMaxSolRequest();
    }

    protected void setMarsTemperature(String marsTemperature) {

        temperatureTextView.setText(marsTemperature);
    }
}
