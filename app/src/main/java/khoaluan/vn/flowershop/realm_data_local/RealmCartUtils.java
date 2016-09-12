package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;

/**
 * Created by samnguyen on 9/11/16.
 */
public class RealmCartUtils {
    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmCartUtils.class.getName();

    public static void save(final List<Cart> carts) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(carts);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Save New Cart Successfully");
            }
        });
    }

    public static RealmResults<Cart> all() {
        return realm.where(Cart.class).findAll();
    }

    public static void updateAll(final List<Cart> carts) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Cart> billings = realm.where(Cart.class).findAll();
                billings.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                save(carts);
            }
        });
    }

    public static void deleteById(final String id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Cart cart = realm.where(Cart.class).equalTo("Id", id).findFirst();
                cart.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Delete cart successfully");
            }
        });
    }

    public static void update(final Cart cartNew) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Cart cart = realm.where(Cart.class).equalTo("Id", cartNew.getId()).findFirst();
                cart.setProductQuantity(cartNew.getProductQuantity());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Update cart quantity success");
            }
        });
    }
}
