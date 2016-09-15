package khoaluan.vn.flowershop.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForOrder;
import khoaluan.vn.flowershop.realm_data_local.RealmBillingUtils;
import khoaluan.vn.flowershop.utils.ActivityUtils;

public class OrderActivity extends AppCompatActivity {
    private OrderContract.Presenter presenter;
    private ActionDefined actionDefined;
    private OrderContract.View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        actionDefined = (ActionDefined) getIntent().getParcelableExtra(Action.ACTION_FOR_ORDER);

        switch (actionDefined.getGo()) {
            case ActionForOrder.INITIALIZE:
                view = (InitializeFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                view = InitializeFragment.newInstance(actionDefined);
                break;
            case ActionForOrder.EXTRA:
                view = (ExtraInfoFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                view = ExtraInfoFragment.newInstance(actionDefined);
                break;
            case ActionForOrder.CONFIRM:
                view = (ConfirmFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);

                view = ConfirmFragment.newInstance();
                break;
            case ActionForOrder.BANK:
                view = (BankFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                view = BankFragment.newInstance();
                break;
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), (Fragment) view, R.id.contentFrame);

        presenter = new OrderPresenter(view, OrderActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RealmBillingUtils.clearBillingConfirm();
    }
}
