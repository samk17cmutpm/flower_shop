package khoaluan.vn.flowershop.user_data.billings;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;
import khoaluan.vn.flowershop.user_data.UserDataContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDataBillingDetailFragment extends BaseFragment implements UserDataContract.View, CommonView.ToolBar, SwipeRefreshLayout.OnRefreshListener, Base {


    private UserDataContract.Presenter presenter;

    private View root;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private Activity activity;

    private Billing billing;

    private MultipleBillingItemAdapter adapter;
    public UserDataBillingDetailFragment() {
        // Required empty public constructor
    }

    public static UserDataBillingDetailFragment newInstance(Billing billing) {
        UserDataBillingDetailFragment fragment = new UserDataBillingDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(BILLING_PARCELABLE, billing);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        billing = (Billing) getArguments().getParcelable(BILLING_PARCELABLE);
        root = inflater.inflate(R.layout.fragment_user_data_billing_detail, container, false);
        ButterKnife.bind(this, root);
        showUI();
        initilizeToolBar();
        showIndicator(true);
        presenter.loadBillingDetail(billing.getId());
        return root;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void initilizeToolBar() {
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Đơn hàng của tôi");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void showUI() {
        activity = getActivity();
        layoutManager = new LinearLayoutManager(activity);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setNestedScrollingEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
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
    public void showIndicator(final boolean active) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showBillingDetail(List<MultipleBillingItem> list) {
        adapter = new MultipleBillingItemAdapter(activity, list);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(UserDataContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void noInternetConnectTion() {
        Snackbar.make(recyclerView, R.string.no_internet_connecttion, Snackbar.LENGTH_INDEFINITE).
                setAction(R.string.retry_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setDuration(5000)
                .show();
    }
}
