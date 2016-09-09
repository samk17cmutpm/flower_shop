package khoaluan.vn.flowershop.main.tab_favorite;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.response.FlowerResponse;
import khoaluan.vn.flowershop.main.MainActivity;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.FlowerClient;
import khoaluan.vn.flowershop.utils.ConvertUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 8/25/16.
 */
public class FavoritePresenter implements FavoriteContract.Presenter{
    private static String TAG = FavoritePresenter.class.getName();
    private final MainActivity activity;
    private final FavoriteContract.View view;
    private final Realm realm;
    private final FlowerClient client;
    public FavoritePresenter (MainActivity activity, FavoriteContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.realm = Realm.getDefaultInstance();
        this.client = ServiceGenerator.createService(FlowerClient.class);
    }
    @Override
    public void loadData() {

    }

    @Override
    public RealmResults<Flower> loadFavoriteFlowers() {
        return RealmFlowerUtils.findBy(RealmFlag.FLAG, RealmFlag.FAVORITE);
    }

    @Override
    public RealmResults<Flower> loadTopFlowers() {
        return RealmFlowerUtils.findBy(RealmFlag.FLAG, RealmFlag.MOST);
    }

    @Override
    public List<FavoriteItem> convertData(List<Flower> flowersFavorite,
                                          List<Flower> flowersRecommend, String title) {
        List<FavoriteItem> favoriteItems = new ArrayList<>();


        FavoriteItem favorite = new FavoriteItem();

        favorite.setItemType(FavoriteItem.FAVORITE);

        favorite.setFavoriteFlowers(flowersFavorite);

        favoriteItems.add(favorite);

        if (!flowersRecommend.isEmpty()) {
            FavoriteItem favoriteTitle = new FavoriteItem();

            favoriteTitle.setItemType(FavoriteItem.TITLE);

            favoriteTitle.setTitle(title);

            favoriteItems.add(favoriteTitle);

            FavoriteItem favoriteRecommend = new FavoriteItem();
            favoriteRecommend.setItemType(FavoriteItem.FLOWER);
            favoriteRecommend.setFlowers(flowersRecommend);

            favoriteItems.add(favoriteRecommend);
        }
        return favoriteItems;
    }

    @Override
    public void loadTopProducts() {
        Observable<Response<FlowerResponse>> observable =
                client.getTopProducts();

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<FlowerResponse>>() {
                    private List<Flower> flowers = new ArrayList<>();
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        RealmFlowerUtils.updateAll(RealmFlag.FLAG, RealmFlag.MOST, flowers);
                        view.updateTop(flowers);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
//                        view.showError(null);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<FlowerResponse> mostFlowerResponseResponse) {
                        if (mostFlowerResponseResponse.isSuccessful()) {
                            flowers.addAll(mostFlowerResponseResponse.body().getResult());
                        }
                    }
                });
    }


    @Override
    public void start() {

    }


}
