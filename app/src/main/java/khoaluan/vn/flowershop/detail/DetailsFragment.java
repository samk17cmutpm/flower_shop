package khoaluan.vn.flowershop.detail;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.main.tab_home.MultipleMainItemAdapter;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends BaseFragment implements DetailsContract.View, Base, View.OnClickListener {
    private View root;
    private DetailsContract.Presenter presenter;
    private Flower flower;
    private FlowerSuggesstion flowerSuggesstion;
    private MutipleDetailItemAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.ln_buy)
    LinearLayout linearLayoutBuyNow;

    @BindView(R.id.ln_add_to_cart)
    LinearLayout linearLayoutAddToCart;

    @BindView(R.id.toolbar)
    Toolbar toolbarView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar.
        inflater.inflate(R.menu.menu_flower_detail, menu);
        ActionItemBadge.update(getActivity(), menu.findItem(R.id.item_samplebadge),
                getActivity().getResources().getDrawable(R.drawable.ic_shopping_cart),
                ActionItemBadge.BadgeStyles.RED, 10);
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
        return root;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showUI() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MutipleDetailItemAdapter(getActivity(), presenter.convertData(flower, flowerSuggesstion));
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setAdapter(adapter);

        linearLayoutBuyNow.setOnClickListener(this);
        linearLayoutAddToCart.setOnClickListener(this);
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
                break;
            case R.id.ln_add_to_cart:
                break;
        }
    }
}
