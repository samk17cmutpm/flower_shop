package khoaluan.vn.flowershop.user_data;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.user_data.billings.MultipleBillingItem;
import khoaluan.vn.flowershop.utils.ActionUtils;

/**
 * A simple {@link Fragment} subclass.
 */

// Đóng góp ý tưởng

public class UserDataIdeaFragment extends BaseFragment implements UserDataContract.View, CommonView.ToolBar {

    private UserDataContract.Presenter presenter;

    private View root;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.full_name)
    EditText full_name;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.phone)
    EditText phone;

    @BindView(R.id.title)
    EditText title;

    @BindView(R.id.content)
    EditText content;

    @BindView(R.id.send)
    Button send;

    private ProgressDialog progressDialog;

    public UserDataIdeaFragment() {
        // Required empty public constructor
    }

    public static UserDataIdeaFragment newInstance() {
        UserDataIdeaFragment fragment = new UserDataIdeaFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_user_data_idea, container, false);
        ButterKnife.bind(this, root);
        showUI();
        initilizeToolBar();
        return root;
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Góp ý");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionUtils.go(getActivity(), 4);
            }
        });
    }

    @Override
    public void showUI() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSenderInfoDone()) {
                    showIndicator(true, "Đang gởi thông tin");
                    presenter.sendFeedBack(
                            UserUtils.getUser(getActivity()).getId(),
                            email.getText().toString(),
                            phone.getText().toString(),
                            full_name.getText().toString(),
                            title.getText().toString(),
                            content.getText().toString()
                    );
                }
            }
        });

        content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.send_info || id == EditorInfo.IME_NULL) {
                    if (isSenderInfoDone()) {
                        showIndicator(true, "Đang gởi thông tin");
                        presenter.sendFeedBack(
                                UserUtils.getUser(getActivity()).getId(),
                                email.getText().toString(),
                                phone.getText().toString(),
                                full_name.getText().toString(),
                                title.getText().toString(),
                                content.getText().toString()
                        );
                    }
                    return true;
                }
                return false;
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
    public void updateDistrict(List<District> districts, boolean problem) {

    }

    @Override
    public void noInternetConnectTion() {


    }

    private boolean isSenderInfoDone() {
        View focusView = null;
        boolean cancel = false;
        if (content.getText().toString().isEmpty()) {
            content.setError(getString(R.string.error_field_required));

            focusView = this.content;
            cancel = true;
        }


        if (title.getText().toString().isEmpty()) {
            title.setError(getString(R.string.error_field_required));

            focusView = this.title;
            cancel = true;
        }

        if (phone.getText().toString().isEmpty()) {
            phone.setError(getString(R.string.error_field_required));

            focusView = this.phone;
            cancel = true;
        }

        if (email.getText().toString().isEmpty()) {
            email.setError(getString(R.string.error_field_required));

            focusView = this.email;
            cancel = true;
        }

        if (full_name.getText().toString().isEmpty()) {
            full_name.setError(getString(R.string.error_field_required));

            focusView = this.full_name;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }

        return !cancel;
    }
}
