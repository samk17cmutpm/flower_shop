package khoaluan.vn.flowershop.main.tab_shop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;
import khoaluan.vn.flowershop.utils.MoneyUtils;


public class ShopFragment extends Fragment implements ShopContract.View, SwipeRefreshLayout.OnRefreshListener, Base {

    private ShopContract.Presenter presenter;
    private View root;
    private LinearLayoutManager linearLayoutManager;
    private Activity activity;
    private ShopAdapter adapter;
    private RealmResults<Flower> flowersCart;
    private List<Flower> flowers;
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

        flowersCart = presenter.loadCartFlowers();

        flowers = new ArrayList<>();
        flowers.addAll(flowersCart);

        textViewTotal.setText(MoneyUtils.getMoney(presenter.countTotalMoney(flowers)));

        adapter = new ShopAdapter(activity, flowers);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        View view_empty = activity.getLayoutInflater().inflate(R.layout.empty_cart,
                (ViewGroup) recyclerView.getParent(), false);
        adapter.setEmptyView(view_empty);
        recyclerView.removeItemDecoration(spaceProduct);
        recyclerView.addItemDecoration(spaceProduct);
        recyclerView.setAdapter(adapter);

        flowersCart.addChangeListener(new RealmChangeListener<RealmResults<Flower>>() {
            @Override
            public void onChange(RealmResults<Flower> element) {
                flowers.clear();
                flowers.addAll(element);
                textViewTotal.setText(MoneyUtils.getMoney(presenter.countTotalMoney(flowers)));
                adapter.notifyDataSetChanged();
            }
        });

        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {}
            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {}
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                RealmFlowerUtils.deleteById(RealmFlag.FLAG, RealmFlag.CART, flowers.get(pos).getId());
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float v, float v1, boolean b) {
            }
        };

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(onItemSwipeListener);

        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(final View view, final int i) {
                ImageView imageView = (ImageView) view.findViewById(R.id.im_delete);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.onItemSwiped(recyclerView.getChildViewHolder(view));
                    }
                });
//                OnItemClickUtils.flowerDetail(activity, flowers.get(i), new FlowerSuggesstion(flowers), false);
            }
        });


        linearLayoutBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void setPresenter(ShopContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRefresh() {

    }
}
