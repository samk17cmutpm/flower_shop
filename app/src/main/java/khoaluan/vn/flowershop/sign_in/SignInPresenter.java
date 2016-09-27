package khoaluan.vn.flowershop.sign_in;

import android.app.Activity;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.User;
import khoaluan.vn.flowershop.data.request.UserRequest;
import khoaluan.vn.flowershop.data.response.BillingAdressResponse;
import khoaluan.vn.flowershop.data.response.UserResponse;
import khoaluan.vn.flowershop.data.shared_prefrences.UserPayment;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.UserClient;
import khoaluan.vn.flowershop.utils.MessageUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

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
        final UserRequest userRequest = new UserRequest(email, password, "");

        Observable<Response<UserResponse>> observable =
                client.signIn(userRequest);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<UserResponse>>() {
                    private UserResponse userResponse;
                    @Override
                    public void onCompleted() {
                        if (userResponse.isSuccess()) {
                            User user = userResponse.getResult();
                            UserUtils.setSignedIn(activity, true);
                            UserUtils.saveUser(user, activity);
                            view.finish();
                            loadUserInfo(user.getId());
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
    public void signInSocial(String email, final String provider, String externaltoken, String deviceId, final String fullName) {
        final UserRequest userRequest = new UserRequest(email, provider, externaltoken, deviceId);

        Observable<Response<UserResponse>> observable =
                client.signSocial(userRequest);

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<UserResponse>>() {
                    private UserResponse userResponse;
                    @Override
                    public void onCompleted() {
                        if (userResponse.isSuccess()) {
                            User user = userResponse.getResult();
                            if (user.getFullName() == null)
                                user.setFullName(fullName);
                            UserUtils.setSignedIn(activity, true);
                            UserUtils.saveUser(user, activity);
                            loadUserInfo(user.getId());
                        } else {
                            MessageUtils.showLong(activity, "Đã xảy ra lỗi, wtf");
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
    public void getAccessTokenGoogle(final String email, final String displayName) {

        BackgroundThread backgroundThread = new BackgroundThread();
        backgroundThread.start();
        Looper backgroundLooper = backgroundThread.getLooper();

        getAccessTokenGoogle(email).subscribeOn(AndroidSchedulers.from(backgroundLooper))
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    private String accessToken;
                    @Override
                    public void onCompleted() {
                        signInSocial(email, Credentials.GOOGLE, accessToken, "", displayName);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, "Không thể lấy token access từ google");
                    }

                    @Override
                    public void onNext(String s) {
                        accessToken = s;
                    }
                });

    }

    @Override
    public void loadUserInfo(String userId) {

        Observable<Response<BillingAdressResponse>> observable =
                client.getUserInfo(userId);
        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<BillingAdressResponse>>() {
                    private BillingAdressResponse response;
                    @Override
                    public void onCompleted() {
                        view.showIndicator(false, null);
                        UserPayment.saveUserPayment(response.getResult(), activity);
                        MessageUtils.showLong(activity, "Đăng nhập thành công");
                        view.finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showIndicator(false, null);
                        MessageUtils.showLong(activity, R.string.no_internet_connecttion);
                    }

                    @Override
                    public void onNext(Response<BillingAdressResponse> billingAdressResponseResponse) {
                        if (billingAdressResponseResponse.isSuccessful())
                            response = billingAdressResponseResponse.body();
                    }
                });
    }

    private Observable<String> getAccessTokenGoogle(final String email) {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override public Observable<String> call() {
                String token = null;
                try {
                    String scopes = "oauth2:profile email";
                    token = GoogleAuthUtil.getToken(activity, email, scopes);
                } catch (IOException e) {
                    Log.e("DMCS", e.getMessage());
                } catch (UserRecoverableAuthException e) {
                } catch (GoogleAuthException e) {
                    Log.e("DMCS", e.getMessage());
                }
                return Observable.just(token);
            }
        });
    }

    @Override
    public void start() {

    }

    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("SchedulerSample-BackgroundThread", THREAD_PRIORITY_BACKGROUND);
        }
    }
}
