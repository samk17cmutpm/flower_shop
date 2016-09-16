package khoaluan.vn.flowershop.main.tab_shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForOrder;
import khoaluan.vn.flowershop.data.shared_prefrences.CartSharedPrefrence;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.order.OrderActivity;
import khoaluan.vn.flowershop.sign_in.SignInActivity;
import khoaluan.vn.flowershop.utils.MessageUtils;
import khoaluan.vn.flowershop.utils.MoneyUtils;


public class ShopFragment extends Fragment implements ShopContract.View, SwipeRefreshLayout.OnRefreshListener, Base {

    private ShopContract.Presenter presenter;
    private View root;
    private LinearLayoutManager linearLayoutManager;
    private Activity activity;
    private ShopAdapter adapter;
    private RealmResults<Cart> carts;
    private final SpacesItemDecoration spaceProduct = new SpacesItemDecoration(PRODUCT_DISTANCE);
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tv_total)
    TextView textViewTotal;

    @BindView(R.id.ln_pay_now)
    LinearLayout linearLayoutBuyNow;

    public ShopFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance() {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, root);
        showUI();
        if (CartSharedPrefrence.isCartIdExisted(activity)) {
            showIndicator(true);
            presenter.syncCarts(CartSharedPrefrence.getCartId(activity));
        }
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


        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        carts = presenter.loadCartFlowers();

        textViewTotal.setText(MoneyUtils.getMoney(presenter.countTotalMoney(carts)));

        adapter = new ShopAdapter(activity, carts, presenter);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        View view_empty = activity.getLayoutInflater().inflate(R.layout.empty_cart,
                (ViewGroup) recyclerView.getParent(), false);
        adapter.setEmptyView(view_empty);
        recyclerView.removeItemDecoration(spaceProduct);
        recyclerView.addItemDecoration(spaceProduct);
        recyclerView.setAdapter(adapter);

        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
//                OnItemClickUtils.flowerDetail(activity, carts.get(i), new FlowerSuggesstion(carts), false);

            }
        });

        carts.addChangeListener(new RealmChangeListener<RealmResults<Cart>>() {
            @Override
            public void onChange(RealmResults<Cart> element) {
                textViewTotal.setText(MoneyUtils.getMoney(presenter.countTotalMoney(element)));
                if (presenter.isChanged()) {
                    adapter.notifyItemChanged(presenter.getPosition());
                    presenter.notifyDataChange(presenter.getPosition(), false);
                }
                else
                    adapter.notifyDataSetChanged();
            }
        });

        linearLayoutBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carts.isEmpty()) {
                    MessageUtils.showLong(activity, "Bạn chưa có sản phẩm nào trong giỏ hàng");
                } else {
                    if (!UserUtils.isSignedIn(activity)) {
                        new MaterialDialog.Builder(activity)
                                .title("Livizi")
                                .content("Vui lòng đăng nhập !")
                                .positiveText("Có")
                                .negativeText("Không")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Intent intent = new Intent(activity, SignInActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    } else {
                        Intent intent = new Intent(activity, OrderActivity.class);
                        intent.putExtra(Action.ACTION_FOR_ORDER, new ActionDefined(ActionForOrder.INITIALIZE, false));
                        startActivity(intent);
                    }
                }
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
    public void onDestroy() {
        if (carts != null)
            carts.removeChangeListeners();
        super.onDestroy();
    }

    @Override
    public void setPresenter(ShopContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRefresh() {
        showIndicator(false);
    }

    @Override
    public void noInternetConnectTion() {
        Snackbar.make(linearLayoutBuyNow, R.string.no_internet_connecttion, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setDuration(5000)
                .show();
    }
}
