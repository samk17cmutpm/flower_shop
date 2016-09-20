package khoaluan.vn.flowershop.main.tab_home;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Advertising;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Item;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Items;
import khoaluan.vn.flowershop.data.response.AdvertisingResponse;
import khoaluan.vn.flowershop.data.response.ListFlowerResponse;
import khoaluan.vn.flowershop.data.response.NewestResponse;
import khoaluan.vn.flowershop.main.MainActivity;
import khoaluan.vn.flowershop.realm_data_local.RealmAdvertisingUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmItemUtils;
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
public class HomePresenter implements HomeContract.Presenter, Base{
    private static String TAG = HomePresenter.class.getName();
    private final MainActivity activity;
    private final HomeContract.View view;
    private final Realm realm;
    private final FlowerClient client;
    private List<MultipleMainItem> multipleMainItems;


    public HomePresenter(MainActivity activity, HomeContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.realm = Realm.getDefaultInstance();
        this.client = ServiceGenerator.createService(FlowerClient.class);
        this.multipleMainItems = new ArrayList<>();
    }
    @Override
    public void loadData() {
        this.view.showIndicator(true);
        this.view.showRealmData(loadDataLocal());
        loadNewestData();
    }

    @Override
    public void refreshData() {
        this.view.showIndicator(true);
        this.multipleMainItems = new ArrayList<>();
        loadNewestData();
    }

    @Override
    public List<MultipleMainItem> loadDataLocal() {
        if (RealmItemUtils.load() != null) {
            for (Item item : RealmItemUtils.load().getItems()) {
                if (item.getAdvertising() != null)
                    Log.d(TAG, item.getAdvertising().getAdvertisingItems().size() + " ADVERTISING ");
                switch (item.type()) {
                    case MultipleMainItem.ADVERTISING:
                        Log.d(TAG, item.getAdvertising().getAdvertisingItems().size() + " ADVERTISING ");
                        break;
                    case MultipleMainItem.BOTH_TITLE_FLOWER:
                        Log.d(TAG, item.getFlowers().size() + " BOTH_TITLE_FLOWER");
                        break;
                }
            }
            return ConvertUtils.convertItemsToMultipleItems(RealmItemUtils.load().getItems());
        }


        return new ArrayList<>();
//        List<MultipleMainItem> multipleMainItems = new ArrayList<>();
//
//        MultipleMainItem multipleMainItem = new MultipleMainItem();
//        multipleMainItem.setItemType(MultipleMainItem.ADVERTISING);
//        multipleMainItem.setAdvertisings(RealmAdvertisingUtils.all());
//        multipleMainItems.add(multipleMainItem);
//
//        multipleMainItems.addAll(ConvertUtils.convertTopProductsToMultipleItems(
//                RealmFlowerUtils.findBy(RealmFlag.FLAG, RealmFlag.NEWEST)));
//        return multipleMainItems;
    }

    @Override
    public void loadNewestData() {
        Observable<Response<NewestResponse>> observable =
                client.getNewestData();

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<NewestResponse>>() {
                    private Items items;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        view.initilizeMainView();
                        RealmItemUtils.updateAll(items);
                        multipleMainItems.addAll(ConvertUtils.convertItemsToMultipleItems(items.getItems()));
                        view.showTopProducts(multipleMainItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.noInternetConnectTion();
                        view.showIndicator(false);
                    }

                    @Override
                    public void onNext(Response<NewestResponse> newestResponseResponse) {
                        items = newestResponseResponse.body().getItems();
                    }
                });
    }


    @Override
    public void loadTopProducts() {
        Observable<Response<ListFlowerResponse>> observable =
                client.getTopProducts();

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<ListFlowerResponse>>() {
                    private List<Flower> flowers = new ArrayList<>();
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        view.initilizeMainView();
                        RealmFlowerUtils.updateAll(RealmFlag.FLAG, RealmFlag.NEWEST, flowers);
                        multipleMainItems.addAll(ConvertUtils.convertTopProductsToMultipleItems(flowers));
                        view.showTopProducts(multipleMainItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        view.noInternetConnectTion();
//                        view.showError(null);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<ListFlowerResponse> mostFlowerResponseResponse) {
                        if (mostFlowerResponseResponse.isSuccessful()) {
                            flowers.addAll(mostFlowerResponseResponse.body().getResult());
                        }
                    }
                });
    }

    @Override
    public void loadAdvertisingItems() {
        Observable<Response<AdvertisingResponse>> observable =
                client.getAdvertisingItems();

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<AdvertisingResponse>>() {
                    List<Advertising> advertisings = new ArrayList<Advertising>();
                    @Override
                    public void onCompleted() {
                        MultipleMainItem multipleMainItem = new MultipleMainItem();
                        multipleMainItem.setItemType(MultipleMainItem.ADVERTISING);
                        multipleMainItem.setAdvertisings(advertisings);
                        multipleMainItems.add(multipleMainItem);
                        RealmAdvertisingUtils.updateAll(advertisings);
                        loadTopProducts();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.noInternetConnectTion();
                        view.showIndicator(false);
//                        view.showError(null);
                    }

                    @Override
                    public void onNext(Response<AdvertisingResponse> advertisingResponseResponse) {
                        if (advertisingResponseResponse.isSuccessful())
                            advertisings.addAll(advertisingResponseResponse.body().getResult());

                    }
                });
    }

    @Override
    public void start() {
        Log.d(TAG, "Starting HomeFragment");
    }


    @Override
    public List<Flower> loadAll(int limit) {
        RealmResults<Flower> flowers = realm.where(Flower.class).equalTo("isNewest", true).findAll();
        return flowers;
    }

    @Override
    public void updateData(final List<Flower> list) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Flower> flowers = realm.where(Flower.class).equalTo("isNewest", true).findAll();
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

    private List<Flower> setFlowersNewest(List<Flower> flowers) {
        for (Flower flower : flowers)
            flower.setFlag(RealmFlag.NEWEST);

        return flowers;
    }
}
