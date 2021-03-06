package khoaluan.vn.flowershop.data.model_parse_and_realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 8/23/16.
 */
public class AdvertisingItem extends RealmObject{
    public static final int PRODUCT = 0;
    public static final int CATEGORY = 1;
    @SerializedName("CategoryId")
    private String categoryId;

    @SerializedName("ProductId")
    private String productId;

    @SerializedName("Image")
    private String image;

    public AdvertisingItem() {
    }

    public AdvertisingItem(String categoryId, String productId, String image) {
        this.categoryId = categoryId;
        this.productId = productId;
        this.image = image;
    }

    public int getType() {
        if (getCategoryId() != null)
            return CATEGORY;
        return PRODUCT;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
