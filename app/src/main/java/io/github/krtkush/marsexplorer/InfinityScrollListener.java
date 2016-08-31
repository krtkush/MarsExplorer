package io.github.krtkush.marsexplorer;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by kartikeykushwaha on 29/08/16.
 */
public abstract class InfinityScrollListener extends RecyclerView.OnScrollListener {

    private GridLayoutManager gridLayoutManager;
    private int pageIndex;

    public InfinityScrollListener (GridLayoutManager gridLayoutManager, int pageIndex) {
        this.gridLayoutManager = gridLayoutManager;
        this.pageIndex = pageIndex;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        checkScrollDirectionAndCallNextPage(dy);
    }

    /**
     * Method to check whether user has scrolled in down direction. If scroll is downward,
     * identify end of list and give a callback to load more.
     * @param dy Scroll value: +ve value is scroll down. -ve value is scroll up.
     */
    private void checkScrollDirectionAndCallNextPage(int dy) {
        // Proceed only of the user is scrolling down
        if(dy > 0) {
            int currentlyVisibleItemCount = gridLayoutManager.getChildCount();
            int totalItemCount = gridLayoutManager.getItemCount();
            int previouslyVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

            if ((currentlyVisibleItemCount + previouslyVisibleItems) >= totalItemCount) {
                // Reached the bottom of RecyclerView (list); we can attempt to load the next page.
                loadMore(pageIndex++);
            }
        }
    }

    public abstract void loadMore(int newPageIndex);
}
