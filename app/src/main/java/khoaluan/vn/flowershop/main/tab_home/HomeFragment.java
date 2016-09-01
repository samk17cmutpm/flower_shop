package khoaluan.vn.flowershop.main.tab_home;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeContract.View,
        SwipeRefreshLayout.OnRefreshListener, Base{

    private HomeContract.Presenter presenter;
    private View root;
    private MultipleMainItemAdapter adapter;
    private Activity activity;

    private LinearLayoutManager linearLayoutManager;
    private List<MultipleMainItem> multipleMainItems;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view_main)
    RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        // UI
        showUI();
        // Initialize GridView
        initilizeMainView();
        // Loading data
        presenter.loadData();

        return root;
    }
    @Override
    public void initilizeMainView() {
        multipleMainItems = new ArrayList<>();
        adapter = new MultipleMainItemAdapter(activity, multipleMainItems);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        View view_empty = this.activity.getLayoutInflater().inflate(R.layout.empty_flower_shop,
                (ViewGroup) recyclerView.getParent(), false);
        adapter.setEmptyView(view_empty);
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int index) {

            }
        });
        recyclerView.setAdapter(adapter);
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

        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

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
    public void showTopProducts(List<MultipleMainItem> multipleMainItems) {
        this.multipleMainItems.addAll(multipleMainItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        View view_empty = this.activity.getLayoutInflater().inflate(R.layout.flowers_empty,
                (ViewGroup) recyclerView.getParent(), false);
        RelativeLayout relativeLayoutRefresh = (RelativeLayout)
                view_empty.findViewById(R.id.rl_refresh);
        relativeLayoutRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadTopProducts();
            }
        });
        adapter.setEmptyView(view_empty);
    }

    @Override
    public void showRealmData(List<MultipleMainItem> multipleMainItems) {
        this.multipleMainItems.addAll(multipleMainItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void noInternetConnectTion() {
        Snackbar.make(recyclerView, R.string.no_internet_connecttion, Snackbar.LENGTH_INDEFINITE).
                setAction(R.string.retry_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.loadAdvertisingItems();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setDuration(5000)
                .show();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRefresh() {
        presenter.refreshData();
    }

}
