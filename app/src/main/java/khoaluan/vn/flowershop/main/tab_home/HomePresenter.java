package khoaluan.vn.flowershop.main.tab_home;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import khoaluan.vn.flowershop.data.Flower;
import khoaluan.vn.flowershop.main.MainActivity;

/**
 * Created by samnguyen on 7/19/16.
 */
public class HomePresenter implements HomeContract.Presenter {
    private static String TAG = HomePresenter.class.getName();
    private final MainActivity activity;
    private final HomeContract.View view;
    public HomePresenter(MainActivity activity, HomeContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
    }
    @Override
    public void loadData() {
        view.showFlowers(dumpData());
    }

    @Override
    public void start() {
        Log.d(TAG, "Starting HomeFragment");
    }

    private List<Flower> dumpData() {
        List<Flower> flowers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            flowers.add(new Flower("" + i, "" + i));
        }
        return flowers;
    }
}
