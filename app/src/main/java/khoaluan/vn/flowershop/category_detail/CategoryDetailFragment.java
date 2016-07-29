package khoaluan.vn.flowershop.category_detail;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.detail.DetailsActivity;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.main.tab_home.FlowerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDetailFragment extends BaseFragment implements CategoryDetailContract.View, Base, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private CategoryDetailContract.Presenter presenter;
    private View root;
    private Activity activity;
    private FlowerAdapter adapter;
    private List<Flower> flowers;

    private GridLayoutManager gridLayoutManager;

    @BindView(R.id.toolbar)
    Toolbar toolbarView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    public static CategoryDetailFragment newInstance() {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_category_detail, container, false);
        ButterKnife.bind(this, root);
        // UI
        initilizeToolBar();
        showUI();
        // Initialize GridView
        initilizeGridView();
        setDeviderForGridView();
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
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int index) {
                showFlowerDetails(flowers.get(index));
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
    public void showFlowerDetails(Flower flower) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(FLOWER_PARCELABLE, flower);
        getActivity().startActivity(intent);
    }

    @Override
    public void setEmptyRecyclerView(String message) {
        View emptyView = activity.getLayoutInflater().inflate(R.layout.recycler_view_empty, (ViewGroup) recyclerView.getParent(), false);
        TextView textView = (TextView) emptyView.findViewById(R.id.textView);
        textView.setText(message);
        adapter.setEmptyView(emptyView);
    }

    @Override
    public void setPresenter(CategoryDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initilizeToolBar() {
        toolbarView = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarView);
        toolbarView.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarView.setNavigationIcon(R.drawable.ic_back);
        toolbarView.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(presenter.getCategory().getName());
        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onRefresh() {
        presenter.loadRefreshData();
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.loadMoreData();
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
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setDuration(5000)
                .show();
    }
}
