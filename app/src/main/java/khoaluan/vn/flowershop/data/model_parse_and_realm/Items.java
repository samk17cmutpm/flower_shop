package khoaluan.vn.flowershop.data.model_parse_and_realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by samnguyen on 9/5/16.
 */
public class Items extends RealmObject {

    @SerializedName("Items")
    private RealmList<Item> items;

    public Items(RealmList<Item> items) {
        this.items = items;
    }

    public Items() {
    }

    public RealmList<Item> getItems() {
        return items;
    }

    public void setItems(RealmList<Item> items) {
        this.items = items;
    }


}
