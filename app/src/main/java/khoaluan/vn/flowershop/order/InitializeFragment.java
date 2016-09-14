package khoaluan.vn.flowershop.order;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import khoaluan.vn.flowershop.data.model_parse_and_realm.User;
import khoaluan.vn.flowershop.data.shared_prefrences.CartSharedPrefrence;
import khoaluan.vn.flowershop.data.shared_prefrences.UserSharedPrefrence;
import khoaluan.vn.flowershop.realm_data_local.RealmCityUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitializeFragment extends BaseFragment implements OrderContract.View, CommonView.ToolBar {

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

    @BindView(R.id.check_box_same_rc)
    CheckBox checkBoxSameRc;

    @BindView(R.id.ln_receive)
    LinearLayout linearLayoutReceive;

    @BindView(R.id.pay)
    Button buttonPay;

    private ExpandableLayout expandableLayout;
    private ExpandableLayout expandableLayoutRc;

    private RealmResults<City> cities;
    private List<District> districts;
    private List<District> districtsRc;

    private ProgressDialog progressDialog;

    private boolean flag;

    public InitializeFragment() {
        // Required empty public constructor
    }

    public static InitializeFragment newInstance() {
        InitializeFragment fragment = new InitializeFragment();
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
        setUpDropData();
        setSenderInfo();
        setUpBilling(false);
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

    private String getIdDistrict(String name) {
        for (District district : districts )
            if (district.getName().equals(name))
                return district.getId();

        return null;
    }

    private String getIdDistrictRc(String name) {
        for (District district : districtsRc )
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
        expandableLayoutRc = (ExpandableLayout) root.findViewById(R.id.expandable_layout_rc);


        checkBoxExportBill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    setUpBilling(b);
                    if (b) {
                        expandableLayout.expand(true);
                    } else {
                        expandableLayout.collapse();
                    }
            }
        });

        checkBoxSameRc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                flag = b;
                if (isSenderInfoDone()) {
                    setSameRc(!b);
                    if (b) {
                        expandableLayoutRc.collapse();
                    } else {
                        expandableLayoutRc.expand();
                    }
                } else {
                    checkBoxSameRc.setChecked(false);
                    setSameRc(true);
                }

            }
        });




        phoneRc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.order || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });


        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSenderInfoDone()) {
                    if (isInvoice())
                        if (isBillingDone())
                            sendDataBilling();
                }

            }
        });

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

        spinnerDictricts.setText(null);
        if (problem)
            spinnerDictricts.setError("Đã xảy ra lỗi, không thể cập nhập dữ liêu, kiểm tra lại internet");
    }

    @Override
    public void updateDistrictRc(List<District> districts, boolean problem) {
        this.districtsRc = districts;
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
    public void setUpDropData() {
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
                if (flag) {
                    // Do nothing here
                } else {
                    if (!charSequence.toString().isEmpty()) {
                        showIndicator("Đang tải dữ liệu địa điểm", true);
                        presenter.loadDistrictsRc(getIdCity(charSequence.toString()));
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        updateDistrict(new ArrayList<District>(), false);
        updateDistrictRc(new ArrayList<District>(), false);
        presenter.loadCities();
    }

    @Override
    public void setUpBilling(boolean active) {
        companyName.setEnabled(active);
        idBilling.setEnabled(active);
        companyAddress.setEnabled(active);
    }

    @Override
    public void setSenderInfo() {
        User user = UserSharedPrefrence.getUser(getActivity());
        fullName.setText(user.getFullName());
        phone.setText(user.getPhone());
        address.setText(user.getAddress());
    }

    @Override
    public void setSameRc(boolean reset) {
        if (reset) {
            fullNameRc.setText(null);
            spinnerCitiesRc.setText(null);
            spinnerDictrictsRc.setText(null);
            phoneRc.setText(null);
            addressRc.setText(null);

            fullNameRc.setEnabled(true);
            spinnerCitiesRc.setEnabled(true);
            spinnerDictrictsRc.setEnabled(true);
            phoneRc.setEnabled(true);
            addressRc.setEnabled(true);
        } else {
            fullNameRc.setText(fullName.getText().toString());
            spinnerCitiesRc.setText(spinnerCities.getText().toString());
            spinnerDictrictsRc.setText(spinnerDictricts.getText().toString());
            phoneRc.setText(phone.getText().toString());
            addressRc.setText(address.getText().toString());

            fullNameRc.setEnabled(false);
            spinnerCitiesRc.setEnabled(false);
            spinnerDictrictsRc.setEnabled(false);
            phoneRc.setEnabled(false);
            addressRc.setEnabled(false);
        }

    }

    @Override
    public boolean isSenderInfoDone() {
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

    @Override
    public boolean isBillingDone() {
        View focusView = null;
        boolean cancel = false;
        if (addressRc.getText().toString().isEmpty()) {
            addressRc.setError(getString(R.string.error_field_required));

            focusView = this.addressRc;
            cancel = true;
        }

        if (spinnerDictrictsRc.getText().toString().isEmpty()) {
            spinnerDictrictsRc.setError(getString(R.string.error_field_required));

            focusView = this.spinnerDictrictsRc;
            cancel = true;
        }

        if (spinnerCitiesRc.getText().toString().isEmpty()) {
            spinnerCitiesRc.setError(getString(R.string.error_field_required));

            focusView = this.spinnerCitiesRc;
            cancel = true;
        }

        if (phoneRc.getText().toString().isEmpty()) {
            phoneRc.setError(getString(R.string.error_field_required));

            focusView = this.phoneRc;
            cancel = true;
        }

        if (fullNameRc.getText().toString().isEmpty()) {
            fullNameRc.setError(getString(R.string.error_field_required));

            focusView = this.fullNameRc;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }

        return !cancel;
    }

    @Override
    public boolean isInvoice() {
        if (checkBoxExportBill.isChecked()) {
            View focusView = null;
            boolean cancel = false;

            if (companyAddress.getText().toString().isEmpty()) {
                companyAddress.setError(getString(R.string.error_field_required));

                focusView = this.companyAddress;
                cancel = true;
            }

            if (idBilling.getText().toString().isEmpty()) {
                idBilling.setError(getString(R.string.error_field_required));

                focusView = this.idBilling;
                cancel = true;
            }

            if (companyName.getText().toString().isEmpty()) {
                companyName.setError(getString(R.string.error_field_required));

                focusView = this.companyName;
                cancel = true;
            }

            if (cancel) {
                focusView.requestFocus();
            }

            return !cancel;
        } else
            return true;
    }

    @Override
    public void sendDataBilling() {
        String cartId = CartSharedPrefrence.getCartId(getActivity());
        String email = UserSharedPrefrence.getUser(getActivity()).getEmail();
        String userId = UserSharedPrefrence.getUser(getActivity()).getId();
        String fullName = this.fullName.getText().toString();
        String phone = this.phone.getText().toString();
        String cityId = getIdCity(spinnerCities.getText().toString());
        String districtsId = getIdDistrict(spinnerDictricts.getText().toString());
        String address = this.address.getText().toString();

        showIndicator("Đang tạo đơn hàng ...", true);
        presenter.setBillingOrder(cartId, userId, fullName, phone, email, cityId, districtsId, address, checkBoxExportBill.isChecked());


    }

    @Override
    public void sendDataShipping() {
        String cartId = CartSharedPrefrence.getCartId(getActivity());
        String email = UserSharedPrefrence.getUser(getActivity()).getEmail();
        String userId = UserSharedPrefrence.getUser(getActivity()).getId();

        String fullNameRc = this.fullNameRc.getText().toString();
        String phoneRc = this.phoneRc.getText().toString();
        String cityIdRc = getIdCity(spinnerCitiesRc.getText().toString());
        String districtsRc = null;
        if (checkBoxSameRc.isChecked()) {
            districtsRc = getIdDistrict(spinnerDictrictsRc.getText().toString());
        } else {
            districtsRc = getIdDistrictRc(spinnerDictrictsRc.getText().toString());
        }

        String addressRc = this.addressRc.getText().toString();

        showIndicator("Đang tạo đơn hàng ...", true);
        presenter.setShippingOrder(cartId, userId, fullNameRc, phoneRc, email, cityIdRc, districtsRc, addressRc);
    }

    @Override
    public void sendInvoice() {
        String userId = UserSharedPrefrence.getUser(getActivity()).getId();
        String cityId = getIdCity(spinnerCities.getText().toString());
        String districtsId = getIdDistrict(spinnerDictricts.getText().toString());
        String idBilling = this.idBilling.getText().toString();
        String companyName = this.companyName.getText().toString();
        String companyAddress = this.companyAddress.getText().toString();

        showIndicator("Đang tạo hóa đơn ...", true);
        presenter.setInvoiceAddress(userId, companyName, idBilling, cityId, districtsId, companyAddress);

    }

    @Override
    public void showDateTimePicker() {

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
