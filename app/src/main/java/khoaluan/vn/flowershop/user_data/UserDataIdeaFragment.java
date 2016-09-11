package khoaluan.vn.flowershop.user_data;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.user_data.billings.MultipleBillingItem;

/**
 * A simple {@link Fragment} subclass.
 */

// Đóng góp ý tưởng

public class UserDataIdeaFragment extends BaseFragment implements UserDataContract.View, CommonView.ToolBar {

    private UserDataContract.Presenter presenter;

    private View root;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void showUI() {

    }

    @Override
    public void showIndicator(boolean active, String message) {

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
    public void noInternetConnectTion() {

    }
}
