package io.github.krtkush.marsexplorer.RoverExplorer.TabHostActivity;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kartikeykushwaha on 01/09/16.
 */
public class TabData {

    //TODO: Convert to AutoValue

    /**
     * Data structure to hold fragment instances and their respective SOLs in lists
     */

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> solList = new ArrayList<>();

    public List<String> getSolList() {
        return solList;
    }

    public void setSolList(List<String> solList) {
        this.solList = solList;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }

}
