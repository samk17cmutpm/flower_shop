package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import khoaluan.vn.flowershop.data.model_parse_and_realm.User;

/**
 * Created by samnguyen on 9/7/16.
 */
public class UserResponse extends ApiResponse<User> {

    @SerializedName("datas")
    private User user;

    public UserResponse(String errcode, String errmessage, boolean hasNext, boolean success, User user) {
        super(errcode, errmessage, hasNext, success);
        this.user = user;
    }

    @Override
    public User getResult() {
        return user;
    }
}
