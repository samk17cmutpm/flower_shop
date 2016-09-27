package khoaluan.vn.flowershop.payment;

import android.app.Activity;

import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.OrderClient;

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
