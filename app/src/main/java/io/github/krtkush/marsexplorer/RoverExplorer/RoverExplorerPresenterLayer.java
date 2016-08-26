package io.github.krtkush.marsexplorer.RoverExplorer;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.krtkush.marsexplorer.MarsExplorerApplication;
import io.github.krtkush.marsexplorer.PicturesJsonDataModels.Photo;
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

    // Variables related to RecyclerView
    private PhotosRecyclerViewAdapter photosRecyclerViewAdapter;
    // List of all the photos and their respective details
    private List<Photo> photoList;

    public RoverExplorerPresenterLayer(RoverExplorerActivity roverExplorerActivityContext) {
        this.roverExplorerActivityContext = roverExplorerActivityContext;
        photoList = new ArrayList<>();
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
                photoList.addAll(photoSearchResultDM.getPhotos());
                photosRecyclerViewAdapter.notifyDataSetChanged();
            }
        };

        // Subscribe to the observable
        nasaMarsPhotosObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(nasaMarsPhotoSubscriber);
    }

    @Override
    public void prepareRecyclerViewAndAddData(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(roverExplorerActivityContext, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        photosRecyclerViewAdapter =
                new PhotosRecyclerViewAdapter(roverExplorerActivityContext, photoList);
        recyclerView.addItemDecoration(new PhotosGridItemDecoration(2, 50, true));
        recyclerView.setAdapter(photosRecyclerViewAdapter);
    }

    @Override
    public void unsubscribeRoverPhotosRequest() {

        if(nasaMarsPhotoSubscriber != null)
            nasaMarsPhotoSubscriber.unsubscribe();
    }
}
