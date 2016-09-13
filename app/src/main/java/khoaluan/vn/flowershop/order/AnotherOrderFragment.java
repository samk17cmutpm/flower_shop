package khoaluan.vn.flowershop.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnotherOrderFragment extends BaseFragment implements OrderContract.View, CommonView.ToolBar {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private View root;

    private OrderContract.Presenter presenter;


    public AnotherOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_another_order, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void initilizeToolBar() {
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Thông tin đặt hàng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void showUI() {

    }

    @Override
    public void updateDistrict(List<District> districts, boolean problem) {

    }

    @Override
    public void updateDistrictRc(List<District> districts, boolean problem) {

    }

    @Override
    public void showIndicator(String message, boolean active) {

    }

    @Override
    public void setUpDropData() {

    }

    @Override
    public void setUpBilling(boolean active) {

    }

    @Override
    public void setSenderInfo() {

    }

    @Override
    public void setSameRc(boolean reset) {

    }

    @Override
    public boolean isSenderInfoDone() {
        return false;
    }

    @Override
    public boolean isBillingDone() {
        return false;
    }

    @Override
    public void sendDataBilling() {

    }

    @Override
    public void sendDataShipping() {

    }

    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
