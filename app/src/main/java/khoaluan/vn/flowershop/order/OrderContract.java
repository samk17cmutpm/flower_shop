package khoaluan.vn.flowershop.order;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;

/**
 * Created by samnguyen on 9/11/16.
 */
public interface OrderContract {

    interface View extends BaseView<Presenter> {
        void showUI();
    }

    interface Presenter extends BasePresenter {
        void loadData();
    }
}
