package khoaluan.vn.flowershop.main.tab_home;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;

/**
 * Created by samnguyen on 8/23/16.
 */
public class MultipleAdvertisingAdapter extends BaseMultiItemQuickAdapter<MultipleAdvertisingItem> implements Base {

    private final Activity activity;
    private final SpacesItemDecoration decoration = new SpacesItemDecoration(ADVERTISING_DISTANCE);

    public MultipleAdvertisingAdapter(Activity activity, List data) {
        super(data);
        this.activity = activity;
        addItemType(MultipleAdvertisingItem.ONLY_ONE, R.layout.advertising_item);
        addItemType(MultipleAdvertisingItem.MORE, R.layout.advertising_items);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultipleAdvertisingItem multipleAdvertisingItem) {
        switch (holder.getItemViewType()) {
            case MultipleAdvertisingItem.ONLY_ONE:
                ImageView imageView = (ImageView) holder.getConvertView().findViewById(R.id.iv_advertising);
                if (multipleAdvertisingItem.getAdvertisingItems().get(0).getImage() != null)
                    ImageUniversalUtils.imageLoader.displayImage(
                            multipleAdvertisingItem.getAdvertisingItems().get(0).getImage(),
                            imageView,
                            ImageUniversalUtils.options
                    );
                break;
            case MultipleAdvertisingItem.MORE:
                AdvertisingAdapter adapter = new AdvertisingAdapter(this.activity, multipleAdvertisingItem.getAdvertisingItems());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, multipleAdvertisingItem.getAdvertisingItems().size());
                RecyclerView recyclerView = (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.removeItemDecoration(decoration);
                recyclerView.addItemDecoration(decoration);
                View view_empty = this.activity.getLayoutInflater().inflate(R.layout.flowers_empty,
                        (ViewGroup) recyclerView.getParent(), false);
                adapter.setEmptyView(view_empty);
                recyclerView.setAdapter(adapter);
                break;
        }
    }
}
