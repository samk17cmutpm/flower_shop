package khoaluan.vn.flowershop.data.model_parse_and_realm;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 9/11/16.
 */
public class BillingAddressDTO extends RealmObject implements Parcelable {
    @SerializedName("Id")
    private String Id;

    @SerializedName("CartId")
    private String CartId;

    @SerializedName("UserId")
    private String UserId;

    @SerializedName("Name")
    private String Name;

    @SerializedName("Phone")
    private String Phone;

    @SerializedName("CityId")
    private String CityId;

    @SerializedName("CityString")
    private String CityString;

    @SerializedName("DistrictId")
    private String DistrictId;

    @SerializedName("DistrictString")
    private String DistrictString;

    @SerializedName("Address")
    private String Address;

    @SerializedName("Longitude")
    private Double Longitude;

    @SerializedName("Latitude")
    private Double Latitude;

    public BillingAddressDTO() {
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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCityString() {
        return CityString;
    }

    public void setCityString(String cityString) {
        CityString = cityString;
    }

    public String getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(String districtId) {
        DistrictId = districtId;
    }

    public String getDistrictString() {
        return DistrictString;
    }

    public void setDistrictString(String districtString) {
        DistrictString = districtString;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.CartId);
        dest.writeString(this.UserId);
        dest.writeString(this.Name);
        dest.writeString(this.Phone);
        dest.writeString(this.CityId);
        dest.writeString(this.CityString);
        dest.writeString(this.DistrictId);
        dest.writeString(this.DistrictString);
        dest.writeString(this.Address);
        dest.writeValue(this.Longitude);
        dest.writeValue(this.Latitude);
    }

    protected BillingAddressDTO(Parcel in) {
        this.Id = in.readString();
        this.CartId = in.readString();
        this.UserId = in.readString();
        this.Name = in.readString();
        this.Phone = in.readString();
        this.CityId = in.readString();
        this.CityString = in.readString();
        this.DistrictId = in.readString();
        this.DistrictString = in.readString();
        this.Address = in.readString();
        this.Longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.Latitude = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<BillingAddressDTO> CREATOR = new Parcelable.Creator<BillingAddressDTO>() {
        @Override
        public BillingAddressDTO createFromParcel(Parcel source) {
            return new BillingAddressDTO(source);
        }

        @Override
        public BillingAddressDTO[] newArray(int size) {
            return new BillingAddressDTO[size];
        }
    };
}
