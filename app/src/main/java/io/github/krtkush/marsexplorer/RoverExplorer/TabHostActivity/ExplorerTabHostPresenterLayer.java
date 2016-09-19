package io.github.krtkush.marsexplorer.RoverExplorer.TabHostActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.krtkush.marsexplorer.GeneralConstants;
import io.github.krtkush.marsexplorer.MarsExplorerApplication;
import io.github.krtkush.marsexplorer.PicturesJsonDataModels.PhotosResultDM;
import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment.RoverExplorerFragment;
import io.github.krtkush.marsexplorer.RoverExplorer.RoverExplorerConstants;
import io.github.krtkush.marsexplorer.UtilityMethods;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 01/09/16.
 */
public class ExplorerTabHostPresenterLayer implements ExplorerTabHostPresenterInteractor {

    private RoverExplorerTabHostActivity activity;
    private String roverName;
    private String roverSol;
    // Variable to keep track of how many SOLs have had their respective tabs added to the viewpager.
    private int roverSolTracker;

    // Viewpager and TabLayout instance. Only used and initiated if maxSol is not passed
    // from previous activity.
    private ViewPager viewPager = null;
    private TabLayout tabLayout = null;

    private Subscriber<PhotosResultDM> nasaMarsPhotoSubscriber;

    public ExplorerTabHostPresenterLayer(RoverExplorerTabHostActivity activity) {
        this.activity = activity;
    }

    @Override
    public void checkInternetConnectivity() {
        if(!UtilityMethods.isNetworkAvailable())
            activity.showToast(activity.getResources()
                    .getString(R.string.no_internet), Toast.LENGTH_LONG);
    }

    @Override
    public void getValuesFromIntent() {
        roverName = activity.getIntent()
                .getStringExtra(RoverExplorerConstants.roverNameExtra);

        roverSol = activity.getIntent()
                .getStringExtra(RoverExplorerConstants.roverMaxSolExtra);
    }

    @Override
    public void setViewsValue() {

        // Set the toolbar title
        activity.setToolbarTitle(roverName);

        // Set the rover image
        switch (roverName) {

            case GeneralConstants.Curiosity:
                activity.setCollapsibleToolbarImage(R.drawable.curiosity_full);
                break;

            case GeneralConstants.Opportunity:
                activity.setCollapsibleToolbarImage(R.drawable.oppertunity_spirit_full);
                break;

            case GeneralConstants.Spirit:
                activity.setCollapsibleToolbarImage(R.drawable.oppertunity_spirit_full);
                break;
        }
    }

    @Override
    public void prepareAndImplementViewPager(final ViewPager viewPager, final TabLayout tabLayout) {

        final int numberOfInitialTabs = 10;
        final int numberOfTabsLeftAfterWhichToAdd = 2;
        final int offScreenPageLimit = 1;

        final List<Fragment> fragmentList = new ArrayList<>();
        final List<String> solList = new ArrayList<>();
        final TabData tabData = new TabData();

        if(roverSol == null || roverSol.isEmpty()) {
            getMaxSol(roverName);
            this.viewPager = viewPager;
            this.tabLayout = tabLayout;
        } else {
            roverSolTracker = Integer.valueOf(roverSol);

            // Initiate and three fragments for the last three SOLs respectively.
            for(int fragmentCount = roverSolTracker;
                fragmentCount > Integer.valueOf(roverSol) - numberOfInitialTabs;
                fragmentCount--) {

                // Arguments to be sent to the fragment
                Bundle args = new Bundle();
                args.putInt(RoverExplorerConstants.roverSolTrackExtra, roverSolTracker);
                args.putString(RoverExplorerConstants.roverNameExtra, roverName);

                fragmentList.add(Fragment.instantiate(activity,
                        RoverExplorerFragment.class.getName(), args));
                solList.add(String.valueOf(roverSolTracker));
                tabData.setFragmentList(fragmentList);
                tabData.setSolList(solList);

                roverSolTracker--;
            }

            final ViewPagerAdapter viewPagerAdapter =
                    new ViewPagerAdapter(activity.getSupportFragmentManager(), tabData);
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setOffscreenPageLimit(offScreenPageLimit);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    // Check if the user has reached the second last or last tab.
                    // If he/ she has and the SOL is not below 0, add another tab.
                    if(fragmentList.size() - position <= numberOfTabsLeftAfterWhichToAdd
                            && roverSolTracker >= 0) {

                        Bundle args = new Bundle();
                        args.putInt(RoverExplorerConstants.roverSolTrackExtra, roverSolTracker);
                        args.putString(RoverExplorerConstants.roverNameExtra, roverName);
                        fragmentList.add(Fragment.instantiate(activity,
                                RoverExplorerFragment.class.getName(), args));
                        solList.add(String.valueOf(roverSolTracker));
                        tabData.setFragmentList(fragmentList);
                        tabData.setSolList(solList);

                        viewPagerAdapter.notifyDataSetChanged();
                        roverSolTracker--;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    /**
     * Method to get the max SOL of the selected rover.
     * This request is sent only if the previous fails to send maxSol.
     * @param roverName
     */
    public void getMaxSol(final String roverName) {

        // Define the observer
        Observable<PhotosResultDM> nasaMarsPhotosObservable
                = MarsExplorerApplication.getApplicationInstance()
                .getNasaMarsPhotosApiInterface()
                .getPhotosBySol(true, true, roverName, "1", 1);

        // Define the subscriber
        nasaMarsPhotoSubscriber = new Subscriber<PhotosResultDM>() {
            @Override
            public void onCompleted() {
                Timber.i("Max SOL of %s found", roverName);
            }

            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onNext(PhotosResultDM photosResultDM) {
                //TODO: Handle no data condition

                roverSol = photosResultDM.photos().get(0).rover().maxSol().toString();
                prepareAndImplementViewPager(viewPager, tabLayout);
            }
        };

        // Subscribe to the observable
        nasaMarsPhotosObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(nasaMarsPhotoSubscriber);
    }

    @Override
    public void unsubscribeMaxSolRequest() {
        if(nasaMarsPhotoSubscriber != null)
            nasaMarsPhotoSubscriber.unsubscribe();
    }
}
