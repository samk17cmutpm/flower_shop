package khoaluan.vn.flowershop.user_data;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;
import khoaluan.vn.flowershop.data.model_parse_and_realm.City;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForUserData;
import khoaluan.vn.flowershop.data.response.BillingAdressResponse;
import khoaluan.vn.flowershop.data.response.BillingDetailResponse;
import khoaluan.vn.flowershop.data.response.BillingResponse;
import khoaluan.vn.flowershop.data.response.CityResponse;
import khoaluan.vn.flowershop.data.response.DistrictResponse;
import khoaluan.vn.flowershop.data.response.InvoiceAddressDTOResponse;
import khoaluan.vn.flowershop.data.response.ListInvoiceAddressDTOResponse;
import khoaluan.vn.flowershop.data.response.ListShippingAddressResponse;
import khoaluan.vn.flowershop.data.response.RemoveResponse;
import khoaluan.vn.flowershop.data.response.ShippingAdressResponse;
import khoaluan.vn.flowershop.data.response.UserResponse;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.order.order_confirm.MultipleOrderBillingItem;
import khoaluan.vn.flowershop.realm_data_local.RealmAddressUtills;
import khoaluan.vn.flowershop.realm_data_local.RealmBillingUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmCityUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.OrderClient;
import khoaluan.vn.flowershop.retrofit.client.UserClient;
import khoaluan.vn.flowershop.utils.ActionUtils;
import khoaluan.vn.flowershop.utils.ConvertUtils;
import khoaluan.vn.flowershop.utils.MessageUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 9/10/16.
 */
