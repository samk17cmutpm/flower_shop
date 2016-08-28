package khoaluan.vn.flowershop.main.tab_favorite;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.main.MainActivity;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;

/**
 * Created by samnguyen on 8/25/16.
 */
public class FavoritePresenter implements FavoriteContract.Presenter{
    private static String TAG = FavoritePresenter.class.getName();
    private final MainActivity activity;
    private final FavoriteContract.View view;
    private final Realm realm;
    public FavoritePresenter (MainActivity activity, FavoriteContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.realm = Realm.getDefaultInstance();
    }
    @Override
    public void loadData() {

    }

    @Override
    public RealmResults<Flower> loadFavoriteFlowers() {
        return RealmFlowerUtils.findBy(RealmFlag.FLAG, RealmFlag.FAVORITE);
//        RealmResults<Flower> flowers = realm.where(Flower.class).equalTo("isSearch", true).findAll();
//        return flowers;
    }

    @Override
    public List<FavoriteItem> convertData(List<Flower> flowersFavorite,
                                          List<Flower> flowersRecommend, String title) {
        List<FavoriteItem> favoriteItems = new ArrayList<>();

        FavoriteItem favorite = new FavoriteItem();

        favorite.setItemType(FavoriteItem.FAVORITE);

        favorite.setFavoriteFlowers(flowersFavorite);

        favoriteItems.add(favorite);

        FavoriteItem favoriteTitle = new FavoriteItem();

        favoriteTitle.setItemType(FavoriteItem.TITLE);

        favoriteTitle.setTitle(title);

        favoriteItems.add(favoriteTitle);

        FavoriteItem favoriteRecommend = new FavoriteItem();
        favoriteRecommend.setItemType(FavoriteItem.FLOWER);
        favoriteRecommend.setFlowers(flowersRecommend);

        favoriteItems.add(favoriteRecommend);

        return favoriteItems;
    }



    @Override
    public void start() {

    }


}
