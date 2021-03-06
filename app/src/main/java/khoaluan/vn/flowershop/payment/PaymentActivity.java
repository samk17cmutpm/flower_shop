package khoaluan.vn.flowershop.payment;

import android.os.Bundle;

import khoaluan.vn.flowershop.BaseActivity;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.utils.ActivityUtils;

public class PaymentActivity extends BaseActivity {
    private PaymentContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        PaymentFragment fragment =
                (PaymentFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = PaymentFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame
            );
        }

        presenter = new PaymentPresenter(fragment, PaymentActivity.this);
    }

}