public class UserDataPresenter implements UserDataContract.Presenter {
    private final Activity activity;
    private final UserDataContract.View view;
    private final UserClient client;
    private final OrderClient orderClient;
    public UserDataPresenter(Activity activity, UserDataContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.client = ServiceGenerator.createService(UserClient.class);
        this.orderClient = ServiceGenerator.createService(OrderClient.class);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void updateInfoProfile(String id, String fullName, String address, String phone) {
        Observable<Response<UserResponse>> observable
                = client.updateProfile(id, fullName, address, phone);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<UserResponse>>() {
                    private UserResponse userResponse;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        if (userResponse.isSuccess()) {
                            MessageUtils.showLong(activity, "Cập nhật thành công thông tin cá nhân");
                            UserUtils.saveUser(userResponse.getResult(), activity);
                            view.done();
                        } else {
                            MessageUtils.showLong(activity, "WTF");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, "Vui lòng kiểm tra lại intenert, không thể kết nối !");
                    }

                    @Override
                    public void onNext(Response<UserResponse> userResponseResponse) {
                        if (userResponseResponse.isSuccessful())
                            userResponse = userResponseResponse.body();
                    }
                });
    }

    @Override
    public void loadBillings(String idUser) {
        Observable<Response<BillingResponse>> observable
                = client.getBillings(idUser);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<BillingResponse>>() {
                    private List<Billing> billings;
                    @Override
                    public void onCompleted() {
                        RealmBillingUtils.updateAll(billings);
                        view.showIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<BillingResponse> billingResponseResponse) {
                        if (billingResponseResponse.isSuccessful())
                            billings = billingResponseResponse.body().getResult();
                    }
                });
    }

    @Override
    public RealmResults<Billing> loadBillingsLocal() {
        return RealmBillingUtils.all();
    }

    @Override
    public RealmResults<ShippingAddressDTO> loadShippingAddressDTOLocal() {
        return RealmAddressUtills.allShippingAddressDTO();
    }

    @Override
    public RealmResults<InvoiceAddressDTO> loadInvoiceAddressDTOLocal() {
        return RealmAddressUtills.allInvoiceAddressDTO();
    }

    @Override
    public void loadBillingDetail(String orderId) {
        Observable<Response<BillingDetailResponse>> observable
                = client.getBillingDetail(orderId);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<BillingDetailResponse>>() {
                    private Billing billing;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        view.showBillingDetail(ConvertUtils.convertBillingToMultipleBillingItem(billing));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<BillingDetailResponse> billingDetailResponseResponse) {
                        if (billingDetailResponseResponse.isSuccessful())
                            billing = billingDetailResponseResponse.body().getResult();
                    }
                });
    }

    @Override
    public void showBillingConfirm(List<MultipleOrderBillingItem> list) {

    }

    @Override
    public void loadDistricts(String idCity) {
        Observable<Response<DistrictResponse>> observable =
                orderClient.getDistricts(idCity);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<DistrictResponse>>() {
                    private List<District> districts;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        view.updateDistrict(districts, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.updateDistrict(new ArrayList<District>(), true);
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, "Không thể kết nối internet để lấy dữ liệu, Vui lòng kiểm tra lại !");
                    }

                    @Override
                    public void onNext(Response<DistrictResponse> districtResponseResponse) {
                        if (districtResponseResponse.isSuccessful())
                            districts = districtResponseResponse.body().getResult();
                    }
                });
    }

    @Override
    public void loadCities() {

        Observable<Response<CityResponse>> observable =
                orderClient.getCities();

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<CityResponse>>() {
                    private List<City> cities;
                    @Override
                    public void onCompleted() {
                        for (City city : cities)
                            city.setFlag(RealmFlag.CITY_SEND);
                        RealmCityUtils.updateAll(cities);
                    }

                    @Override
                    public void onError(Throwable e) {
                        MessageUtils.showLong(activity, "Không thể kết nối internet để lấy dữ liệu, Vui lòng kiểm tra lại !");
                    }

                    @Override
                    public void onNext(Response<CityResponse> cityResponseResponse) {
                        if (cityResponseResponse.isSuccessful())
                            cities = cityResponseResponse.body().getResult();
                    }
                });

    }

    @Override
    public void updateBillingAddress(String userId, String name, String phone, String cityid, String districtid, String address) {
        Observable<Response<BillingAdressResponse>> observable =
                orderClient.updateBillingAddress(userId, name, phone, cityid, districtid, address);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<BillingAdressResponse>>() {
                    private BillingAdressResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, "Cập nhập thông tin thành công ");
                        RealmAddressUtills.updateBillongAddress(response.getResult());
                        ActionUtils.go(activity, 4);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showIndicator(false, null);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<BillingAdressResponse> billingAddressDTOResponse) {
                        if (billingAddressDTOResponse.isSuccessful())
                            response = billingAddressDTOResponse.body();
                    }
                });
    }

    @Override
    public void updateShippingAddress(String id, String userId, String name, String phone, String cityid, String districtid, String address) {
        Observable<Response<ShippingAdressResponse>> observable =
                orderClient.updateShippingAddress(
                        id, userId, name, phone, cityid, districtid, address
                );

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<ShippingAdressResponse>>() {
                    private ShippingAdressResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, "Đã câp nhập thành công");

                        Intent intent = new Intent(activity, UserDataActivity.class);
                        intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.DELIVERY_ADDRESS));
                        activity.startActivity(intent);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<ShippingAdressResponse> shippingAdressResponseResponse) {
                        if (shippingAdressResponseResponse.isSuccessful())
                            response = shippingAdressResponseResponse.body();
                    }
                });
    }

    @Override
    public void createShippingAddress(String userId, String name, String phone, String cityid, String districtid, String address) {
        Observable<Response<ShippingAdressResponse>> observable =
                orderClient.updateShippingAddress(
                        userId, name, phone, cityid, districtid, address
                );

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<ShippingAdressResponse>>() {
                    private ShippingAdressResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, "Đã tạo mới thành công thành công");

                        Intent intent = new Intent(activity, UserDataActivity.class);
                        intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.DELIVERY_ADDRESS));
                        activity.startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<ShippingAdressResponse> shippingAdressResponseResponse) {
                        if (shippingAdressResponseResponse.isSuccessful())
                            response = shippingAdressResponseResponse.body();
                    }
                });
    }

    @Override
    public void createInvoiceAddress(String userId, String companyName, String taxCode, String address) {
        Observable<Response<InvoiceAddressDTOResponse>> observable =
                orderClient.updateInvoiceAddress(userId, companyName, taxCode, address);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<InvoiceAddressDTOResponse>>() {
                    private InvoiceAddressDTOResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, "Đã tạo mới thành công");

                        Intent intent = new Intent(activity, UserDataActivity.class);
                        intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.BILLING_INFO));
                        activity.startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        MessageUtils.showLong(activity, "Không có kết nối internet, Vui lòng kiểm tra lại");
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<InvoiceAddressDTOResponse> invoiceAddressDTOResponseResponse) {
                        if (invoiceAddressDTOResponseResponse.isSuccessful())
                            response = invoiceAddressDTOResponseResponse.body();
                    }
                });
    }

    @Override
    public void updateInvoiceAddress(String id, String userId, String companyName, String taxCode, String address) {
        Observable<Response<InvoiceAddressDTOResponse>> observable =
                orderClient.updateInvoiceAddress(userId, companyName, taxCode, address);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<InvoiceAddressDTOResponse>>() {
                    private InvoiceAddressDTOResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, "Đã cập nhập thành công");

                        Intent intent = new Intent(activity, UserDataActivity.class);
                        intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.BILLING_INFO));
                        activity.startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false, null);
                        e.printStackTrace();
                        MessageUtils.showLong(activity, "Không có kết nối internet, Vui lòng kiểm tra lại");
                    }

                    @Override
                    public void onNext(Response<InvoiceAddressDTOResponse> invoiceAddressDTOResponseResponse) {
                        if (invoiceAddressDTOResponseResponse.isSuccessful())
                            response = invoiceAddressDTOResponseResponse.body();
                    }
                });
    }

    @Override
    public void sendFeedBack(String userId, String Email, String Phone, String FullName, String Subject, String Content) {
        Observable<Response<RemoveResponse>> observable =
                client.feedBack(userId, Email, Phone, FullName, Subject, Content);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<RemoveResponse>>() {
                    private RemoveResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        if (response.getResult()) {
                            MessageUtils.showLong(activity, "Đã đóng góp ý kiến thành công");
                            ActionUtils.go(activity, 4);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, "Không có kết nối internet, Vui lòng kiểm tra lại");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<RemoveResponse> removeCartResponseResponse) {
                        response = removeCartResponseResponse.body();
                    }
                });
    }

    @Override
    public void loadShippingAddressDTO(String userId) {

        Observable<Response<ListShippingAddressResponse>> observable =
                orderClient.getShippingAddress(userId);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<ListShippingAddressResponse>>() {
                    private ListShippingAddressResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        RealmAddressUtills.updateAllShippingAddressDTO(response.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<ListShippingAddressResponse> listShippingAddressResponseResponse) {
                        if (listShippingAddressResponseResponse.isSuccessful())
                            response = listShippingAddressResponseResponse.body();
                    }
                });

    }

    @Override
    public void loadInvoiceAddressDTO(String userId) {
        Observable<Response<ListInvoiceAddressDTOResponse>> observable =
                orderClient.getInvoiceAddress(userId);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<ListInvoiceAddressDTOResponse>>() {
                    private ListInvoiceAddressDTOResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        RealmAddressUtills.updateAllInvoiceDTO(response.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<ListInvoiceAddressDTOResponse> listInvoiceAddressDTOResponseResponse) {
                        if (listInvoiceAddressDTOResponseResponse.isSuccessful())
                            response = listInvoiceAddressDTOResponseResponse.body();
                    }
                });
    }

    @Override
    public void deleteBilling(String id) {
        Observable<Response<RemoveResponse>> observable =
                orderClient.deleteBilling(id);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<RemoveResponse>>() {
                    private RemoveResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        if (response.getResult()) {
                            MessageUtils.showLong(activity, "Đã xóa đơn hàng thành công ");
                            activity.onBackPressed();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<RemoveResponse> removeResponseResponse) {
                        if (removeResponseResponse.isSuccessful())
                            response = removeResponseResponse.body();
                    }
                });
    }

    @Override
    public void deleteInvoice(String id) {
        Observable<Response<RemoveResponse>> observable =
                orderClient.deleteInvoice(id);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<RemoveResponse>>() {
                    private RemoveResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        if (response.getResult()) {
                            MessageUtils.showLong(activity, "Đã xóa thông tin hóa đơn thành công ");
                            activity.onBackPressed();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<RemoveResponse> removeResponseResponse) {
                        if (removeResponseResponse.isSuccessful())
                            response = removeResponseResponse.body();
                    }
                });
    }

    @Override
    public void deleteShipping(String id) {
        Observable<Response<RemoveResponse>> observable =
                orderClient.deleteShipping(id);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<RemoveResponse>>() {
                    private RemoveResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        if (response.getResult()) {
                            MessageUtils.showLong(activity, "Đã địa chỉ giao hàng thành công ");
                            activity.onBackPressed();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<RemoveResponse> removeResponseResponse) {
                        if (removeResponseResponse.isSuccessful())
                            response = removeResponseResponse.body();
                    }
                });
    }

    @Override
    public void start() {

    }
}
