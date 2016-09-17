package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.BillingAddressDTO;

/**
 * Created by samnguyen on 9/17/16.
 */
public class RealmAddressUtills {
    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmAddressUtills.class.getName();


    public static void updateBillongAddress(final BillingAddressDTO billingAddressDTO) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<BillingAddressDTO> results = realm.where(BillingAddressDTO.class).findAll();
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Remove all Billing Address Successfully");
                save(billingAddressDTO);
            }
        });
    }

    public static void save(final BillingAddressDTO billingAddressDTO) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(billingAddressDTO);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Save Billing Address Successfully");
            }
        });
    }

    public static BillingAddressDTO getBillingAddressDTO() {
        return realm.where(BillingAddressDTO.class).findFirst();
    }

    public static RealmResults<BillingAddressDTO> all() {
        return realm.where(BillingAddressDTO.class).findAll();
    }
}