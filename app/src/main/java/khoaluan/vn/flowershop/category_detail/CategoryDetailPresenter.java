package khoaluan.vn.flowershop.category_detail;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.response.FlowerResponse;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.FlowerClient;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 7/28/16.
 */
public class CategoryDetailPresenter implements CategoryDetailContract.Presenter, Base {
    private static String TAG = CategoryDetailPresenter.class.getName();
    private final CategoryDetailContract.View view;
    private final Activity activity;
    private final Realm realm;
    private final FlowerClient client;
    private int currentPage = 0;
    private boolean hasNext;
    private final Category category;

    public CategoryDetailPresenter(CategoryDetailContract.View view, Activity activity, Category category) {
        this.view = view;
        this.activity = activity;
        this.view.setPresenter(this);
        this.category = category;
        this.realm = Realm.getDefaultInstance();
        this.client = ServiceGenerator.createService(FlowerClient.class);
    }

    @Override
    public void loadData() {
        this.view.showIndicator(true);
        this.view.showFlowers(RealmFlowerUtils.findBy(RealmFlag.FLAG, category.getName()), false);
        loadRefreshData();
    }

    @Override
    public void loadRefreshData() {
        Observable<Response<FlowerResponse>> observable =
                client.getFlowersByCategory(category.getId(), 0, SIZE);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<FlowerResponse>>() {
                    private List<Flower> flowers = new ArrayList<>();
                    @Override
                    public void onCompleted() {
                        view.clearAllDataLocal();
                        view.showIndicator(false);
                        view.setEmptyRecyclerView("Hiện Tại Chưa Có Dữ Liệu Cho Mục Này");
                        view.showFlowers(flowers, hasNext);
                        RealmFlowerUtils.updateAll(RealmFlag.FLAG, category.getName(), flowers);
                        currentPage = 1;
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<FlowerResponse> mostFlowerResponseResponse) {
                        if (mostFlowerResponseResponse.isSuccessful()) {
                            flowers.addAll(mostFlowerResponseResponse.body().getResult());
                            hasNext = mostFlowerResponseResponse.body().isHasNext();
                        }
                    }
                });


    }

    @Override
    public void loadMoreData() {
        Observable<Response<FlowerResponse>> observable =
                client.getFlowersByCategory(category.getId(), currentPage, SIZE);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<FlowerResponse>>() {
                    private List<Flower> flowers = new ArrayList<>();
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        view.showFlowers(flowers, hasNext);
                        view.finishLoadMore(hasNext);
                        currentPage ++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.finishLoadMore(true);
                        view.noInternetConnectTion();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<FlowerResponse> mostFlowerResponseResponse) {
                        if (mostFlowerResponseResponse.isSuccessful()) {
                            flowers.addAll(mostFlowerResponseResponse.body().getResult());
                            hasNext = mostFlowerResponseResponse.body().isHasNext();
                        }

                    }
                });
    }

    @Override
    public void start() {

    }

    @Override
    public List<Flower> loadLocalFlowersByCategory(String categoryId) {
        RealmResults<Flower> flowers = realm.where(Flower.class).equalTo("categoryId", categoryId).findAll();
        return flowers;
    }

    @Override
    public void updateLocalFlowersByCategory(final String categoryId, final List<Flower> list) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Flower> flowers = realm.where(Flower.class).equalTo("categoryId", categoryId).findAll();
                flowers.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Clear data local flowers by category successfully");
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(list);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Save data by category success");
                    }
                });
            }
        });
    }

    @Override
    public Category getCategory() {
        return category;
    }
}
