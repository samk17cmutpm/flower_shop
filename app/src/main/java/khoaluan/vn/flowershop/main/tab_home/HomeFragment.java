package khoaluan.vn.flowershop.main.tab_home;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, Base{

    private HomeContract.Presenter presenter;
    private View root;
    private FlowerAdapter adapter;
    private List<Flower> flowers;
    private Activity activity;
    private GridLayoutManager gridLayoutManager;

    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

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
        initilizeGridView();
        setDeviderForGridView();
        // Loading data
        presenter.loadData();
        return root;
    }

    @Override
    public void clearAllDataLocal() {
        initilizeGridView();
    }

    @Override
    public void initilizeGridView() {
        gridLayoutManager = new GridLayoutManager(getActivity(), GRID_VIEW_SIZE);
        flowers = new ArrayList<>();
        adapter = new FlowerAdapter(activity, flowers);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setOnLoadMoreListener(this);
        View viewLoadingMore = getActivity().getLayoutInflater().inflate(R.layout.loading_more_ui,
                (ViewGroup) recyclerView.getParent(), false);
        adapter.setLoadingView(viewLoadingMore);
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
    }

    @Override
    public void showFlowers(List<Flower> flowers, boolean isHasNext) {
        this.flowers.addAll(flowers);
        adapter.notifyDataSetChanged();
        adapter.openLoadMore(this.flowers.size(), isHasNext);
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
    public void setDeviderForGridView() {
        SpacesItemDecoration decoration = new SpacesItemDecoration(GRID_VIEW_DISTANCE);
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void finishLoadMore(boolean finish) {
        if (finish)
            adapter.notifyDataChangedAfterLoadMore(finish);
        else
            adapter.openLoadMore(finish);
    }

    @Override
    public void noInternetConnectTion() {
        Snackbar.make(recyclerView, R.string.no_internet_connecttion, Snackbar.LENGTH_INDEFINITE).
                setAction(R.string.retry_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.loadData();
                    }
                })
                .setActionTextColor(R.color.colorAccent)
                .setDuration(5000)
                .show();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRefresh() {
        presenter.loadRefreshData();
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.loadMoreData();
    }
}
