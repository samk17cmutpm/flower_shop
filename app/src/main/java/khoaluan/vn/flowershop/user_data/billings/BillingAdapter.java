package khoaluan.vn.flowershop.user_data.billings;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;

/**
 * Created by samnguyen on 9/11/16.
 */
public class BillingAdapter extends BaseQuickAdapter<Billing> {


    public BillingAdapter(List<Billing> data) {
        super(R.layout.billing_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Billing billing) {
        baseViewHolder.setText(R.id.tv_id, billing.getOrderCode())
                .setText(R.id.tv_status, billing.getOrderStatusString())
                .setText(R.id.tv_payment_dae, billing.getDateSetPayment());
    }
}
