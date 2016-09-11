package khoaluan.vn.flowershop.user_data.billings;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;

/**
 * Created by samnguyen on 9/11/16.
 */
public class MultipleBillingItem extends MultiItemEntity {
    public static final int HEADER = 1;
    public static final int PROODUCT = 2;
    public static final int INFO = 3;

    private Billing billing;

    public MultipleBillingItem() {
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }
}
