package giphyimage.gifmos.avick.com.giphyimage.Utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by avick on 11/3/16.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = false; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 3; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItemPosition, visibleItemCount, totalItemCount;

    private int counter = 0;

    private RecyclerView.LayoutManager layoutManager;

    public EndlessRecyclerOnScrollListener(RecyclerView.LayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }

    public EndlessRecyclerOnScrollListener(RecyclerView.LayoutManager linearLayoutManager, int visibleThreshold) {
        this.layoutManager = linearLayoutManager;
        this.visibleThreshold = visibleThreshold;
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        if (layoutManager instanceof LinearLayoutManager) {
            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else {
            Log.e("scrolling", "The provided LayoutManager is not supported.");
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + visibleThreshold)) {
            // End has been reached
            counter++;
            onLoadMore(counter);
            loading = true;
        }
    }

    abstract public void onLoadMore(int count);

    public void resetPageNo() {
        counter = 0;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
