package khoaluan.vn.flowershop.main.tab_home;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.data.client_parse.Flower;
import khoaluan.vn.flowershop.data.response.MostFlowerResponse;
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
        this.view.showFlowers(loadLocalData());
        loadDataFirst();
    }

    @Override
    public void loadMostFlowers(int page, int size) {

        Observable<Response<MostFlowerResponse>> observable =
                client.getMostFlowers(page, size);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<MostFlowerResponse>>() {
                    private List<Flower> flowers = new ArrayList<>();
                    @Override
                    public void onCompleted() {
                        view.showFlowers(flowers);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<MostFlowerResponse> mostFlowerResponseResponse) {
                        if (mostFlowerResponseResponse.isSuccessful()) {
                            flowers.addAll(mostFlowerResponseResponse.body().getResult());
                            hasNext = mostFlowerResponseResponse.body().isHasNext();
                        }

                    }
                });
    }



    @Override
    public boolean isFlowersExisted() {
        return false;
    }

    @Override
    public boolean isHasNext() {
        return hasNext;
    }

    @Override
    public List<Flower> loadLocalData() {
        RealmResults<Flower> flowers = realm.where(Flower.class).findAll();
        return flowers;
    }

    @Override
    public void loadDataFirst() {
        Observable<Response<MostFlowerResponse>> observable =
                client.getMostFlowers(0, SIZE);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<MostFlowerResponse>>() {
                    private List<Flower> flowers = new ArrayList<>();
                    @Override
                    public void onCompleted() {
                        view.clearAllDataLocal();
                        view.showIndicator(false);
                        view.showFlowers(flowers);
                        updateLocalData(flowers);
                        currentPage = 0;
                        currentPage ++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<MostFlowerResponse> mostFlowerResponseResponse) {
                        if (mostFlowerResponseResponse.isSuccessful()) {
                            flowers.addAll(mostFlowerResponseResponse.body().getResult());
                            hasNext = mostFlowerResponseResponse.body().isHasNext();
                        }

                    }
                });
    }

    @Override
    public void loadDataMore() {
        Observable<Response<MostFlowerResponse>> observable =
                client.getMostFlowers(currentPage, SIZE);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<MostFlowerResponse>>() {
                    private List<Flower> flowers = new ArrayList<>();
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        view.showFlowers(flowers);
                        view.finishLoadMore(true);
                        currentPage ++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<MostFlowerResponse> mostFlowerResponseResponse) {
                        if (mostFlowerResponseResponse.isSuccessful()) {
                            flowers.addAll(mostFlowerResponseResponse.body().getResult());
                            hasNext = mostFlowerResponseResponse.body().isHasNext();
                            if (!hasNext)
                                view.finishLoadMore(false);
                        }

                    }
                });
    }

    @Override
    public void updateLocalData(final List<Flower> flowers) {

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
                        realm.copyToRealm(flowers);
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

    @Override
    public void start() {
        Log.d(TAG, "Starting HomeFragment");
    }


}
