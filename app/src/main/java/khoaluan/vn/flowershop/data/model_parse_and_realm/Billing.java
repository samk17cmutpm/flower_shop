package khoaluan.vn.flowershop.data.model_parse_and_realm;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by samnguyen on 9/11/16.
 */
public class Billing extends RealmObject implements Parcelable {

    @SerializedName("Id")
    private String Id;

    @SerializedName("OrderCode")
    private String OrderCode;

    @SerializedName("CartId")
    private String CartId;

    @SerializedName("ShippingCost")
    private int ShippingCost;

    @SerializedName("VoucherCost")
    private int VoucherCost;

    @SerializedName("VATCost")
    private int VATCost;

    @SerializedName("TotalCost")
    private int TotalCost;

    @SerializedName("OrderStatusId")
    private int OrderStatusId;

    @SerializedName("OrderStatusString")
    private String OrderStatusString;

    @SerializedName("CancelComment")
    private String CancelComment;

    @SerializedName("OrderStatusImage")
    private String OrderStatusImage;

    @SerializedName("CreatedDate")
    private String CreatedDate;

    @SerializedName("CreatedOnUtc")
    private long CreatedOnUtc;

    @SerializedName("UpdatedOnUtc")
    private long UpdatedOnUtc;

    @SerializedName("billingAddressDTO")
    private BillingAddressDTO billingAddressDTO;

    @SerializedName("shippingAddressDTO")
    private ShippingAddressDTO shippingAddressDTO;

    @SerializedName("extraInformationDTO")
    private ExtraInformationDTO extraInformationDTO;

    @SerializedName("invoiceAddressDTO")
    private InvoiceAddressDTO invoiceAddressDTO;

    @SerializedName("orderItemsDTO")
    private RealmList<Cart> carts;

    private String flag;

    public Billing() {
    }

    public Billing(BillingAddressDTO billingAddressDTO, ShippingAddressDTO shippingAddressDTO, ExtraInformationDTO extraInformationDTO, InvoiceAddressDTO invoiceAddressDTO, RealmList<Cart> carts) {
        this.billingAddressDTO = billingAddressDTO;
        this.shippingAddressDTO = shippingAddressDTO;
        this.extraInformationDTO = extraInformationDTO;
        this.invoiceAddressDTO = invoiceAddressDTO;
        this.carts = carts;
    }

    public String getDateSetPayment() {
        String year = getCreatedDate().substring(0, 4);
        String month = getCreatedDate().substring(5, 7);
        String date = getCreatedDate().substring(8, 10);
        return "Đặt hàng ngày " + date + " tháng " + month + " năm " + year;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getCartId() {
        return CartId;
    }

    public void setCartId(String cartId) {
        CartId = cartId;
    }

    public int getShippingCost() {
        return ShippingCost;
    }

    public void setShippingCost(int shippingCost) {
        ShippingCost = shippingCost;
    }

    public int getVoucherCost() {
        return VoucherCost;
    }

    public void setVoucherCost(int voucherCost) {
        VoucherCost = voucherCost;
    }

    public int getVATCost() {
        return VATCost;
    }

    public void setVATCost(int VATCost) {
        this.VATCost = VATCost;
    }

    public int getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(int totalCost) {
        TotalCost = totalCost;
    }

    public int getOrderStatusId() {
        return OrderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        OrderStatusId = orderStatusId;
    }

    public String getOrderStatusString() {
        return OrderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        OrderStatusString = orderStatusString;
    }

    public String getCancelComment() {
        return CancelComment;
    }

    public void setCancelComment(String cancelComment) {
        CancelComment = cancelComment;
    }

    public String getOrderStatusImage() {
        return OrderStatusImage;
    }

    public void setOrderStatusImage(String orderStatusImage) {
        OrderStatusImage = orderStatusImage;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public long getCreatedOnUtc() {
        return CreatedOnUtc;
    }

    public void setCreatedOnUtc(long createdOnUtc) {
        CreatedOnUtc = createdOnUtc;
    }

    public long getUpdatedOnUtc() {
        return UpdatedOnUtc;
    }

    public void setUpdatedOnUtc(long updatedOnUtc) {
        UpdatedOnUtc = updatedOnUtc;
    }

    public BillingAddressDTO getBillingAddressDTO() {
        return billingAddressDTO;
    }

    public void setBillingAddressDTO(BillingAddressDTO billingAddressDTO) {
        this.billingAddressDTO = billingAddressDTO;
    }

    public ShippingAddressDTO getShippingAddressDTO() {
        return shippingAddressDTO;
    }

    public void setShippingAddressDTO(ShippingAddressDTO shippingAddressDTO) {
        this.shippingAddressDTO = shippingAddressDTO;
    }

    public ExtraInformationDTO getExtraInformationDTO() {
        return extraInformationDTO;
    }

    public void setExtraInformationDTO(ExtraInformationDTO extraInformationDTO) {
        this.extraInformationDTO = extraInformationDTO;
    }

    public InvoiceAddressDTO getInvoiceAddressDTO() {
        return invoiceAddressDTO;
    }

    public void setInvoiceAddressDTO(InvoiceAddressDTO invoiceAddressDTO) {
        this.invoiceAddressDTO = invoiceAddressDTO;
    }

    public RealmList<Cart> getCarts() {
        return carts;
    }

    public void setCarts(RealmList<Cart> carts) {
        this.carts = carts;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.OrderCode);
        dest.writeString(this.CartId);
        dest.writeInt(this.ShippingCost);
        dest.writeInt(this.VoucherCost);
        dest.writeInt(this.VATCost);
        dest.writeInt(this.TotalCost);
        dest.writeInt(this.OrderStatusId);
        dest.writeString(this.OrderStatusString);
        dest.writeString(this.CancelComment);
        dest.writeString(this.OrderStatusImage);
        dest.writeString(this.CreatedDate);
        dest.writeLong(this.CreatedOnUtc);
        dest.writeLong(this.UpdatedOnUtc);
        dest.writeParcelable(this.billingAddressDTO, flags);
        dest.writeParcelable(this.shippingAddressDTO, flags);
        dest.writeParcelable(this.extraInformationDTO, flags);
        dest.writeParcelable(this.invoiceAddressDTO, flags);
        dest.writeList(this.carts);
    }

    protected Billing(Parcel in) {
        this.Id = in.readString();
        this.OrderCode = in.readString();
        this.CartId = in.readString();
        this.ShippingCost = in.readInt();
        this.VoucherCost = in.readInt();
        this.VATCost = in.readInt();
        this.TotalCost = in.readInt();
        this.OrderStatusId = in.readInt();
        this.OrderStatusString = in.readString();
        this.CancelComment = in.readString();
        this.OrderStatusImage = in.readString();
        this.CreatedDate = in.readString();
        this.CreatedOnUtc = in.readLong();
        this.UpdatedOnUtc = in.readLong();
        this.billingAddressDTO = in.readParcelable(BillingAddressDTO.class.getClassLoader());
        this.shippingAddressDTO = in.readParcelable(ShippingAddressDTO.class.getClassLoader());
        this.extraInformationDTO = in.readParcelable(ExtraInformationDTO.class.getClassLoader());
        this.invoiceAddressDTO = in.readParcelable(InvoiceAddressDTO.class.getClassLoader());
        in.readList(this.carts, Cart.class.getClassLoader());
    }

    public static final Creator<Billing> CREATOR = new Creator<Billing>() {
        @Override
        public Billing createFromParcel(Parcel source) {
            return new Billing(source);
        }

        @Override
        public Billing[] newArray(int size) {
            return new Billing[size];
        }
    };
}
