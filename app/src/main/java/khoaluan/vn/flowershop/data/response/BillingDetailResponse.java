package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;

/**
 * Created by samnguyen on 9/11/16.
 */
public class BillingDetailResponse extends ApiResponse<Billing> {
    @SerializedName("datas")
    private Billing billing;

    public BillingDetailResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public Billing getResult() {
        return billing;
    }
}
