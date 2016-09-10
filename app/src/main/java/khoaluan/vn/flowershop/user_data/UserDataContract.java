package khoaluan.vn.flowershop.user_data;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;

/**
 * Created by samnguyen on 9/10/16.
 */
public interface UserDataContract {

    interface View extends BaseView<Presenter> {
        void showUI();
    }

    interface Presenter extends BasePresenter {
        void loadData();
    }
}
