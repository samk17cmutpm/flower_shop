package khoaluan.vn.flowershop.user_data.billings;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;

/**
 * Created by samnguyen on 9/17/16.
 */
public class InvoiceAdapter extends BaseQuickAdapter<InvoiceAddressDTO> {
    public InvoiceAdapter(List<InvoiceAddressDTO> data) {
        super(R.layout.shiiping_address_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, InvoiceAddressDTO invoiceAddressDTO) {
        baseViewHolder.setText(R.id.tv_full_name, "Tên công ty : " + invoiceAddressDTO.getCompanyName())
                .setText(R.id.tv_phone_number, "Mã số thuế : " + invoiceAddressDTO.getTaxCode())
                .setText(R.id.tv_address, "Địa chỉ : " + invoiceAddressDTO.getAddress());
    }
}
