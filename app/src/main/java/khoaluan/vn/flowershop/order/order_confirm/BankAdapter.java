package khoaluan.vn.flowershop.order.order_confirm;

import android.app.Activity;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Bank;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;

/**
 * Created by samnguyen on 9/15/16.
 */
public class BankAdapter extends BaseQuickAdapter<Bank> {
    private final Activity activity;

    public BankAdapter(List<Bank> data, Activity activity) {
        super(R.layout.bank_item, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Bank bank) {
        baseViewHolder.setText(R.id.tv_name, bank.getBank())
                .setText(R.id.tv_number, "Số tài khoản : " + bank.getAccountNumber())
                .setText(R.id.tv_owner, "Chủ tài khoản : " + bank.getAccountOwner());

        ImageView imageView = (ImageView) baseViewHolder.getConvertView().findViewById(R.id.iv_bank);
        if (bank.getImage() != null)
            ImageUniversalUtils.imageLoader.displayImage(bank.getImage(), imageView, ImageUniversalUtils.options);
    }
}
