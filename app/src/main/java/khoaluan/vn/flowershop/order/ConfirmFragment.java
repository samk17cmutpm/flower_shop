package khoaluan.vn.flowershop.order;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
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
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.order.order_confirm.MultipleOrderBillingItemAdapter;
import khoaluan.vn.flowershop.realm_data_local.RealmBillingUtils;
import khoaluan.vn.flowershop.utils.ConvertUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmFragment extends BaseFragment implements OrderContract.View, CommonView.ToolBar {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private View root;

    private OrderContract.Presenter presenter;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private Activity activity;

    private Billing billing;

    private ProgressDialog progressDialog;

    private MultipleOrderBillingItemAdapter adapter;


    public ConfirmFragment() {
        // Required empty public constructor
    }

    public static ConfirmFragment newInstance() {
        ConfirmFragment fragment = new ConfirmFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_confirm, container, false);
        ButterKnife.bind(this, root);
        initilizeToolBar();
        showUI();
        return root;
    }

    @Override
    public void showUI() {
        activity = getActivity();
        layoutManager = new LinearLayoutManager(activity);
        swipeRefreshLayout.setNestedScrollingEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MultipleOrderBillingItemAdapter(activity, ConvertUtils.convertBillingToMultipleOrderItem(RealmBillingUtils.getBillCofirm()));
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void updateDistrict(List<District> districts, boolean problem) {

    }

    @Override
    public void updateDistrictRc(List<District> districts, boolean problem) {

    }

    @Override
    public void showIndicator(String message, boolean active) {

    }

    @Override
    public void setUpDropData() {

    }

    @Override
    public void setUpBilling(boolean active) {

    }

    @Override
    public void setSenderInfo() {

    }

    @Override
    public void setSameRc(boolean reset) {

    }

    @Override
    public boolean isSenderInfoDone() {
        return false;
    }

    @Override
    public boolean isBillingDone() {
        return false;
    }

    @Override
    public boolean isInvoice() {
        return false;
    }

    @Override
    public void sendDataBilling() {

    }

    @Override
    public void sendDataShipping() {

    }

    @Override
    public void sendInvoice() {

    }

    @Override
    public void showDateTimePicker() {

    }

    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Thông tin đặt hàng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}
