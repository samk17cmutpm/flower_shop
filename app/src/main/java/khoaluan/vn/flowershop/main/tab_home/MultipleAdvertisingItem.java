package khoaluan.vn.flowershop.main.tab_home;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.AdvertisingItem;

/**
 * Created by samnguyen on 8/23/16.
 */
public class MultipleAdvertisingItem extends MultiItemEntity {
    public static final int ONLY_ONE = 1;
    public static final int MORE = 2;

    private List<AdvertisingItem> advertisingItems;

    private int width;

    private int height;

    public List<AdvertisingItem> getAdvertisingItems() {
        return advertisingItems;
    }

    public void setAdvertisingItems(List<AdvertisingItem> advertisingItems) {
        this.advertisingItems = advertisingItems;
    }

    public MultipleAdvertisingItem(List<AdvertisingItem> advertisingItems) {
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

    public MultipleAdvertisingItem() {
    }
}
