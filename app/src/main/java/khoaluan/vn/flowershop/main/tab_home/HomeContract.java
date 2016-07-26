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
        void clearAllDataLocal();
        void initilizeGridView();
        void showUI();
        void showFlowers(List<Flower> flowers, boolean isHasNext);
        void showIndicator(boolean active);
        void setDeviderForGridView();
        void finishLoadMore(boolean finish);
    }
    interface Presenter extends BasePresenter, RealmAction<Flower> {
        void loadData();
        boolean isHasNext();

        // Local Data
        void loadRefreshData();
        void loadMoreData();

    }
}
