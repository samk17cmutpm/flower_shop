package khoaluan.vn.flowershop.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.main.tab_home.FlowerAdapter;
import khoaluan.vn.flowershop.main.tab_home.MultipleMainItemAdapter;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;
import khoaluan.vn.flowershop.utils.OnItemClickUtils;

/**
 * Created by samnguyen on 8/25/16.
 */
public class MutipleDetailItemAdapter extends BaseMultiItemQuickAdapter<MutipleDetailItem> implements Base {
    private final Activity activity;
    private final SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(PRODUCT_DISTANCE);

    public MutipleDetailItemAdapter(Activity activity, List<MutipleDetailItem> data) {
        super(data);
        this.activity = activity;
        addItemType(MutipleDetailItem.HEADER, R.layout.flower_detail_header);
        addItemType(MutipleDetailItem.SHORT_DESCRIPTION, R.layout.flower_detail_short_description);
        addItemType(MutipleDetailItem.IMAGE, R.layout.flower_detail_image);
        addItemType(MutipleDetailItem.FULL_DESCRIPTION, R.layout.flower_detail_full_description);
        addItemType(MutipleDetailItem.TITLE, R.layout.flower_detail_title);
        addItemType(MutipleDetailItem.SUGGESTION, R.layout.flower_detail_suggesstion);
        addItemType(MutipleDetailItem.ACTION, R.layout.flower_detail_action);
        addItemType(MutipleDetailItem.ADDRESS, R.layout.flower_detail_address);
    }

    @Override
    protected void convert(BaseViewHolder holder, final MutipleDetailItem item) {
        switch (holder.getItemViewType()) {
            case MutipleDetailItem.HEADER:
                holder.setText(R.id.tv_name, item.getFlower().getName());
                holder.setText(R.id.tv_price, item.getFlower().getMoney(item.getFlower().getPrice()));
                TextView textViewOldPrice = (TextView) holder.getConvertView().findViewById(R.id.tv_price_old);
                textViewOldPrice.setText(item.getFlower().getMoney(item.getFlower().getOldPrice()));
                textViewOldPrice.setPaintFlags(textViewOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                RatingBar ratingBar = (RatingBar) holder.getConvertView().findViewById(R.id.rating_bar);
                if (item.getFlower().getRating() != null)
                    ratingBar.setRating(Float.parseFloat(item.getFlower().getRating()));
                break;

            case MutipleDetailItem.SHORT_DESCRIPTION:
                holder.setText(R.id.tv_content, item.getFlower().getShortDescription());
                break;

            case MutipleDetailItem.IMAGE:
                ImageView imageView = (ImageView) holder.getConvertView().findViewById(R.id.im_flower);
                if (item.getFlower().getImage() != null)
                    ImageUniversalUtils.imageLoader.displayImage(item.getFlower().getImage(), imageView, ImageUniversalUtils.options);
                break;

            case MutipleDetailItem.ACTION:
                MaterialFavoriteButton toolbarFavorite = (MaterialFavoriteButton)
                        holder.getConvertView().findViewById(R.id.favorite);
//                toolbarFavorite.setFavorite(false, false);
//                toolbarFavorite.setColor(MaterialFavoriteButton.STYLE_WHITE);
//                toolbarFavorite.setType(MaterialFavoriteButton.STYLE_HEART);
//                toolbarFavorite.setRotationDuration(4000);
                break;

            case MutipleDetailItem.FULL_DESCRIPTION:
                holder.setText(R.id.tv_content, item.getFlower().getFullDescription());
                break;

            case MutipleDetailItem.TITLE:
                break;

            case MutipleDetailItem.ADDRESS:
                break;

            case MutipleDetailItem.SUGGESTION:
                FlowerAdapter adapter = new FlowerAdapter(activity, item.getFlowers());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerView = (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.removeItemDecoration(spacesItemDecoration);
                recyclerView.addItemDecoration(spacesItemDecoration);
                View view_empty = activity.getLayoutInflater().inflate(R.layout.flowers_empty,
                        (ViewGroup) recyclerView.getParent(), false);
                adapter.setEmptyView(view_empty);
                adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        OnItemClickUtils.flowerDetail(activity, item.getFlowers().get(i),
                                new FlowerSuggesstion(item.getFlowers()), true);
                    }
                });
                recyclerView.setAdapter(adapter);
                break;
        }
    }
}
