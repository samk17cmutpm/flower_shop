package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Notifycation;

/**
 * Created by samnguyen on 9/19/16.
 */
public class RealmNotifycationUtils {
    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmNotifycationUtils.class.getName();

    public static void updateAll(final List<Notifycation> notifycations) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Notifycation> results = realm.where(Notifycation.class).findAll();
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                save(notifycations);
            }
        });
    }

    public static void save(final List<Notifycation> notifycations) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(notifycations);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Save new Notifycations Successfully");
            }
        });
    }

    public static RealmResults<Notifycation> all() {
        return realm.where(Notifycation.class).findAll();
    }
}
