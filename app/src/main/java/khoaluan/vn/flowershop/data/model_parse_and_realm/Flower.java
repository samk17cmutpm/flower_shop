package khoaluan.vn.flowershop.data.model_parse_and_realm;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 7/25/16.
 */
public class Flower extends RealmObject implements Parcelable {

    @SerializedName("Id")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("CategoryId")
    private String categoryId;

    @SerializedName("CategoryName")
    private String CategoryName;

    @SerializedName("Rating")
    private String rating;

    @SerializedName("ShortDescription")
    private String shortDescription;

    @SerializedName("FullDescription")
    private String fullDescription;

    @SerializedName("Image")
    private String image;

    @SerializedName("OldPrice")
    private int oldPrice;

    @SerializedName("Price")
    private int price;

    private String flag;

    private boolean isNewest;

    private boolean isSearch;

    private boolean isFavorite;

    private int number;

    public Flower() {
    }



    public Flower(String id, String name, String categoryId, String shortDescription, String fullDescription, String image, int oldPrice, int price) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.image = image;
        this.oldPrice = oldPrice;
        this.price = price;
    }

    public Flower(String id, String name, String categoryId, String categoryName, String rating, String shortDescription, String fullDescription, String image, int oldPrice, int price, boolean isNewest, boolean isSearch) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        CategoryName = categoryName;
        this.rating = rating;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.image = image;
        this.oldPrice = oldPrice;
        this.price = price;
        this.isNewest = isNewest;
        this.isSearch = isSearch;
    }

    public Flower(String id, String name, String categoryId, String categoryName, String rating, String shortDescription, String fullDescription, String image, int oldPrice, int price, String flag, boolean isNewest, boolean isSearch, boolean isFavorite, int number) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        CategoryName = categoryName;
        this.rating = rating;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.image = image;
        this.oldPrice = oldPrice;
        this.price = price;
        this.flag = flag;
        this.isNewest = isNewest;
        this.isSearch = isSearch;
        this.isFavorite = isFavorite;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public boolean isSearch() {
        return isSearch;
    }

    public void setSearch(boolean search) {
        isSearch = search;
    }

    public boolean isNewest() {
        return isNewest;
    }

    public void setNewest(boolean newest) {
        isNewest = newest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getMoney(int serverPrice) {
        if (serverPrice == 0)
            return null;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat();
        df.setDecimalFormatSymbols(symbols);
        df.setGroupingSize(3);
        df.setMaximumFractionDigits(2);
        return df.format(serverPrice) + " VND";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.categoryId);
        dest.writeString(this.CategoryName);
        dest.writeString(this.rating);
        dest.writeString(this.shortDescription);
        dest.writeString(this.fullDescription);
        dest.writeString(this.image);
        dest.writeInt(this.oldPrice);
        dest.writeInt(this.price);
        dest.writeString(this.flag);
        dest.writeByte(this.isNewest ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSearch ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
        dest.writeInt(this.number);
    }

    protected Flower(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.categoryId = in.readString();
        this.CategoryName = in.readString();
        this.rating = in.readString();
        this.shortDescription = in.readString();
        this.fullDescription = in.readString();
        this.image = in.readString();
        this.oldPrice = in.readInt();
        this.price = in.readInt();
        this.flag = in.readString();
        this.isNewest = in.readByte() != 0;
        this.isSearch = in.readByte() != 0;
        this.isFavorite = in.readByte() != 0;
        this.number = in.readInt();
    }

    public static final Creator<Flower> CREATOR = new Creator<Flower>() {
        @Override
        public Flower createFromParcel(Parcel source) {
            return new Flower(source);
        }

        @Override
        public Flower[] newArray(int size) {
            return new Flower[size];
        }
    };
}
