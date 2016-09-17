package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;

/**
 * Created by samnguyen on 9/17/16.
 */
public class ListShippingAddressResponse extends ApiResponse<List<ShippingAddressDTO>> {
    @SerializedName("datas")
    private List<ShippingAddressDTO> datas;
    public ListShippingAddressResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public List<ShippingAddressDTO> getResult() {
        return datas;
    }
}
