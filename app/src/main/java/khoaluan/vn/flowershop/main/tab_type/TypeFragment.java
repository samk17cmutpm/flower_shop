package khoaluan.vn.flowershop.main.tab_type;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import khoaluan.vn.flowershop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_type, container, false);
    }

}
