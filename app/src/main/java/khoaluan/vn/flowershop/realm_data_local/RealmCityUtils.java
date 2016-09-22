package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.City;

/**
 * Created by samnguyen on 9/12/16.
 */
public class RealmCityUtils {
    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmCityUtils.class.getName();

    public static RealmResults<City> all(String flag) {
        return realm.where(City.class).equalTo("flag", flag).findAll();
    }

    public static void save(final List<City> cities) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(cities);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Save city successfully");
            }
        });
    }

    public static void updateAll(final List<City> citiesNew) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<City> cities = realm.where(City.class).findAll();
                cities.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                save(citiesNew);
            }
        });
    }
}
