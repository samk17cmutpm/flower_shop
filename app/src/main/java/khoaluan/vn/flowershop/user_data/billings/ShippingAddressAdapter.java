package khoaluan.vn.flowershop.user_data.billings;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;

/**
 * Created by samnguyen on 9/17/16.
 */
public class ShippingAddressAdapter extends BaseQuickAdapter<ShippingAddressDTO> {
    public ShippingAddressAdapter(List<ShippingAddressDTO> data) {
        super(R.layout.shiiping_address_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ShippingAddressDTO shippingAddressDTO) {
        baseViewHolder.setText(R.id.tv_full_name, shippingAddressDTO.getName())
                .setText(R.id.tv_phone_number, "Số điện thoại : " + shippingAddressDTO.getPhone())
                .setText(R.id.tv_address, shippingAddressDTO.getAddress() + ", " + shippingAddressDTO.getDistrictString() + ", " + shippingAddressDTO.getCityString());
    }
}
