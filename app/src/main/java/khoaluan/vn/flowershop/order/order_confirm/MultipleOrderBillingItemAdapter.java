package khoaluan.vn.flowershop.order.order_confirm;

import android.app.Activity;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;

/**
 * Created by samnguyen on 9/14/16.
 */
public class MultipleOrderBillingItemAdapter extends BaseMultiItemQuickAdapter<MultipleOrderBillingItem> implements Base{
    private final Activity activity;
    private final SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(1);
    public MultipleOrderBillingItemAdapter(Activity activity, List<MultipleOrderBillingItem> data) {
        super(data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MultipleOrderBillingItem multipleOrderBillingItem) {

    }
}
