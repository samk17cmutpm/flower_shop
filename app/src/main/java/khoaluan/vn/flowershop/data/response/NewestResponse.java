package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Items;

/**
 * Created by samnguyen on 9/5/16.
 */
public class NewestResponse extends ApiResponse<Items>{

    @SerializedName("datas")
    private Items items;

    public NewestResponse(String errcode, String errmessage, boolean hasNext, boolean success, Items items) {
        super(errcode, errmessage, hasNext, success);
        this.items = items;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    @Override
    public Items getResult() {
        return getItems();
    }
}
