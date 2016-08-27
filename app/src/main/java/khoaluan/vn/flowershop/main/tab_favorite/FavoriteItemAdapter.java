package khoaluan.vn.flowershop.main.tab_favorite;

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
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.main.tab_home.FlowerAdapter;
import khoaluan.vn.flowershop.main.tab_home.MultipleMainItem;
import khoaluan.vn.flowershop.utils.OnItemClickUtils;

/**
 * Created by samnguyen on 8/27/16.
 */
public class FavoriteItemAdapter extends BaseMultiItemQuickAdapter<FavoriteItem> implements Base {
    private final Activity activity;
    private final SpacesItemDecoration spaceProduct = new SpacesItemDecoration(PRODUCT_DISTANCE);

    public FavoriteItemAdapter(Activity activity, List data) {
        super(data);
        this.activity = activity;
        addItemType(FavoriteItem.TITLE, R.layout.multiple_item_title);
        addItemType(FavoriteItem.FAVORITE, R.layout.multiple_item_favorite);
        addItemType(FavoriteItem.FLOWER, R.layout.multiple_item_flowers);
    }

    @Override
    protected void convert(BaseViewHolder holder, FavoriteItem favoriteItem) {
        switch (holder.getItemViewType()) {
            case FavoriteItem.FLOWER:
                FlowerAdapter adapter = new FlowerAdapter(activity, favoriteItem.getFlowers());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerView = (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.removeItemDecoration(spaceProduct);
                recyclerView.addItemDecoration(spaceProduct);
                View view_empty = activity.getLayoutInflater().inflate(R.layout.flowers_empty,
                        (ViewGroup) recyclerView.getParent(), false);
                adapter.setEmptyView(view_empty);
                adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
//                        OnItemClickUtils.flowerDetail(activity, flowers.get(i),
//                                new FlowerSuggesstion(flowers), false);

                    }
                });
                recyclerView.setAdapter(adapter);
                break;
            case FavoriteItem.TITLE:
                holder.setText(R.id.tv_title, favoriteItem.getTitle());
                break;
            case FavoriteItem.FAVORITE:
                FavoriteAdapter favoriteAdapter = new FavoriteAdapter(activity, favoriteItem.getFavoriteFlowers());
                LinearLayoutManager linearLayoutAdvertisingManager =
                        new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                RecyclerView recyclerViewFavorite =
                        (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);

                recyclerViewFavorite.setHasFixedSize(true);
                recyclerViewFavorite.setLayoutManager(linearLayoutAdvertisingManager);
                recyclerViewFavorite.removeItemDecoration(spaceProduct);
                recyclerViewFavorite.addItemDecoration(spaceProduct);

                View view_empty_advertising = activity.getLayoutInflater().inflate(R.layout.flowers_empty,
                        (ViewGroup) recyclerViewFavorite.getParent(), false);

                favoriteAdapter.setEmptyView(view_empty_advertising);
                recyclerViewFavorite.setAdapter(favoriteAdapter);
                break;
        }
    }
}
