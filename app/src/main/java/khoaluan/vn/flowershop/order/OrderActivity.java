package khoaluan.vn.flowershop.order;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.utils.ActivityUtils;

public class OrderActivity extends AppCompatActivity {
    private OrderContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        OrderFragment fragment =
                (OrderFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = OrderFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);
        }

        presenter = new OrderPresenter(fragment, OrderActivity.this);
    }

}
