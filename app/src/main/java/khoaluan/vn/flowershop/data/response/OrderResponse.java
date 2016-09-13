package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;

/**
 * Created by samnguyen on 9/13/16.
 */
public class OrderResponse extends ApiResponse<ShippingAddressDTO> {
    @SerializedName("datas")
    private ShippingAddressDTO shippingAddressDTO;
    public OrderResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public ShippingAddressDTO getResult() {
        return shippingAddressDTO;
    }
}
