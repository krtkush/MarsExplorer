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

    private RoverExplorerTabHostActivity context;
    private String roverName;
    private String roverSol;
    // Variable to keep track of how many SOLs have had their respective fragments added
    // to the viewpager
    private int roverSolTracker;

    public ExplorerTabHostPresenterLayer(RoverExplorerTabHostActivity context) {
        this.context = context;
    }

    @Override
    public void checkInternetConnectivity() {
        if(!UtilityMethods.isNetworkAvailable())
            context.showToast(context.getResources()
                    .getString(R.string.no_internet), Toast.LENGTH_LONG);
    }

    @Override
    public void getRoverNameFromIntent() {
        roverName =
                context.getIntent()
                        .getStringExtra(RoverExplorerConstants.roverNameExtra);
    }

    @Override
    public void getRoverSolFromIntent() {
        roverSol =
                context.getIntent()
                        .getStringExtra(RoverExplorerConstants.roverMaxSolExtra);
    }

    @Override
    public void prepareAndImplementViewPager(final ViewPager viewPager, final TabLayout tabLayout) {

        int numberOfInitialTabs = 10;

        final List<Fragment> fragmentList = new ArrayList<>();
        final List<String> solList = new ArrayList<>();
        final TabData tabData = new TabData();

        roverSolTracker = Integer.valueOf(roverSol);

        // Initiate and three fragments into the  for the last three SOLs respectively
        for(int fragmentCount = roverSolTracker;
            fragmentCount > Integer.valueOf(roverSol) - numberOfInitialTabs;
            fragmentCount--) {

            Bundle args = new Bundle();
            args.putInt(RoverExplorerConstants.roverSolTrack, roverSolTracker);

            fragmentList.add(Fragment.instantiate(context,
                    RoverExplorerFragment.class.getName(), args));
            solList.add(String.valueOf(roverSolTracker));
            tabData.setFragmentList(fragmentList);
            tabData.setSolList(solList);

            roverSolTracker--;
        }

        final ViewPagerAdapter viewPagerAdapter =
                new ViewPagerAdapter(context.getSupportFragmentManager(), tabData);
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
                if(fragmentList.size() - position <= 2 && roverSolTracker >= 0) {

                    Bundle args = new Bundle();
                    args.putInt(RoverExplorerConstants.roverSolTrack, roverSolTracker);
                    fragmentList.add(Fragment.instantiate(context,
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
