package khoaluan.vn.flowershop.user_data;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.user_data.billings.MultipleBillingItem;
import khoaluan.vn.flowershop.user_data.billings.ShippingAddressAdapter;
import khoaluan.vn.flowershop.utils.ActionUtils;
import khoaluan.vn.flowershop.utils.CartUtils;

/**
 * A simple {@link Fragment} subclass.
 */
// ĐÂY LÀ ĐỊA CHỈ GIAO HÀNG

public class UserDataAddressDeliveryFragment extends BaseFragment implements UserDataContract.View, CommonView.ToolBar, Base,  SwipeRefreshLayout.OnRefreshListener{

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

    private RealmResults<ShippingAddressDTO> shippingAddressDTOs;

    private ShippingAddressAdapter adapter;

    private final SpacesItemDecoration spaceProduct = new SpacesItemDecoration(PRODUCT_DISTANCE);


    public UserDataAddressDeliveryFragment() {
        // Required empty public constructor
    }

    public static UserDataAddressDeliveryFragment newInstance() {
        UserDataAddressDeliveryFragment fragment = new UserDataAddressDeliveryFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_user_data_address_delivery, container, false);
        ButterKnife.bind(this, root);
        showUI();
        initilizeToolBar();
        initilizeRecyclerView();
        showIndicator(true);
        presenter.loadShippingAddressDTO(UserUtils.getUser(activity).getId());
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                // Handle this selection
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar.
        inflater.inflate(R.menu.user_data, menu);
        super.onCreateOptionsMenu(menu,inflater);
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Địa chỉ giao hàng");
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
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setNestedScrollingEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void showIndicator(boolean active, String message) {

    }

    @Override
    public void done() {

    }

    @Override
    public void initilizeRecyclerView() {

        shippingAddressDTOs = presenter.loadShippingAddressDTOLocal();
        layoutManager = new LinearLayoutManager(activity);

        adapter = new ShippingAddressAdapter(shippingAddressDTOs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(spaceProduct);

        shippingAddressDTOs.addChangeListener(new RealmChangeListener<RealmResults<ShippingAddressDTO>>() {
            @Override
            public void onChange(RealmResults<ShippingAddressDTO> element) {
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {

            }
        });

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

    }

    @Override
    public void updateDistrict(List<District> districts, boolean problem) {

    }

    @Override
    public void noInternetConnectTion() {
        Snackbar.make(recyclerView, R.string.no_internet_connecttion, Snackbar.LENGTH_INDEFINITE).
                setAction(R.string.retry_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.loadBillings(UserUtils.getUser(activity).getId());
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setDuration(5000)
                .show();
    }

    @Override
    public void onRefresh() {
        presenter.loadShippingAddressDTO(UserUtils.getUser(getActivity()).getId());
    }
}
