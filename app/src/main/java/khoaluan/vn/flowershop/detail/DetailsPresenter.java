package khoaluan.vn.flowershop.detail;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Rating;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.data.response.CartResponse;
import khoaluan.vn.flowershop.data.response.FlowerResponse;
import khoaluan.vn.flowershop.data.response.RatingListResponse;
import khoaluan.vn.flowershop.realm_data_local.RealmCartUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.CartClient;
import khoaluan.vn.flowershop.retrofit.client.FlowerClient;
import khoaluan.vn.flowershop.utils.ActionUtils;
import khoaluan.vn.flowershop.utils.MessageUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 7/28/16.
 */
public class DetailsPresenter implements DetailsContract.Presenter {
    private static String TAG = DetailsPresenter.class.getName();
    private final DetailsContract.View view;
    private final Activity activity;
    private final Realm realm;
    private final CartClient client;
    private final FlowerClient flowerClient;
    public DetailsPresenter(DetailsContract.View view, Activity activity) {
        this.view = view;
        this.activity = activity;
        this.view.setPresenter(this);
        this.realm = Realm.getDefaultInstance();
        this.client = ServiceGenerator.createService(CartClient.class);
        this.flowerClient = ServiceGenerator.createService(FlowerClient.class);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void addToFavoriteList(Flower flower) {
        List<Flower> flowers = new ArrayList<>();
        flowers.add(flower);
        RealmFlowerUtils.save(flowers);
        MessageUtils.showLong(activity, "Đã Thêm Sản Phẩm Này Vào Yêu Thích");
    }

    @Override
    public void removeFavoriteFlower(Flower flower) {
        RealmFlowerUtils.deleteById(RealmFlag.FLAG, RealmFlag.FAVORITE, flower.getId());
        MessageUtils.showLong(activity, "Đã Xóa Sản Phẩm Này Ra Khỏi Yêu Thích");
    }

    @Override
    public void addToCart(List<Flower> flowers) {
        RealmFlowerUtils.save(flowers);
    }

    @Override
    public boolean isExistedInCart(Flower flower) {
        return RealmFlowerUtils.isExistedById(RealmFlag.FLAG, RealmFlag.CART, flower.getId());
    }

    @Override
    public RealmResults<Flower> getFlowersCart() {
        return RealmFlowerUtils.findBy(RealmFlag.FLAG, RealmFlag.CART);
    }

    @Override
    public void addToCart(String idCart, String idProduct, final boolean buyNow) {
        Observable<Response<CartResponse>> observable =
                client.addToCart(idCart, idProduct);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<CartResponse>>() {
                    private CartResponse cartResponse;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        if (cartResponse.isSuccess()) {
                            if (buyNow) {
                                ActionUtils.go(activity, 3);
                            }
                            MessageUtils.showLong(activity, "Đã Thêm Sản Phẩm Này Vào Trong Giỏ Hàng Của Bạn");
                            RealmCartUtils.updateAll(cartResponse.getResult());
                        }else {
                            MessageUtils.showLong(activity, "Không thể thêm, xảy ra lỗi");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<CartResponse> cartResponseResponse) {
                        if (cartResponseResponse.isSuccessful())
                            cartResponse = cartResponseResponse.body();
                    }
                });

    }

    @Override
    public void loadRatingData(String ProductId) {
        Observable<Response<RatingListResponse>> observable =
                flowerClient.getRatings(ProductId, 1, 5);

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<RatingListResponse>>() {
                    private RatingListResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        view.updateRatings(response.getResult());
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
    public void loadFlowerDetail(String id) {
        Observable<Response<FlowerResponse>> observable =
                flowerClient.getFlowerDetails(id);

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<FlowerResponse>>() {
                    private FlowerResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        view.forAd(response.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<FlowerResponse> flowerResponseResponse) {
                        response = flowerResponseResponse.body();
                    }
                });
    }


    @Override
    public List<MutipleDetailItem> convertData(Flower flower, FlowerSuggesstion flowerSuggesstion) {
        List<MutipleDetailItem> items = new ArrayList<>();

        MutipleDetailItem header = new MutipleDetailItem();
        header.setItemType(MutipleDetailItem.HEADER);
        header.setFlower(flower);
        items.add(header);

        MutipleDetailItem shortDescription = new MutipleDetailItem();
        shortDescription.setItemType(MutipleDetailItem.SHORT_DESCRIPTION);
        shortDescription.setFlower(flower);
        items.add(shortDescription);

        MutipleDetailItem image = new MutipleDetailItem();
        image.setItemType(MutipleDetailItem.IMAGE);
        image.setFlower(flower);
        items.add(image);

        MutipleDetailItem action = new MutipleDetailItem();
        action.setItemType(MutipleDetailItem.ACTION);
        action.setFlower(flower);
        items.add(action);

        MutipleDetailItem fullDescription = new MutipleDetailItem();
        fullDescription.setItemType(MutipleDetailItem.FULL_DESCRIPTION);
        fullDescription.setFlower(flower);
        items.add(fullDescription);

        MutipleDetailItem address = new MutipleDetailItem();
        address.setItemType(MutipleDetailItem.ADDRESS);
        items.add(address);

        if (flowerSuggesstion != null) {

            MutipleDetailItem rating = new MutipleDetailItem();
            rating.setItemType(MutipleDetailItem.RATING);
            rating.setFlower(flower);
            rating.setFlowers(flowerSuggesstion.getFlowers());
            rating.setRatings(new ArrayList<Rating>());
            items.add(rating);


            MutipleDetailItem title = new MutipleDetailItem();
            title.setItemType(MutipleDetailItem.TITLE);
            items.add(title);

            MutipleDetailItem suggesstion = new MutipleDetailItem();
            suggesstion.setItemType(MutipleDetailItem.SUGGESTION);
            suggesstion.setFlowers(flowerSuggesstion.getFlowers());
            items.add(suggesstion);
        }
        return items;
    }

    @Override
    public void start() {

    }
}
