package khoaluan.vn.flowershop.sign_up;

import android.app.Activity;

import khoaluan.vn.flowershop.data.request.UserRequest;
import khoaluan.vn.flowershop.data.response.UserResponse;
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
    public void signUp(String email, String password, String passwordConfirm) {
        final UserRequest userRequest = new UserRequest(email, password);

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
