package khoaluan.vn.flowershop.main.tab_shop;

import java.util.List;

import io.realm.RealmResults;
import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;

/**
 * Created by samnguyen on 7/19/16.
 */
public interface ShopContract {
    interface View extends BaseView<Presenter>, Network {
        void showUI();
        void showIndicator(boolean active);
    }
    interface Presenter extends BasePresenter {
        void loadData();
        RealmResults<Cart> loadCartFlowers();
        int countTotalMoney(List<Cart> carts);
        boolean isExistedCart();

        void syncCarts(String cartId);
        void updateQuantity(String id, String idCart, String idProduct, int quantity);
        void removeCartItem(String id, String idCart, String idProduct);
        void notifyDataChange(int position, boolean changed);
        int getPosition();
        boolean isChanged();

    }
}
