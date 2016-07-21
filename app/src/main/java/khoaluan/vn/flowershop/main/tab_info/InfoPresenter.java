package khoaluan.vn.flowershop.main.tab_info;

import android.util.Log;

import khoaluan.vn.flowershop.main.MainActivity;

/**
 * Created by samnguyen on 7/19/16.
 */
public class InfoPresenter implements InfoContract.Presenter {
    private static String TAG = InfoPresenter.class.getName();
    private final MainActivity activity;
    private final InfoContract.View view;
    public InfoPresenter (MainActivity activity, InfoContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
    }
    @Override
    public void loadData() {

    }

    @Override
    public void start() {
        Log.d(TAG, "Starting Info Tab");
    }
}
