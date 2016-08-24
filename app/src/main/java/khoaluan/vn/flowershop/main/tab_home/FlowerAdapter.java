package khoaluan.vn.flowershop.main.tab_home;

import android.app.Activity;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;

/**
 * Created by samnguyen on 7/19/16.
 */
public class FlowerAdapter extends BaseQuickAdapter<Flower> {

    private List<Flower> flowers;

    public FlowerAdapter(int layoutResId, List<Flower> flowers) {
        super(layoutResId, flowers);
    }
    public FlowerAdapter(Activity activity, List<Flower> flowers) {
        super(R.layout.flower_item, flowers);
    }

    public FlowerAdapter(Activity activity, List<Flower> flowers, int layoutId) {
        super(layoutId, flowers);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, Flower flower) {
        baseViewHolder.setText(R.id.tv_flower_name, flower.getName())
                .setText(R.id.tv_price, flower.getMoney(flower.getPrice()));

        TextView textViewOldPrice = (TextView) baseViewHolder.getConvertView().findViewById(R.id.tv_price_old);
        textViewOldPrice.setText(flower.getMoney(flower.getOldPrice()));
        textViewOldPrice.setPaintFlags(textViewOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        RatingBar ratingBar = (RatingBar) baseViewHolder.getConvertView().findViewById(R.id.rating_bar);
        ratingBar.setRating(Float.parseFloat(flower.getRating()));

        ImageView imageView = (ImageView) baseViewHolder.getConvertView().findViewById(R.id.im_flower);
        if (flower.getImage() != null)
            ImageUniversalUtils.imageLoader.displayImage(flower.getImage(), imageView, ImageUniversalUtils.options);

    }
}
