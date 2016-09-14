package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import khoaluan.vn.flowershop.data.model_parse_and_realm.BillingAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;

/**
 * Created by samnguyen on 9/13/16.
 */
public class BillingAdressResponse extends ApiResponse<BillingAddressDTO> {
    @SerializedName("datas")
    private BillingAddressDTO billingAddressDTO;
    public BillingAdressResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public BillingAddressDTO getResult() {
        return billingAddressDTO;
    }
}
