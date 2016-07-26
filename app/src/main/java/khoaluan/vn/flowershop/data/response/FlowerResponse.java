package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 7/25/16.
 */
public class FlowerResponse extends ApiResponse<List<Flower>> {

    @SerializedName("datas")
    private List<Flower> flowers;

    public FlowerResponse(String errcode, String errmessage, boolean hasNext, boolean success, List<Flower> flowers) {
        super(errcode, errmessage, hasNext, success);
        this.flowers = flowers;
    }

    @Override
    public List<Flower> getResult() {
        return flowers;
    }
}
