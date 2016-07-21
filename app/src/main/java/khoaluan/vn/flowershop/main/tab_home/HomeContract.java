package khoaluan.vn.flowershop.main.tab_home;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.data.Flower;

/**
 * Created by samnguyen on 7/19/16.
 */
public interface HomeContract {
    interface View extends BaseView<Presenter> {
        void initilizeGridView();
        void showUI();
        void showFlowers(List<Flower> flowers);
    }
    interface Presenter extends BasePresenter {
        void loadData();
    }
}
