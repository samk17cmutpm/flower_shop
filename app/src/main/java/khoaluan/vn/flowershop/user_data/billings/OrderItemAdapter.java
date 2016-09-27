package khoaluan.vn.flowershop.user_data.billings;

import android.app.Activity;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;
import khoaluan.vn.flowershop.utils.MoneyUtils;

/**
 * Created by samnguyen on 9/11/16.
 */
public class OrderItemAdapter extends BaseQuickAdapter<Cart>{
    private Activity activity;

    public OrderItemAdapter(Activity activity, List<Cart> data) {
        super(R.layout.order_item, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Cart orderItemsDTO) {
        ImageView imageView = (ImageView) baseViewHolder.getConvertView().findViewById(R.id.iv_flower);
        ImageUniversalUtils.imageLoader.displayImage(orderItemsDTO.getProductImage(), imageView, ImageUniversalUtils.options);

        baseViewHolder.setText(R.id.tv_name, orderItemsDTO.getProductName())
                .setText(R.id.tv_number, "Số lượng : " + orderItemsDTO.getProductQuantity())
                .setText(R.id.tv_price, MoneyUtils.getMoney(orderItemsDTO.getProductCost()) + " x " + orderItemsDTO.getProductQuantity());
    }
}
