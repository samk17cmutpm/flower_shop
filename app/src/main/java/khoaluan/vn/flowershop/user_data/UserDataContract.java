package khoaluan.vn.flowershop.user_data;

import java.util.List;

import io.realm.RealmResults;
import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;
import khoaluan.vn.flowershop.user_data.billings.MultipleBillingItem;

/**
 * Created by samnguyen on 9/10/16.
 */
public interface UserDataContract  {

    interface View extends BaseView<Presenter>, Network {
        void showUI();
        void showIndicator(boolean active, String message);
        void done();
        void initilizeRecyclerView();
        void showIndicator(boolean active);
        void showBillingDetail(List<MultipleBillingItem> list);
    }

    interface Presenter extends BasePresenter {
        void loadData();
        void updateInfoProfile(String id, String fullName, String address, String phone);
        void loadBillings(String idUser);
        RealmResults<Billing> loadBillingsLocal();
        void loadBillingDetail(String orderId);
    }
}
