package khoaluan.vn.flowershop.user_data;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForUserData;
import khoaluan.vn.flowershop.utils.ActivityUtils;

public class UserDataActivity extends AppCompatActivity {

    private UserDataContract.Presenter presenter;
    private ActionDefined actionDefined;
    private UserDataContract.View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        actionDefined = (ActionDefined) getIntent().getParcelableExtra(Action.ACTION_FOR_USER_DATA);

        switch (actionDefined.getGo()) {
            case ActionForUserData.UPDATE_INFO:
                view = (UserDataUpdateInfoFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                view = UserDataUpdateInfoFragment.newInstance();
                break;

            case ActionForUserData.BILLLING:
                view = (UserDataBillingsFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                view = UserDataBillingsFragment.newInstance();

                break;

            case ActionForUserData.PAYMENT_ADDRESS:
                view = (UserDataAddressPaymentFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);

                view = UserDataAddressPaymentFragment.newInstance();

                break;

            case ActionForUserData.DELIVERY_ADDRESS:
                view = (UserDataAddressDeliveryFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);

                view = UserDataAddressDeliveryFragment.newInstance();
                break;

            case ActionForUserData.BILLING_INFO:
                view = (UserDataBillingInfoFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);

                view = UserDataBillingInfoFragment.newInstance();
                break;

            case ActionForUserData.IDEA:
                view = (UserDataIdeaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);

                view = UserDataIdeaFragment.newInstance();
                break;
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), (Fragment) view, R.id.contentFrame);
        presenter = new UserDataPresenter(UserDataActivity.this, view);

    }

}
