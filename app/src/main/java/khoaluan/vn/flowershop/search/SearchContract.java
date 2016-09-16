package khoaluan.vn.flowershop.search;

import android.view.Menu;
import android.view.MenuInflater;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_presenter.RealmAction;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.request.SearchRequest;

/**
 * Created by samnguyen on 7/19/16.
 */
public interface SearchContract {

    interface View extends BaseView<Presenter>, Network {
        void showUI();
        void initilizeGridview();
        void showDataSearch(List<Flower> flowers, boolean isHasNext);
        void finishLoadMore(boolean finish);
        void setDeviderForGridView();
        void showIndicator(boolean active);
        void showNoResult();
        void setEnableRefresh(boolean active);
        void showFlowerDetails(Flower flower);
        void showMaterialSearch();
        void initializeSearchSuggestion();
    }
    interface Presenter extends BasePresenter, RealmAction.Flower<Flower> {
        void loadData();
        void loadDataBySearch(SearchRequest searchRequest);
        void loadMoreDataBySearch();
        void initilizeSearchRequest(SearchRequest searchRequest);
        void resetData();
    }
}
