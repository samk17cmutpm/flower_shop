package khoaluan.vn.flowershop.main.tab_search;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import khoaluan.vn.flowershop.main.MainActivity;

/**
 * Created by samnguyen on 7/19/16.
 */
public class SearchPresenter implements SearchContract.Presenter {
    private static String TAG = SearchPresenter.class.getName();
    private final MainActivity activity;
    private final SearchContract.View view;
    public SearchPresenter (MainActivity activity, SearchContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
    }
    @Override
    public void loadData() {

    }

    @Override
    public void start() {
        Log.d(TAG, "Search Presenter");
    }
}
