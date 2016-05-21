package io.github.krtkush.marsexplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.krtkush.marsexplorer.JsonDataModels.PhotoSearchResultDM;
import io.github.krtkush.marsexplorer.RESTClient.Constants;
import io.github.krtkush.marsexplorer.RESTClient.NASARestApiClient;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    //Interface to the REST client
    private NASARestApiClient.NASAMarsPhotosApiInterface nasaMarsPhotosApiInterface;
    private Subscriber<PhotoSearchResultDM> nasaMarsPhotoSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.tag(MainActivity.this.getClass().getSimpleName());

        getMaxSol(Constants.Curiosity);
        getMaxSol(Constants.Opportunity);
        getMaxSol(Constants.Spirit);
    }

    /**
     * Method to get the max possible SOL for a specified rover.
     * The API is hit for SOL 1, from which the max SOL is extracted.
     * @param roverName Name of the rover for which max SOL is needed.
     */
    private void getMaxSol(final String roverName) {

        nasaMarsPhotosApiInterface = NASARestApiClient.getNasaMarsPhotosApiInterface();

        //Define the observer
        Observable<PhotoSearchResultDM> nasaMarsPhotosObservable
                = nasaMarsPhotosApiInterface.getPhotosBySol(roverName, "1", null);

        //Define the subscriber
        nasaMarsPhotoSubscriber = new Subscriber<PhotoSearchResultDM>() {
            @Override
            public void onCompleted() {
                Timber.d("Max SOL of %s found", roverName);
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

        //Subscribe to the observable
        nasaMarsPhotosObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(nasaMarsPhotoSubscriber);
    }

    @Override
    protected void onStop() {
        super.onStop();

        nasaMarsPhotoSubscriber.unsubscribe();
    }
}
