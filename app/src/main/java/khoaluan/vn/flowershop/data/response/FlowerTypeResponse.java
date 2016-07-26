package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.FlowerType;

/**
 * Created by samnguyen on 7/26/16.
 */
public class FlowerTypeResponse extends ApiResponse<List<FlowerType>>{
    @SerializedName("datas")
    private List<FlowerType> flowerTypes;

    public FlowerTypeResponse(String errcode, String errmessage, boolean hasNext, boolean success, List<FlowerType> flowerTypes) {
        super(errcode, errmessage, hasNext, success);
        this.flowerTypes = flowerTypes;
    }

    @Override
    public List<FlowerType> getResult() {
        return flowerTypes;
    }
}
