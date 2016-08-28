package khoaluan.vn.flowershop.detail;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;

/**
 * Created by samnguyen on 7/28/16.
 */
public class DetailsPresenter implements DetailsContract.Presenter {
    private static String TAG = DetailsPresenter.class.getName();
    private final DetailsContract.View view;
    private final Activity activity;
    private final Realm realm;
    public DetailsPresenter(DetailsContract.View view, Activity activity) {
        this.view = view;
        this.activity = activity;
        this.view.setPresenter(this);
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void addToFavoriteList(Flower flower) {
        List<Flower> flowers = new ArrayList<>();
        flowers.add(flower);
        RealmFlowerUtils.save(flowers);
    }

    @Override
    public void removeFavoriteFlower(Flower flower) {
        RealmFlowerUtils.deleteById(RealmFlag.FLAG, RealmFlag.FAVORITE, flower.getId());
    }

    @Override
    public List<MutipleDetailItem> convertData(Flower flower, FlowerSuggesstion flowerSuggesstion) {
        List<MutipleDetailItem> items = new ArrayList<>();

        MutipleDetailItem header = new MutipleDetailItem();
        header.setItemType(MutipleDetailItem.HEADER);
        header.setFlower(flower);
        items.add(header);

        MutipleDetailItem shortDescription = new MutipleDetailItem();
        shortDescription.setItemType(MutipleDetailItem.SHORT_DESCRIPTION);
        shortDescription.setFlower(flower);
        items.add(shortDescription);

        MutipleDetailItem image = new MutipleDetailItem();
        image.setItemType(MutipleDetailItem.IMAGE);
        image.setFlower(flower);
        items.add(image);

        MutipleDetailItem action = new MutipleDetailItem();
        action.setItemType(MutipleDetailItem.ACTION);
        action.setFlower(flower);
        items.add(action);

        MutipleDetailItem fullDescription = new MutipleDetailItem();
        fullDescription.setItemType(MutipleDetailItem.FULL_DESCRIPTION);
        fullDescription.setFlower(flower);
        items.add(fullDescription);

        MutipleDetailItem address = new MutipleDetailItem();
        address.setItemType(MutipleDetailItem.ADDRESS);
        items.add(address);

        if (flowerSuggesstion != null) {
            MutipleDetailItem title = new MutipleDetailItem();
            title.setItemType(MutipleDetailItem.TITLE);
            items.add(title);

            MutipleDetailItem suggesstion = new MutipleDetailItem();
            suggesstion.setItemType(MutipleDetailItem.SUGGESTION);
            suggesstion.setFlowers(flowerSuggesstion.getFlowers());
            items.add(suggesstion);
        }
        return items;
    }

    @Override
    public void start() {

    }
}
