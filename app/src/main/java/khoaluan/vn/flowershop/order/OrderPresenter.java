package khoaluan.vn.flowershop.order;

import android.app.Activity;

import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.OrderClient;
import khoaluan.vn.flowershop.retrofit.client.UserClient;

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
}
