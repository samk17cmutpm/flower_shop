package khoaluan.vn.flowershop.order;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;

/**
 * Created by samnguyen on 9/11/16.
 */
public interface OrderContract {

    interface View extends BaseView<Presenter> {
        void showUI();
        void updateDistrict(List<District> districts, boolean problem);
        void updateDistrictRc(List<District> districts, boolean problem);
        void showIndicator(String message, boolean active);
        void setUpDropData();
        void setSenderInfo();
        void setSameRc(boolean reset);
        boolean isSenderInfoDone();
    }

    interface Presenter extends BasePresenter {
        void loadData();
        void loadDistricts(String idCity);
        void loadDistrictsRc(String idCity);
        void loadCities();

    }
}
