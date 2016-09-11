package khoaluan.vn.flowershop.data.model_parse_and_realm;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 9/11/16.
 * "Id": "630afb73-7f7f-4e54-b95f-92d9ce768343",
 "CartId": "630afb73-7f7f-4e54-b95f-92d9ce768343",
 "UserId": "4264bf49-d1f9-434f-a23d-dfed41877149",
 "CompanyName": "",
 "TaxCode": "",
 "Address": "",
 "Longitude": 108.277199,
 "Latitude": 14.058324
 *
 *
 */
public class InvoiceAddressDTO extends RealmObject implements Parcelable {
    @SerializedName("Id")
    private String Id;

    @SerializedName("CartId")
    private String CartId;

    @SerializedName("UserId")
    private String UserId;

    @SerializedName("CompanyName")
    private String CompanyName;

    @SerializedName("TaxCode")
    private String TaxCode;

    @SerializedName("Address")
    private String Address;

    @SerializedName("Longitude")
    private Double Longitude;

    @SerializedName("Latitude")
    private Double Latitude;

    public InvoiceAddressDTO() {
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

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getTaxCode() {
        return TaxCode;
    }

    public void setTaxCode(String taxCode) {
        TaxCode = taxCode;
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
        dest.writeString(this.CompanyName);
        dest.writeString(this.TaxCode);
        dest.writeString(this.Address);
        dest.writeValue(this.Longitude);
        dest.writeValue(this.Latitude);
    }

    protected InvoiceAddressDTO(Parcel in) {
        this.Id = in.readString();
        this.CartId = in.readString();
        this.UserId = in.readString();
        this.CompanyName = in.readString();
        this.TaxCode = in.readString();
        this.Address = in.readString();
        this.Longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.Latitude = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<InvoiceAddressDTO> CREATOR = new Parcelable.Creator<InvoiceAddressDTO>() {
        @Override
        public InvoiceAddressDTO createFromParcel(Parcel source) {
            return new InvoiceAddressDTO(source);
        }

        @Override
        public InvoiceAddressDTO[] newArray(int size) {
            return new InvoiceAddressDTO[size];
        }
    };
}
