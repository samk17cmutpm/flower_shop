package khoaluan.vn.flowershop.user_data;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

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
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForUserData;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmAddressUtills;
import khoaluan.vn.flowershop.realm_data_local.RealmCityUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.user_data.billings.MultipleBillingItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAndEditShippingAddressFragment extends BaseFragment implements UserDataContract.View, CommonView.ToolBar {

    private UserDataContract.Presenter presenter;

    private View root;

    private String[] ITEMS_DISTRICTS;
    private String[] ITEMS_CITIES;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.full_name)
    EditText fullName;

    @BindView(R.id.phone)
    EditText phone;

    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.spinner_cities)
    MaterialBetterSpinner spinnerCities;

    @BindView(R.id.spinner_dictricts)
    MaterialBetterSpinner spinnerDictricts;

    @BindView(R.id.update)
    Button update;

    private ArrayAdapter<String> adapterCities;

    private ArrayAdapter<String> adapterDistricts;

    private RealmResults<City> cities;
    private List<District> districts;
    private ProgressDialog progressDialog;

    private ShippingAddressDTO shippingAddressDTO;
    private boolean isEdit = false;

    private int count = 0;

    public AddAndEditShippingAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shippingAddressDTO = (ShippingAddressDTO) getArguments().get(Action.ACTION_FOR_SHIPPING_ADDRESS);
        if (shippingAddressDTO != null)
            isEdit = true;
    }

    public static AddAndEditShippingAddressFragment newInstance(ShippingAddressDTO shippingAddressDTO) {
        AddAndEditShippingAddressFragment fragment = new AddAndEditShippingAddressFragment();
        Bundle args = new Bundle();
        args.putParcelable(Action.ACTION_FOR_SHIPPING_ADDRESS, shippingAddressDTO);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_and_edit_shipping_address, container, false);
        ButterKnife.bind(this, root);
        initilizeToolBar();
        showUI();
        if (isEdit) {
            initializeData();
            update.setText("Cập Nhập");
        }
        return root;
    }

    private void initializeData() {
        fullName.setText(shippingAddressDTO.getName());
        phone.setText(shippingAddressDTO.getPhone());
        address.setText(shippingAddressDTO.getAddress());
    }

    @Override
    public void initilizeToolBar() {
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Mẩu địa chỉ thanh toán");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserDataActivity.class);
                intent.putExtra(Action.ACTION_FOR_USER_DATA, new ActionDefined(ActionForUserData.DELIVERY_ADDRESS));
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void showUI() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        cities = RealmCityUtils.all(RealmFlag.CITY_SEND);
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
                count ++;
                showIndicator(true,"Đang tải dữ liệu địa điểm");
                presenter.loadDistricts(getIdCity(charSequence.toString()));
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        updateDistrict(new ArrayList<District>(), false);
        presenter.loadCities();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSenderInfoDone()) {
                    showIndicator(true,"Đang cập nhập dữ liệu");
                    if (isEdit) {
                        presenter.updateShippingAddress(
                                shippingAddressDTO.getId(),
                                UserUtils.getUser(getActivity()).getId(),
                                fullName.getText().toString(),
                                phone.getText().toString(),
                                getIdCity(spinnerCities.getText().toString()),
                                getIdDistrict(spinnerDictricts.getText().toString()),
                                address.getText().toString()
                        );
                    } else {
                        presenter.createShippingAddress(
                                UserUtils.getUser(getActivity()).getId(),
                                fullName.getText().toString(),
                                phone.getText().toString(),
                                getIdCity(spinnerCities.getText().toString()),
                                getIdDistrict(spinnerDictricts.getText().toString()),
                                address.getText().toString()
                        );
                    }

                }
            }
        });

    }

    private String getIdCity(String name) {
        for (City city : cities)
            if (city.getName().equals(name))
                return city.getId();

        return null;
    }

    private String getIdDistrict(String name) {
        for (District district : districts )
            if (district.getName().equals(name))
                return district.getId();

        return null;
    }

    private void setUpCities(List<City> cities) {
        ITEMS_CITIES = new String[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            ITEMS_CITIES[i] = cities.get(i).getName();
        }
        adapterCities = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, ITEMS_CITIES);
        spinnerCities.setAdapter(adapterCities);

        try {
            if (shippingAddressDTO != null) {
                spinnerCities.setText(shippingAddressDTO.getCityString());
                spinnerDictricts.setText(shippingAddressDTO.getDistrictString());
            }
            else
                spinnerCities.setText(ITEMS_CITIES[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showIndicator(boolean active, String message) {
        if (active) {
            progressDialog.setMessage(message);
            progressDialog.show();
        } else {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    @Override
    public void done() {

    }

    @Override
    public void initilizeRecyclerView() {

    }

    @Override
    public void showIndicator(boolean active) {

    }

    @Override
    public void showBillingDetail(List<MultipleBillingItem> list) {

    }

    @Override
    public void updateDistrict(List<District> districts, boolean problem) {
        this.districts = districts;
        ITEMS_DISTRICTS = new String[districts.size()];
        for (int i = 0; i < districts.size(); i++) {
            ITEMS_DISTRICTS[i] = districts.get(i).getName();
        }
        adapterDistricts = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, ITEMS_DISTRICTS);
        spinnerDictricts.setAdapter(adapterDistricts);
        if (shippingAddressDTO != null && count < 2){
            spinnerDictricts.setText(shippingAddressDTO.getDistrictString());
        }
        else
            spinnerDictricts.setText(null);
        if (problem)
            spinnerDictricts.setError("Đã xảy ra lỗi, không thể cập nhập dữ liêu, kiểm tra lại");

    }

    @Override
    public void setPresenter(UserDataContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void noInternetConnectTion() {

    }

    @Override
    public void onDestroy() {
        if (cities != null)
            cities.removeChangeListeners();
        super.onDestroy();
    }

    private boolean isSenderInfoDone() {
        View focusView = null;
        boolean cancel = false;
        if (address.getText().toString().isEmpty()) {
            address.setError(getString(R.string.error_field_required));

            focusView = this.address;
            cancel = true;
        }

        if (spinnerDictricts.getText().toString().isEmpty()) {
            spinnerDictricts.setError(getString(R.string.error_field_required));

            focusView = this.spinnerDictricts;
            cancel = true;
        }

        if (spinnerCities.getText().toString().isEmpty()) {
            spinnerCities.setError(getString(R.string.error_field_required));

            focusView = this.spinnerCities;
            cancel = true;
        }

        if (phone.getText().toString().isEmpty()) {
            phone.setError(getString(R.string.error_field_required));

            focusView = this.phone;
            cancel = true;
        }

        if (fullName.getText().toString().isEmpty()) {
            fullName.setError(getString(R.string.error_field_required));

            focusView = this.fullName;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }

        return !cancel;
    }
}
