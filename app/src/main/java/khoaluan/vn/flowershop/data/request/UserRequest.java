package khoaluan.vn.flowershop.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samnguyen on 9/7/16.
 */
public class UserRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("deviceId")
    private String deviceId;

    @SerializedName("provider")
    private String provider;

    @SerializedName("externaltoken")
    private String externalToken;

    public UserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserRequest(String email, String password, String deviceId) {
        this.email = email;
        this.password = password;
        this.deviceId = deviceId;
    }

    public UserRequest(String email, String provider, String externalToken, String deviceId) {
        this.email = email;
        this.provider = provider;
        this.externalToken = externalToken;
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getExternalToken() {
        return externalToken;
    }

    public void setExternalToken(String externalToken) {
        this.externalToken = externalToken;
    }
}
