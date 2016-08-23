package khoaluan.vn.flowershop.main.tab_home;

import android.app.Activity;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.AdvertisingItem;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;

/**
 * Created by samnguyen on 8/23/16.
 */
public class AdvertisingAdapter extends BaseQuickAdapter<AdvertisingItem> {
    private List<AdvertisingItem> advertisingItems;

    public AdvertisingAdapter(Activity activity, List<AdvertisingItem> advertisingItems) {
        super(R.layout.advertising_item_detail, advertisingItems);
    }

    @Override
    protected void convert(BaseViewHolder holder, AdvertisingItem advertisingItem) {
        ImageView imageView = (ImageView) holder.getConvertView().findViewById(R.id.iv_advertising);
        if (advertisingItem.getImage() != null)
            ImageUniversalUtils.imageLoader.displayImage(
                    advertisingItem.getImage(),
                    imageView,
                    ImageUniversalUtils.options
            );
    }
}
