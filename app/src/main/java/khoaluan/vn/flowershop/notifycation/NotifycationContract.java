package khoaluan.vn.flowershop.notifycation;

import io.realm.RealmResults;
import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Notifycation;

/**
 * Created by samnguyen on 9/19/16.
 */
public interface NotifycationContract {

    interface View extends BaseView<Presenter>, Network {
        void showUI();
        void showIndicator(boolean active);
        void setUpRecyclerView();
    }

    interface Presenter extends BasePresenter {
        void loadNotifycation(String userId);
        RealmResults<Notifycation> loadNotifycationLocal();
        void loadNotifycationMore(String userId);
        boolean isHasNext();
    }
}
