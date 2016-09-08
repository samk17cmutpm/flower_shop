package khoaluan.vn.flowershop.sign_in;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;

/**
 * Created by samnguyen on 8/31/16.
 */
public interface SignInContract {
    interface View extends BaseView<Presenter> {
        void showUI();
        void showIndicator(boolean active, String message);
        void attemptLogin();
    }
    interface Presenter extends BasePresenter {
        void signIn(String email, String password);
        void signInFb();
        void siginInGoogle();
    }
}
