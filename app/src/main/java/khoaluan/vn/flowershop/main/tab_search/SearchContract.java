package khoaluan.vn.flowershop.main.tab_search;

import android.view.Menu;
import android.view.MenuInflater;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 7/19/16.
 */
public interface SearchContract {

    interface View extends BaseView<Presenter>, Network {
        void showUI();
        void setUpFloatSearch();
        void initilizeGridview();
        void showDataSearch(List<Flower> flowers, boolean isHasNext);
        void finishLoadMore(boolean finish);
        void setDeviderForGridView();
        void showIndicator(boolean active);
        void showNoResult();
        void setEnableRefresh(boolean active);

    }
    interface Presenter extends BasePresenter {
        void loadData();
        void loadDataBySearch(String key);
        void loadMoreDataBySearch();
        void resetData();
    }
}
