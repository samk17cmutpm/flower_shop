package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Advertising;

/**
 * Created by samnguyen on 8/23/16.
 */
public class AdvertisingResponse extends ApiResponse<List<Advertising>> {

    @SerializedName("datas")
    private List<Advertising> advertisings;

    public AdvertisingResponse(String errcode, String errmessage, boolean hasNext, boolean success, List<Advertising> advertisings) {
        super(errcode, errmessage, hasNext, success);
        this.advertisings = advertisings;
    }

    @Override
    public List<Advertising> getResult() {
        return advertisings;
    }
}
