package khoaluan.vn.flowershop.category_detail;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_presenter.RealmAction;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 7/28/16.
 */
public interface CategoryDetailContract {

    interface View extends BaseView<Presenter>, CommonView.ToolBar, Network {
        void clearAllDataLocal();
        void initilizeGridView();
        void showUI();
        void showFlowers(List<Flower> flowers, boolean isHasNext);
        void showIndicator(boolean active);
        void setDeviderForGridView();
        void finishLoadMore(boolean finish);
        void showFlowerDetails(Flower flower);
        void setEmptyRecyclerView(String message);
    }

    interface Presenter extends BasePresenter, RealmAction.FlowerByCategory<Flower> {
        void loadData();
        void loadRefreshData();
        void loadMoreData();
        Category getCategory();
    }
}
