package io.github.krtkush.marsexplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private MainActivityPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.tag(MainActivity.this.getClass().getSimpleName());

        presenterInteractor = new MainActivityPresenterLayer(this);
    }

    @OnClick(R.id.sendRequest)
    public void sendNetworkRequest() {

        presenterInteractor.getMarsWeather();
        presenterInteractor.getMaxSol(GeneralConstants.Curiosity);
        presenterInteractor.getMaxSol(GeneralConstants.Opportunity);
        presenterInteractor.getMaxSol(GeneralConstants.Spirit);
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenterInteractor.unsubscribeMarsWeatherRequest();
        presenterInteractor.unsubscribeMaxSolRequest();
    }
}
