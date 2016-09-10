package khoaluan.vn.flowershop.main.tab_info;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForUserData;
import khoaluan.vn.flowershop.data.shared_prefrences.UserSharedPrefrence;
import khoaluan.vn.flowershop.sign_in.SignInActivity;
import khoaluan.vn.flowershop.user_data.UserDataActivity;
import khoaluan.vn.flowershop.utils.ActionUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements InfoContract.View, Base, View.OnClickListener{

    private InfoContract.Presenter presenter;
    private View root;

    @BindView(R.id.rl_sign_in)
    RelativeLayout rl_sign_in;

    @BindView(R.id.tv_sign_in)
    TextView tvSignInName;

    @BindView(R.id.rl_sign_out)
    RelativeLayout rl_sign_out;

    @BindView(R.id.rl_update_info)
    RelativeLayout rl_update_info;

    @BindView(R.id.rl_billing)
    RelativeLayout rl_billing;

    @BindView(R.id.rl_payment_address)
    RelativeLayout rl_payment_address;

    @BindView(R.id.rl_delivery_address)
    RelativeLayout rl_delivery_address;

    @BindView(R.id.rl_info_payment)
    RelativeLayout rl_info_payment;

    @BindView(R.id.rl_policy)
    RelativeLayout rl_policy;

    @BindView(R.id.rl_contact)
    RelativeLayout rl_contact;

    @BindView(R.id.rl_idea)
    RelativeLayout rl_idea;


    private Activity activity;

    public InfoFragment() {
        // Required empty public constructor
    }

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, root);
        showUI();
        return root;
    }

    @Override
    public void showUI() {
        activity = getActivity();

        rl_sign_in.setOnClickListener(this);

        rl_sign_out.setOnClickListener(this);

        rl_update_info.setOnClickListener(this);
        rl_billing.setOnClickListener(this);
        rl_payment_address.setOnClickListener(this);
        rl_delivery_address.setOnClickListener(this);
        rl_info_payment.setOnClickListener(this);
        rl_policy.setOnClickListener(this);
        rl_contact.setOnClickListener(this);
        rl_idea.setOnClickListener(this);

        if (UserSharedPrefrence.isSignedIn(activity)) {
            rl_sign_in.setEnabled(false);
            tvSignInName.setText(UserSharedPrefrence.getUser(activity).getEmail());
        } else {
            rl_sign_out.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPresenter(InfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_sign_in:
                intent = new Intent(activity, SignInActivity.class);
                activity.startActivity(intent);
                activity.finish();
                break;
            case R.id.rl_update_info:
                intent = new Intent(activity, UserDataActivity.class);
                intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.UPDATE_INFO));
                activity.startActivity(intent);
                break;
            case R.id.rl_billing:
                intent = new Intent(activity, UserDataActivity.class);
                intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.BILLLING));
                activity.startActivity(intent);
                break;
            case R.id.rl_payment_address:
                intent = new Intent(activity, UserDataActivity.class);
                intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.PAYMENT_ADDRESS));
                activity.startActivity(intent);
                break;
            case R.id.rl_delivery_address:
                intent = new Intent(activity, UserDataActivity.class);
                intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.DELIVERY_ADDRESS));
                activity.startActivity(intent);
                break;
            case R.id.rl_info_payment:
                intent = new Intent(activity, UserDataActivity.class);
                intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.BILLING_INFO));
                activity.startActivity(intent);
                break;
            case R.id.rl_policy:
                break;
            case R.id.rl_contact:
                break;
            case R.id.rl_idea:
                intent = new Intent(activity, UserDataActivity.class);
                intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.IDEA));
                activity.startActivity(intent);
                break;
            case R.id.rl_sign_out:
                new MaterialDialog.Builder(activity)
                        .title("Livizi")
                        .content("Bạn muốn đăng xuất ra khỏi ứng dụng ?")
                        .positiveText("Có")
                        .negativeText("Không")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                presenter.signOut();
                                ActionUtils.go(activity, TAB_INFO);
                            }
                        })
                        .show();
                break;
        }
    }
}
