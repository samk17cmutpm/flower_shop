package khoaluan.vn.flowershop.rating;


import android.app.Activity;
import android.content.Intent;
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
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Rating;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForRating;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends BaseFragment implements RatingContract.View, CommonView.ToolBar, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, Base {

    private RatingContract.Presenter presenter;
    private View root;
    private Activity activity;
    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.toolbar)
    Toolbar toolbarView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.write_rating)
    LinearLayout write_rating;

    private RatingAdapter adapter;

    private List<Rating> ratings;

    private Flower flower;
    private FlowerSuggesstion flowerSuggesstion;

    public RatingFragment() {
        // Required empty public constructor
    }

    public static RatingFragment newInstance(Flower flower, FlowerSuggesstion flowerSuggesstion) {
        RatingFragment fragment = new RatingFragment();
        Bundle args = new Bundle();
        args.putParcelable(FLOWER_PARCELABLE, flower);
        args.putParcelable(LIST_FLOWER_PARCELABLE, flowerSuggesstion);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_rating, container, false);
        flower = (Flower) getArguments().getParcelable(FLOWER_PARCELABLE);
        flowerSuggesstion = (FlowerSuggesstion) getArguments().getParcelable(LIST_FLOWER_PARCELABLE);
        ButterKnife.bind(this, root);
        initilizeToolBar();
        showUI();
        setUpRecyclerView();
        showIndicator(true);
        presenter.loadRating(flower.getId());
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

        write_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, RatingActivity.class);
                intent.putExtra(FLOWER_PARCELABLE, flower);
                intent.putExtra(LIST_FLOWER_PARCELABLE, flowerSuggesstion);
                intent.putExtra(Action.ACTION_FOR_RATING, new ActionDefined(ActionForRating.ADD_RATINGS));
                activity.startActivity(intent);
                activity.finish();
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
    public void showIndicator(boolean active, String message) {

    }

    @Override
    public void setUpRecyclerView() {
        ratings = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(activity);
        adapter = new RatingAdapter(ratings);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(adapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(PRODUCT_DISTANCE);
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void addToRatings(List<Rating> ratings) {
        this.ratings.addAll(ratings);
        adapter.notifyDataChangedAfterLoadMore(true);
        adapter.openLoadMore(this.ratings.size(), presenter.isHasNext());
    }

    @Override
    public void setPresenter(RatingContract.Presenter presenter) {
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

    @Override
    public void initilizeToolBar() {
        toolbarView = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarView);
        toolbarView.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarView.setNavigationIcon(R.drawable.ic_back);
        toolbarView.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Nhận xét của khách hàng");
        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }

    @Override
    public void onRefresh() {
        showIndicator(false);
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.loadRatingMore(flower.getId());
    }
}
