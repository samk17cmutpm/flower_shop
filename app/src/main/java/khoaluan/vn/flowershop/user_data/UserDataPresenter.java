package khoaluan.vn.flowershop.user_data;

import android.app.Activity;

import java.util.List;

import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;
import khoaluan.vn.flowershop.data.response.BillingDetailResponse;
import khoaluan.vn.flowershop.data.response.BillingResponse;
import khoaluan.vn.flowershop.data.response.UserResponse;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.order.order_confirm.MultipleOrderBillingItem;
import khoaluan.vn.flowershop.realm_data_local.RealmBillingUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.UserClient;
import khoaluan.vn.flowershop.utils.ConvertUtils;
import khoaluan.vn.flowershop.utils.MessageUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 9/10/16.
 */
public class UserDataPresenter implements UserDataContract.Presenter {
    private final Activity activity;
    private final UserDataContract.View view;
    private final UserClient client;
    public UserDataPresenter(Activity activity, UserDataContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.client = ServiceGenerator.createService(UserClient.class);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void updateInfoProfile(String id, String fullName, String address, String phone) {
        Observable<Response<UserResponse>> observable
                = client.updateProfile(id, fullName, address, phone);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<UserResponse>>() {
                    private UserResponse userResponse;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        if (userResponse.isSuccess()) {
                            MessageUtils.showLong(activity, "Cập nhật thành công thông tin cá nhân");
                            UserUtils.saveUser(userResponse.getResult(), activity);
                            view.done();
                        } else {
                            MessageUtils.showLong(activity, "WTF");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, "Vui lòng kiểm tra lại intenert, không thể kết nối !");
                    }

                    @Override
                    public void onNext(Response<UserResponse> userResponseResponse) {
                        if (userResponseResponse.isSuccessful())
                            userResponse = userResponseResponse.body();
                    }
                });
    }

    @Override
    public void loadBillings(String idUser) {
        Observable<Response<BillingResponse>> observable
                = client.getBillings(idUser);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<BillingResponse>>() {
                    private List<Billing> billings;
                    @Override
                    public void onCompleted() {
                        RealmBillingUtils.updateAll(billings);
                        view.showIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<BillingResponse> billingResponseResponse) {
                        if (billingResponseResponse.isSuccessful())
                            billings = billingResponseResponse.body().getResult();
                    }
                });
    }

    @Override
    public RealmResults<Billing> loadBillingsLocal() {
        return RealmBillingUtils.all();
    }

    @Override
    public void loadBillingDetail(String orderId) {
        Observable<Response<BillingDetailResponse>> observable
                = client.getBillingDetail(orderId);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<BillingDetailResponse>>() {
                    private Billing billing;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false);
                        view.showBillingDetail(ConvertUtils.convertBillingToMultipleBillingItem(billing));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showIndicator(false);
                        view.noInternetConnectTion();
                    }

                    @Override
                    public void onNext(Response<BillingDetailResponse> billingDetailResponseResponse) {
                        if (billingDetailResponseResponse.isSuccessful())
                            billing = billingDetailResponseResponse.body().getResult();
                    }
                });
    }

    @Override
    public void showBillingConfirm(List<MultipleOrderBillingItem> list) {

    }

    @Override
    public void start() {

    }
}
