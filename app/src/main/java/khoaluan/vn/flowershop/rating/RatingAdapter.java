package khoaluan.vn.flowershop.rating;

import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Rating;

/**
 * Created by samnguyen on 9/19/16.
 */
public class RatingAdapter extends BaseQuickAdapter<Rating> {
    public RatingAdapter(List<Rating> data) {
        super(R.layout.rating_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Rating rating) {
        baseViewHolder.setText(R.id.tv_full_name, rating.getFullName())
                .setText(R.id.tv_feed_back, rating.getFeedback());

        RatingBar ratingBar = (RatingBar) baseViewHolder.getConvertView().findViewById(R.id.rating_bar);
        ratingBar.setRating(rating.getStarRating());
    }
}
