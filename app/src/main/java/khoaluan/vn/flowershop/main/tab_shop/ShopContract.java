package khoaluan.vn.flowershop.main.tab_shop;

import io.realm.RealmResults;
import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 7/19/16.
 */
public interface ShopContract {
    interface View extends BaseView<Presenter> {
        void showUI();
    }
    interface Presenter extends BasePresenter {
        void loadData();
        RealmResults<Flower> loadCartFlowers();
    }
}
