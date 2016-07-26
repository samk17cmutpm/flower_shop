package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.client_parse.Flower;
import khoaluan.vn.flowershop.retrofit.client.FlowerClient;

/**
 * Created by samnguyen on 7/25/16.
 */
public class MostFlowerResponse extends ApiResponse<List<khoaluan.vn.flowershop.data.client_parse.Flower>> {

    @SerializedName("datas")
    private List<Flower> flowers;

    public MostFlowerResponse(String errcode, String errmessage, boolean hasNext, boolean success, List<Flower> flowers) {
        super(errcode, errmessage, hasNext, success);
        this.flowers = flowers;
    }

    @Override
    public List<Flower> getResult() {
        return flowers;
    }
}
