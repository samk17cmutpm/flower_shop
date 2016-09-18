package khoaluan.vn.flowershop.order;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.BillingAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.City;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForOrder;
import khoaluan.vn.flowershop.data.parcelable.ActionForUserData;
import khoaluan.vn.flowershop.data.request.InvoiceRequest;
import khoaluan.vn.flowershop.data.response.BankResponse;
import khoaluan.vn.flowershop.data.response.BillingDetailResponse;
import khoaluan.vn.flowershop.data.response.CityResponse;
import khoaluan.vn.flowershop.data.response.DistrictResponse;
import khoaluan.vn.flowershop.data.response.ExtraInformationDTOResponse;
import khoaluan.vn.flowershop.data.response.InvoiceAddressDTOResponse;
import khoaluan.vn.flowershop.data.response.BillingAdressResponse;
import khoaluan.vn.flowershop.data.response.ListInvoiceAddressDTOResponse;
import khoaluan.vn.flowershop.data.response.ShippingAdressResponse;
import khoaluan.vn.flowershop.data.shared_prefrences.CartSharedPrefrence;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmAddressUtills;
import khoaluan.vn.flowershop.realm_data_local.RealmBankUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmBillingUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmCartUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmCityUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.OrderClient;
import khoaluan.vn.flowershop.user_data.UserDataActivity;
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
                            if (isInvoice) {
                                view.sendInvoice();
                            }
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
    public void setShippingOrder(String cartId, final String userId, final String name, final String phone, String mail, final String cityid, final String districtid, final String address, final boolean isSaveTemplate) {
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

                            if (isSaveTemplate) {
                                view.showIndicator("Đang tạo mẩu mới", true);
                                setNewShippingAddress(userId, name, phone, cityid, districtid, address);
                            } else {
                                if (view.isEdited() && view.isExistedExtraInfo()) {
                                    MessageUtils.showLong(activity, "Cập nhập thành công ");
                                    ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.CONFIRM, false));
                                } else {
                                    MessageUtils.showLong(activity, "Đã lưu trữ thành công ");
                                    ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.EXTRA, false));
                                }

                            }
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

                            if (view.isEdited()) {
                                MessageUtils.showLong(activity, "Cập nhập thành công ");
                                ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.CONFIRM, false));
                            } else {
                                if (response.getResult().getPaymentMethodId()  == 2)
                                    ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.BANK, false));
                                else
                                    ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.CONFIRM, false));
                            }


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
    public void setInvoiceAddress(String cartId, String userId, String companyName, String taxCode, String address, final boolean isSaveTemplater) {

        Observable<Response<InvoiceAddressDTOResponse>> observable =
                client.setInvoice(cartId, userId, companyName, taxCode, address);


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
                            if (isSaveTemplater) {
                                view.saveNewInvoiceTemplate();
                            } else
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
    public void setInvoiceAddress(InvoiceRequest invoiceAddress) {

    }

    @Override
    public void setNewInvoiceAddress(InvoiceRequest invoiceRequest) {
        Observable<Response<InvoiceAddressDTOResponse>> observable =
                client.setInvoice(invoiceRequest);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<InvoiceAddressDTOResponse>>() {
                    private InvoiceAddressDTOResponse invoiceAddressDTOResponse;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        if (invoiceAddressDTOResponse.isSuccess()) {
                            List<InvoiceAddressDTO> list = new ArrayList<InvoiceAddressDTO>();
                            list.add(invoiceAddressDTOResponse.getResult());
                            RealmAddressUtills.saveInvoiceAddressDTO(list);
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
    public void setNewShippingAddress(String userId, String name, String phone, String cityid, String districtid, String address) {
        Observable<Response<ShippingAdressResponse>> observable =
                client.updateShippingAddress(
                        userId, name, phone, cityid, districtid, address
                );

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<ShippingAdressResponse>>() {
                    private ShippingAdressResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        List<ShippingAddressDTO> list = new ArrayList<ShippingAddressDTO>();
                        list.add(response.getResult());
                        RealmAddressUtills.saveShippingAddressDTO(list);
                        if (view.isEdited()) {
                            MessageUtils.showLong(activity, "Cập nhập thành công ");
                            ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.CONFIRM, false));
                        } else {
                            MessageUtils.showLong(activity, "Đã lưu trữ thành công ");
                            ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.EXTRA, false));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showIndicator(null, false);
                    }

                    @Override
                    public void onNext(Response<ShippingAdressResponse> shippingAdressResponseResponse) {
                        if (shippingAdressResponseResponse.isSuccessful())
                            response = shippingAdressResponseResponse.body();
                    }
                });
    }

    @Override
    public void makeAnOrder() {
        view.showIndicator("Đang tạo đơn hàng, Vui lòng chờ ...", true);

        Observable<Response<BillingDetailResponse>> observable =
                client.makeAnOrder(CartSharedPrefrence.getCartId(activity), UserUtils.getUser(activity).getId());

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<BillingDetailResponse>>() {
                    private BillingDetailResponse billingDetailResponse;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        if (billingDetailResponse.isSuccess()) {
                            RealmBillingUtils.clearBillingConfirm();
                            RealmCartUtils.clear();
                            CartSharedPrefrence.saveCartId(null, activity);
                            new MaterialDialog.Builder(activity)
                                    .title("Livizi")
                                    .content("Đã tạo đơn hàng thành công")
                                    .positiveText("Xác nhận")
                                    .cancelable(false)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            ActionUtils.go(activity, 0);
                                        }
                                    })
                                    .show();
                        }
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
    public void loadBanks() {
        Observable<Response<BankResponse>> observable =
                client.getBanks();

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<BankResponse>>() {
                    private BankResponse bankResponse;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        if (bankResponse.isSuccess())
                            RealmBankUtils.updateAll(bankResponse.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(null, false);
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<BankResponse> bankResponseResponse) {
                        if (bankResponseResponse.isSuccessful())
                            bankResponse = bankResponseResponse.body();
                    }
                });

    }

    @Override
    public void loadInvoiceAddressDTO(String userId) {
        Observable<Response<ListInvoiceAddressDTOResponse>> observable =
                client.getInvoiceAddress(userId);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<ListInvoiceAddressDTOResponse>>() {
                    private ListInvoiceAddressDTOResponse response;
                    @Override
                    public void onCompleted() {
                        RealmAddressUtills.updateAllInvoiceDTO(response.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Response<ListInvoiceAddressDTOResponse> listInvoiceAddressDTOResponseResponse) {
                        if (listInvoiceAddressDTOResponseResponse.isSuccessful())
                            response = listInvoiceAddressDTOResponseResponse.body();
                    }
                });
    }

    @Override
    public void loadShippingAddressDTO(String userId) {

    }

    @Override
    public void createInvoiceAddress(String userId, String companyName, String taxCode, String address) {
        Observable<Response<InvoiceAddressDTOResponse>> observable =
                client.updateInvoiceAddress(userId, companyName, taxCode, address);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<InvoiceAddressDTOResponse>>() {
                    private InvoiceAddressDTOResponse response;
                    @Override
                    public void onCompleted() {
                        MessageUtils.showLong(activity, "Đã tạo mới thành công");
                        List<InvoiceAddressDTO> list = new ArrayList<InvoiceAddressDTO>();
                        list.add(response.getResult());
                        RealmAddressUtills.saveInvoiceAddressDTO(list);
                    }

                    @Override
                    public void onError(Throwable e) {
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
