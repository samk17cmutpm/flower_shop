package khoaluan.vn.flowershop.payment;

import android.app.Activity;

/**
 * Created by samnguyen on 9/8/16.
 */
public class PaymentPresenter implements PaymentContract.Presenter {
    private final PaymentContract.View view;
    private final Activity activity;

    public PaymentPresenter(PaymentContract.View view, Activity activity) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void syncData() {

    }

    @Override
    public void start() {

    }
}
