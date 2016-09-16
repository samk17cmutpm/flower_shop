package khoaluan.vn.flowershop.order;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;
import khoaluan.vn.flowershop.data.model_parse_and_realm.BillingAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ExtraInformationDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForOrder;
import khoaluan.vn.flowershop.data.shared_prefrences.CartSharedPrefrence;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmBillingUtils;
import khoaluan.vn.flowershop.utils.ActionUtils;
import khoaluan.vn.flowershop.utils.MessageUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExtraInfoFragment extends BaseFragment implements OrderContract.View, CommonView.ToolBar {

    SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {
                Timestamp check = new Timestamp(selectedDate.getEndDate().getTimeInMillis());
                if (timeStamp.before(check)) {
                    dateDelivery = selectedDate.getEndDate().getTimeInMillis();
                    Log.e("=======>", dateDelivery + "");
                    editTextDate.setText(getDataDelivery(selectedDate.getEndDate().getTimeInMillis()));

                }
                else
                    MessageUtils.showLong(getActivity(), "Phải sau ngày đặt ít nhất 1 ngày");

        }
    };


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.date)
    EditText editTextDate;

    @BindView(R.id.rl_change_date)
    RelativeLayout relativeLayoutChangeDate;

    @BindView(R.id.payment)
    RadioGroup radioGroupPayment;

    @BindView(R.id.hide_info)
    CheckBox hide_info;

    @BindView(R.id.pay)
    Button buttonPay;

    @BindView(R.id.message)
    EditText message;

    @BindView(R.id.note)
    EditText note;

    @BindView(R.id.is_pay_credit)
    RadioButton is_pay_credit;

    @BindView(R.id.is_pay_rc)
    RadioButton is_pay_rc;

    private SublimePickerFragment pickerFrag;

    private View root;

    private Timestamp timeStamp;

    private OrderContract.Presenter presenter;

    private long dateDelivery;

    private int paymentMethod;

    private ProgressDialog progressDialog;

    private ActionDefined actionDefined;

    private Billing billing;



    public ExtraInfoFragment() {
        // Required empty public constructor
    }

    public static ExtraInfoFragment newInstance(ActionDefined actionDefined) {
        ExtraInfoFragment fragment = new ExtraInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(Action.ACTION_FOR_ORDER, actionDefined);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionDefined = (ActionDefined) getArguments().getParcelable(Action.ACTION_FOR_ORDER);
        if (actionDefined.isEdit()) {
            billing = RealmBillingUtils.getBillCofirm();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_another_order, container, false);
        ButterKnife.bind(this, root);
        initilizeToolBar();
        showUI();
        if (actionDefined.isEdit())
            editFormExtra(billing.getExtraInformationDTO());
        return root;
    }

    Pair<Boolean, SublimeOptions> getOptions() {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;
        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;
        displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
        options.setDisplayOptions(displayOptions);
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }

    @Override
    public void initilizeToolBar() {
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Thông tin khác");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmBillingUtils.clearBillingConfirm();
                ActionUtils.goOrder(getActivity(), new ActionDefined(ActionForOrder.INITIALIZE, true));
            }
        });
    }

    @Override
    public void showUI() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(mFragmentCallback);
        Pair<Boolean, SublimeOptions> optionsPair = getOptions();

        if (!optionsPair.first) { // If options are not valid
            Toast.makeText(getActivity(), "No pickers activated",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        dateDelivery = date.getTime();
        Log.d("==========>", date.getTime() + "");
        timeStamp = new Timestamp(dateDelivery);
        editTextDate.setText(getDataDelivery(dateDelivery));
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pickerFrag.isVisible())
                    showDateTimePicker();
            }
        });


        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cartId = CartSharedPrefrence.getCartId(getActivity());
                String userId = UserUtils.getUser(getActivity()).getId();
                long deliverydate = dateDelivery / 1000;
                String noteMessage = note.getText().toString();
                String messageTemp = message.getText().toString();
                int hiddenInfo = 1;
                if (hide_info.isChecked())
                    hiddenInfo = 2;

                if (is_pay_credit.isChecked())
                    paymentMethod = 2;
                else
                    paymentMethod = 1;

                showIndicator("Đang cập thông tin", true);
                presenter.setInfoOrder(
                        cartId, userId, deliverydate, hiddenInfo, noteMessage, messageTemp, paymentMethod
                );
            }
        });

    }



    public String getDataDelivery(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd");
        String delivery =  format.format(date);
        String year = delivery.substring(0, 4);
        String month = delivery.substring(5, 7);
        String day = delivery.substring(8, 10);
        return "ngày " + day + " tháng " + month + " năm " + year;
    }

    @Override
    public void updateDistrict(List<District> districts, boolean problem) {

    }

    @Override
    public void updateDistrictRc(List<District> districts, boolean problem) {

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
    public boolean isInvoice() {
        return false;
    }

    @Override
    public void sendDataBilling() {

    }

    @Override
    public void sendDataShipping() {

    }

    @Override
    public void sendInvoice() {

    }

    @Override
    public void showDateTimePicker() {
        pickerFrag.show(getActivity().getSupportFragmentManager(), "SUBLIME_PICKER");
    }

    @Override
    public void editFormSender(BillingAddressDTO billingAddressDTO) {

    }

    @Override
    public void editFormInvoice(InvoiceAddressDTO invoiceAddressDTO) {

    }

    @Override
    public void editFormShipping(ShippingAddressDTO shippingAddressDTO) {

    }

    @Override
    public void editFormExtra(ExtraInformationDTO extraInformationDTO) {
        editTextDate.setText(getDataDelivery(extraInformationDTO.getDeliveryDate()*1000));
        if (extraInformationDTO.getPaymentMethodId() == 1)
            is_pay_rc.setChecked(true);
        else
            is_pay_credit.setChecked(true);

        message.setText(extraInformationDTO.getMessage());
        note.setText(extraInformationDTO.getNote());
        if (extraInformationDTO.getHideSender() == 2)
            hide_info.setChecked(true);

        buttonPay.setText("Cập nhập");
    }

    @Override
    public boolean isEdited() {
        return actionDefined.isEdit();
    }

    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
