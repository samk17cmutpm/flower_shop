package khoaluan.vn.flowershop.detail;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;

/**
 * Created by samnguyen on 7/28/16.
 */
public interface DetailsContract {

    interface View extends BaseView<Presenter>, CommonView.ToolBar {
        void showUI();
    }

    interface Presenter extends BasePresenter {
        void loadData();
        List<MutipleDetailItem> convertData(Flower flower, FlowerSuggesstion flowerSuggesstion);
    }
}
