package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Advertising;

/**
 * Created by samnguyen on 8/30/16.
 */
public class RealmAdvertisingUtils {
    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmAdvertisingUtils.class.getName();

    public static void save(final List<Advertising> advertisings) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(advertisings);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Save Advertising Data Successfully");
            }
        });
    }

    public static RealmResults<Advertising> all() {
        return realm.where(Advertising.class).findAll();
    }

    public static void updateAll(final List<Advertising> advertisings) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Advertising> advertisings = realm.where(Advertising.class).findAll();
                advertisings.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                save(advertisings);
            }
        });
    }

    public static void clear(final String tag, final String value) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Advertising> advertisings = realm.where(Advertising.class).findAll();
                advertisings.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Clear data local AdvertisingItem successfully");
            }
        });
    }
}
