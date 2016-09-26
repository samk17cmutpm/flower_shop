package khoaluan.vn.flowershop.detail;

import android.view.MenuItem;

import java.util.List;

import io.realm.RealmResults;
import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Rating;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;

/**
 * Created by samnguyen on 7/28/16.
 */
public interface DetailsContract {

    interface View extends BaseView<Presenter>, CommonView.ToolBar, Network {
        void showUI();
        void updateBadge(int number, MenuItem menuItemBadge);
        void showIndicator(boolean active);
        void updateRatings(List<Rating> ratings);
        void forAd(Flower flower);
    }

    interface Presenter extends BasePresenter {
        void loadData();
        List<MutipleDetailItem> convertData(Flower flower, FlowerSuggesstion flowerSuggesstion);
        void addToFavoriteList(Flower flower);
        void removeFavoriteFlower(Flower flower);
        void addToCart(List<Flower> flowers);
        boolean isExistedInCart(Flower flower);
        RealmResults<Flower> getFlowersCart();

        void addToCart(String idCart, String idProduct, boolean buyNow);
        void loadRatingData(String ProductId);
        void loadFlowerDetail(String id);
    }
}
