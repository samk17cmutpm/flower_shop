package khoaluan.vn.flowershop.main.tab_search;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

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
    private final MainActivity activity;
    private final SearchContract.View view;
    private final FlowerClient client;
    private int currentPage = 0;
    private boolean hasNext;
    private String key;
    public SearchPresenter (MainActivity activity, SearchContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.client = ServiceGenerator.createService(FlowerClient.class);
    }
    @Override
    public void loadData() {

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
}
