package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;

/**
 * Created by samnguyen on 9/11/16.
 */
public class CartResponse extends ApiResponse<List<Cart>> {
    @SerializedName("datas")
    private List<Cart> carts;

    public CartResponse(String errcode, String errmessage, boolean hasNext, boolean success, List<Cart> carts) {
        super(errcode, errmessage, hasNext, success);
        this.carts = carts;
    }

    @Override
    public List<Cart> getResult() {
        return carts;
    }
}
