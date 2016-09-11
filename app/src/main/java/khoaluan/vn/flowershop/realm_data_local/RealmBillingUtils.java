package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;

/**
 * Created by samnguyen on 9/11/16.
 */
public class RealmBillingUtils {

    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmBillingUtils.class.getName();

    public static void save(final List<Billing> billing) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(billing);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Save New Billings Successfully");
            }
        });

    }

    public static RealmResults<Billing> all() {
        return realm.where(Billing.class).findAll();
    }

    public static void updateAll(final List<Billing> newBillings) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Billing> billings = realm.where(Billing.class).findAll();
                billings.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                save(newBillings);
            }
        });
    }
}
