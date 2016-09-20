package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 9/20/16.
 */
public class FlowerResponse extends ApiResponse<Flower> {
    @SerializedName("datas")
    private Flower flower;
    public FlowerResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public Flower getResult() {
        return flower;
    }
}
