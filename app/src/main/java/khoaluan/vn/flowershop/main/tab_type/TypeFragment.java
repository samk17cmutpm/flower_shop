package khoaluan.vn.flowershop.main.tab_type;


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
import khoaluan.vn.flowershop.action.CommonView;
import khoaluan.vn.flowershop.data.Type;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends Fragment implements TypeContract.View, CommonView.ToolBar, Base {

    private TypeContract.Presenter presenter;
    private View root;
    private TypeAdapter adapter;
    private List<Type> types;
    private Activity activity;
    private LinearLayoutManager layoutManager;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public TypeFragment() {
        // Required empty public constructor
    }

    public static TypeFragment newInstance() {
        TypeFragment fragment = new TypeFragment();
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
        initilizeToolBar();
        showUI();
        initilizeRecyclerView();
        presenter.loadData();
        return root;
    }

    @Override
    public void initilizeRecyclerView() {
        layoutManager = new LinearLayoutManager(activity);
        this.types = new ArrayList<>();
        adapter = new TypeAdapter(activity, types);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        SpacesItemDecoration decoration = new SpacesItemDecoration(RECYCLER_VIEW_DISTANCE);
        recyclerView.addItemDecoration(decoration);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showUI() {
        activity = getActivity();
    }

    @Override
    public void showFlowerTypes(List<Type> types) {
        this.types.addAll(types);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(TypeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initilizeToolBar() {
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
    }
}
