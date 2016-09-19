package khoaluan.vn.flowershop.notifycation;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Notifycation;
import khoaluan.vn.flowershop.utils.DateTimeUtils;

/**
 * Created by samnguyen on 9/19/16.
 */
public class NotifycationAdapter extends BaseQuickAdapter<Notifycation>{
    public NotifycationAdapter(List<Notifycation> data) {
        super(R.layout.notifycation_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Notifycation notifycation) {
        baseViewHolder.setText(R.id.tv_title, notifycation.getSubject())
                .setText(R.id.tv_content, notifycation.getContent())
                .setText(R.id.tv_date, DateTimeUtils.getDataDelivery(notifycation.getCreatedOnUtc()));
    }
}
