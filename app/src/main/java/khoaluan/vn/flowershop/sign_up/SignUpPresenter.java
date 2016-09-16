package khoaluan.vn.flowershop.sign_up;

import android.app.Activity;

import khoaluan.vn.flowershop.data.request.UserSignUpRequest;
import khoaluan.vn.flowershop.data.response.UserResponse;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.UserClient;
import khoaluan.vn.flowershop.utils.MessageUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samnguyen on 9/8/16.
 */
public class SignUpPresenter implements SignUpContract.Presenter {
    private final SignUpContract.View view;
    private final Activity activity;
    private final UserClient client;
    public SignUpPresenter(SignUpContract.View view, Activity activity) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.client = ServiceGenerator.createService(UserClient.class);
    }
    @Override
    public void signUp(String email, String password, String passwordConfirm,
                       String address, String phone, String fullName, String deviceId) {
        final UserSignUpRequest userRequest = new UserSignUpRequest(email, fullName, address, phone, deviceId, password);

        Observable<Response<UserResponse>> observable =
                client.signUp(userRequest);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<UserResponse>>() {
                    private UserResponse userResponse;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        if (userResponse.isSuccess()) {
                            MessageUtils.showLong(activity, "Đăng ký tài khoản thành công !");
                            UserUtils.setSignedIn(activity, true);
                            UserUtils.saveUser(userResponse.getResult(), activity);
                            view.finish();
                        } else {
                            MessageUtils.showLong(activity, "Email này đã được đăng ký, vui lòng kiểm tra lại !");
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
    public void start() {

    }
}
