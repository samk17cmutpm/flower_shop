package khoaluan.vn.flowershop.payment;

import android.app.Activity;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.City;
import khoaluan.vn.flowershop.data.response.CityResponse;
import khoaluan.vn.flowershop.data.response.DistrictResponse;
import khoaluan.vn.flowershop.realm_data_local.RealmCityUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.OrderClient;
import khoaluan.vn.flowershop.retrofit.client.UserClient;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 9/8/16.
 */
public class PaymentPresenter implements PaymentContract.Presenter {
    private final PaymentContract.View view;
    private final Activity activity;
    private final OrderClient client;
    public PaymentPresenter(PaymentContract.View view, Activity activity) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        client =  ServiceGenerator.createService(OrderClient.class);
    }

    @Override
    public void syncData() {

    }
    @Override
    public void start() {

    }
}
