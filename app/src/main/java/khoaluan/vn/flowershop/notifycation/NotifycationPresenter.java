package khoaluan.vn.flowershop.notifycation;

import android.app.Activity;

import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Notifycation;
import khoaluan.vn.flowershop.data.response.NotifycationResponse;
import khoaluan.vn.flowershop.realm_data_local.RealmNotifycationUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.UserClient;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 9/19/16.
 */
public class NotifycationPresenter implements NotifycationContract.Presenter, Base {
    private final Activity activity;
    private final NotifycationContract.View view;
    private final UserClient client;
    private int currentPage = 1;
    private boolean isHasNext;

    public NotifycationPresenter(Activity activity, NotifycationContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.client = ServiceGenerator.createService(UserClient.class);
    }

    @Override
    public void loadNotifycation(String userId) {
        currentPage = 1;

        Observable<Response<NotifycationResponse>> observable =
                client.getNotifycations(userId, currentPage, SIZE);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<NotifycationResponse>>() {
                    private NotifycationResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        isHasNext = response.isHasNext();
                        RealmNotifycationUtils.updateAll(response.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<NotifycationResponse> notifycationResponseResponse) {
                        if (notifycationResponseResponse.isSuccessful())
                            response = notifycationResponseResponse.body();

                    }
                });

    }

    @Override
    public RealmResults<Notifycation> loadNotifycationLocal() {
        return RealmNotifycationUtils.all();
    }

    @Override
    public void loadNotifycationMore(String userId) {
        Observable<Response<NotifycationResponse>> observable =
                client.getNotifycations(userId, ++currentPage, SIZE);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<NotifycationResponse>>() {
                    private NotifycationResponse response;
                    @Override
                    public void onCompleted() {
                        isHasNext = response.isHasNext();
                        RealmNotifycationUtils.save(response.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<NotifycationResponse> notifycationResponseResponse) {
                        if (notifycationResponseResponse.isSuccessful())
                            response = notifycationResponseResponse.body();
                    }
                });

    }

    @Override
    public boolean isHasNext() {
        return isHasNext;
    }

    @Override
    public void start() {

    }
}
