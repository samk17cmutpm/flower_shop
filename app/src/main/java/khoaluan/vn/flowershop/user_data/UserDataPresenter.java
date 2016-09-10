package khoaluan.vn.flowershop.user_data;

import android.app.Activity;

import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.UserClient;

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
    public void start() {

    }
}
