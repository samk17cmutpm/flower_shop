package khoaluan.vn.flowershop.main.tab_favorite;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;

/**
 * Created by samnguyen on 8/25/16.
 */
public interface FavoriteContract {
    interface View extends BaseView<Presenter> {
        void showUI();
    }
    interface Presenter extends BasePresenter {
        void loadData();
    }
}
