package khoaluan.vn.flowershop.main.tab_search;

import android.view.Menu;
import android.view.MenuInflater;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;

/**
 * Created by samnguyen on 7/19/16.
 */
public interface SearchContract {

    interface View extends BaseView<Presenter> {
        void showUI();
    }
    interface Presenter extends BasePresenter {
        void loadData();
    }
}
