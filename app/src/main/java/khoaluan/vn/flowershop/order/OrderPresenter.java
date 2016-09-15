package khoaluan.vn.flowershop.order;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.BillingAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.City;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.data.parcelable.ActionForOrder;
import khoaluan.vn.flowershop.data.request.InvoiceRequest;
import khoaluan.vn.flowershop.data.response.BillingDetailResponse;
import khoaluan.vn.flowershop.data.response.CityResponse;
import khoaluan.vn.flowershop.data.response.DistrictResponse;
import khoaluan.vn.flowershop.data.response.ExtraInformationDTOResponse;
import khoaluan.vn.flowershop.data.response.InvoiceAddressDTOResponse;
import khoaluan.vn.flowershop.data.response.BillingAdressResponse;
import khoaluan.vn.flowershop.data.response.ShippingAdressResponse;
import khoaluan.vn.flowershop.data.shared_prefrences.CartSharedPrefrence;
import khoaluan.vn.flowershop.data.shared_prefrences.UserSharedPrefrence;
import khoaluan.vn.flowershop.realm_data_local.RealmBillingUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmCartUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmCityUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.OrderClient;
import khoaluan.vn.flowershop.utils.ActionUtils;
import khoaluan.vn.flowershop.utils.MessageUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 9/11/16.
 */
public class OrderPresenter implements OrderContract.Presenter{
    private final OrderContract.View view;
    private final Activity activity;
    private final OrderClient client;

    public OrderPresenter(OrderContract.View view, Activity activity) {
        this.view = view;
        this.activity = activity;
        this.view.setPresenter(this);
        this.client = ServiceGenerator.createService(OrderClient.class);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void start() {

    }

    @Override
    public void loadCities() {
        Observable<Response<CityResponse>> observable =
                client.getCities();

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<CityResponse>>() {
                    private List<City> cities;
                    @Override
                    public void onCompleted() {
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
    public void setBillingOrder(String cartId, String userId, String name, String phone, String mail, String cityid, String districtid, String address, final boolean isInvoice) {
        Observable<Response<BillingAdressResponse>> observable =
                client.setBillingOrder(cartId, userId, name, phone, mail, cityid, districtid, address);

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<BillingAdressResponse>>() {
                    private boolean success;
                    private BillingAddressDTO billingAddressDTO;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        if (success) {
                            RealmBillingUtils.updateBillingAddressDTO(billingAddressDTO);
                            if (isInvoice)
                                view.sendInvoice();
                            else
                                view.sendDataShipping();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(null, false);
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<BillingAdressResponse> billingAdressResponseResponse) {
                        if (billingAdressResponseResponse.isSuccessful()) {
                            success = billingAdressResponseResponse.body().isSuccess();
                            billingAddressDTO = billingAdressResponseResponse.body().getResult();
                        }
                    }
                });
    }

    @Override
    public void setShippingOrder(String cartId, String userId, String name, String phone, String mail, String cityid, String districtid, final String address) {
        Observable<Response<ShippingAdressResponse>> observable =
                client.setShippingOrder(cartId, userId, name, phone, mail, cityid, districtid, address);

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<ShippingAdressResponse>>() {
                    private boolean success;
                    private ShippingAddressDTO shippingAddressDTO;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        if (success) {
                            RealmBillingUtils.updateShippingAddressDTO(shippingAddressDTO);
                            MessageUtils.showLong(activity, "Đã lưu trữ thành công ");
                            ActionUtils.goOrder(activity, ActionForOrder.EXTRA);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(null, false);
                        e.printStackTrace();
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<ShippingAdressResponse> shippingAdressResponseResponse) {
                        if (shippingAdressResponseResponse.isSuccessful()) {
                            success = shippingAdressResponseResponse.body().isSuccess();
                            shippingAddressDTO = shippingAdressResponseResponse.body().getResult();
                        }

                    }
                });
    }

    @Override
    public void setInfoOrder(String cartId, String userId, long deliverydate, int hidesender, String note, String message, int paymentId) {
        Observable<Response<ExtraInformationDTOResponse>> observable =
                client.setExtraInfo(
                        cartId,
                        userId,
                        deliverydate,
                        hidesender,
                        note,
                        message,
                        paymentId
                );

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<ExtraInformationDTOResponse>>() {
                    private ExtraInformationDTOResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        if (response.isSuccess()) {
                            RealmBillingUtils.updateExtraInformationDTO(response.getResult());
                            ActionUtils.goOrder(activity, ActionForOrder.CONFIRM);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(null, false);
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<ExtraInformationDTOResponse> extraInformationDTOResponseResponse) {
                        if (extraInformationDTOResponseResponse.isSuccessful())
                            response = extraInformationDTOResponseResponse.body();
                    }
                });
    }

    @Override
    public void setInvoiceAddress(InvoiceRequest invoiceAddress) {
        Observable<Response<InvoiceAddressDTOResponse>> observable =
                client.setInvoice(invoiceAddress);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<InvoiceAddressDTOResponse>>() {
                    private InvoiceAddressDTOResponse invoiceAddressDTOResponse;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        if (invoiceAddressDTOResponse.isSuccess()) {
                            RealmBillingUtils.updateInvoiceAddressDTO(invoiceAddressDTOResponse.getResult());
                            view.sendDataShipping();
                        } else {
                            MessageUtils.showLong(activity, "Địa chỉ công ty trong phần thêm hóa đơn thuế không đúng, vui lòng kiểm tra lại");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(null, false);
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<InvoiceAddressDTOResponse> invoiceAddressDTOResponseResponse) {
                        if (invoiceAddressDTOResponseResponse.isSuccessful()) {
                            invoiceAddressDTOResponse = invoiceAddressDTOResponseResponse.body();
                        }
                    }
                });
    }

    @Override
    public void makeAnOrder() {
        view.showIndicator("Đang tạo đơn hàng, Vui lòng chờ ...", true);

        Observable<Response<BillingDetailResponse>> observable =
                client.makeAnOrder(CartSharedPrefrence.getCartId(activity), UserSharedPrefrence.getUser(activity).getId());

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<BillingDetailResponse>>() {
                    private BillingDetailResponse billingDetailResponse;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        if (billingDetailResponse.isSuccess())
                            MessageUtils.showLong(activity, "Đã tạo đơn hàng thành công");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(null, false);
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<BillingDetailResponse> billingDetailResponseResponse) {
                        if (billingDetailResponseResponse.isSuccessful()) {
                            billingDetailResponse = billingDetailResponseResponse.body();
                        }

                    }
                });
    }


    @Override
    public void loadDistricts(String idCity) {
        Observable<Response<DistrictResponse>> observable =
                client.getDistricts(idCity);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<DistrictResponse>>() {
                    private List<District> districts;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        view.updateDistrict(districts, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.updateDistrict(new ArrayList<District>(), true);
                        view.showIndicator(null, false);
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
    public void loadDistrictsRc(String idCity) {
        Observable<Response<DistrictResponse>> observable =
                client.getDistricts(idCity);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<DistrictResponse>>() {
                    private List<District> districts;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        view.updateDistrictRc(districts, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.updateDistrictRc(new ArrayList<District>(), true);
                        view.showIndicator(null, false);
                        MessageUtils.showLong(activity, "Không thể kết nối internet để lấy dữ liệu, Vui lòng kiểm tra lại !");
                    }

                    @Override
                    public void onNext(Response<DistrictResponse> districtResponseResponse) {
                        if (districtResponseResponse.isSuccessful())
                            districts = districtResponseResponse.body().getResult();
                    }
                });
    }

}
