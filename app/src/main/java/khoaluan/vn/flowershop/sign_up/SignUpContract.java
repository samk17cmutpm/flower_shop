package khoaluan.vn.flowershop.sign_up;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;

/**
 * Created by samnguyen on 9/8/16.
 */
public interface SignUpContract {
    interface View extends BaseView<Presenter> {
        void showUI();
        void showIndicator(boolean active, String message);
        void attemptSignUp();
        void finish();
    }
    interface Presenter extends BasePresenter {
        void signUp(String email, String password, String passwordConfirm,
                    String address, String phone, String fullName, String deviceId);
    }
}
