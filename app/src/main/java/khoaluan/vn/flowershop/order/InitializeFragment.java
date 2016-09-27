package khoaluan.vn.flowershop.order;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;
import khoaluan.vn.flowershop.data.model_parse_and_realm.BillingAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.City;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ExtraInformationDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.request.InvoiceRequest;
import khoaluan.vn.flowershop.data.shared_prefrences.CartSharedPrefrence;
import khoaluan.vn.flowershop.data.shared_prefrences.UserPayment;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmAddressUtills;
import khoaluan.vn.flowershop.realm_data_local.RealmBillingUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmCityUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.user_data.billings.InvoiceAdapter;
import khoaluan.vn.flowershop.user_data.billings.ShippingAddressAdapter;
import khoaluan.vn.flowershop.utils.ActionUtils;
import khoaluan.vn.flowershop.utils.AddressUtils;
import khoaluan.vn.flowershop.utils.MessageUtils;

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
    private String[] ITEMS_CITIES_RC;

    private ArrayAdapter<String> adapterCities;

    private ArrayAdapter<String> adapterCitiesRc;

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
    private RealmResults<City> citiesRc;
    private List<District> districts;
    private List<District> districtsRc;

    private ProgressDialog progressDialog;

    private boolean flag;

    private Billing billing;

    private ActionDefined actionDefined;

    private InvoiceAdapter invoiceAdapter;
    private ShippingAddressAdapter shippingAddressAdapter;

    private RealmResults<InvoiceAddressDTO> invoiceAddressDTOs;
    private RealmResults<ShippingAddressDTO> shippingAddressDTOs;

    private MaterialDialog materialDialogInvoice;
    private MaterialDialog materialDialogShippingAddress;

    private boolean flag_choosen_form;

    private BillingAddressDTO userInfo;
    private Activity activity;

    public InitializeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionDefined = (ActionDefined) getArguments().getParcelable(Action.ACTION_FOR_ORDER);
        activity = getActivity();
        if (actionDefined.isEdit()) {
            billing = RealmBillingUtils.getBillCofirm();
        } else {
            RealmBillingUtils.createConfirmBilling();
        }
    }

    public static InitializeFragment newInstance(ActionDefined actionDefined) {
        InitializeFragment fragment = new InitializeFragment();
        Bundle args = new Bundle();
        args.putParcelable(Action.ACTION_FOR_ORDER, actionDefined);
        fragment.setArguments(args);
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
        if (actionDefined.isEdit()) {
            editFormSender(billing.getBillingAddressDTO());
            if (billing.getInvoiceAddressDTO() != null)
                editFormInvoice(billing.getInvoiceAddressDTO());
            editFormShipping(billing.getShippingAddressDTO());
        }
        return root;
    }

    @Override
    public void onDestroy() {
        if (cities != null)
            cities.removeChangeListeners();
        super.onDestroy();
    }

    private void setUpCities(List<City> cities) {

        ITEMS_CITIES = new String[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            ITEMS_CITIES[i] = cities.get(i).getName();
        }
        adapterCities = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, ITEMS_CITIES);
        spinnerCities.setAdapter(adapterCities);

        try {
            if (userInfo.getCityString() != null)
                spinnerCities.setText(userInfo.getCityString());
            else
                spinnerCities.setText(cities.get(0).getName());

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void setUpCitiesRc(List<City> citiesRc) {
        ITEMS_CITIES_RC = new String[citiesRc.size()];
        for (int i = 0; i < citiesRc.size(); i++) {
            ITEMS_CITIES_RC[i] = citiesRc.get(i).getName();
        }
        adapterCitiesRc = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, ITEMS_CITIES_RC);
        spinnerCitiesRc.setAdapter(adapterCitiesRc);

        try {
            if (!isEdited())
                spinnerCitiesRc.setText(cities.get(0).getName());
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showUI() {
        // UserInfo For Sender
        userInfo = UserPayment.getUserPayment(activity);

        // Dialog
        progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        // expandble layout
        expandableLayout = (ExpandableLayout) root.findViewById(R.id.expandable_layout);
        expandableLayoutRc = (ExpandableLayout) root.findViewById(R.id.expandable_layout_rc);


        checkBoxExportBill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    setUpBilling(b);
                    if (b) {
                        expandableLayout.expand(true);
                        textViewChooseFromBill.setVisibility(View.VISIBLE);
                    } else {
                        expandableLayout.collapse();
                        textViewChooseFromBill.setVisibility(View.INVISIBLE);
                    }
            }
        });

        checkBoxSameRc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                flag = b;
                if (isSenderInfoDone() && checkExisted(spinnerCities.getText().toString(), ITEMS_CITIES_RC)) {
                    setSameRc(!b);
                    if (b) {
                        textViewChooseFromRc.setVisibility(View.INVISIBLE);
                        expandableLayoutRc.collapse();
                    } else {
                        textViewChooseFromRc.setVisibility(View.VISIBLE);
                        expandableLayoutRc.expand();
                    }
                } else {
                    MessageUtils.showLong(activity, "Hiện nay chúng tôi chỉ giao hàng tận nơi tại 3 thành phố Đà Nẵng, Hà Nội, Sài Gòn");
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

        invoiceAddressDTOs = RealmAddressUtills.allInvoiceAddressDTO();
        shippingAddressDTOs = RealmAddressUtills.allShippingAddressDTO();

        if (shippingAddressDTOs.isEmpty())
            presenter.loadShippingAddressDTO(UserUtils.getUser(activity).getId());

        if (invoiceAddressDTOs.isEmpty())
            presenter.loadInvoiceAddressDTO(UserUtils.getUser(activity).getId());

        invoiceAdapter = new InvoiceAdapter(invoiceAddressDTOs);
        shippingAddressAdapter = new ShippingAddressAdapter(shippingAddressDTOs);

        invoiceAddressDTOs.addChangeListener(new RealmChangeListener<RealmResults<InvoiceAddressDTO>>() {
            @Override
            public void onChange(RealmResults<InvoiceAddressDTO> element) {
                invoiceAdapter.notifyDataSetChanged();
            }
        });


        shippingAddressDTOs.addChangeListener(new RealmChangeListener<RealmResults<ShippingAddressDTO>>() {
            @Override
            public void onChange(RealmResults<ShippingAddressDTO> element) {
                shippingAddressAdapter.notifyDataSetChanged();
            }
        });


        materialDialogInvoice = new MaterialDialog.Builder(activity)
                .title(R.string.order_invoice)
                .adapter(invoiceAdapter, null)
                .autoDismiss(true).build();

        materialDialogShippingAddress = new MaterialDialog.Builder(activity)
                .title(R.string.order_invoice)
                .adapter(shippingAddressAdapter, null)
                .autoDismiss(true).build();

        textViewChooseFromBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (invoiceAddressDTOs.isEmpty())
                    MessageUtils.showLong(activity, "Hiện tại bạn chưa có mẩu nào");
                else
                    materialDialogInvoice.show();

            }
        });

        textViewChooseFromRc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shippingAddressDTOs.isEmpty())
                    MessageUtils.showLong(activity, "Hiện tại bạn chưa có mẩu nào");
                else
                    materialDialogShippingAddress.show();
            }
        });

        invoiceAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                companyName.setText(invoiceAddressDTOs.get(i).getCompanyName());
                idBilling.setText(invoiceAddressDTOs.get(i).getTaxCode());
                companyAddress.setText(invoiceAddressDTOs.get(i).getAddress());
                materialDialogInvoice.dismiss();
            }
        });

        shippingAddressAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                flag_choosen_form = true;
                fullNameRc.setText(shippingAddressDTOs.get(i).getName());
                spinnerCitiesRc.setText(shippingAddressDTOs.get(i).getCityString());
                spinnerDictrictsRc.setText(shippingAddressDTOs.get(i).getDistrictString());
                phoneRc.setText(shippingAddressDTOs.get(i).getPhone());
                addressRc.setText(shippingAddressDTOs.get(i).getAddress());

                fullNameRc.setEnabled(true);
                spinnerCitiesRc.setEnabled(true);
                spinnerDictrictsRc.setEnabled(true);
                phoneRc.setEnabled(true);
                addressRc.setEnabled(true);

                materialDialogShippingAddress.dismiss();

            }
        });

    }

    private boolean checkExisted(String contain, String[] ITEM) {
        for (String item: ITEM)
            if (contain.equals(item)) {
                return true;
            }

//        MessageUtils.showLong(activity, "Chúng tôi không thể giao hàng tới địa điểm " + spinnerCities.getText().toString() + " Vui lòng chọn địa điểm giao hàng khác");
        return false;
    }

    @Override
    public void updateDistrict(List<District> districts, boolean problem) {
        this.districts = districts;
        ITEMS_DISTRICTS = new String[districts.size()];
        for (int i = 0; i < districts.size(); i++) {
            ITEMS_DISTRICTS[i] = districts.get(i).getName();
        }
        adapterDistricts = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, ITEMS_DISTRICTS);
        spinnerDictricts.setAdapter(adapterDistricts);

        if (userInfo.getDistrictString() != null && checkExisted(userInfo.getDistrictString(), ITEMS_DISTRICTS))
            spinnerDictricts.setText(userInfo.getDistrictString());
        else
            try {
                spinnerDictricts.setText(ITEMS_DISTRICTS[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
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
        adapterDistrictsRc = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, ITEMS_DISTRICTS_RC);
        spinnerDictrictsRc.setAdapter(adapterDistrictsRc);

        if (!flag_choosen_form)
            if (isEdited() && checkExisted(spinnerDictrictsRc.getText().toString(), ITEMS_DISTRICTS_RC));
            else
                try {
                    spinnerDictrictsRc.setText(ITEMS_DISTRICTS_RC[0]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

        if (problem)
            spinnerDictrictsRc.setError("Đã xảy ra lỗi, không thể cập nhập dữ liêu, kiểm tra lại internet");
    }

    @Override
    public void showIndicator(String message, boolean active) {
        if (active) {
            if(!activity.isFinishing())
            {
                progressDialog.setMessage(message);
                progressDialog.show();
            }
        } else {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    @Override
    public void setUpDropData() {
        cities = RealmCityUtils.all(RealmFlag.CITY_SEND);
        citiesRc = RealmCityUtils.all(RealmFlag.CITY_RECEIVE);

        cities.addChangeListener(new RealmChangeListener<RealmResults<City>>() {
            @Override
            public void onChange(RealmResults<City> element) {
                if (element != null && element.size() > 0)
                    setUpCities(element);
            }
        });

        citiesRc.addChangeListener(new RealmChangeListener<RealmResults<City>>() {
            @Override
            public void onChange(RealmResults<City> element) {
                if (element != null && element.size() > 0)
                    setUpCitiesRc(citiesRc);
            }
        });
        setUpCities(cities);
        setUpCitiesRc(citiesRc);
        spinnerCities.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showIndicator("Đang tải dữ liệu địa điểm", true);
                presenter.loadDistricts(AddressUtils.getIdOfCity(charSequence.toString(), cities));
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
                        presenter.loadDistrictsRc(AddressUtils.getIdOfCity(charSequence.toString(), cities));
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
        presenter.loadCitiesPayment();
    }

    @Override
    public void setUpBilling(boolean active) {
        companyName.setEnabled(active);
        idBilling.setEnabled(active);
        companyAddress.setEnabled(active);
    }

    @Override
    public void setSenderInfo() {
        fullName.setText(userInfo.getName());
        phone.setText(userInfo.getPhone());
        address.setText(userInfo.getAddress());
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
        String cartId = CartSharedPrefrence.getCartId(activity);
        String email = UserUtils.getUser(activity).getEmail();
        String userId = UserUtils.getUser(activity).getId();
        String fullName = this.fullName.getText().toString();
        String phone = this.phone.getText().toString();
        String cityId = AddressUtils.getIdOfCity(spinnerCities.getText().toString(), cities);
        String districtsId = AddressUtils.getIdOfDistrict(spinnerDictricts.getText().toString(), districts);
        String address = this.address.getText().toString();

        showIndicator("Đang tạo đơn hàng ...", true);
        presenter.setBillingOrder(cartId, userId, fullName, phone, email, cityId, districtsId, address, checkBoxExportBill.isChecked());


    }

    @Override
    public void sendDataShipping() {
        String cartId = CartSharedPrefrence.getCartId(activity);
        String email = UserUtils.getUser(activity).getEmail();
        String userId = UserUtils.getUser(activity).getId();

        String fullNameRc = this.fullNameRc.getText().toString();
        String phoneRc = this.phoneRc.getText().toString();
        String cityIdRc = AddressUtils.getIdOfCity(spinnerCitiesRc.getText().toString(), cities);
        String districtsRc = null;
        if (checkBoxSameRc.isChecked()) {
            districtsRc = AddressUtils.getIdOfDistrict(spinnerDictrictsRc.getText().toString(), this.districts);
        } else {
            districtsRc = AddressUtils.getIdOfDistrict(spinnerDictrictsRc.getText().toString(), this.districtsRc);
        }

        String addressRc = this.addressRc.getText().toString();
        presenter.setShippingOrder(cartId, userId, fullNameRc, phoneRc, email, cityIdRc, districtsRc, addressRc, checkBoxSaveNewRc.isChecked());
    }

    @Override
    public void sendInvoice() {
        String cartId = CartSharedPrefrence.getCartId(activity);
        String userId = UserUtils.getUser(activity).getId();
        String idBilling = this.idBilling.getText().toString();
        String companyName = this.companyName.getText().toString();
        String companyAddress = this.companyAddress.getText().toString();
        presenter.setInvoiceAddress(cartId, userId, companyName, idBilling, companyAddress, checkBoxSaveNewBill.isChecked());

    }

    @Override
    public void showDateTimePicker() {

    }

    @Override
    public void editFormSender(BillingAddressDTO billingAddressDTO) {
        fullName.setText(billingAddressDTO.getName());
        phone.setText(billingAddressDTO.getPhone());
        address.setText(billingAddressDTO.getAddress());
        spinnerCities.setText(billingAddressDTO.getCityString());
        spinnerDictricts.setText(billingAddressDTO.getDistrictString());
    }

    @Override
    public void editFormInvoice(InvoiceAddressDTO invoiceAddressDTO) {
        checkBoxExportBill.setChecked(true);
        companyName.setText(invoiceAddressDTO.getCompanyName());
        companyAddress.setText(invoiceAddressDTO.getAddress());
        idBilling.setText(invoiceAddressDTO.getTaxCode());
    }

    @Override
    public void editFormShipping(ShippingAddressDTO shippingAddressDTO) {
        fullNameRc.setText(shippingAddressDTO.getName());
        phoneRc.setText(shippingAddressDTO.getPhone());
        addressRc.setText(shippingAddressDTO.getAddress());
        spinnerCitiesRc.setText(shippingAddressDTO.getCityString());
        spinnerDictrictsRc.setText(shippingAddressDTO.getDistrictString());

        buttonPay.setText("Cập nhập");
    }

    @Override
    public void editFormExtra(ExtraInformationDTO extraInformationDTO) {

    }

    @Override
    public boolean isEdited() {
        return actionDefined.isEdit();
    }

    @Override
    public void saveNewInvoiceTemplate() {
        String userId = UserUtils.getUser(activity).getId();
        String idBilling = this.idBilling.getText().toString();
        String companyName = this.companyName.getText().toString();
        String companyAddress = this.companyAddress.getText().toString();
        presenter.setNewInvoiceAddress(new InvoiceRequest(userId, companyName, idBilling, companyAddress));
    }

    @Override
    public void saveNewShippingAdress() {

    }

    @Override
    public boolean isExistedExtraInfo() {
        if (isEdited())
            if (billing.getExtraInformationDTO() != null)
                return true;
        return false;
    }

    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initilizeToolBar() {
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)activity).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)activity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)activity).getSupportActionBar().setTitle("Thông tin đặt hàng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmBillingUtils.clearBillingConfirm();
                ActionUtils.go(activity, 3);
            }
        });
    }
}
