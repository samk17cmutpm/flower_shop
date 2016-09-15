package khoaluan.vn.flowershop.data.model_parse_and_realm;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 9/11/16.
 *
 * "Id": "630afb73-7f7f-4e54-b95f-92d9ce768343",
 "CartId": "630afb73-7f7f-4e54-b95f-92d9ce768343",
 "UserId": "4264bf49-d1f9-434f-a23d-dfed41877149",
 "PaymentMethodId": 2,
 "PaymentMethodString": "Chuyển khoản qua ngân hàng",
 "HideSender": 0,
 "Note": "",
 "Message": "",
 "DeliveryDate": 1473530452
 */
public class ExtraInformationDTO extends RealmObject implements Parcelable {

    @SerializedName("Id")
    private String Id;

    @SerializedName("CartId")
    private String CartId;

    @SerializedName("UserId")
    private String UserId;

    @SerializedName("PaymentMethodId")
    private int PaymentMethodId;

    @SerializedName("PaymentMethodString")
    private String PaymentMethodString;

    @SerializedName("HideSender")
    private int HideSender;

    @SerializedName("Note")
    private String Note;

    @SerializedName("Message")
    private String Message;

    @SerializedName("DeliveryDate")
    private long DeliveryDate;

    public ExtraInformationDTO() {
    }

    public String getDataDelivery() {
        Date date = new Date(DeliveryDate*1000);
        Format format = new SimpleDateFormat("yyyy MM dd");
        String delivery =  format.format(date);
        String year = delivery.substring(0, 4);
        String month = delivery.substring(5, 7);
        String day = delivery.substring(8, 10);
        return "Giao hàng ngày " + day + " tháng " + month + " năm " + year;
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

    public int getPaymentMethodId() {
        return PaymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        PaymentMethodId = paymentMethodId;
    }

    public String getPaymentMethodString() {
        return PaymentMethodString;
    }

    public void setPaymentMethodString(String paymentMethodString) {
        PaymentMethodString = paymentMethodString;
    }

    public int getHideSender() {
        return HideSender;
    }

    public void setHideSender(int hideSender) {
        HideSender = hideSender;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public long getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(long deliveryDate) {
        DeliveryDate = deliveryDate;
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
        dest.writeInt(this.PaymentMethodId);
        dest.writeString(this.PaymentMethodString);
        dest.writeInt(this.HideSender);
        dest.writeString(this.Note);
        dest.writeString(this.Message);
        dest.writeLong(this.DeliveryDate);
    }

    protected ExtraInformationDTO(Parcel in) {
        this.Id = in.readString();
        this.CartId = in.readString();
        this.UserId = in.readString();
        this.PaymentMethodId = in.readInt();
        this.PaymentMethodString = in.readString();
        this.HideSender = in.readInt();
        this.Note = in.readString();
        this.Message = in.readString();
        this.DeliveryDate = in.readLong();
    }

    public static final Creator<ExtraInformationDTO> CREATOR = new Creator<ExtraInformationDTO>() {
        @Override
        public ExtraInformationDTO createFromParcel(Parcel source) {
            return new ExtraInformationDTO(source);
        }

        @Override
        public ExtraInformationDTO[] newArray(int size) {
            return new ExtraInformationDTO[size];
        }
    };
}
