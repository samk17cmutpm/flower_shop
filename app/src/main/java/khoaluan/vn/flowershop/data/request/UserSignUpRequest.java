package khoaluan.vn.flowershop.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samnguyen on 9/10/16.
 */
public class UserSignUpRequest {
    @SerializedName("Id")
    private String id;

    @SerializedName("Email")
    private String email;

    @SerializedName("FullName")
    private String fullName;

    @SerializedName("Address")
    private String address;

    @SerializedName("Phone")
    private String phone;

    @SerializedName("DeviceId")
    private String deviceId;

    @SerializedName("Password")
    private String password;

    public UserSignUpRequest(String email, String fullName, String address, String phone, String deviceId, String password) {
        this.email = email;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.deviceId = deviceId;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
