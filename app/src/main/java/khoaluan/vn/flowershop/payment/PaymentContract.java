package khoaluan.vn.flowershop.payment;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;

/**
 * Created by samnguyen on 9/8/16.
 */
public class PaymentContract {
    interface View extends BaseView<Presenter> {
        void showUI();
    }
    interface Presenter extends BasePresenter {
        void syncData();
    }
}
