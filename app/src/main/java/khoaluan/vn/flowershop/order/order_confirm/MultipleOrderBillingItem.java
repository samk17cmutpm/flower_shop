package khoaluan.vn.flowershop.order.order_confirm;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;

/**
 * Created by samnguyen on 9/14/16.
 */
public class MultipleOrderBillingItem extends MultiItemEntity {
    public static final int HEADER = 1;
    public static final int PROODUCT = 2;
    public static final int INFO = 3;
    public static final int ACTION = 4;

    private Billing billing;

    public MultipleOrderBillingItem() {
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }
}
