package khoaluan.vn.flowershop.order;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.City;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.response.CityResponse;
import khoaluan.vn.flowershop.data.response.DistrictResponse;
import khoaluan.vn.flowershop.data.response.OrderResponse;
import khoaluan.vn.flowershop.realm_data_local.RealmCityUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.OrderClient;
import khoaluan.vn.flowershop.retrofit.client.UserClient;
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
    public void setBillingOrder(String cartId, String userId, String name, String phone, String mail, String cityid, String districtid, String address) {
        Observable<Response<OrderResponse>> observable =
                client.setBillingOrder(cartId, userId, name, phone, mail, cityid, districtid, address);

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<OrderResponse>>() {
                    private boolean success;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        if (success)
                            view.sendDataShipping();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(null, false);
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<OrderResponse> orderResponseResponse) {
                        if (orderResponseResponse.isSuccessful())
                            success = orderResponseResponse.body().isSuccess();
                    }
                });
    }

    @Override
    public void setShippingOrder(String cartId, String userId, String name, String phone, String mail, String cityid, String districtid, final String address) {
        Observable<Response<OrderResponse>> observable =
                client.setShippingOrder(cartId, userId, name, phone, mail, cityid, districtid, address);

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<OrderResponse>>() {
                    private boolean success;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(null, false);
                        if (success)
                            MessageUtils.showLong(activity, "Đã lưu trữ thành công ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(null, false);
                        e.printStackTrace();
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<OrderResponse> orderResponseResponse) {
                        if (orderResponseResponse.isSuccessful())
                            success = orderResponseResponse.body().isSuccess();
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
