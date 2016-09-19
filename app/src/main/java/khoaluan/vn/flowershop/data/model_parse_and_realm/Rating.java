package khoaluan.vn.flowershop.data.model_parse_and_realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 9/19/16.
 */
public class Rating extends RealmObject{
    @SerializedName("Id")
    private String Id;

    @SerializedName("UserId")
    private String UserId;

    @SerializedName("FullName")
    private String FullName;

    @SerializedName("ProductId")
    private String ProductId;

    @SerializedName("StarRating")
    private Float StarRating;

    @SerializedName("Feedback")
    private String Feedback;

    @SerializedName("CreatedDate")
    private String CreatedDate;

    @SerializedName("CreatedOnUtc")
    private String CreatedOnUtc;

    public Rating() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public Float getStarRating() {
        return StarRating;
    }

    public void setStarRating(Float starRating) {
        StarRating = starRating;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreatedOnUtc() {
        return CreatedOnUtc;
    }

    public void setCreatedOnUtc(String createdOnUtc) {
        CreatedOnUtc = createdOnUtc;
    }
}
