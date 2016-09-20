package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Rating;

/**
 * Created by samnguyen on 9/20/16.
 */
public class RatingResponse extends ApiResponse<Rating> {

    @SerializedName("datas")
    private Rating rating;

    public RatingResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public Rating getResult() {
        return rating;
    }
}
