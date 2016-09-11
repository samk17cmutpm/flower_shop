package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;

/**
 * Created by samnguyen on 9/11/16.
 */
public class BillingResponse extends ApiResponse<List<Billing>> {
    @SerializedName("datas")
    private List<Billing> billings;
    public BillingResponse(String errcode, String errmessage, boolean hasNext, boolean success, List<Billing> billings) {
        super(errcode, errmessage, hasNext, success);
        this.billings = billings;
    }

    @Override
    public List<Billing> getResult() {
        return billings;
    }
}
