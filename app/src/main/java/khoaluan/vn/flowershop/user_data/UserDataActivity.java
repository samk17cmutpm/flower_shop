package khoaluan.vn.flowershop.user_data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseActivity;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForUserData;
import khoaluan.vn.flowershop.user_data.billings.UserDataBillingDetailFragment;
import khoaluan.vn.flowershop.user_data.billings.UserDataBillingsFragment;
import khoaluan.vn.flowershop.utils.ActionUtils;
import khoaluan.vn.flowershop.utils.ActivityUtils;

public class UserDataActivity extends BaseActivity implements Base{

    private UserDataContract.Presenter presenter;
    private ActionDefined actionDefined;
    private UserDataContract.View view;
    private Billing billing;

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

            case ActionForUserData.SHIPPING_ADDRESS:
                ShippingAddressDTO shippingAddressDTO = (ShippingAddressDTO) getIntent().getParcelableExtra(Action.ACTION_FOR_SHIPPING_ADDRESS);
                view = (AddAndEditShippingAddressFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);

                view = AddAndEditShippingAddressFragment.newInstance(shippingAddressDTO);
                break;

            case ActionForUserData.INVOICE_ADDRESS:
                InvoiceAddressDTO invoiceAddressDTO = (InvoiceAddressDTO) getIntent().getParcelableExtra(Action.ACTION_FOR_INVOICE_ADDRESS);
                view = (AddAndEditInvoiceAddressFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);

                view = AddAndEditInvoiceAddressFragment.newInstance(invoiceAddressDTO);
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

            case ActionForUserData.BILLING_DETAIL:
                billing = (Billing) getIntent().getParcelableExtra(BILLING_PARCELABLE);

                view = (UserDataBillingDetailFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                view = UserDataBillingDetailFragment.newInstance(billing);
                break;

            case ActionForUserData.POLICY:
                view = (UserDataPolicyFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                view = UserDataPolicyFragment.newInstance();
                break;

            case ActionForUserData.ABOUT:
                view = (UserDataAboutFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                view = UserDataAboutFragment.newInstance();
                break;
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), (Fragment) view, R.id.contentFrame);
        presenter = new UserDataPresenter(UserDataActivity.this, view);

    }

    @Override
    public void onBackPressed() {
        Intent intent;
        switch (actionDefined.getGo()) {
            case ActionForUserData.BILLING_DETAIL:
                intent = new Intent(UserDataActivity.this, UserDataActivity.class);
                intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.BILLLING));
                startActivity(intent);
                break;
            case ActionForUserData.SHIPPING_ADDRESS:
                intent = new Intent(UserDataActivity.this, UserDataActivity.class);
                intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.DELIVERY_ADDRESS));
                startActivity(intent);
                break;
            case ActionForUserData.INVOICE_ADDRESS:
                intent = new Intent(UserDataActivity.this, UserDataActivity.class);
                intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.BILLING_INFO));
                startActivity(intent);
                break;
            default:
                ActionUtils.go(UserDataActivity.this, 4);
                break;
        }
    }

}
