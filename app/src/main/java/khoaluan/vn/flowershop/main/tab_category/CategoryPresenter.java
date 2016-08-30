package khoaluan.vn.flowershop.main.tab_category;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.response.CategoryResponse;
import khoaluan.vn.flowershop.main.MainActivity;
import khoaluan.vn.flowershop.realm_data_local.RealmCategoryUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.FlowerClient;
import khoaluan.vn.flowershop.utils.ConvertUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 7/19/16.
 */
public class CategoryPresenter implements CategoryContract.Presenter {
    private final CategoryContract.View view;
    private final MainActivity activity;
    private final Realm realm;
    private final FlowerClient client;

    private static final String GIFT = "GIFT";
    private static final String TYPE = "type";
    private static final String TAG = CategoryPresenter.class.getName();

    public CategoryPresenter(MainActivity activity, CategoryContract.View view) {
        this.view = view;
        this.activity = activity;
        this.view.setPresenter(this);
        this.realm = Realm.getDefaultInstance();
        this.client = ServiceGenerator.createService(FlowerClient.class);
    }

    @Override
    public void loadData() {
        view.showCategories(ConvertUtils.convertCategoriseToExpandCategories(RealmCategoryUtils.all(RealmFlag.FLOWER)));
        loadFlowerCategories();
        loadGiftCategories();
    }

    @Override
    public void loadFlowerCategories() {
        Observable<Response<CategoryResponse>> observable =
                client.getFlowerCategories();

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<CategoryResponse>>() {
                    private List<Category> categories = new ArrayList<Category>();
                    @Override
                    public void onCompleted() {
                        RealmCategoryUtils.updateAll(RealmFlag.FLOWER, categories);
                        view.showCategories(ConvertUtils.convertCategoriseToExpandCategories(categories));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<CategoryResponse> categoryResponseResponse) {
                        if (categoryResponseResponse.isSuccessful())
                            categories.addAll(categoryResponseResponse.body().getResult());
                    }
                });
    }

    @Override
    public void loadGiftCategories() {
        Observable<Response<CategoryResponse>> observable =
                client.getGiftCategories();

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<CategoryResponse>>() {
                    private List<Category> categories = new ArrayList<Category>();
                    @Override
                    public void onCompleted() {
                        RealmCategoryUtils.updateAll(RealmFlag.GIFT, categories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<CategoryResponse> categoryResponseResponse) {
                        if (categoryResponseResponse.isSuccessful())
                            categories.addAll(categoryResponseResponse.body().getResult());
                    }
                });
    }

    @Override
    public List<Category> loadLocalFlowerCategories() {
        RealmResults<Category> categories = realm.where(Category.class).notEqualTo(TYPE, GIFT).findAll();
        return categories;
    }

    @Override
    public List<Category> loadLocalGiftCategories() {
        RealmResults<Category> categories = realm.where(Category.class).equalTo(TYPE, GIFT).findAll();
        return categories;
    }

    @Override
    public void updateLocalFlowerCategories(final List<Category> newList) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Category> categories = realm.where(Category.class).notEqualTo(TYPE, GIFT).findAll();
                categories.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Clear data local categories flowers successfully");
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(newList);
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

    @Override
    public void updateLocalGiftCategories(final List<Category> newList) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Category> categories = realm.where(Category.class).equalTo(TYPE, GIFT).findAll();
                categories.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Clear data local categories flowers successfully");
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(newList);
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


    @Override
    public void start() {

    }

}
