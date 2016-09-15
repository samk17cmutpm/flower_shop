package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Bank;

/**
 * Created by samnguyen on 9/15/16.
 */
public class BankResponse extends ApiResponse<List<Bank>> {
    @SerializedName("datas")
    private List<Bank> banks;
    public BankResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public List<Bank> getResult() {
        return banks;
    }
}
