package khoaluan.vn.flowershop.main.tab_info;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.InfoType;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements InfoContract.View, Base, CommonView.ToolBar{

    private InfoContract.Presenter presenter;
    private View root;
    private InfoAdapter adapter;
    private List<InfoType> infoTypes;
    private Activity activity;
    private LinearLayoutManager linearLayoutManager;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public InfoFragment() {
        // Required empty public constructor
    }

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, root);
        initilizeToolBar();
        showUI();
        initilizeRecyclerView();
        presenter.loadData();
        return root;
    }

    @Override
    public void showUI() {
        activity = getActivity();
    }

    @Override
    public void initilizeRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(activity);
        this.infoTypes = new ArrayList<>();
        adapter = new InfoAdapter(activity, infoTypes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        SpacesItemDecoration decoration = new SpacesItemDecoration(RECYCLER_VIEW_DISTANCE);
        recyclerView.addItemDecoration(decoration);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showInfoTypes(List<InfoType> infoTypes) {
        this.infoTypes.addAll(infoTypes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(InfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initilizeToolBar() {
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
    }
}
