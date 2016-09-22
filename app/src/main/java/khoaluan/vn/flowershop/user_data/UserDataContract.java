package khoaluan.vn.flowershop.user_data;

import java.util.List;

import io.realm.RealmResults;
import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.order.order_confirm.MultipleOrderBillingItem;
import khoaluan.vn.flowershop.user_data.billings.MultipleBillingItem;
import retrofit2.http.Field;

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
        void updateDistrict(List<District> districts, boolean problem);
    }

    interface Presenter extends BasePresenter {
        void loadData();
        void updateInfoProfile(String id, String fullName, String address, String phone);
        void loadBillings(String idUser);
        RealmResults<Billing> loadBillingsLocal();
        RealmResults<ShippingAddressDTO> loadShippingAddressDTOLocal();
        RealmResults<InvoiceAddressDTO> loadInvoiceAddressDTOLocal();
        void loadBillingDetail(String orderId);
        void showBillingConfirm(List<MultipleOrderBillingItem> list);
        void loadDistricts(String idCity);
        void loadCities();

        void updateBillingAddress(
                String userId,
                String name,
                String phone,
                String cityid,
                String districtid,
                String address
        );

        void updateShippingAddress(
                String id,
                String userId,
                String name,
                String phone,
                String cityid,
                String districtid,
                String address
        );

        void createShippingAddress(
                String userId,
                String name,
                String phone,
                String cityid,
                String districtid,
                String address
        );

        void createInvoiceAddress(
                String userId,
                String companyName,
                String taxCode,
                String address
        );

        void updateInvoiceAddress(
                String id,
                String userId,
                String companyName,
                String taxCode,
                String address
        );

        void sendFeedBack(
                String userId,
                String Email,
                String Phone,
                String FullName,
                String Subject,
                String Content
        );

        void loadShippingAddressDTO(String userId);
        void loadInvoiceAddressDTO(String userId);

        void deleteBilling(String id);
        void deleteInvoice(String id);
        void deleteShipping(String id);

    }
}
