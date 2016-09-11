package khoaluan.vn.flowershop.order;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.sign_in.SignInActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseFragment implements OrderContract.View, CommonView.ToolBar {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private View root;

    private ProgressDialog progressDialog;

    private OrderContract.Presenter presenter;

    private static final String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};

    private ArrayAdapter<String> adapter;

    @BindView(R.id.spinner_cities)
    MaterialBetterSpinner spinnerCities;

    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, root);
        showUI();
        initilizeToolBar();
        return root;
    }

    @Override
    public void showUI() {
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, ITEMS);
        spinnerCities.setAdapter(adapter);

    }

    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initilizeToolBar() {
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Đăng Ký Tài Khoản");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
