package khoaluan.vn.flowershop.main.tab_type;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.FlowerType;

/**
 * Created by samnguyen on 7/19/16.
 */
public interface TypeContract {
    interface View extends BaseView<Presenter> {
        void initilizeRecyclerView();
        void showUI();
        void showFlowerTypes(List<FlowerType> flowerTypes);
    }
    interface Presenter extends BasePresenter {
        void loadData();
    }
}
