package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.City;

/**
 * Created by samnguyen on 9/12/16.
 */
public class CityResponse extends ApiResponse<List<City>> {
    @SerializedName("datas")
    private List<City> cities;
    public CityResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public List<City> getResult() {
        return cities;
    }
}
