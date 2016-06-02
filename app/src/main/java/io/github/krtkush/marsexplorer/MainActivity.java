package io.github.krtkush.marsexplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.krtkush.marsexplorer.JsonDataModels.PhotoSearchResultDM;
import io.github.krtkush.marsexplorer.RESTClient.RestClientConstants;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private Subscriber<PhotoSearchResultDM> nasaMarsPhotoSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Timber.tag(MainActivity.this.getClass().getSimpleName());
    }

    @OnClick(R.id.sendRequest)
    public void sendNetworkRequest() {

        getMaxSol(GeneralConstants.Curiosity);

       /* getMaxSol(GeneralConstants.Opportunity);
        getMaxSol(GeneralConstants.Spirit);*/
    }

    /**
     * Method to get the max possible SOL for a specified rover.
     * The API is hit for SOL 1, from which the max SOL is extracted.
     * @param roverName Name of the rover for which max SOL is needed.
     */
    private void getMaxSol(final String roverName) {

        //Define the observer
        Observable<PhotoSearchResultDM> nasaMarsPhotosObservable
                = MarsExplorer.getApplicationInstance()
                .getNasaMarsPhotosApiInterface()
                .getPhotosBySol(true, true, roverName, "1", null);

        //Define the subscriber
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

        //Subscribe to the observable
        nasaMarsPhotosObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(nasaMarsPhotoSubscriber);
    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            nasaMarsPhotoSubscriber.unsubscribe();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
