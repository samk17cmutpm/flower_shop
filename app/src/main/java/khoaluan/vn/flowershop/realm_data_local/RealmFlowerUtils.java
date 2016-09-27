package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 8/28/16.
 */
public class RealmFlowerUtils {
    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmFlowerUtils.class.getName();

    public static void save(final List<Flower> flowers) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(flowers);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Save New Data Successfully");
            }
        });
    }

    public static RealmResults<Flower> findBy(String tag, String value) {
        RealmResults<Flower> flowers = realm.where(Flower.class).equalTo(tag, value).findAll();
        return flowers;
    }

    public static void clear(final String tag, final String value) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Flower> flowers = realm.where(Flower.class).equalTo(tag, value).findAll();
                flowers.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Clear data local flowers successfully");
            }
        });
    }

    public static boolean isExistedById(String tag, String value, String flowerId) {
        List<Flower> flowers = findBy(tag, value);
        for (Flower flower : flowers) {
            if (flower.getId().equals(flowerId))
                return true;
        }
        return false;
    }

    public static void deleteById(final String tag, final String value, final String flowerId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Flower> flowers = realm.where(Flower.class).equalTo(tag, value).equalTo("id", flowerId).findAll();
                flowers.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Clear favorite local flowers successfully");
            }
        });
    }

    public static void updateNumberById(final String tag, final String value, final String flowerId, final int number) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Flower flower = realm.where(Flower.class).equalTo(tag, value).equalTo("id", flowerId).findFirst();
                flower.setNumber(number);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "update number favorite local flowers successfully");
            }
        });
    }

    public static void updateAll(final String tag, final String value, final List<Flower> flowers) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Flower> flowers = realm.where(Flower.class).equalTo(tag, value).findAll();
                flowers.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                for (Flower flower : flowers)
                    flower.setFlag(value);
                save(flowers);
            }
        });
    }


}
