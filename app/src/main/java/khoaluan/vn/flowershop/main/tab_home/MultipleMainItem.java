package khoaluan.vn.flowershop.main.tab_home;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Advertising;
import khoaluan.vn.flowershop.data.model_parse_and_realm.AdvertisingItem;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 8/22/16.
 */
public class MultipleMainItem extends MultiItemEntity {
    public static final int TITLE = 1;
    public static final int FLOWER = 2;
    public static final int ADVERTISING = 3;

    private String title;
    private List<Flower> flowers;
    private List<Advertising> advertisings;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
    }

    public List<Advertising> getAdvertisings() {
        return advertisings;
    }

    public void setAdvertisings(List<Advertising> advertisings) {
        this.advertisings = advertisings;
    }

    public MultipleMainItem() {
    }
}

