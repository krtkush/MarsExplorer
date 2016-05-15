package io.github.krtkush.marsexplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.github.krtkush.marsexplorer.JsonDataModels.PhotoSearchResultDM;
import io.github.krtkush.marsexplorer.RESTClient.Constants;
import io.github.krtkush.marsexplorer.RESTClient.NASARestApiClient;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    //Interface to the REST client
    private NASARestApiClient.NASAMarsPhotosApiInterface nasaMarsPhotosApiInterface;
    private Subscriber<PhotoSearchResultDM> nasaMarsPhotoSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nasaMarsPhotosApiInterface = NASARestApiClient.getNasaMarsPhotosApiInterface();

        Observable<PhotoSearchResultDM> nasaMarsPhotosObservable
                = nasaMarsPhotosApiInterface.getPhotosBySol(Constants.Curiosity, "1", null);

        nasaMarsPhotoSubscriber = new Subscriber<PhotoSearchResultDM>() {
            @Override
            public void onCompleted() {
                Log.i("TEST", "SUCCESSFUL");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(PhotoSearchResultDM photoSearchResultDM) {
                Log.i("TEST", "NEXT");
            }
        };

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
