package io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.krtkush.marsexplorer.InfinityScrollListener;
import io.github.krtkush.marsexplorer.MarsExplorerApplication;
import io.github.krtkush.marsexplorer.PhotosJsonDataModels.Photos;
import io.github.krtkush.marsexplorer.PhotosJsonDataModels.PhotosResultDM;
import io.github.krtkush.marsexplorer.RoverExplorer.RoverExplorerConstants;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 17/07/16.
 */
public class RoverExplorerPresenterLayer implements RoverExplorerPresenterInteractor {

    private Fragment fragment;
    private String roverName;
    private String roverSol;
    private Subscriber<PhotosResultDM> nasaMarsPhotoSubscriber;

    // The index number of the page to load
    private int pageIndex = 1;
    // Flag to indicate if an API request is in process or not
    private boolean isFetchingDataFromApi;
    // Flag to indicate if the last page of given SOL has been hit or not
    private boolean isMaxPage = false;

    // Variables related to RecyclerView
    private PhotosRecyclerViewAdapter photosRecyclerViewAdapter;
    // List of all the photos and their respective details
    private List<Photos> photoList;

    public RoverExplorerPresenterLayer(RoverExplorerFragment fragment) {
        this.fragment = fragment;
        photoList = new ArrayList<>();
    }

    @Override
    public void getValuesFromIntent() {

        Bundle bundleArgs = fragment.getArguments();
        roverSol = String.valueOf(bundleArgs.getInt(RoverExplorerConstants.roverSolTrackExtra));
        roverName = bundleArgs.getString(RoverExplorerConstants.roverNameExtra);

        Timber.i("Sol: %s", roverSol);
        Timber.i("Rover Name: %s", roverName);
    }

    @Override
    public void getRoverPhotos() {

        // Define the observer
        Observable<PhotosResultDM> nasaMarsPhotosObservable
                = MarsExplorerApplication.getApplicationInstance()
                .getNasaMarsPhotosApiInterface()
                .getPhotosBySol(true, true, roverName, roverSol, pageIndex);

        // Define the subscriber
        nasaMarsPhotoSubscriber = new Subscriber<PhotosResultDM>() {
            @Override
            public void onCompleted() {
                Timber.i("PhotosResultDM of %s retrieved", roverName);
                Timber.i("Request completed at %s", System.currentTimeMillis());
                isFetchingDataFromApi = false;
            }

            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                isFetchingDataFromApi = false;
            }

            @Override
            public void onNext(PhotosResultDM photosResultDM) {
                //TODO: Handle no data condition
                Timber.i("%s photos fetched", photosResultDM.photos().size());

                if(photosResultDM.photos().size() != 0)
                    photoList.addAll(photosResultDM.photos());
                else
                    // Reached end of page for given SOL
                    isMaxPage = true;

                photosRecyclerViewAdapter.notifyDataSetChanged();
            }
        };

        // Subscribe to the observable
        isFetchingDataFromApi = true;
        nasaMarsPhotosObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(nasaMarsPhotoSubscriber);
    }

    @Override
    public void prepareRecyclerViewAndAddData(RecyclerView recyclerView) {

        // Number of columns to show in the GridView
        int numberOfColumns = 2;
        int gridItemSpacing = 15;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(fragment.getActivity(),
                numberOfColumns);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new InfinityScrollListener(gridLayoutManager, pageIndex) {
            @Override
            public void loadMore(int newPageIndex) {
                pageIndex = newPageIndex;
                // Attempt to load more pics only if we have not reached the max page
                // and any previous API request is not active
                if(!isMaxPage && !isFetchingDataFromApi)
                    getRoverPhotos();
            }
        });
        photosRecyclerViewAdapter =
                new PhotosRecyclerViewAdapter(fragment.getActivity(), photoList);
        recyclerView.addItemDecoration(new PhotosGridItemDecoration(numberOfColumns,
                gridItemSpacing, true));
        recyclerView.setAdapter(photosRecyclerViewAdapter);
    }

    @Override
    public void unsubscribeRoverPhotosRequest() {

        if(nasaMarsPhotoSubscriber != null)
            nasaMarsPhotoSubscriber.unsubscribe();
    }
}
