package khoaluan.vn.flowershop.data.model_parse_and_realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import khoaluan.vn.flowershop.main.tab_home.MultipleMainItem;

/**
 * Created by samnguyen on 9/5/16.
 */
public class Item extends RealmObject{

    @SerializedName("Title")
    private String title;

    @SerializedName("Advertising")
    private Advertising advertising;

    @SerializedName("List")
    private RealmList<Flower> flowers;

    public Item(String title, Advertising advertising, RealmList<Flower> flowers) {
        this.title = title;
        this.advertising = advertising;
        this.flowers = flowers;
    }

    public Item() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Advertising getAdvertising() {
        return advertising;
    }

    public void setAdvertising(Advertising advertising) {
        this.advertising = advertising;
    }

    public RealmList<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(RealmList<Flower> flowers) {
        this.flowers = flowers;
    }

    public int type() {
        if (getAdvertising() != null)
            if (getAdvertising().getAdvertisingItems().size() > 0)
                return MultipleMainItem.ADVERTISING;

        return MultipleMainItem.BOTH_TITLE_FLOWER;

    }
}
