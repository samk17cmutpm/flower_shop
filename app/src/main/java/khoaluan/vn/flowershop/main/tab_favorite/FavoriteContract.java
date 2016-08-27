package khoaluan.vn.flowershop.main.tab_favorite;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 8/25/16.
 */
public interface FavoriteContract {
    interface View extends BaseView<Presenter> {
        void showUI();
        void showIndicator(boolean active);
    }
    interface Presenter extends BasePresenter {
        void loadData();
        List<Flower> loadFavoriteFlowers();
        List<FavoriteItem> convertData(List<Flower> flowersFavorite, List<Flower> flowersRecommend, String title);
    }
}
