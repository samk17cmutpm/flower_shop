package khoaluan.vn.flowershop.main.tab_info;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.InfoType;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.sign_in.SignInActivity;
import khoaluan.vn.flowershop.sign_up.SignUpActivity;
import khoaluan.vn.flowershop.utils.UserUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements InfoContract.View, Base, View.OnClickListener{

    private InfoContract.Presenter presenter;
    private View root;

    @BindView(R.id.rl_sign_in)
    RelativeLayout relativeLayoutSignIn;

    @BindView(R.id.rl_sign_up)
    RelativeLayout relativeLayoutSignUp;

    @BindView(R.id.rl_billing)
    RelativeLayout relativeLayoutBilling;

    @BindView(R.id.rl_policy)
    RelativeLayout relativeLayoutPolicy;

    @BindView(R.id.rl_contact)
    RelativeLayout relativeLayoutContact;

    @BindView(R.id.rl_sign_out)
    RelativeLayout relativeLayoutSignOut;

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

        relativeLayoutSignIn.setOnClickListener(this);
        relativeLayoutSignUp.setOnClickListener(this);
        relativeLayoutBilling.setOnClickListener(this);
        relativeLayoutPolicy.setOnClickListener(this);
        relativeLayoutContact.setOnClickListener(this);
        relativeLayoutSignOut.setOnClickListener(this);

        if (UserUtils.isSignedIn()) {
            relativeLayoutSignUp.setVisibility(View.GONE);
        } else {
            relativeLayoutSignOut.setVisibility(View.GONE);
        }
    }

    @Override
    public void initilizeRecyclerView() {


    }

    @Override
    public void showInfoTypes(List<InfoType> infoTypes) {

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
            case R.id.rl_sign_up:
                intent = new Intent(activity, SignUpActivity.class);
                activity.startActivity(intent);
                break;
            case R.id.rl_billing:
                break;
            case R.id.rl_policy:
                break;
            case R.id.rl_contact:
                break;
            case R.id.rl_sign_out:
                break;
        }
    }
}
