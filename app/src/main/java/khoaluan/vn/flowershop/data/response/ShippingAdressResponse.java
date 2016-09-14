package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;

/**
 * Created by samnguyen on 9/14/16.
 */
public class ShippingAdressResponse extends ApiResponse<ShippingAddressDTO> {
    @SerializedName("datas")
    private ShippingAddressDTO shippingAddressDTO;
    public ShippingAdressResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public ShippingAddressDTO getResult() {
        return shippingAddressDTO;
    }
}
