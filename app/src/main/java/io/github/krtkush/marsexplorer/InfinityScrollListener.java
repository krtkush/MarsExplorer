package io.github.krtkush.marsexplorer;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by kartikeykushwaha on 29/08/16.
 */
public abstract class InfinityScrollListener extends RecyclerView.OnScrollListener {

    private GridLayoutManager gridLayoutManager;
    private Boolean isLoading = true;
    private int pageIndex = 0;

    public InfinityScrollListener (GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        checkScrollDirectionAndCallNextPage(dy);
    }

    private void checkScrollDirectionAndCallNextPage(int dy) {
        // Proceed only of the user is scrolling down
        if(dy > 0) {
            int visibleItemsCount = gridLayoutManager.getChildCount();
            int totalItemCount = gridLayoutManager.getItemCount();
            int previouslyVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

            if ((visibleItemsCount + previouslyVisibleItems) >= totalItemCount) {
                onLoadMore(pageIndex);
            }

            if(!isLoading) {
                // Check if the user has come to the end of the list; if he/ she has, load more.

            }
        }
    }

    public abstract void onLoadMore(int pageIndex);
}
