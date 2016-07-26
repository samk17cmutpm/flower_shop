package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;

/**
 * Created by samnguyen on 7/26/16.
 */
public class CategoryResponse extends ApiResponse<List<Category>>{
    @SerializedName("datas")
    private List<Category> categories;

    public CategoryResponse(String errcode, String errmessage, boolean hasNext, boolean success, List<Category> categories) {
        super(errcode, errmessage, hasNext, success);
        this.categories = categories;
    }

    @Override
    public List<Category> getResult() {
        return categories;
    }
}
