package io.github.krtkush.marsexplorer;

import io.github.krtkush.marsexplorer.PicturesJsonDataModels.PhotoSearchResultDM;
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
    private Subscriber<PhotoSearchResultDM> nasaMarsPhotoSubscriber;
    private Subscriber<MarsWeatherDM> maasMarsWeatherSubscriber;

    public MainActivityPresenterLayer(MainActivity mainActivityContext) {
        this.mainActivityContext = mainActivityContext;
    }

    /**
     * Method ot get the latest weather conditions on Mars.
     */
    @Override
    public void getMarsWeather() {

        // Define the observer
        Observable<MarsWeatherDM> marsWeatherDMObservable
                = MarsExplorer.getApplicationInstance()
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
            }

            @Override
            public void onNext(MarsWeatherDM marsWeatherDM) {
                marsWeatherDM.getReport().getMaxTemp();
            }
        };

        // Subscribe to the observable
        marsWeatherDMObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(maasMarsWeatherSubscriber);
    }


    /**
     * Method to get the max possible SOL for a specified rover.
     * The API is hit for SOL 1, from which the max SOL is extracted.
     * @param roverName Name of the rover for which max SOL is required.
     */
    @Override
    public void getMaxSol(final String roverName) {

        // Define the observer
        Observable<PhotoSearchResultDM> nasaMarsPhotosObservable
                = MarsExplorer.getApplicationInstance()
                .getNasaMarsPhotosApiInterface()
                .getPhotosBySol(true, true, roverName, "1", null);

        // Define the subscriber
        nasaMarsPhotoSubscriber = new Subscriber<PhotoSearchResultDM>() {
            @Override
            public void onCompleted() {
                Timber.i("Max SOL of %s found", roverName);
            }

            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onNext(PhotoSearchResultDM photoSearchResultDM) {
                photoSearchResultDM.getPhotos().get(0).getRover().getMaxSol();
            }
        };

        // Subscribe to the observable
        nasaMarsPhotosObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(nasaMarsPhotoSubscriber);
    }

    @Override
    public void unsubscribeMaxSolRequest() throws NullPointerException {

        try {
            nasaMarsPhotoSubscriber.unsubscribe();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void unsubscribeMarsWeatherRequest() {

        try {
            maasMarsWeatherSubscriber.unsubscribe();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
