package khoaluan.vn.flowershop.payment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends BaseFragment implements PaymentContract.View {
    private PaymentContract.Presenter presenter;
    private View root;
    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance() {
        PaymentFragment fragment = new PaymentFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_payment, container, false);
        return root;
    }

    @Override
    public void showUI() {

    }

    @Override
    public void setPresenter(PaymentContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
