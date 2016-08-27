package khoaluan.vn.flowershop.main.tab_favorite;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import khoaluan.vn.flowershop.action.action_presenter.RealmAction;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 8/27/16.
 */
public class FavoriteItem extends MultiItemEntity {
    public static final int FAVORITE = 1;
    public static final int FLOWER = 3;
    public static final int TITLE =2;

    private List<Flower> favoriteFlowers;
    private String title;
    private List<Flower> flowers;

    public FavoriteItem() {
    }

    public List<Flower> getFavoriteFlowers() {
        return favoriteFlowers;
    }

    public void setFavoriteFlowers(List<Flower> favoriteFlowers) {
        this.favoriteFlowers = favoriteFlowers;
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
