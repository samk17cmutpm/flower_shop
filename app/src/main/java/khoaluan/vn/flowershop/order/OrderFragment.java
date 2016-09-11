package khoaluan.vn.flowershop.order;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import net.cachapa.expandablelayout.ExpandableLayout;

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

    @BindView(R.id.spinner_dictricts)
    MaterialBetterSpinner spinnerDictricts;

    @BindView(R.id.spinner_cities_rc)
    MaterialBetterSpinner spinnerCitiesRc;

    @BindView(R.id.spinner_dictricts_rc)
    MaterialBetterSpinner spinnerDictrictsRc;

    @BindView(R.id.checkBoxExportBill)
    CheckBox checkBoxExportBill;

    @BindView(R.id.full_name)
    EditText fullName;

    @BindView(R.id.phone)
    EditText phone;

    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.tv_choose_form_bill)
    TextView textViewChooseFromBill;

    @BindView(R.id.company_name)
    EditText companyName;

    @BindView(R.id.id_billing)
    EditText idBilling;

    @BindView(R.id.company_address)
    EditText companyAddress;

    @BindView(R.id.check_box_save_new_bill)
    CheckBox checkBoxSaveNewBill;

    @BindView(R.id.tv_choose_from_rc)
    TextView textViewChooseFromRc;

    @BindView(R.id.full_name_rc)
    EditText fullNameRc;

    @BindView(R.id.phone_rc)
    EditText phoneRc;

    @BindView(R.id.address_rc)
    EditText addressRc;

    @BindView(R.id.check_box_save_new_rc)
    CheckBox checkBoxSaveNewRc;

    private ExpandableLayout expandableLayout;

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
        spinnerDictricts.setAdapter(adapter);
        spinnerCitiesRc.setAdapter(adapter);
        spinnerDictrictsRc.setAdapter(adapter);
        spinnerCities.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("=============>", i + " position");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        expandableLayout = (ExpandableLayout) root.findViewById(R.id.expandable_layout);

        checkBoxExportBill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        expandableLayout.expand(true);
                    } else {
                        expandableLayout.collapse();
                    }
            }
        });

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Thông tin đặt hàng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}
