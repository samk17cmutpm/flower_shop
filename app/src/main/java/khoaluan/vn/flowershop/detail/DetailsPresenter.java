package khoaluan.vn.flowershop.detail;

import android.app.Activity;

/**
 * Created by samnguyen on 7/28/16.
 */
public class DetailsPresenter implements DetailsContract.Presenter {
    private static String TAG = DetailsPresenter.class.getName();
    private final DetailsContract.View view;
    private final Activity activity;

    public DetailsPresenter(DetailsContract.View view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void start() {

    }
}
