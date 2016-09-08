package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.User;

/**
 * Created by samnguyen on 9/7/16.
 */
public class RealmUserUtils {
    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmUserUtils.class.getName();

    public static void save(final User user) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(user);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Save New User Successfully");
            }
        });
    }

    public static User load() {
        User user = realm.where(User.class).findFirst();
        return user;
    }

    public static void updateAll(final User user) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> items = realm.where(User.class).findAll();
                items.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Clear data local User successfully");
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(user);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Save data User success");
                    }
                });
            }
        });
    }
}
