package khoaluan.vn.flowershop.order;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Bank;
import khoaluan.vn.flowershop.data.model_parse_and_realm.BillingAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ExtraInformationDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForOrder;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.order.order_confirm.BankAdapter;
import khoaluan.vn.flowershop.realm_data_local.RealmBankUtils;
import khoaluan.vn.flowershop.utils.ActionUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankFragment extends BaseFragment implements OrderContract.View, CommonView.ToolBar, Base {

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

    private BankAdapter adapter;

    private RealmResults<Bank> banks;

    private final SpacesItemDecoration spaceProduct = new SpacesItemDecoration(PRODUCT_DISTANCE);


    @BindView(R.id.go)
    LinearLayout linearLayoutGo;

    public BankFragment() {
        // Required empty public constructor
    }

    public static BankFragment newInstance() {
        BankFragment fragment = new BankFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_bank, container, false);
        ButterKnife.bind(this, root);
        initilizeToolBar();
        showUI();
        showIndicator(null, true);
        presenter.loadBanks();
        return root;
    }

    @Override
    public void showUI() {
        banks = RealmBankUtils.all();
        banks.addChangeListener(new RealmChangeListener<RealmResults<Bank>>() {
            @Override
            public void onChange(RealmResults<Bank> element) {
                adapter.notifyDataSetChanged();
            }
        });
        activity = getActivity();
        layoutManager = new LinearLayoutManager(activity);
        swipeRefreshLayout.setNestedScrollingEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BankAdapter(banks, activity);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        recyclerView.removeItemDecoration(spaceProduct);
        recyclerView.addItemDecoration(spaceProduct);
        recyclerView.setAdapter(adapter);

        linearLayoutGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionUtils.goOrder(getActivity(), new ActionDefined(ActionForOrder.CONFIRM, false));
            }
        });

        new MaterialDialog.Builder(activity)
                .title("Livizi")
                .content("Chấp nhận thanh toán qua chuyển khoản ngân hàng, Vui lòng chuyển khoản qua một trong các ngân hàng sau ")
                .positiveText("Xác nhận")
                .show();
    }

    @Override
    public void updateDistrict(List<District> districts, boolean problem) {

    }

    @Override
    public void updateDistrictRc(List<District> districts, boolean problem) {

    }

    @Override
    public void showIndicator(String message, final boolean active) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
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
    public void editFormSender(BillingAddressDTO billingAddressDTO) {

    }

    @Override
    public void editFormInvoice(InvoiceAddressDTO invoiceAddressDTO) {

    }

    @Override
    public void editFormShipping(ShippingAddressDTO shippingAddressDTO) {

    }

    @Override
    public void editFormExtra(ExtraInformationDTO extraInformationDTO) {

    }

    @Override
    public boolean isEdited() {
        return false;
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Thanh toán");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionUtils.goOrder(getActivity(), new ActionDefined(ActionForOrder.EXTRA, true));
            }
        });
    }
}
