package io.github.krtkush.marsexplorer.RoverExplorer.TabHostActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by kartikeykushwaha on 01/09/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private TabData tabData;

    public ViewPagerAdapter(FragmentManager fragmentManager,
                            TabData tabData) {
        super(fragmentManager);

        this.tabData = tabData;
    }

    @Override
    public Fragment getItem(int position) {
        return tabData.getFragmentList().get(position);
    }

    @Override
    public int getCount() {
        return tabData.getFragmentList().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  "SOL " + tabData.getSolList().get(position);
    }
}
