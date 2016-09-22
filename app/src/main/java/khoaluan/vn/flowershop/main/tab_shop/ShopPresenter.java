package khoaluan.vn.flowershop.main.tab_shop;

import java.util.List;

import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;
import khoaluan.vn.flowershop.data.response.CartResponse;
import khoaluan.vn.flowershop.data.response.RemoveResponse;
import khoaluan.vn.flowershop.data.shared_prefrences.CartSharedPrefrence;
import khoaluan.vn.flowershop.main.MainActivity;
import khoaluan.vn.flowershop.realm_data_local.RealmCartUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.CartClient;
import khoaluan.vn.flowershop.utils.MessageUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 7/19/16.
 */
public class ShopPresenter implements ShopContract.Presenter {
    private final MainActivity activity;
    private final ShopContract.View view;
    private final CartClient client;
    private int position;
    private int offSet;
    private boolean changed = false;
    public ShopPresenter (MainActivity activity, ShopContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.client = ServiceGenerator.createService(CartClient.class);
    }
    @Override
    public void loadData() {

    }

    @Override
    public RealmResults<Cart> loadCartFlowers() {
        return RealmCartUtils.all();
    }

    @Override
    public int countTotalMoney(List<Cart> carts) {
        int total = 0;
        for (Cart cart : carts) {
            total = total + (cart.getProductCost() * cart.getProductQuantity());
        }
        return total;
    }

    @Override
    public boolean isExistedCart() {
        if (CartSharedPrefrence.getCartId(activity) != null)
            return true;
        return false;
    }

    @Override
    public void syncCarts(String cartId) {
        Observable<Response<CartResponse>> observable =
                client.getCarts(cartId);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<CartResponse>>() {
                    private List<Cart> carts;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        RealmCartUtils.updateAll(carts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<CartResponse> cartResponseResponse) {
                        if (cartResponseResponse.isSuccessful()) {
                            carts = cartResponseResponse.body().getResult();
                        }
                    }
                });

    }

    @Override
    public void updateQuantity(final String id, String idCart, String idProduct, int quantity) {
        Observable<Response<CartResponse>> observable =
                client.updateQuantityCart(idCart, idProduct, quantity);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<CartResponse>>() {
                    private CartResponse cartResponse;
                    @Override
                    public void onCompleted() {
                        if (cartResponse.isSuccess()) {
                            Cart cartNew = null;
                            for (Cart cart : cartResponse.getResult()) {
                                if (cart.getId().equals(id)) {
                                    cartNew = cart;
                                    break;
                                }
                            }
                            RealmCartUtils.update(cartNew);
                        }else {
                            MessageUtils.showLong(activity, "Không thể thêm, xảy ra lỗi");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<CartResponse> cartResponseResponse) {
                        if (cartResponseResponse.isSuccessful())
                            cartResponse = cartResponseResponse.body();
                    }
                });
    }

    @Override
    public void removeCartItem(final String id, String idCart, String idProduct) {
        Observable<Response<RemoveResponse>> observable =
                client.removeCartItem(idCart, idProduct);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<RemoveResponse>>() {
                    private Boolean removed;
                    @Override
                    public void onCompleted() {
                        if (removed) {
                            RealmCartUtils.deleteById(id);
                        } else {
                            view.noInternetConnectTion();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<RemoveResponse> removeCartResponseResponse) {
                        if (removeCartResponseResponse.isSuccessful())
                            removed = removeCartResponseResponse.body().getResult();
                    }
                });

    }

    @Override
    public void notifyDataChange(int position, boolean changed) {
        this.position = position;
        this.changed = changed;

    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public boolean isChanged() {
        return this.changed;
    }

    @Override
    public void start() {

    }
}
