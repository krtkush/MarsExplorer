package io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment;

import android.os.Bundle;
import android.os.Handler;
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

    /**
     * Handler and Runnable to delay the photos API call by 1.5 seconds.
     */
    private Handler fetchPhotosHandler = new Handler();
    private Runnable fetchPhotosRunnable = new Runnable() {
        @Override
        public void run() {
            fetchPhotosHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestPhotosApiCall();
                }
            }, 1500);
        }
    };

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
    public void getRoverPhotos(boolean delayApiRequest) {

        if(delayApiRequest)
            fetchPhotosRunnable.run();
        else
            requestPhotosApiCall();
    }

    @Override
    public void prepareRecyclerViewAndAddData(RecyclerView recyclerView) {

        // Number of columns to show in the GridView
        int numberOfColumns = 2;
        // Spacing between columns
        int gridItemSpacing = 13;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(fragment.getActivity(),
                numberOfColumns);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (photosRecyclerViewAdapter.getItemViewType(position)) {

                    case 0: // PROGRESS_ITEM
                        return 2;   // Two columns

                    case 1: // PHOTO_ITEM
                        return 1;   // One column

                    default:
                        return -1;
                }
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new InfinityScrollListener(gridLayoutManager, pageIndex) {
            @Override
            public void loadMore(int newPageIndex) {
                pageIndex = newPageIndex;
                // Add progress bar and attempt to load more pics only if we have not
                // reached the max page and any previous API request is not active.
                if(!isMaxPage && !isFetchingDataFromApi) {
                    isFetchingDataFromApi = true;
                    photoList.add(null);
                    photosRecyclerViewAdapter.notifyItemInserted(photoList.size());
                    getRoverPhotos(false);
                }
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

        // Also, stop the handler which is responsible for the API call delay.
        if(fetchPhotosHandler != null)
            fetchPhotosHandler.removeCallbacks(fetchPhotosRunnable);
    }

    /**
     * Method to make the API call
     */
    private void requestPhotosApiCall() {

        // Define the observer
        Observable<PhotosResultDM> nasaMarsPhotosObservable
                = MarsExplorerApplication.getApplicationInstance()
                .getNasaMarsPhotosApiInterface()
                .getPhotosBySol(true, true, roverName, roverSol, pageIndex);

        // Define the subscriber
        nasaMarsPhotoSubscriber = new Subscriber<PhotosResultDM>() {
            @Override
            public void onCompleted() {
                Timber.i("Photos of %s retrieved", roverName);
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

                if (photoList != null && photoList.size() != 0) {
                    // Remove the progress bar
                    photoList.remove(photoList.size() - 1);
                    photosRecyclerViewAdapter.notifyItemRemoved(photoList.size());
                }

                if(photosResultDM.photos().size() != 0)
                    photoList.addAll(photosResultDM.photos());
                else
                    // Reached end of page for given SOL
                    isMaxPage = true;

                photosRecyclerViewAdapter.notifyDataSetChanged();
            }
        };

        // Subscribe to the observable
        nasaMarsPhotosObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(nasaMarsPhotoSubscriber);
    }
}
