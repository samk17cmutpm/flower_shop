package khoaluan.vn.flowershop.main.tab_favorite;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;

import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.main.tab_home.FlowerAdapter;
import khoaluan.vn.flowershop.main.tab_home.MultipleMainItem;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;
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
    protected void convert(BaseViewHolder holder, final FavoriteItem favoriteItem) {
        switch (holder.getItemViewType()) {
            case FavoriteItem.FAVORITE:
                setUpFavoriteFlowers(activity, holder, favoriteItem.getFavoriteFlowers(), spaceProduct);
                break;
            case FavoriteItem.TITLE:
                setUpTitle(activity, holder, favoriteItem.getTitle());
                break;
            case FavoriteItem.FLOWER:
                setUpRecommendedFlowers(activity, holder, favoriteItem.getFlowers(), spaceProduct);
                break;
        }
    }

    private
    void setUpTitle(Activity activity, BaseViewHolder holder, String title) {
        holder.setText(R.id.tv_title, title);
    }

    private void setUpRecommendedFlowers(final Activity activity, BaseViewHolder holder,
                                         final List<Flower> flowers, SpacesItemDecoration spacesItemDecoration) {
        FlowerAdapter adapter = new FlowerAdapter(activity, flowers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.removeItemDecoration(spacesItemDecoration);
        recyclerView.addItemDecoration(spacesItemDecoration);
//        View view_empty = activity.getLayoutInflater().inflate(R.layout.empty_favorite,
//                (ViewGroup) recyclerView.getParent(), false);
//        adapter.setEmptyView(view_empty);
        adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                OnItemClickUtils.flowerDetail(activity, flowers.get(i),
                        new FlowerSuggesstion(flowers), false);

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setUpFavoriteFlowers(final Activity activity, BaseViewHolder holder,
                                      final List<Flower> flowers, SpacesItemDecoration spacesItemDecoration) {
        final FavoriteAdapter favoriteAdapter = new FavoriteAdapter(activity, flowers);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        final RecyclerView recyclerViewFavorite =
                (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);

        recyclerViewFavorite.setHasFixedSize(true);
        recyclerViewFavorite.setLayoutManager(layoutManager);
        recyclerViewFavorite.removeItemDecoration(spacesItemDecoration);
        recyclerViewFavorite.addItemDecoration(spacesItemDecoration);

        View view_empty_advertising = activity.getLayoutInflater().inflate(R.layout.empty_favorite,
                (ViewGroup) recyclerViewFavorite.getParent(), false);


        recyclerViewFavorite.setAdapter(favoriteAdapter);
        favoriteAdapter.setEmptyView(view_empty_advertising);
        favoriteAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(final View view, final int i) {
                OnItemClickUtils.flowerDetail(activity, flowers.get(i), new FlowerSuggesstion(flowers), false);
            }
        });
    }
}
