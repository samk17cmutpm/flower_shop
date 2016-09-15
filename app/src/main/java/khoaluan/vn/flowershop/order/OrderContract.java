package khoaluan.vn.flowershop.order;

import java.util.List;

import io.realm.RealmList;
import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.BillingAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ExtraInformationDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.data.request.InvoiceRequest;
import retrofit2.http.Field;

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
        void setUpBilling(boolean active);
        void setSenderInfo();
        void setSameRc(boolean reset);
        boolean isSenderInfoDone();
        boolean isBillingDone();
        boolean isInvoice();
        void sendDataBilling();
        void sendDataShipping();
        void sendInvoice();
        void showDateTimePicker();
    }

    interface Presenter extends BasePresenter {
        void loadData();
        void loadDistricts(String idCity);
        void loadDistrictsRc(String idCity);
        void loadCities();
        void setBillingOrder(String cartId,
                             String userId,
                             String name,
                             String phone,
                             String mail,
                             String cityid,
                             String districtid,
                             String address,
                             boolean isInvoice);

        void setShippingOrder(String cartId,
                             String userId,
                             String name,
                             String phone,
                             String mail,
                             String cityid,
                             String districtid,
                             String address);


        void setInfoOrder(String cartId,
                              String userId,
                              long deliverydate,
                              int hidesender,
                              String note,
                              String message,
                              int paymentId);
//
//        void setInvoiceAddress(
//                String userId,
//                String companyName,
//                String taxCode,
//                String address
//        );

        void setInvoiceAddress(
                InvoiceRequest invoiceRequest
        );

        void makeAnOrder();


    }
}
