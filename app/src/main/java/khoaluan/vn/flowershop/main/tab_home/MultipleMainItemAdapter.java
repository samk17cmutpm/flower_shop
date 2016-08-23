package khoaluan.vn.flowershop.main.tab_home;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Advertising;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;

/**
 * Created by samnguyen on 8/22/16.
 */
public class MultipleMainItemAdapter extends BaseMultiItemQuickAdapter<MultipleMainItem> implements Base {
    private final Activity activity;
    private final SpacesItemDecoration spaceProduct = new SpacesItemDecoration(PRODUCT_DISTANCE);
    private final SpacesItemDecoration spaceAdvertising = new SpacesItemDecoration(ADVERTISING_DISTANCE);
    public MultipleMainItemAdapter(Activity activity, List data) {
        super(data);
        this.activity = activity;
        addItemType(MultipleMainItem.TITLE, R.layout.multiple_item_title);
        addItemType(MultipleMainItem.FLOWER, R.layout.multiple_item_flowers);
        addItemType(MultipleMainItem.ADVERTISING, R.layout.multiple_item_advertising);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultipleMainItem item) {
        switch (holder.getItemViewType()) {
            case MultipleMainItem.ADVERTISING:
                MultipleAdvertisingAdapter multipleAdvertisingAdapter =
                        new MultipleAdvertisingAdapter(this.activity,
                                convertAdvertisingToMultipleAdvertisingItem(item.getAdvertisings()));

                LinearLayoutManager linearLayoutAdvertisingManager =
                        new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                RecyclerView recyclerViewAdvertising =
                        (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);

                recyclerViewAdvertising.setHasFixedSize(true);
                recyclerViewAdvertising.setLayoutManager(linearLayoutAdvertisingManager);
                recyclerViewAdvertising.removeItemDecoration(spaceAdvertising);
                recyclerViewAdvertising.addItemDecoration(spaceAdvertising);

                View view_empty_advertising = this.activity.getLayoutInflater().inflate(R.layout.flowers_empty,
                        (ViewGroup) recyclerViewAdvertising.getParent(), false);

                multipleAdvertisingAdapter.setEmptyView(view_empty_advertising);
                recyclerViewAdvertising.setAdapter(multipleAdvertisingAdapter);
                break;
            case MultipleMainItem.TITLE:
                holder.setText(R.id.tv_title, item.getTitle());
                break;
            case MultipleMainItem.FLOWER:
                FlowerAdapter adapter = new FlowerAdapter(this.activity, item.getFlowers());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerView = (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.removeItemDecoration(spaceProduct);
                recyclerView.addItemDecoration(spaceProduct);
                View view_empty = this.activity.getLayoutInflater().inflate(R.layout.flowers_empty,
                        (ViewGroup) recyclerView.getParent(), false);
                adapter.setEmptyView(view_empty);
                recyclerView.setAdapter(adapter);
                break;
        }
    }

    private List<MultipleAdvertisingItem> convertAdvertisingToMultipleAdvertisingItem(List<Advertising> advertisings) {
        List<MultipleAdvertisingItem> multipleAdvertisingItems = new ArrayList<>();
        for (Advertising advertising : advertisings) {
            MultipleAdvertisingItem multipleAdvertisingItem = new MultipleAdvertisingItem(advertising.getAdvertisingItems());
            if (advertising.getAdvertisingItems().size() > 1) {
                multipleAdvertisingItem.setItemType(MultipleAdvertisingItem.MORE);
            } else {
                multipleAdvertisingItem.setItemType(MultipleAdvertisingItem.ONLY_ONE);
            }
            multipleAdvertisingItems.add(multipleAdvertisingItem);
        }
        return multipleAdvertisingItems;
    }

}
