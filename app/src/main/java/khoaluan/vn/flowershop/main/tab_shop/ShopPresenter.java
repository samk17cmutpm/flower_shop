package khoaluan.vn.flowershop.main.tab_shop;

import khoaluan.vn.flowershop.main.MainActivity;

/**
 * Created by samnguyen on 7/19/16.
 */
public class ShopPresenter implements ShopContract.Presenter {
    private final MainActivity activity;
    private final ShopContract.View view;
    public ShopPresenter (MainActivity activity, ShopContract.View view) {
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
