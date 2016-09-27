package khoaluan.vn.flowershop.main.tab_home;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.category_detail.CategoryDetailActivity;
import khoaluan.vn.flowershop.data.model_parse_and_realm.AdvertisingItem;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.detail.DetailsActivity;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.utils.AdvertisingUtils;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;
import khoaluan.vn.flowershop.utils.ScreenUtils;

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
    protected void convert(BaseViewHolder holder, final MultipleAdvertisingItem multipleAdvertisingItem) {
        switch (holder.getItemViewType()) {
            case MultipleAdvertisingItem.ONLY_ONE:

                int hight = ScreenUtils.getScreenWidth(activity) / multipleAdvertisingItem.getHeight();
                RelativeLayout relativeLayoutRoot = (RelativeLayout) holder.getConvertView().findViewById(R.id.rl_root);
                ViewGroup.LayoutParams params = relativeLayoutRoot.getLayoutParams();
                params.height = hight;
                relativeLayoutRoot.setLayoutParams(params);

                ImageView imageView = (ImageView) holder.getConvertView().findViewById(R.id.iv_advertising);
                if (multipleAdvertisingItem.getAdvertisingItems().get(0).getImage() != null)
                    ImageUniversalUtils.imageLoader.displayImage(
                            multipleAdvertisingItem.getAdvertisingItems().get(0).getImage(),
                            imageView,
                            ImageUniversalUtils.options
                    );
                break;
            case MultipleAdvertisingItem.MORE:

                int hightMore = ScreenUtils.getScreenWidth(activity) / multipleAdvertisingItem.getHeight() / multipleAdvertisingItem.getAdvertisingItems().size();
                RelativeLayout relativeLayoutRootMore = (RelativeLayout) holder.getConvertView().findViewById(R.id.rl_root);
                ViewGroup.LayoutParams paramsMore = relativeLayoutRootMore.getLayoutParams();
                paramsMore.height = hightMore;
                relativeLayoutRootMore.setLayoutParams(paramsMore);

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
                adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        AdvertisingItem advertisingItem = multipleAdvertisingItem.getAdvertisingItems().get(i);
                        AdvertisingUtils.goDetails(advertisingItem, activity);
                    }
                });
                recyclerView.setAdapter(adapter);
                break;
        }
    }
}
