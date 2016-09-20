package khoaluan.vn.flowershop.rating;

import android.app.Activity;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.data.response.RatingListResponse;
import khoaluan.vn.flowershop.data.response.RatingResponse;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.FlowerClient;
import khoaluan.vn.flowershop.utils.MessageUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 9/19/16.
 */
public class RatingPresenter implements RatingContract.Presenter, Base {

    private final Activity activity;
    private final RatingContract.View view;
    private final FlowerClient client;
    private int currentPage = 1;
    private boolean isHasNext;

    public RatingPresenter(Activity activity, RatingContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.client = ServiceGenerator.createService(FlowerClient.class);
    }

    @Override
    public void loadRating(String productId) {

        currentPage = 1;

        Observable<Response<RatingListResponse>> observable =
                client.getRatings(productId, currentPage, SIZE);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<RatingListResponse>>() {
                    private RatingListResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        isHasNext = response.isHasNext();
                        view.addToRatings(response.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<RatingListResponse> ratingResponseResponse) {
                        if (ratingResponseResponse.isSuccessful())
                            response = ratingResponseResponse.body();
                    }
                });

    }

    @Override
    public void loadRatingMore(String productId) {
        Observable<Response<RatingListResponse>> observable =
                client.getRatings(productId, ++currentPage, SIZE);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<RatingListResponse>>() {
                    private RatingListResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        isHasNext = response.isHasNext();
                        view.addToRatings(response.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<RatingListResponse> ratingResponseResponse) {
                        if (ratingResponseResponse.isSuccessful())
                            response = ratingResponseResponse.body();
                    }
                });
    }

    @Override
    public boolean isHasNext() {
        return isHasNext;
    }

    @Override
    public void sendRating(String UserId, String ProductId, int StarRating, String Feedback) {
        Observable<Response<RatingResponse>> observable =
                client.createRating(UserId, ProductId, StarRating, Feedback);
        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<RatingResponse>>() {
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, "Đã gởi nhận xét thành công");
                        activity.onBackPressed();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<RatingResponse> ratingResponseResponse) {

                    }
                });
    }

    @Override
    public void start() {

    }
}
