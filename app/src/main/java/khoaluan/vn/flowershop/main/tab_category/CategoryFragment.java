package khoaluan.vn.flowershop.main.tab_category;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.android.segmented.SegmentedGroup;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.category_detail.CategoryDetailActivity;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ExpandCategory;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.main.MainDrawerAdapter;
import khoaluan.vn.flowershop.utils.ConvertUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements CategoryContract.View, Base,
        RadioGroup.OnCheckedChangeListener{

    private CategoryContract.Presenter presenter;
    private View root;
    private MainDrawerAdapter adapter;
    private Activity activity;
    private LinearLayoutManager layoutManager;
    private List<ExpandCategory> expandCategories;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_type, container, false);
        ButterKnife.bind(this, root);
        setSegment();
        showUI();
        setDevider();
        presenter.loadData();
        return root;
    }

    @Override
    public void initilizeRecyclerView() {
        layoutManager = new LinearLayoutManager(activity);
        this.expandCategories = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainDrawerAdapter(activity, recyclerView, expandCategories);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showUI() {
        activity = getActivity();
    }

    @Override
    public void showCategories(List<ExpandCategory> expandCategories) {
        initilizeRecyclerView();
        this.expandCategories.addAll(expandCategories);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setDevider() {
        SpacesItemDecoration decoration = new SpacesItemDecoration(RECYCLER_VIEW_DISTANCE);
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void setSegment() {
        SegmentedGroup segmentedGroup = (SegmentedGroup) root.findViewById(R.id.segment);
        segmentedGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void noInternetConnectTion() {
        Snackbar.make(recyclerView, R.string.no_internet_connecttion, Snackbar.LENGTH_INDEFINITE)
                .setDuration(5000)
                .show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.flower:
                showCategories(ConvertUtils.convertCategoriseToExpandCategories(presenter.loadLocalFlowerCategories()));
                break;
            case R.id.gift:
                showCategories(ConvertUtils.convertCategoriseToExpandCategories(presenter.loadLocalGiftCategories()));
                break;

        }
    }
}
