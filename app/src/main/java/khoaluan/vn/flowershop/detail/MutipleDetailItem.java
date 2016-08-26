package khoaluan.vn.flowershop.detail;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 8/25/16.
 */
public class MutipleDetailItem extends MultiItemEntity {
    public static final int HEADER = 1;
    public static final int SHORT_DESCRIPTION = 2;
    public static final int IMAGE = 3;
    public static final int FULL_DESCRIPTION = 4;
    public static final int TITLE = 5;
    public static final int SUGGESTION = 6;
    public static final int ACTION = 7;
    public static final int ADDRESS = 8;

    private Flower flower;
    private String title;
    private List<Flower> flowers;

    public MutipleDetailItem() {
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

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
}

