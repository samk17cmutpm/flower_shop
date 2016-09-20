package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Rating;

/**
 * Created by samnguyen on 9/19/16.
 */
public class RatingListResponse extends ApiResponse<List<Rating>> {
    @SerializedName("datas")
    private List<Rating> ratings;

    public RatingListResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public List<Rating> getResult() {
        return ratings;
    }
}
