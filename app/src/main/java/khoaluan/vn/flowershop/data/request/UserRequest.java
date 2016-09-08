package khoaluan.vn.flowershop.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samnguyen on 9/7/16.
 */
public class UserRequest {
    @SerializedName("Email")
    private String email;

    @SerializedName("Password")
    private String password;

    public UserRequest(String email, String password) {
        this.email = email;
        this.password = password;
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
}
