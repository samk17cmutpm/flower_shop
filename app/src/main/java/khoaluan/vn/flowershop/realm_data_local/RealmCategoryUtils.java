package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;

/**
 * Created by samnguyen on 8/30/16.
 */
public class RealmCategoryUtils {
    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmCategoryUtils.class.getName();

    public static List<Category> all(String tag) {
        switch (tag) {
            case RealmFlag.GIFT:
                return realm.where(Category.class).equalTo(RealmFlag.TYPE, RealmFlag.GIFT).findAll();
            default:
                return realm.where(Category.class).notEqualTo(RealmFlag.TYPE, RealmFlag.GIFT).findAll();
        }
    }

    public static void updateAll(final String tag, final List<Category> categories) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                switch (tag) {
                    case RealmFlag.GIFT:
                        RealmResults<Category> categories = realm.where(Category.class).equalTo(RealmFlag.TYPE, RealmFlag.GIFT).findAll();
                        categories.deleteAllFromRealm();
                        break;
                    default:
                        RealmResults<Category> categoriesFLower = realm.where(Category.class).notEqualTo(RealmFlag.TYPE, RealmFlag.GIFT).findAll();
                        categoriesFLower.deleteAllFromRealm();
                        break;
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Clear data local categories flowers successfully");
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(categories);
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
