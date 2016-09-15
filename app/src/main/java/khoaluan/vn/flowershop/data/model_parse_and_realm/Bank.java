package khoaluan.vn.flowershop.data.model_parse_and_realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 9/15/16.
 */
public class Bank extends RealmObject {

    @SerializedName("AccountOwner")
    private String AccountOwner;

    @SerializedName("AccountNumber")
    private String AccountNumber;


    @SerializedName("Bank")
    private String Bank;

    @SerializedName("Image")
    private String Image;

    public Bank() {
    }

    public String getAccountOwner() {
        return AccountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        AccountOwner = accountOwner;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
