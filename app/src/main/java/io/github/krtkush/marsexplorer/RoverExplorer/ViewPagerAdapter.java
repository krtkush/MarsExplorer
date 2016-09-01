package io.github.krtkush.marsexplorer.RoverExplorer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by kartikeykushwaha on 01/09/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int pageCount = 3;
    private String sol;
    private String roverName;
    private List<Fragment> fragmentCollection;

    public ViewPagerAdapter(FragmentManager fragmentManager,
                            List<Fragment> fragmentCollection) {
        super(fragmentManager);

        this.fragmentCollection = fragmentCollection;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentCollection.get(position);
    }

    @Override
    public int getCount() {
        return fragmentCollection.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "SOL";
    }
}
