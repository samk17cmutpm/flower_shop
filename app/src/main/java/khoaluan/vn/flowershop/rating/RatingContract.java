package khoaluan.vn.flowershop.rating;

import java.util.List;

import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.BaseView;
import khoaluan.vn.flowershop.action.action_view.Network;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Rating;

/**
 * Created by samnguyen on 9/19/16.
 */
public interface RatingContract {

    interface View extends BaseView<Presenter>, Network {
        void showUI();
        void showIndicator(boolean active);
        void showIndicator(boolean active, String message);
        void setUpRecyclerView();
        void addToRatings(List<Rating> ratings);

    }

    interface Presenter extends BasePresenter {
        void loadRating(String productId);
        void loadRatingMore(String productId);
        boolean isHasNext();
        void sendRating(String UserId, String ProductId, int StarRating, String Feedback);
    }
}
