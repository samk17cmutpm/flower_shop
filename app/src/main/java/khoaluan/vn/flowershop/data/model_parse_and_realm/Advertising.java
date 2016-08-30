package khoaluan.vn.flowershop.data.model_parse_and_realm;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by samnguyen on 8/23/16.
 */
public class Advertising extends RealmObject {

    @SerializedName("Width")
    private int width;

    @SerializedName("Height")
    private int height;

    @SerializedName("AdvertisingItems")
    private RealmList<AdvertisingItem> advertisingItems;

    public Advertising() {
    }

    public Advertising(int width, int height, RealmList<AdvertisingItem> advertisingItems) {
        this.width = width;
        this.height = height;
        this.advertisingItems = advertisingItems;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<AdvertisingItem> getAdvertisingItems() {
        return advertisingItems;
    }

    public void setAdvertisingItems(RealmList<AdvertisingItem> advertisingItems) {
        this.advertisingItems = advertisingItems;
    }
}
