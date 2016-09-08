package khoaluan.vn.flowershop.sign_in;

import android.app.Activity;
import android.util.Log;
import khoaluan.vn.flowershop.data.model_parse_and_realm.User;
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
 * Created by samnguyen on 8/31/16.
 */
public class SignInPresenter implements SignInContract.Presenter{
    private final SignInContract.View view;
    private final Activity activity;
    private final UserClient client;
    public SignInPresenter(SignInContract.View view, Activity activity) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        this.client = ServiceGenerator.createService(UserClient.class);
    }
    @Override
    public void signIn(String email, String password) {
        final UserRequest userRequest = new UserRequest(email, password);

        Observable<Response<UserResponse>> observable =
                client.signIn(userRequest);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<UserResponse>>() {
                    private UserResponse userResponse;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        if (userResponse.isSuccess()) {
                            MessageUtils.showLong(activity, "Đăng nhập thành công !");
                        } else {
                            MessageUtils.showLong(activity, "Email hoặc Mật Khẩu không đúng, vui lòng kiểm tra lại !");
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
    public void signInFb() {

    }

    @Override
    public void siginInGoogle() {

    }

    @Override
    public void start() {

    }
}
