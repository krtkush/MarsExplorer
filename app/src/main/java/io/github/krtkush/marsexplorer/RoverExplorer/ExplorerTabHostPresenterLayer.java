package io.github.krtkush.marsexplorer.RoverExplorer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.UtilityMethods;

/**
 * Created by kartikeykushwaha on 01/09/16.
 */
public class ExplorerTabHostPresenterLayer implements ExplorerTabHostPresenterInteractor {

    private RoverExplorerTabHostActivity context;
    private String roverName;
    private String roverSol;
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
    public void getRoverBasicDetails() {

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
    public void prepareViewPager(final ViewPager viewPager, TabLayout tabLayout) {

        List<Fragment> fragmentCollection = new ArrayList<>();
        roverSolTracker = Integer.valueOf(roverSol);

        // Initiate three fragments for the last three SOLs respectively
        for(int fragmentCount = Integer.valueOf(roverSol);
            fragmentCount > Integer.valueOf(roverSol) - 3;
            fragmentCount--) {

            Bundle args = new Bundle();
            args.putInt("sol", roverSolTracker);

            fragmentCollection.add(Fragment.instantiate(context,
                    RoverExplorerFragment.class.getName(), args));

            roverSolTracker--;
        }

        ViewPagerAdapter viewPagerAdapter =
                new ViewPagerAdapter(context.getSupportFragmentManager(), fragmentCollection);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
