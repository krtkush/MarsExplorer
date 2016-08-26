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
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private PhotosRecyclerViewAdapter photosRecyclerViewAdapter;

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
                photoSearchResultDM.getPhotos().get(0).getImgSrc();
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
        this.recyclerView = recyclerView;
        this.recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(roverExplorerActivityContext, 2);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        photosRecyclerViewAdapter =
                new PhotosRecyclerViewAdapter(roverExplorerActivityContext, photoList);
        recyclerView.setAdapter(photosRecyclerViewAdapter);
    }

    @Override
    public void unsubscribeRoverPhotosRequest() {

        if(nasaMarsPhotoSubscriber != null)
            nasaMarsPhotoSubscriber.unsubscribe();
    }
}
