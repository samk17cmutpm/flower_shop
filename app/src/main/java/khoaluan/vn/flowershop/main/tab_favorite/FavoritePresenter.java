package khoaluan.vn.flowershop.main.tab_favorite;

import khoaluan.vn.flowershop.main.MainActivity;

/**
 * Created by samnguyen on 8/25/16.
 */
public class FavoritePresenter implements FavoriteContract.Presenter{
    private static String TAG = FavoritePresenter.class.getName();
    private final MainActivity activity;
    private final FavoriteContract.View view;
    public FavoritePresenter (MainActivity activity, FavoriteContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
    }
    @Override
    public void loadData() {

    }

    @Override
    public void start() {

    }
}
