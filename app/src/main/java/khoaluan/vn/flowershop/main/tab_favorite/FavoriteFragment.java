package khoaluan.vn.flowershop.main.tab_favorite;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends BaseFragment implements FavoriteContract.View,SwipeRefreshLayout.OnRefreshListener, Base {

    private FavoriteContract.Presenter presenter;
    private View root;
    private Activity activity;
    private LinearLayoutManager linearLayoutManager;
    private List<FavoriteItem> favoriteItems;
    private FavoriteItemAdapter adapter;
    private RealmResults<Flower> flowerRealmFavoriteResults;
    private RealmResults<Flower> flowerRealmTopFlower;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view_main)
    RecyclerView recyclerView;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, root);
        showUI();
        presenter.loadTopProducts();
        return root;
    }

    @Override
    public void showUI() {

        activity = getActivity();
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setNestedScrollingEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setEnabled(false);

        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        flowerRealmFavoriteResults = presenter.loadFavoriteFlowers();
        flowerRealmTopFlower = presenter.loadTopFlowers();
        favoriteItems = new ArrayList<>();
        favoriteItems.addAll(presenter.convertData(flowerRealmFavoriteResults, flowerRealmTopFlower, "Được Mua Nhiều Nhất"));

        adapter = new FavoriteItemAdapter(activity, favoriteItems);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

//        View viewEmpty = activity.getLayoutInflater().inflate(R.layout.empty_favorite,
//                (ViewGroup) recyclerView.getParent(), false);
//        adapter.setEmptyView(viewEmpty);

        recyclerView.setAdapter(adapter);

        flowerRealmFavoriteResults.addChangeListener(new RealmChangeListener<RealmResults<Flower>>() {
            @Override
            public void onChange(RealmResults<Flower> element) {
                updateChange(element);
            }
        });
    }



    @Override
    public void showIndicator(final boolean active) {
        swipeRefreshLayout.setEnabled(active);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void updateChange(List<Flower> flowers) {
        favoriteItems = null;
        favoriteItems = new ArrayList<FavoriteItem>();
        favoriteItems.addAll(presenter.convertData(flowers, this.flowerRealmTopFlower, "Được Mua Nhiều Nhất"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateTop(List<Flower> flowers) {
        favoriteItems = null;
        favoriteItems = new ArrayList<FavoriteItem>();
        favoriteItems.addAll(presenter.convertData(this.flowerRealmFavoriteResults, flowers, "Được Mua Nhiều Nhất"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onDestroy() {
        if (flowerRealmFavoriteResults != null)
            flowerRealmFavoriteResults.removeChangeListeners();
        super.onDestroy();
    }
}
