package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Bank;

/**
 * Created by samnguyen on 9/15/16.
 */
public class RealmBankUtils {

    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmBankUtils.class.getName();

    public static void save(final List<Bank> banks) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(banks);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Save New Banls Successfully");
            }
        });
    }

    public static RealmResults<Bank> all() {
        return realm.where(Bank.class).findAll();
    }

    public static void updateAll(final List<Bank> banks) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Bank> bankRealmResults = realm.where(Bank.class).findAll();
                bankRealmResults.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                save(banks);
            }
        });
    }



}
