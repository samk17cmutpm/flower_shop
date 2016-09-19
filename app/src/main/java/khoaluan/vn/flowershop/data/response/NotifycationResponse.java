package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Notifycation;

/**
 * Created by samnguyen on 9/19/16.
 */
public class NotifycationResponse extends ApiResponse<List<Notifycation>> {
    @SerializedName("datas")
    private List<Notifycation> notifycations;
    public NotifycationResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public List<Notifycation> getResult() {
        return notifycations;
    }
}
