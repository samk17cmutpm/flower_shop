package khoaluan.vn.flowershop.detail;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
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
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Rating;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.data.shared_prefrences.CartSharedPrefrence;
import khoaluan.vn.flowershop.realm_data_local.RealmCartUtils;
import khoaluan.vn.flowershop.utils.ActionUtils;
import khoaluan.vn.flowershop.utils.CartUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends BaseFragment implements DetailsContract.View, Base, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View root;
    private DetailsContract.Presenter presenter;
    private Flower flower;
    private FlowerSuggesstion flowerSuggesstion;
    private MutipleDetailItemAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Menu menu;
    private MenuItem menuItemBadge;
    private Activity activity;
    private RealmResults<Cart> carts;
    @BindView(R.id.ln_buy)
    LinearLayout linearLayoutBuyNow;

    @BindView(R.id.ln_add_to_cart)
    LinearLayout linearLayoutAddToCart;

    @BindView(R.id.toolbar)
    Toolbar toolbarView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<MutipleDetailItem> list;


    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carts = RealmCartUtils.all();
        carts.addChangeListener(new RealmChangeListener<RealmResults<Cart>>() {
            @Override
            public void onChange(RealmResults<Cart> element) {
                updateBadge(CartUtils.getSum(carts), menuItemBadge);
            }
        });
        setHasOptionsMenu(true);
    }

    public static DetailsFragment newInstance(Flower flower, FlowerSuggesstion flowerSuggesstion) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(FLOWER_PARCELABLE, flower);
        args.putParcelable(LIST_FLOWER_PARCELABLE, flowerSuggesstion);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                // Handle this selection
                return true;
            case R.id.item_samplebadge:
                ActionUtils.go(getActivity(), 3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar.
        inflater.inflate(R.menu.menu_flower_detail, menu);
        this.menu = menu;
        menuItemBadge = this.menu.findItem(R.id.item_samplebadge);
        updateBadge(CartUtils.getSum(carts), menuItemBadge);
        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_detail, container, false);
        flower = (Flower) getArguments().getParcelable(FLOWER_PARCELABLE);
        flowerSuggesstion = (FlowerSuggesstion) getArguments().getParcelable(LIST_FLOWER_PARCELABLE);
        ButterKnife.bind(this, root);
        initilizeToolBar();
        showUI();

        if (flower != null && flower.getId() != null && flowerSuggesstion != null) {
            showIndicator(true);
            presenter.loadRatingData(flower.getId());
        } else {
            showIndicator(true);
            presenter.loadFlowerDetail(flower.getId());
        }

        return root;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showUI() {
        activity = getActivity();
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setNestedScrollingEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        list = presenter.convertData(flower, flowerSuggesstion);

        adapter = new MutipleDetailItemAdapter(getActivity(), list, presenter);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setAdapter(adapter);

        linearLayoutBuyNow.setOnClickListener(this);
        linearLayoutAddToCart.setOnClickListener(this);
    }

    @Override
    public void updateBadge(int number, MenuItem menuItemBadge) {
        if (number != 0)
            ActionItemBadge.update(activity, menuItemBadge,
                activity.getResources().getDrawable(R.drawable.shopping_cart),
                ActionItemBadge.BadgeStyles.RED, number);
        else
            menuItemBadge.setVisible(false);
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
    public void updateRatings(List<Rating> ratings) {
        list.get(6).getRatings().addAll(ratings);
        adapter.notifyItemChanged(6);
    }

    @Override
    public void forAd(Flower flower) {
        for (int i = 0; i < 6; i++)
            list.get(i).setFlower(flower);

        adapter.notifyDataSetChanged();
    }


    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Chi Tiết Sản Phẩm");
        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ln_buy:
                recyclerView.scrollToPosition(0);
                if (!CartSharedPrefrence.isCartIdExisted(activity)) {
                    CartSharedPrefrence.saveCartId(java.util.UUID.randomUUID().toString(), activity);
                }
                showIndicator(true);
                presenter.addToCart(CartSharedPrefrence.getCartId(activity), flower.getId(), true);
                break;
            case R.id.ln_add_to_cart:
                recyclerView.scrollToPosition(0);
                if (!CartSharedPrefrence.isCartIdExisted(activity)) {
                    CartSharedPrefrence.saveCartId(java.util.UUID.randomUUID().toString(), activity);
                }
                showIndicator(true);
                presenter.addToCart(CartSharedPrefrence.getCartId(activity), flower.getId(), false);
                break;
        }
    }

    @Override
    public void onRefresh() {
        showIndicator(false);
    }

    @Override
    public void noInternetConnectTion() {
        Snackbar.make(linearLayoutAddToCart, R.string.no_internet_connecttion, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setDuration(5000)
                .show();
    }
}
