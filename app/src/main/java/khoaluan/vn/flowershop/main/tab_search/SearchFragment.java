package khoaluan.vn.flowershop.main.tab_search;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.main.tab_home.FlowerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchContract.View, Base,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,
                                                            FloatingSearchView.OnSearchListener {
    private SearchContract.Presenter presenter;
    private View root;
    private FlowerAdapter adapter;
    private List<Flower> flowers;
    private GridLayoutManager gridLayoutManager;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FloatingSearchView floatingSearchView;
    private Activity activity;
    private String currentQuery;
    public SearchFragment() {
        // Required empty public constructor
    }
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, root);
        showUI();
        setUpFloatSearch();
        initilizeGridview();
        setDeviderForGridView();
        return root;
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
        setEnableRefresh(false);
    }

    @Override
    public void setUpFloatSearch() {
        floatingSearchView = (FloatingSearchView) root.findViewById(R.id.floating_search_view);
        floatingSearchView.setOnSearchListener(this);
    }

    @Override
    public void initilizeGridview() {
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
    public void showDataSearch(List<Flower> flowers, boolean isHasNext) {
        this.flowers.addAll(flowers);
        adapter.notifyDataSetChanged();
        adapter.openLoadMore(this.flowers.size(), isHasNext);
    }

    @Override
    public void finishLoadMore(boolean finish) {
        if (finish)
            adapter.notifyDataChangedAfterLoadMore(finish);
        else
            adapter.openLoadMore(finish);
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
    public void showNoResult() {
        View emptyView = activity.getLayoutInflater().inflate(R.layout.search_empty, (ViewGroup) recyclerView.getParent(), false);
        adapter.setEmptyView(emptyView);
    }

    @Override
    public void setEnableRefresh(boolean active) {
        swipeRefreshLayout.setEnabled(active);
    }

    @Override
    public void setDeviderForGridView() {
        SpacesItemDecoration decoration = new SpacesItemDecoration(GRID_VIEW_DISTANCE);
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

    }

    @Override
    public void onSearchAction(String currentQuery) {
        this.currentQuery = currentQuery;
        showIndicator(true);
        presenter.loadDataBySearch(currentQuery);
    }

    @Override
    public void onRefresh() {
        presenter.loadDataBySearch(currentQuery);
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.loadMoreDataBySearch();
    }

    @Override
    public void noInternetConnectTion() {
        Snackbar.make(recyclerView, R.string.no_internet_connecttion, Snackbar.LENGTH_INDEFINITE)
                .setDuration(5000)
                .show();
    }
}
