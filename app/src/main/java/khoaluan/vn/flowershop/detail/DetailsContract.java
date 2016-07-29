package khoaluan.vn.flowershop.detail;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_view.CommonView;

/**
 * Created by samnguyen on 7/28/16.
 */
public interface DetailsContract {

    interface View extends BaseView<Presenter>, CommonView.ToolBar {
        void showUI();
    }

    interface Presenter extends BasePresenter {
        void loadData();
    }
}
