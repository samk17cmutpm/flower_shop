package khoaluan.vn.flowershop.data.model_parse_and_realm;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 9/11/16.
 *
 * "Id": "96918c8c-b5c7-4e8b-8f08-5c9415fff2da",
 "CartId": "630afb73-7f7f-4e54-b95f-92d9ce768343",
 "OrderId": null,
 "ProductId": "d6e61baa-ab97-4e11-aaa2-7b34a6f7a246",
 "ProductName": "Mừng tốt nghiệp 3",
 "ProductImage": "http://dienhoaquatang.com/ProductImages/D6E61BAA-AB97-4E11-AAA2-7B34A6F7A246.jpg",
 "ProductQuantity": 2,
 "ProductCost": 650000
 */
public class OrderItemsDTO extends RealmObject implements Parcelable {
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

    public OrderItemsDTO() {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.CartId);
        dest.writeString(this.OrderId);
        dest.writeString(this.ProductId);
        dest.writeString(this.ProductName);
        dest.writeString(this.ProductImage);
        dest.writeInt(this.ProductQuantity);
        dest.writeInt(this.ProductCost);
    }

    protected OrderItemsDTO(Parcel in) {
        this.Id = in.readString();
        this.CartId = in.readString();
        this.OrderId = in.readString();
        this.ProductId = in.readString();
        this.ProductName = in.readString();
        this.ProductImage = in.readString();
        this.ProductQuantity = in.readInt();
        this.ProductCost = in.readInt();
    }

    public static final Parcelable.Creator<OrderItemsDTO> CREATOR = new Parcelable.Creator<OrderItemsDTO>() {
        @Override
        public OrderItemsDTO createFromParcel(Parcel source) {
            return new OrderItemsDTO(source);
        }

        @Override
        public OrderItemsDTO[] newArray(int size) {
            return new OrderItemsDTO[size];
        }
    };
}
