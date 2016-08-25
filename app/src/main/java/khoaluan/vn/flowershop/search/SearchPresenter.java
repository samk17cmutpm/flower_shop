package khoaluan.vn.flowershop.search;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.response.FlowerResponse;
import khoaluan.vn.flowershop.main.MainActivity;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.FlowerClient;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 7/19/16.
 */
public class SearchPresenter implements SearchContract.Presenter, Base {
    private static String TAG = SearchPresenter.class.getName();
    private final Activity activity;
    private final SearchContract.View view;
    private final FlowerClient client;
    private int currentPage = 0;
    private boolean hasNext;
    private String key;
    private final Realm realm;

    public SearchPresenter (Activity activity, SearchContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.realm = Realm.getDefaultInstance();
        this.client = ServiceGenerator.createService(FlowerClient.class);
    }
    @Override
    public void loadData() {
        this.view.showDataSearch(loadAll(20), false);
    }

    @Override
    public void loadDataBySearch(String key) {
        this.key = key;
        Observable<Response<FlowerResponse>> observable =
                client.getFlowersBySearch(key, 0, SIZE);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<FlowerResponse>>() {
                    private List<Flower> flowers = new ArrayList<Flower>();
                    @Override
                    public void onCompleted() {
                        view.initilizeGridview();
                        view.showNoResult();
                        view.showDataSearch(flowers, hasNext);
                        view.showIndicator(false);
                        view.setEnableRefresh(!flowers.isEmpty());
                        if (!flowers.isEmpty())
                            updateData(setFlowersIsSearch(flowers));
                        currentPage = 1;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<FlowerResponse> flowerResponseResponse) {
                        if (flowerResponseResponse.isSuccessful()) {
                            flowers.addAll(flowerResponseResponse.body().getResult());
                            hasNext = flowerResponseResponse.body().isHasNext();
                        }
                    }
                });
    }

    @Override
    public void loadMoreDataBySearch() {
        Observable<Response<FlowerResponse>> observable =
                client.getFlowersBySearch(key, currentPage, SIZE);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<FlowerResponse>>() {
                    private List<Flower> flowers = new ArrayList<Flower>();
                    @Override
                    public void onCompleted() {
                        view.showDataSearch(flowers, hasNext);
                        view.finishLoadMore(hasNext);
                        currentPage ++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<FlowerResponse> flowerResponseResponse) {
                        if (flowerResponseResponse.isSuccessful()) {
                            flowers.addAll(flowerResponseResponse.body().getResult());
                            hasNext = flowerResponseResponse.body().isHasNext();
                        }
                    }
                });
    }

    @Override
    public void resetData() {

    }

    @Override
    public void start() {
        Log.d(TAG, "Search Presenter");
    }

    private List<Flower> setFlowersIsSearch(List<Flower> flowers) {
        for (Flower flower : flowers)
            flower.setSearch(true);

        return flowers;
    }

    @Override
    public List<Flower> loadAll(int limit) {
        RealmResults<Flower> flowers = realm.where(Flower.class).equalTo("isSearch", true).findAll();
        return flowers;
    }

    @Override
    public void updateData(final List<Flower> list) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Flower> flowers = realm.where(Flower.class).equalTo("isSearch", true).findAll();
                flowers.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Clear data local flowers successfully");
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(list);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Save data success");
                    }
                });
            }
        });
    }
}
