package khoaluan.vn.flowershop.search;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.data.request.SearchRequest;
import khoaluan.vn.flowershop.detail.DetailsActivity;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.main.tab_home.FlowerAdapter;
import khoaluan.vn.flowershop.utils.OnItemClickUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchContract.View, Base,CommonView.ToolBar,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{
    private SearchContract.Presenter presenter;
    private View root;
    private FlowerAdapter adapter;
    private List<Flower> flowers;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private MaterialSearchView searchView;
    private SearchRequest request;

    private ExpandableLayout expandable_suggestion;

    @BindView(R.id.iv_close_suggestion)
    ImageView iv_close_suggestion;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.search_suggestion)
    RecyclerView search_suggestion;

    private SearchSuggestionAdapter adapterSuggestion;

    private Toolbar toolbar;

    private Activity activity;

    private List<String> suggestions;
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
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, root);
        showUI();
        initilizeGridview();
        initilizeToolBar();
        showMaterialSearch();
        setDeviderForGridView();
        initializeSearchSuggestion();
        presenter.loadData();
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                // Handle this selection
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar.

        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        super.onCreateOptionsMenu(menu,inflater);
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

        expandable_suggestion = (ExpandableLayout) root.findViewById(R.id.expandable_suggestion);
    }

    @Override
    public void initilizeGridview() {
        gridLayoutManager = new GridLayoutManager(getActivity(), GRID_VIEW_SIZE);
        flowers = new ArrayList<>();
        adapter = new FlowerAdapter(activity, flowers, R.layout.flower_girdview_item);
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
            public void onItemClick(View view, int i) {
                OnItemClickUtils.flowerDetail(activity, flowers.get(i), new FlowerSuggesstion(flowers), false);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showFlowerDetails(Flower flower) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(FLOWER_PARCELABLE, flower);
        getActivity().startActivity(intent);
    }

    @Override
    public void showMaterialSearch() {
        searchView = (MaterialSearchView) root.findViewById(R.id.search_view);
        searchView.setHint("Tìm Kiếm ...");
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                expandable_suggestion.collapse();
                request = new SearchRequest(query, 0, 20, 0, 10000000, "COLOR;FLOWER;BIRTHDAY;TOPIC");
                showIndicator(true);
                presenter.loadDataBySearch(request);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!expandable_suggestion.isExpanded())
                    expandable_suggestion.expand();
                //Do some magic
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                activity.onBackPressed();
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            root.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    searchView.showSearch(true);
                }
            });
        } else {
            searchView.showSearch(true);
        }
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void initializeSearchSuggestion() {
        suggestions = new ArrayList<>();
        suggestions.add("hoa tình yêu");
        suggestions.add("hoa hồng");
        suggestions.add("hoa sinh nhật");
        suggestions.add("hoa ly");
        suggestions.add("hoa quỳnh");
        suggestions.add("hoa cúc");
        suggestions.add("hoa");

        linearLayoutManager = new LinearLayoutManager(activity);

        search_suggestion.setHasFixedSize(true);
        search_suggestion.setLayoutManager(linearLayoutManager);

        adapterSuggestion = new SearchSuggestionAdapter(suggestions);
        adapterSuggestion.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapterSuggestion.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                expandable_suggestion.collapse();
                request = new SearchRequest(suggestions.get(i), 0, 20, 0, 10000000, "COLOR;FLOWER;BIRTHDAY;TOPIC");
                showIndicator(true);
                presenter.loadDataBySearch(request);
            }
        });

        search_suggestion.setAdapter(adapterSuggestion);

        iv_close_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandable_suggestion.collapse();
            }
        });


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
        searchView.hideKeyboard(root);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showNoResult() {
        View emptyView = activity.getLayoutInflater().inflate(R.layout.recycler_view_empty, (ViewGroup) recyclerView.getParent(), false);
        adapter.setEmptyView(emptyView);
    }

    @Override
    public void setEnableRefresh(boolean active) {
        swipeRefreshLayout.setEnabled(active);
    }

    @Override
    public void setDeviderForGridView() {
        SpacesItemDecoration decoration = new SpacesItemDecoration(PRODUCT_DISTANCE);
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRefresh() {
        request.setCurrentPage(0);
        presenter.loadDataBySearch(request);
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

    @Override
    public void initilizeToolBar() {
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        toolbar.setNavigationIcon(R.drawable.ic_back);
//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.onBackPressed();
//            }
//        });
    }
}
