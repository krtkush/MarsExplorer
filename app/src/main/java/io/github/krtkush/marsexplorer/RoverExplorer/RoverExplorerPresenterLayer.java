package io.github.krtkush.marsexplorer.RoverExplorer;

import io.github.krtkush.marsexplorer.MarsExplorerApplication;
import io.github.krtkush.marsexplorer.PicturesJsonDataModels.PhotoSearchResultDM;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 17/07/16.
 */
public class RoverExplorerPresenterLayer implements RoverExplorerPresenterInteractor {

    private RoverExplorerActivity roverExplorerActivityContext;
    private String roverName;
    private String roverSol;
    // The page number for a particular SOL
    private int requiredPage;
    private Subscriber<PhotoSearchResultDM> nasaMarsPhotoSubscriber;

    public RoverExplorerPresenterLayer(RoverExplorerActivity roverExplorerActivityContext) {
        this.roverExplorerActivityContext = roverExplorerActivityContext;
        requiredPage = 1;
    }

    @Override
    public void getRoverNameFromIntent() {
        roverName =
                roverExplorerActivityContext.getIntent()
                        .getStringExtra(RoverExplorerConstants.roverNameExtra);
    }

    @Override
    public void getRoverSolFromIntent() {
        roverSol =
                roverExplorerActivityContext.getIntent()
                        .getStringExtra(RoverExplorerConstants.roverMaxSolExtra);
    }

    @Override
    public void getRoverBasicDetails() {

    }

    @Override
    public void getRoverPhotos() {

        // Define the observer
        Observable<PhotoSearchResultDM> nasaMarsPhotosObservable
                = MarsExplorerApplication.getApplicationInstance()
                .getNasaMarsPhotosApiInterface()
                .getPhotosBySol(true, true, roverName, roverSol, requiredPage);

        // Define the subscriber
        nasaMarsPhotoSubscriber = new Subscriber<PhotoSearchResultDM>() {
            @Override
            public void onCompleted() {
                Timber.i("Photos of %s retrieved", roverName);
            }

            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onNext(PhotoSearchResultDM photoSearchResultDM) {
                //TODO: Handle no data condition
                Timber.i("%s photos fetched", photoSearchResultDM.getPhotos().size());
                photoSearchResultDM.getPhotos().get(0).getImgSrc();
            }
        };

        // Subscribe to the observable
        nasaMarsPhotosObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(nasaMarsPhotoSubscriber);
    }

    @Override
    public void unsubscribeRoverPhotosRequest() {

        if(nasaMarsPhotoSubscriber != null)
            nasaMarsPhotoSubscriber.unsubscribe();
    }
}
