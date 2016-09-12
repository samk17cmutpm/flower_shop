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
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;
import khoaluan.vn.flowershop.utils.MoneyUtils;

/**
 * Created by samnguyen on 8/28/16.
 */
public class ShopAdapter extends BaseQuickAdapter<Cart> {
    private List<Cart> carts;
    private Activity activity;
    private final ShopContract.Presenter presenter;

    public ShopAdapter(Activity activity, List<Cart> carts, ShopContract.Presenter presenter) {
        super(R.layout.shop_item, carts);
        this.activity = activity;
        this.presenter = presenter;
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final Cart cart) {
        baseViewHolder.setText(R.id.tv_flower_name, cart.getProductName())
                .setText(R.id.tv_price, MoneyUtils.getMoney(cart.getProductCost()));

//        TextView textViewOldPrice = (TextView) baseViewHolder.getConvertView().findViewById(R.id.tv_price_old);
//        textViewOldPrice.setText(cart.getMoney(cart.getOldPrice()));
//        textViewOldPrice.setPaintFlags(textViewOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        final TextView textViewNumber = (TextView) baseViewHolder.getConvertView().findViewById(R.id.tv_number);
        final ElegantNumberButton elegantNumberButton = (ElegantNumberButton) baseViewHolder.getConvertView().findViewById(R.id.number_button);
        elegantNumberButton.setRange(1, 100);
        elegantNumberButton.setNumber(cart.getProductQuantity() + "");
        textViewNumber.setText(elegantNumberButton.getNumber());
        elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                elegantNumberButton.setNumber(cart.getProductQuantity() + "");
                presenter.updateQuantity(cart.getId(), cart.getCartId(), cart.getProductId(), newValue);
                presenter.notifyDataChange(baseViewHolder.getAdapterPosition(), true);
            }
        });
        ImageView imageView = (ImageView) baseViewHolder.getConvertView().findViewById(R.id.im_flower);
        if (cart.getProductImage() != null)
            ImageUniversalUtils.imageLoader.displayImage(cart.getProductImage(), imageView, ImageUniversalUtils.options);

        ImageView imageViewDelete = (ImageView) baseViewHolder.getConvertView().findViewById(R.id.im_delete);
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new MaterialDialog.Builder(activity)
                        .title("Livizi")
                        .content("Bạn muốn xóa sản phẩm " + cart.getProductName() + " ra khỏi giỏ hàng ?")
                        .positiveText("Có")
                        .negativeText("Không")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                presenter.removeCartItem(cart.getId(), cart.getCartId(), cart.getProductId());
                            }
                        })
                        .show();
            }
        });
    }
}
