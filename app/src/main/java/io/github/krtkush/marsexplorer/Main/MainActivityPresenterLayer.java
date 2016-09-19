package io.github.krtkush.marsexplorer.Main;

import android.content.Intent;
import android.widget.Toast;

import io.github.krtkush.marsexplorer.GeneralConstants;
import io.github.krtkush.marsexplorer.MarsExplorerApplication;
import io.github.krtkush.marsexplorer.PicturesJsonDataModels.PhotosResultDM;
import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.RoverExplorer.RoverExplorerConstants;
import io.github.krtkush.marsexplorer.RoverExplorer.TabHostActivity.RoverExplorerTabHostActivity;
import io.github.krtkush.marsexplorer.UtilityMethods;
import io.github.krtkush.marsexplorer.WeatherJsonDataModel.MarsWeatherDM;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 16/07/16.
 */
public class MainActivityPresenterLayer implements MainActivityPresenterInteractor {

    private MainActivity mainActivityContext;
    private Subscriber<PhotosResultDM> nasaMarsPhotoSubscriber;
    private Subscriber<MarsWeatherDM> maasMarsWeatherSubscriber;

    /**
     * Variables to store the max SOL available for respective rovers.
     */
    private String curiosityMaxSol, opportunityMaxSol, spiritMaxSol;

    public MainActivityPresenterLayer(MainActivity mainActivityContext) {
        this.mainActivityContext = mainActivityContext;
    }

    @Override
    public void checkInternetConnectivity() {
        if(!UtilityMethods.isNetworkAvailable())
            mainActivityContext.showToast(mainActivityContext.getResources()
                    .getString(R.string.no_internet), Toast.LENGTH_LONG);
    }

    /**
     * Method ot get the latest weather conditions on Mars.
     */
    @Override
    public void getMarsWeather() {

        // Define the observer
        Observable<MarsWeatherDM> marsWeatherDMObservable
                = MarsExplorerApplication.getApplicationInstance()
                .getMaasWeatherApiInterface()
                .getLatestMarsWeather(true);

        // Define the subscriber
        maasMarsWeatherSubscriber = new Subscriber<MarsWeatherDM>() {
            @Override
            public void onCompleted() {
                Timber.i("Weather found");
            }

            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                mainActivityContext
                        .setMarsWeather(
                                " " + mainActivityContext.getResources()
                                        .getString(R.string.no_temperature),
                                " " + mainActivityContext.getResources()
                                        .getString(R.string.no_temperature),
                                " " + mainActivityContext.getResources()
                                        .getString(R.string.no_temperature),
                                " " + mainActivityContext.getResources()
                                        .getString(R.string.no_temperature));
            }

            @Override
            public void onNext(MarsWeatherDM marsWeatherDM) {

                // Prepare the text to be displayed
                mainActivityContext.setMarsWeather(
                        " " + marsWeatherDM.getReport().getMaxTemp() + "\u00B0C",
                        " " + marsWeatherDM.getReport().getMinTemp() + "\u00B0C",
                        " " + marsWeatherDM.getReport().getSol().toString(),
                        " " + marsWeatherDM.getReport().getPressure() + " atm");
            }
        };

        // Subscribe to the observable
        marsWeatherDMObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(maasMarsWeatherSubscriber);
    }

    /**
     * Method to get the max possible SOL for a specific rover.
     * The API is hit for SOL 1, from which the max SOL is extracted.
     * @param roverName Name of the rover for which max SOL is required.
     */
    @Override
    public void getMaxSol(final String roverName) {

        // Define the observer
        Observable<PhotosResultDM> nasaMarsPhotosObservable
                = MarsExplorerApplication.getApplicationInstance()
                .getNasaMarsPhotosApiInterface()
                .getPhotosBySol(true, true, roverName, "1", 1);

        // Define the subscriber
        nasaMarsPhotoSubscriber = new Subscriber<PhotosResultDM>() {
            @Override
            public void onCompleted() {
                Timber.i("Max SOL of %s found", roverName);
            }

            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onNext(PhotosResultDM photosResultDM) {
                //TODO: Handle no data condition

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
    public void goToRoverSection(String roverName) {

        Intent goToRoverExplorer = new Intent(mainActivityContext,
                RoverExplorerTabHostActivity.class);

        switch (roverName) {

            case GeneralConstants.Curiosity:
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverNameExtra,
                        GeneralConstants.Curiosity);
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverMaxSolExtra,
                        curiosityMaxSol);
                mainActivityContext.startActivity(goToRoverExplorer);
                break;

            case GeneralConstants.Opportunity:
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverNameExtra,
                        GeneralConstants.Opportunity);
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverMaxSolExtra,
                        opportunityMaxSol);
                mainActivityContext.startActivity(goToRoverExplorer);
                break;

            case GeneralConstants.Spirit:
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverNameExtra,
                        GeneralConstants.Spirit);
                goToRoverExplorer.putExtra(RoverExplorerConstants.roverMaxSolExtra,
                        spiritMaxSol);
                mainActivityContext.startActivity(goToRoverExplorer);
                break;
        }
    }
}
