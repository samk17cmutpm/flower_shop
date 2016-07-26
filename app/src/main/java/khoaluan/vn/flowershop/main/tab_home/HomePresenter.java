package khoaluan.vn.flowershop.main.tab_home;

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
public class HomePresenter implements HomeContract.Presenter, Base{
    private static String TAG = HomePresenter.class.getName();
    private final MainActivity activity;
    private final HomeContract.View view;
    private final Realm realm;
    private final FlowerClient client;
    private int currentPage = 0;
    private boolean hasNext;

    public HomePresenter(MainActivity activity, HomeContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.realm = Realm.getDefaultInstance();
        this.client = ServiceGenerator.createService(FlowerClient.class);
    }
    @Override
    public void loadData() {
        this.view.showIndicator(true);
        this.view.showFlowers(loadAll(), false);
        loadRefreshData();
    }
    @Override
    public boolean isHasNext() {
        return hasNext;
    }

    @Override
    public void loadRefreshData() {
        Observable<Response<FlowerResponse>> observable =
                client.getMostFlowers(currentPage, SIZE);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<FlowerResponse>>() {
                    private List<Flower> flowers = new ArrayList<>();
                    @Override
                    public void onCompleted() {
                        view.clearAllDataLocal();
                        view.showIndicator(false);
                        view.showFlowers(flowers, hasNext);
                        updateData(flowers);
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
                client.getMostFlowers(currentPage, SIZE);

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
        Log.d(TAG, "Starting HomeFragment");
    }


    @Override
    public List<Flower> loadAll() {
        RealmResults<Flower> flowers = realm.where(Flower.class).findAll();
        return flowers;
    }

    @Override
    public void updateData(final List<Flower> list) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Flower> flowers = realm.where(Flower.class).findAll();
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
