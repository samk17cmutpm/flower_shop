package khoaluan.vn.flowershop.data.model_parse_and_realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 9/11/16.
 */
public class Cart extends RealmObject{

    @SerializedName("Id")
    private String Id;

    @SerializedName("CartId")
    private String CartId;

    @SerializedName("OrderId")
    private String OrderId;

    @SerializedName("ProductId")
    private String ProductId;

    @SerializedName("ProductName")
    private String ProductName;

    @SerializedName("ProductImage")
    private String ProductImage;

    @SerializedName("ProductQuantity")
    private int ProductQuantity;

    @SerializedName("ProductCost")
    private int ProductCost;

    public Cart() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCartId() {
        return CartId;
    }

    public void setCartId(String cartId) {
        CartId = cartId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public int getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        ProductQuantity = productQuantity;
    }

    public int getProductCost() {
        return ProductCost;
    }

    public void setProductCost(int productCost) {
        ProductCost = productCost;
    }
}
