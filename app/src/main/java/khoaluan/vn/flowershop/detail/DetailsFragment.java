package khoaluan.vn.flowershop.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

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
public class DetailsFragment extends BaseFragment implements DetailsContract.View, Base {
    private View root;
    private DetailsContract.Presenter presenter;
    private Flower flower;
    private FlowerSuggesstion flowerSuggesstion;
    private MutipleDetailItemAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.toolbar)
    Toolbar toolbarView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public DetailsFragment() {
        // Required empty public constructor
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

    @Override
    public void showUI() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MutipleDetailItemAdapter(getActivity(), presenter.convertData(flower, flowerSuggesstion));
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setAdapter(adapter);
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
}
