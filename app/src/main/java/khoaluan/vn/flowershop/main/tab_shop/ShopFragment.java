package khoaluan.vn.flowershop.main.tab_shop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import khoaluan.vn.flowershop.R;


public class ShopFragment extends Fragment implements ShopContract.View{

    private ShopContract.Presenter presenter;
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
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void showUI() {

    }

    @Override
    public void setPresenter(ShopContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
