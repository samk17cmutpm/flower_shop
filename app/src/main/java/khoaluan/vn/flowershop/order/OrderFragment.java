package khoaluan.vn.flowershop.order;


import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.City;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.realm_data_local.RealmCityUtils;
import khoaluan.vn.flowershop.utils.ActionUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseFragment implements OrderContract.View, CommonView.ToolBar {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private View root;

    private OrderContract.Presenter presenter;

    private String[] ITEMS_DISTRICTS;
    private String[] ITEMS_CITIES;
    private String[] ITEMS_DISTRICTS_RC;

    private ArrayAdapter<String> adapterCities;

    private ArrayAdapter<String> adapterDistricts;

    private ArrayAdapter<String> adapterDistrictsRc;

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

    private RealmResults<City> cities;

    private ProgressDialog progressDialog;

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

        cities = RealmCityUtils.all();
        cities.addChangeListener(new RealmChangeListener<RealmResults<City>>() {
            @Override
            public void onChange(RealmResults<City> element) {
                if (element != null && element.size() > 0)
                    setUpCities(element);
            }
        });
        setUpCities(cities);
        spinnerCities.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showIndicator("Đang tải dữ liệu địa điểm", true);
                presenter.loadDistricts(getIdCity(charSequence.toString()));
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spinnerCitiesRc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showIndicator("Đang tải dữ liệu địa điểm", true);
                presenter.loadDistrictsRc(getIdCity(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        updateDistrict(new ArrayList<District>(), false);
        updateDistrictRc(new ArrayList<District>(), false);
        presenter.loadCities();
        return root;
    }

    @Override
    public void onDestroy() {
        if (cities != null)
            cities.removeChangeListeners();
        super.onDestroy();
    }

    private String getIdCity(String name) {
        for (City city : cities)
            if (city.getName().equals(name))
                return city.getId();

        return null;
    }

    private void setUpCities(List<City> cities) {
        ITEMS_CITIES = new String[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            ITEMS_CITIES[i] = cities.get(i).getName();
        }
        adapterCities = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, ITEMS_CITIES);
        spinnerCities.setAdapter(adapterCities);
        spinnerCitiesRc.setAdapter(adapterCities);

        try {
            spinnerCities.setText(cities.get(0).getName());
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showUI() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
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
    public void updateDistrict(List<District> districts, boolean problem) {
        ITEMS_DISTRICTS = new String[districts.size()];
        for (int i = 0; i < districts.size(); i++) {
            ITEMS_DISTRICTS[i] = districts.get(i).getName();
        }
        adapterDistricts = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, ITEMS_DISTRICTS);
        spinnerDictricts.setAdapter(adapterDistricts);

        spinnerDictricts.setText(null);
        if (problem)
            spinnerDictricts.setError("Đã xảy ra lỗi, không thể cập nhập dữ liêu, kiểm tra lại internet");
    }

    @Override
    public void updateDistrictRc(List<District> districts, boolean problem) {
        ITEMS_DISTRICTS_RC = new String[districts.size()];
        for (int i = 0; i < districts.size(); i++) {
            ITEMS_DISTRICTS_RC[i] = districts.get(i).getName();
        }
        adapterDistrictsRc = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, ITEMS_DISTRICTS_RC);
        spinnerDictrictsRc.setAdapter(adapterDistrictsRc);

        spinnerDictrictsRc.setText(null);
        if (problem)
            spinnerDictrictsRc.setError("Đã xảy ra lỗi, không thể cập nhập dữ liêu, kiểm tra lại internet");
    }

    @Override
    public void showIndicator(String message, boolean active) {
        if (active) {
            progressDialog.setMessage(message);
            progressDialog.show();
        } else {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
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
