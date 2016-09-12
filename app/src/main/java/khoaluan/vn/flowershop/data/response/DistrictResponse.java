package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.District;


/**
 * Created by samnguyen on 9/12/16.
 */
public class DistrictResponse extends ApiResponse<List<District>> {
    @SerializedName("datas")
    private List<District> districts;

    public DistrictResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public List<District> getResult() {
        return districts;
    }
}
