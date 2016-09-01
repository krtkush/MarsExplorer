package io.github.krtkush.marsexplorer.RoverExplorer;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.UtilityMethods;

/**
 * Created by kartikeykushwaha on 01/09/16.
 */
public class ExplorerTabHostPresenterLayer implements ExplorerTabHostPresenterInteractor {

    private RoverExplorerTabHostActivity context;
    private String roverName;
    private String roverSol;

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
    public void prepareViewPager(ViewPager viewPager, TabLayout tabLayout) {

        ViewPagerAdapter viewPagerAdapter =
                new ViewPagerAdapter(context.getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
