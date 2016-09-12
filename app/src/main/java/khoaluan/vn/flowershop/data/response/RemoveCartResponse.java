package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samnguyen on 9/12/16.
 */
public class RemoveCartResponse extends ApiResponse<Boolean>{
    @SerializedName("datas")
    private Boolean datas;

    public RemoveCartResponse(String errcode, String errmessage, boolean hasNext, boolean success, Boolean datas) {
        super(errcode, errmessage, hasNext, success);
        this.datas = datas;
    }

    @Override
    public Boolean getResult() {
        return datas;
    }
}
