package khoaluan.vn.flowershop.main.tab_favorite;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;

/**
 * Created by samnguyen on 8/27/16.
 */
public class FavoriteAdapter extends BaseQuickAdapter<Flower> {
    private List<Flower> flowers;
    private Activity activity;

    public FavoriteAdapter(Activity  activity, List<Flower> flowers) {
        super(R.layout.favorite_item, flowers);
        this.activity = activity;
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, final Flower flower) {
        baseViewHolder.setText(R.id.tv_flower_name, flower.getName())
                .setText(R.id.tv_price, flower.getMoney(flower.getPrice()));

        TextView textViewOldPrice = (TextView) baseViewHolder.getConvertView().findViewById(R.id.tv_price_old);
        textViewOldPrice.setText(flower.getMoney(flower.getOldPrice()));
        textViewOldPrice.setPaintFlags(textViewOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        RatingBar ratingBar = (RatingBar) baseViewHolder.getConvertView().findViewById(R.id.rating_bar);
        if (flower.getRating() != null)
          ratingBar.setRating(Float.parseFloat(flower.getRating()));

        ImageView imageView = (ImageView) baseViewHolder.getConvertView().findViewById(R.id.im_flower);
        if (flower.getImage() != null)
            ImageUniversalUtils.imageLoader.displayImage(flower.getImage(), imageView, ImageUniversalUtils.options);

        RelativeLayout relativeLayoutDelete = (RelativeLayout) baseViewHolder.getConvertView().findViewById(R.id.rl_delete);
        relativeLayoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(activity)
                        .title("Livizi")
                        .content("Bạn muốn xóa sản phẩm " + flower.getName() + " ra khỏi mục ưa thích ?")
                        .positiveText("Có")
                        .negativeText("Không")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                RealmFlowerUtils.deleteById(RealmFlag.FLAG, RealmFlag.FAVORITE, flower.getId());
                            }
                        })
                        .show();

            }
        });


    }
}
