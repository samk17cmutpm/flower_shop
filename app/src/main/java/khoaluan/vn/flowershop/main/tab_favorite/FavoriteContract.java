package khoaluan.vn.flowershop.main.tab_favorite;

import java.util.List;

import io.realm.RealmResults;
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
        void updateChange(List<Flower> flowers);
        void updateTop(List<Flower> flowers);
    }
    interface Presenter extends BasePresenter {
        void loadData();
        RealmResults<Flower> loadFavoriteFlowers();
        RealmResults<Flower> loadTopFlowers();
        List<FavoriteItem> convertData(List<Flower> flowersFavorite, List<Flower> flowersRecommend, String title);
        void loadTopProducts();
    }
}
