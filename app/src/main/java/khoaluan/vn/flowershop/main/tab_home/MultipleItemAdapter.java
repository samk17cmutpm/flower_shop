package khoaluan.vn.flowershop.main.tab_home;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;

/**
 * Created by samnguyen on 8/22/16.
 */
public class MultipleItemAdapter extends BaseMultiItemQuickAdapter<MultipleItem> implements Base {
    private final Activity activity;
    private final SpacesItemDecoration decoration = new SpacesItemDecoration(GRID_VIEW_DISTANCE);
    public MultipleItemAdapter(Activity activity, List data) {
        super(data);
        this.activity = activity;
        addItemType(MultipleItem.TITLE, R.layout.multiple_item_title);
        addItemType(MultipleItem.FLOWER, R.layout.multiple_item_flowers);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultipleItem item) {
        switch (holder.getItemViewType()) {
            case MultipleItem.TITLE:
                holder.setText(R.id.tv_title, item.getTitle());
                break;
            case MultipleItem.FLOWER:
                FlowerAdapter adapter = new FlowerAdapter(this.activity, item.getFlowers());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerView = (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
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
