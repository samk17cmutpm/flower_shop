package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samnguyen on 7/25/16.
 */
public abstract class ApiResponse<T> {
    @SerializedName("errcode")
    private String errcode;

    @SerializedName("errmessage")
    private String errmessage;

    @SerializedName("hasNext")
    private boolean hasNext;

    @SerializedName("success")
    private boolean success;

    public abstract T getResult();

    public ApiResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        this.errcode = errcode;
        this.errmessage = errmessage;
        this.hasNext = hasNext;
        this.success = success;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmessage() {
        return errmessage;
    }

    public void setErrmessage(String errmessage) {
        this.errmessage = errmessage;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
