package khoaluan.vn.flowershop.user_data;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForUserData;
import khoaluan.vn.flowershop.user_data.billings.MultipleBillingItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAndEditInvoiceAddressFragment extends BaseFragment implements UserDataContract.View, CommonView.ToolBar {

    private UserDataContract.Presenter presenter;

    private View root;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.full_name)
    EditText fullName;

    @BindView(R.id.phone)
    EditText phone;

    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.update)
    Button update;

    private boolean isEdit = false;

    private ProgressDialog progressDialog;

    private InvoiceAddressDTO invoiceAddressDTO;

    public AddAndEditInvoiceAddressFragment() {
        // Required empty public constructor
    }

    public static AddAndEditInvoiceAddressFragment newInstance(InvoiceAddressDTO invoiceAddressDTO) {
        AddAndEditInvoiceAddressFragment fragment = new AddAndEditInvoiceAddressFragment();
        Bundle args = new Bundle();
        args.putParcelable(Action.ACTION_FOR_SHIPPING_ADDRESS, invoiceAddressDTO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invoiceAddressDTO = (InvoiceAddressDTO) getArguments().get(Action.ACTION_FOR_INVOICE_ADDRESS);
        if (invoiceAddressDTO != null)
            isEdit = true;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_and_edit_invoice_address, container, false);
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
        fullName.setText(invoiceAddressDTO.getCompanyName());
        phone.setText(invoiceAddressDTO.getTaxCode());
        address.setText(invoiceAddressDTO.getAddress());
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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {

                } else {

                }
            }
        });

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

    }

    @Override
    public void setPresenter(UserDataContract.Presenter presenter) {

    }

    @Override
    public void noInternetConnectTion() {

    }
}
