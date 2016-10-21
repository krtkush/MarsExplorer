package io.github.krtkush.marsexplorer.Main;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.Toast;

import io.github.krtkush.marsexplorer.GeneralConstants;
import io.github.krtkush.marsexplorer.MarsExplorerApplication;
import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.RESTClients.DataModels.PhotosJsonDataModels.PhotosResultDM;
import io.github.krtkush.marsexplorer.RESTClients.DataModels.WeatherJsonDataModel.MarsWeatherResultDM;
import io.github.krtkush.marsexplorer.RoverExplorer.RoverExplorerConstants;
import io.github.krtkush.marsexplorer.RoverExplorer.TabHostActivity.RoverExplorerTabHostActivity;
import io.github.krtkush.marsexplorer.UtilityMethods;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 16/07/16.
 */
public class MainActivityPresenterLayer implements MainActivityPresenterInteractor {

    private MainActivity activity;
    private Subscriber<PhotosResultDM> nasaMarsPhotoSubscriber;
    private Subscriber<MarsWeatherResultDM> maasMarsWeatherSubscriber;

    /**
     * Variables to store the max SOL available for respective rovers.
     */
    private String curiosityMaxSol, opportunityMaxSol, spiritMaxSol;

    public MainActivityPresenterLayer(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void checkInternetConnectivity() {
        if(!UtilityMethods.isNetworkAvailable())
            activity.showToast(activity.getResources()
                            .getString(R.string.no_internet),
                    Toast.LENGTH_LONG);
    }

    /**
     * Method ot get the latest weather conditions on Mars.
     */
    @Override
    public void getMarsWeather() {

        // Define the observer
        Observable<MarsWeatherResultDM> marsWeatherDMObservable
                = MarsExplorerApplication.getApplicationInstance()
                .getMarsWeatherInterface()
                .getLatestMarsWeather(true);

        // Define the subscriber
        maasMarsWeatherSubscriber = new Subscriber<MarsWeatherResultDM>() {
            @Override
            public void onCompleted() {
                Timber.i("Weather found");
            }

            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                setUiIfNoWeatherData();
            }

            @Override
            public void onNext(MarsWeatherResultDM marsWeatherResultDM) {

                if(marsWeatherResultDM.weatherReportList().size() <= 0) {
                    // The API was success but, ni data was returned.
                    setUiIfNoWeatherData();
                } else {
                    // Prepare the text to be displayed.
                    activity.setMarsWeather(
                            " " + marsWeatherResultDM.weatherReportList()
                                    .get(0).maxTemp() + "\u00B0C",
                            " " + marsWeatherResultDM.weatherReportList()
                                    .get(0).minTemp() + "\u00B0C",
                            " " + marsWeatherResultDM.weatherReportList()
                                    .get(0).sol(),
                            " " + marsWeatherResultDM.weatherReportList()
                                    .get(0).pressure() + " atm");
                }
            }
        };

        // Subscribe to the observable
        marsWeatherDMObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(maasMarsWeatherSubscriber);
    }

    /**
     * Method to set the UI in case weather API fails.
     */
    private void setUiIfNoWeatherData() {
        activity
                .setMarsWeather(
                        " " + activity.getResources()
                                .getString(R.string.no_temperature),
                        " " + activity.getResources()
                                .getString(R.string.no_temperature),
                        " " + activity.getResources()
                                .getString(R.string.no_temperature),
                        " " + activity.getResources()
                                .getString(R.string.no_temperature));
    }

    /**
     * Method to get the max possible SOL for a specific rover.
     * The API is hit for SOL 0, from which the max SOL is extracted.
     * @param roverName Name of the rover for which max SOL is required.
     */
    @Override
    public void getMaxSol(final String roverName) {

        // Define the observer
        Observable<PhotosResultDM> nasaMarsPhotosObservable
                = MarsExplorerApplication.getApplicationInstance()
                .getNasaMarsPhotosApiInterface()
                .getPhotosBySol(true, true, roverName, "1");

        // Define the subscriber
        nasaMarsPhotoSubscriber = new Subscriber<PhotosResultDM>() {
            @Override
            public void onCompleted() {
                Timber.i("Max SOL of %s found", roverName);
                Timber.i("Request completed at: %s", System.currentTimeMillis());
            }

            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onNext(PhotosResultDM photosResultDM) {

                switch (roverName) {

                    case GeneralConstants.Curiosity:
                        curiosityMaxSol = photosResultDM.photos().get(0).rover()
                                .maxSol().toString();
                        break;

                    case GeneralConstants.Opportunity:
                        opportunityMaxSol = photosResultDM.photos().get(0).rover()
                                .maxSol().toString();
                        break;

                    case GeneralConstants.Spirit:
                        spiritMaxSol = photosResultDM.photos().get(0).rover()
                                .maxSol().toString();
                        break;
                }
            }
        };

        // Subscribe to the observable
        nasaMarsPhotosObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(nasaMarsPhotoSubscriber);
    }

    @Override
    public void unsubscribeMaxSolRequest() {
        if(nasaMarsPhotoSubscriber != null)
            nasaMarsPhotoSubscriber.unsubscribe();
    }

    @Override
    public void unsubscribeMarsWeatherRequest() {
        if(maasMarsWeatherSubscriber != null)
            maasMarsWeatherSubscriber.unsubscribe();
    }

    /**
     * Method to take user to the rover explore section.
     * @param roverName Rover which the user wants to explore.
     */
    @Override
    public void goToRoverSection(String roverName, View view) {

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, view, "roverImageTransition");

        Intent goToRoverExplorer = new Intent(activity,
                RoverExplorerTabHostActivity.class);

        switch (roverName) {

            case GeneralConstants.Curiosity:
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverNameExtra,
                        GeneralConstants.Curiosity);
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverMaxSolExtra,
                        curiosityMaxSol);
                break;

            case GeneralConstants.Opportunity:
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverNameExtra,
                        GeneralConstants.Opportunity);
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverMaxSolExtra,
                        opportunityMaxSol);
                break;

            case GeneralConstants.Spirit:
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverNameExtra,
                        GeneralConstants.Spirit);
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverMaxSolExtra,
                        spiritMaxSol);
                break;
        }

        activity.startActivity(goToRoverExplorer, activityOptionsCompat.toBundle());
    }
}
