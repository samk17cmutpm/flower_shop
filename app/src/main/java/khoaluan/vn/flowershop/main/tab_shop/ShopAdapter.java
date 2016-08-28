package khoaluan.vn.flowershop.main.tab_shop;

import android.app.Activity;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;

/**
 * Created by samnguyen on 8/28/16.
 */
public class ShopAdapter extends BaseQuickAdapter<Flower> {
    private List<Flower> flowers;

    public ShopAdapter(Activity activity, List<Flower> flowers) {
        super(R.layout.shop_item, flowers);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Flower flower) {
        baseViewHolder.setText(R.id.tv_flower_name, flower.getName())
                .setText(R.id.tv_price, flower.getMoney(flower.getPrice()));

        TextView textViewOldPrice = (TextView) baseViewHolder.getConvertView().findViewById(R.id.tv_price_old);
        textViewOldPrice.setText(flower.getMoney(flower.getOldPrice()));
        textViewOldPrice.setPaintFlags(textViewOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        ImageView imageView = (ImageView) baseViewHolder.getConvertView().findViewById(R.id.im_flower);
        if (flower.getImage() != null)
            ImageUniversalUtils.imageLoader.displayImage(flower.getImage(), imageView, ImageUniversalUtils.options);
    }
}
