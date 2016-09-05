package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Items;

/**
 * Created by samnguyen on 9/6/16.
 */
public class RealmItemUtils {
    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmItemUtils.class.getName();

    public static void save(final Items items) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(items);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Save New Items Successfully");
            }
        });
    }

    public static Items load() {
        Items items = realm.where(Items.class).findFirst();
        return items;
    }

    public static void updateAll(final Items items) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Items> items = realm.where(Items.class).findAll();
                items.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Clear data local categories flowers successfully");
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(items);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Save data categories flowers success");
                    }
                });
            }
        });
    }
}
