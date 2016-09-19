package khoaluan.vn.flowershop.notifycation;


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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Notifycation;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.realm_data_local.RealmNotifycationUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifycationFragment extends BaseFragment implements NotifycationContract.View, CommonView.ToolBar, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, Base {

    private NotifycationContract.Presenter presenter;
    private View root;
    private Activity activity;
    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.toolbar)
    Toolbar toolbarView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private NotifycationAdapter adapter;

    private RealmResults<Notifycation> notifycations;

    public NotifycationFragment() {
        // Required empty public constructor
    }

    public static NotifycationFragment newInstance() {
        NotifycationFragment fragment = new NotifycationFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_notifycation, container, false);
        ButterKnife.bind(this, root);
        initilizeToolBar();
        showUI();
        setUpRecyclerView();
        showIndicator(true);
        presenter.loadNotifycation(UserUtils.getUser(activity).getId());
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
    public void setUpRecyclerView() {
        notifycations =presenter.loadNotifycationLocal();
        linearLayoutManager = new LinearLayoutManager(activity);
        adapter = new NotifycationAdapter(notifycations);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setOnLoadMoreListener(this);
        View viewLoadingMore = getActivity().getLayoutInflater().inflate(R.layout.loading_more_ui,
                (ViewGroup) recyclerView.getParent(), false);
        adapter.setLoadingView(viewLoadingMore);
        recyclerView.setAdapter(adapter);
        notifycations.addChangeListener(new RealmChangeListener<RealmResults<Notifycation>>() {
            @Override
            public void onChange(RealmResults<Notifycation> element) {
                adapter.notifyDataSetChanged();
                adapter.notifyDataChangedAfterLoadMore(true);
                adapter.openLoadMore(notifycations.size(), presenter.isHasNext());
            }
        });

        View view_empty = activity.getLayoutInflater().inflate(R.layout.empty_recycler_view,
                (ViewGroup) recyclerView.getParent(), false);
        TextView textView = (TextView) view_empty.findViewById(R.id.tv_empty);
        textView.setText("Bạn chưa có thông báo nào");
        adapter.setEmptyView(view_empty);

        SpacesItemDecoration decoration = new SpacesItemDecoration(PRODUCT_DISTANCE);
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void setPresenter(NotifycationContract.Presenter presenter) {
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Thông báo từ hệ thống");
        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onRefresh() {
        presenter.loadNotifycation(UserUtils.getUser(activity).getId());
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
    public void onLoadMoreRequested() {
        presenter.loadNotifycationMore(UserUtils.getUser(activity).getId());
    }

    @Override
    public void onDestroy() {
        if (notifycations != null)
            notifycations.removeChangeListeners();
        super.onDestroy();
    }
}
