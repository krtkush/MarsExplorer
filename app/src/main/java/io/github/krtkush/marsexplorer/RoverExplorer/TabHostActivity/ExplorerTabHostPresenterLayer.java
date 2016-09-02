package io.github.krtkush.marsexplorer.RoverExplorer.TabHostActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.RoverExplorer.ExplorerFragment.RoverExplorerFragment;
import io.github.krtkush.marsexplorer.RoverExplorer.RoverExplorerConstants;
import io.github.krtkush.marsexplorer.UtilityMethods;

/**
 * Created by kartikeykushwaha on 01/09/16.
 */
public class ExplorerTabHostPresenterLayer implements ExplorerTabHostPresenterInteractor {

    private RoverExplorerTabHostActivity activity;
    private String roverName;
    private String roverSol;
    // Variable to keep track of how many SOLs have had their respective fragments have been added
    // to the viewpager
    private int roverSolTracker;

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
    public void getRoverNameFromIntent() {
        roverName =
                activity.getIntent()
                        .getStringExtra(RoverExplorerConstants.roverNameExtra);
    }

    @Override
    public void getRoverSolFromIntent() {
        roverSol =
                activity.getIntent()
                        .getStringExtra(RoverExplorerConstants.roverMaxSolExtra);
    }

    @Override
    public void prepareAndImplementViewPager(final ViewPager viewPager, final TabLayout tabLayout) {

        final int numberOfInitialTabs = 10;
        final int numberOfTabsLeftAfterWhichToAdd = 2;

        final List<Fragment> fragmentList = new ArrayList<>();
        final List<String> solList = new ArrayList<>();
        final TabData tabData = new TabData();

        roverSolTracker = Integer.valueOf(roverSol);

        // Initiate and three fragments into the  for the last three SOLs respectively
        for(int fragmentCount = roverSolTracker;
            fragmentCount > Integer.valueOf(roverSol) - numberOfInitialTabs;
            fragmentCount--) {

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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // Check if the user has reached the second last or last tab.
                // If he/ she has and the SOL is not below 0, add another tab
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

                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
