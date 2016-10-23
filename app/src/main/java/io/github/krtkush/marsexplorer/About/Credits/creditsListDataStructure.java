package io.github.krtkush.marsexplorer.About.Credits;

import android.util.SparseArray;

/**
 * Created by kartikeykushwaha on 23/10/16.
 */

public class CreditsListDataStructure {

    private SparseArray<String> title = new SparseArray<>();
    private SparseArray<String> subTitle = new SparseArray<>();

    public SparseArray<String> getTitle() {
        return title;
    }

    public void setTitle(SparseArray<String> title) {
        this.title = title;
    }

    public SparseArray<String> getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(SparseArray<String> subTitle) {
        this.subTitle = subTitle;
    }
}
