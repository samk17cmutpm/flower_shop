package khoaluan.vn.flowershop.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samnguyen on 9/14/16.
 */
public class InvoiceRequest {
    @SerializedName("userId")
    private String userId;

    @SerializedName("companyName")
    private String companyName;

    @SerializedName("taxCode")
    private String taxCode;

    @SerializedName("address")
    private String address;

    public InvoiceRequest(String userId, String companyName, String taxCode, String address) {
        this.userId = userId;
        this.companyName = companyName;
        this.taxCode = taxCode;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
