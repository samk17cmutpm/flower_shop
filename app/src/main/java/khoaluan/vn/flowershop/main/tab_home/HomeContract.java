package khoaluan.vn.flowershop.main.tab_home;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.Network;
import khoaluan.vn.flowershop.data.client_parse.Flower;

/**
 * Created by samnguyen on 7/19/16.
 */
public interface HomeContract {
    interface View extends BaseView<Presenter>, Network {
        void clearAllDataLocal();
        void initilizeGridView();
        void showUI();
        void showFlowers(List<Flower> flowers, boolean isHasNext);
        void showIndicator(boolean active);
        void setDeviderForGridView();
        void finishLoadMore(boolean finish);

        @Override
        void noInternetConnectTion();
    }
    interface Presenter extends BasePresenter {
        void loadData();
        boolean isHasNext();

        // Local Data
        List<Flower> loadLocalData();
        void loadDataFirst();
        void loadDataMore();
        void updateLocalData(List<Flower> flowers);

    }
}
