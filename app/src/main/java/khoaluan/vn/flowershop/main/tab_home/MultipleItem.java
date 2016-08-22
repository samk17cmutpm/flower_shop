package khoaluan.vn.flowershop.main.tab_home;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 8/22/16.
 */
public class MultipleItem extends MultiItemEntity {
    public static final int TITLE = 1;
    public static final int FLOWER = 2;

    private String title;
    private List<Flower> flowers;


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

    public MultipleItem(String title) {
        this.title = title;
    }

    public MultipleItem(List<Flower> flowers) {
        this.flowers = flowers;
    }

    public MultipleItem() {
    }
}

