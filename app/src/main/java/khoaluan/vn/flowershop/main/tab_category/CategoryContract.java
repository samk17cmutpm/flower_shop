package khoaluan.vn.flowershop.main.tab_category;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_presenter.RealmAction;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ExpandCategory;

/**
 * Created by samnguyen on 7/19/16.
 */
public interface CategoryContract {
    interface View extends BaseView<Presenter>, Network {
        void initilizeRecyclerView();
        void showUI();
        void showCategories(List<ExpandCategory> expandCategories);
        void setDevider();
        void setSegment();
    }
    interface Presenter extends BasePresenter, RealmAction.Category<Category>{
        void loadData();
        void loadFlowerCategories();
        void loadGiftCategories();
    }
}
