package khoaluan.vn.flowershop.user_data;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.User;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.user_data.billings.MultipleBillingItem;

/**
 * A simple {@link Fragment} subclass.
 */
// Cập nhập thông tin cá nhân
public class UserDataUpdateInfoFragment extends BaseFragment implements UserDataContract.View, CommonView.ToolBar {

    private UserDataContract.Presenter presenter;

    private View root;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.update)
    Button buttonUpdate;

    @BindView(R.id.full_name)
    EditText fullName;

    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.phone)
    EditText phone;

    private Activity activity;

    private ProgressDialog progressDialog;

    public UserDataUpdateInfoFragment() {
        // Required empty public constructor
    }

    public static UserDataUpdateInfoFragment newInstance() {
        UserDataUpdateInfoFragment fragment = new UserDataUpdateInfoFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_user_data_update_info, container, false);
        ButterKnife.bind(this, root);
        showUI();
        initilizeToolBar();
        return root;
    }

    @Override
    public void showUI() {
        activity = getActivity();
        progressDialog = new ProgressDialog(activity, ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        final User user = UserUtils.getUser(getActivity());

        fullName.setText(user.getFullName());
        address.setText(user.getAddress());
        phone.setText(user.getPhone());

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fullName.getText().toString().isEmpty())
                    user.setFullName(fullName.getText().toString());

                if (!address.getText().toString().isEmpty())
                    user.setAddress(address.getText().toString());

                if (!phone.getText().toString().isEmpty())
                    user.setPhone(phone.getText().toString());
                showIndicator(true, "Đang cập nhập profile vui lòng chờ !");
                presenter.updateInfoProfile(user.getId(), user.getFullName(), user.getAddress(), user.getPhone());
            }
        });

    }

    @Override
    public void showIndicator(boolean active, String message) {
        if (active) {
            progressDialog.setMessage(message);
            progressDialog.show();
        } else {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    @Override
    public void done() {
        activity.onBackPressed();
    }

    @Override
    public void initilizeRecyclerView() {

    }

    @Override
    public void showIndicator(boolean active) {

    }

    @Override
    public void showBillingDetail(List<MultipleBillingItem> list) {

    }

    @Override
    public void setPresenter(UserDataContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initilizeToolBar() {
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Cập nhập thông tin cá nhân");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void noInternetConnectTion() {
        Snackbar.make(root, R.string.no_internet_connecttion, Snackbar.LENGTH_INDEFINITE).
                setAction(R.string.retry_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.loadData();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setDuration(5000)
                .show();
    }
}
