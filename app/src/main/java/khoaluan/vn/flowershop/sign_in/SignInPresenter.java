package khoaluan.vn.flowershop.sign_in;

import android.app.Activity;

/**
 * Created by samnguyen on 8/31/16.
 */
public class SignInPresenter implements SignInContract.Presenter{
    private final SignInContract.View view;
    private final Activity activity;
    public SignInPresenter(SignInContract.View view, Activity activity) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
    }
    @Override
    public void signIn(String email, String password) {

    }

    @Override
    public void start() {

    }
}
