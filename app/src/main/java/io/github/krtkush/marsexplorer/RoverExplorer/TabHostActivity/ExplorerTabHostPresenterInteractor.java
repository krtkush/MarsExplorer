package io.github.krtkush.marsexplorer.RoverExplorer.TabHostActivity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

/**
 * Created by kartikeykushwaha on 01/09/16.
 */
public interface ExplorerTabHostPresenterInteractor {

    /**
     * Method to initialize the view pager
     * @param viewPager
     */
    void prepareAndImplementViewPager(ViewPager viewPager, TabLayout tabLayout);

    /**
     * Method to retrieve all the data sent from the previous activity via intent
     */
    void getValuesFromIntent();

    /**
     * Method to check if the device is connected to the internet or not and show a warning toast
     * in case of latter.
     */
    void checkInternetConnectivity();

    /**
     * Method to set values, sources etc to all the UI elements
     */
    void setViewsValue();
}
