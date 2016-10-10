package io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.krtkush.marsexplorer.MarsExplorerApplication;
import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.RESTClients.DataModels.PhotosJsonDataModels.Photos;
import io.github.krtkush.marsexplorer.RESTClients.DataModels.PhotosJsonDataModels.PhotosResultDM;
import io.github.krtkush.marsexplorer.RoverExplorer.RoverExplorerConstants;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 17/07/16.
 */
public class RoverExplorerPresenterLayer implements RoverExplorerPresenterInteractor {

    private RoverExplorerFragment fragment;
    private String roverName;
    private String roverSol;
    private Subscriber<PhotosResultDM> nasaMarsPhotoSubscriber;

    // Variables related to RecyclerView
    private PhotosRecyclerViewAdapter photosRecyclerViewAdapter;
    // List of all the photos and their respective details
    private List<Photos> photoList;

    private SwipeRefreshLayout swipeRefreshLayout; // TODO: This view should not be here. A method in an activity should manipulate the view.

    /**
     * Handler and Runnable to delay the photos API call by 1.5 seconds.
     */
    private Handler fetchPhotosHandler = new Handler();
    private Runnable fetchPhotosRunnable = new Runnable() {
        @Override
        public void run() {

            long photoApiRequestDelayTime = 1500;

            fetchPhotosHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestPhotosApiCall();
                }
            }, photoApiRequestDelayTime);
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
    public void prepareRecyclerViewAndAddData(RecyclerView recyclerView,
                                              final SwipeRefreshLayout swipeRefreshLayout) {

        // Number of columns to show in the GridView
        int numberOfColumns = 2;
        // Spacing between columns
        int gridItemSpacing = 13;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(fragment.getActivity(),
                numberOfColumns);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        photosRecyclerViewAdapter =
                new PhotosRecyclerViewAdapter(fragment.getActivity(), photoList);
        recyclerView.addItemDecoration(new PhotosGridItemDecoration(numberOfColumns,
                gridItemSpacing, true));
        recyclerView.setAdapter(photosRecyclerViewAdapter);

        // Define the action when user pulls down to refresh.
        this.swipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getRoverPhotos(false);
            }
        });
    }

    @Override
    public void unsubscribeRoverPhotosRequest() {

        if(nasaMarsPhotoSubscriber != null)
            nasaMarsPhotoSubscriber.unsubscribe();

        // Also, stop the handler which is responsible for the delay in API call.
        if(fetchPhotosHandler != null)
            fetchPhotosHandler.removeCallbacks(fetchPhotosRunnable);
    }

    /**
     * Method to make the API call.
     * Always call this method via getRoverPhotos(flag) method not directly.
     */
    private void requestPhotosApiCall() {

        try {
            fragment.viewsVisibilityToggle(null, false, false, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Define the observer
        Observable<PhotosResultDM> nasaMarsPhotosObservable
                = MarsExplorerApplication.getApplicationInstance()
                .getNasaMarsPhotosApiInterface()
                .getPhotosBySol(true, true, roverName, roverSol);

        // Define the subscriber
        nasaMarsPhotoSubscriber = new Subscriber<PhotosResultDM>() {
            @Override
            public void onCompleted() {
                Timber.i("Photos of %s retrieved", roverName);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);

                try {
                    if(((HttpException) ex).code() == 400 && photoList.size() == 0) {
                        fragment.viewsVisibilityToggle(fragment.getResources()
                                .getString(R.string.no_photos_message), false, true, false);
                    } else {
                        fragment.viewsVisibilityToggle(fragment.getResources()
                                        .getString(R.string.something_went_wrong_message),
                                false, true, false);
                    }
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                }
            }

            @Override
            public void onNext(PhotosResultDM photosResultDM) {
                Timber.i("%s photos fetched", photosResultDM.photos().size());

                if(photosResultDM.photos().size() != 0) {
                    photoList.clear();
                    photoList.addAll(photosResultDM.photos());

                    try {
                        fragment.viewsVisibilityToggle(null, true, false, false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } else if(photoList.size() == 0 && photosResultDM.photos().size() == 0) {
                    // There are no photos to show at all.
                    try {
                        fragment.viewsVisibilityToggle(fragment.getResources()
                                .getString(R.string.no_photos_message), false, true, false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

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
