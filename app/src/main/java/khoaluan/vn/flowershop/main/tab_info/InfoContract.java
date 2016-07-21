package khoaluan.vn.flowershop.main.tab_info;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;

/**
 * Created by samnguyen on 7/19/16.
 */
public interface InfoContract {
    interface View extends BaseView<Presenter> {
        void showUI();
    }
    interface Presenter extends BasePresenter {
        void loadData();
    }
}
