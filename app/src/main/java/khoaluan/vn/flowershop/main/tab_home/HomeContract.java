package khoaluan.vn.flowershop.main.tab_home;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.action.action_presenter.RealmAction;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 7/19/16.
 */
public interface HomeContract {
    interface View extends BaseView<Presenter>, Network {
        void initilizeMainView();
        void showUI();
        void showIndicator(boolean active);
        void showTopProducts(List<MultipleMainItem> multipleMainItems);
        void showError(String message);
    }
    interface Presenter extends BasePresenter, RealmAction.Flower<Flower> {
        void loadData();
        // Local Data
        void loadTopProducts();
        void loadAdvertisingItems();
        void refreshData();
    }
}
