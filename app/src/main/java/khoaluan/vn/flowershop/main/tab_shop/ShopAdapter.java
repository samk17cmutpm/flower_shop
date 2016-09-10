package khoaluan.vn.flowershop.main.tab_shop;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;

/**
 * Created by samnguyen on 8/28/16.
 */
public class ShopAdapter extends BaseQuickAdapter<Flower> {
    private List<Flower> flowers;
    private Activity activity;

    public ShopAdapter(Activity activity, List<Flower> flowers) {
        super(R.layout.shop_item, flowers);
        this.activity = activity;
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final Flower flower) {
        baseViewHolder.setText(R.id.tv_flower_name, flower.getName())
                .setText(R.id.tv_price, flower.getMoney(flower.getPrice()));

        TextView textViewOldPrice = (TextView) baseViewHolder.getConvertView().findViewById(R.id.tv_price_old);
        textViewOldPrice.setText(flower.getMoney(flower.getOldPrice()));
        textViewOldPrice.setPaintFlags(textViewOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        final TextView textViewNumber = (TextView) baseViewHolder.getConvertView().findViewById(R.id.tv_number);
        ElegantNumberButton elegantNumberButton = (ElegantNumberButton) baseViewHolder.getConvertView().findViewById(R.id.number_button);
        elegantNumberButton.setRange(1, 100);
        elegantNumberButton.setNumber(flower.getNumber() + "");
        textViewNumber.setText(elegantNumberButton.getNumber());
        elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                RealmFlowerUtils.updateNumberById(RealmFlag.FLAG, RealmFlag.CART, flower.getId(), newValue);
                textViewNumber.setText("" + newValue);
            }
        });
        ImageView imageView = (ImageView) baseViewHolder.getConvertView().findViewById(R.id.im_flower);
        if (flower.getImage() != null)
            ImageUniversalUtils.imageLoader.displayImage(flower.getImage(), imageView, ImageUniversalUtils.options);

        ImageView imageViewDelete = (ImageView) baseViewHolder.getConvertView().findViewById(R.id.im_delete);
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(activity)
                        .title("Livizi")
                        .content("Bạn muốn xóa sản phẩm " + flower.getName() + " ra khỏi giỏ hàng ?")
                        .positiveText("Có")
                        .negativeText("Không")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                RealmFlowerUtils.deleteById(RealmFlag.FLAG, RealmFlag.CART, flower.getId());
                            }
                        })
                        .show();
            }
        });
    }
}
