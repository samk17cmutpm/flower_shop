package khoaluan.vn.flowershop.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForRating;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.main.tab_home.FlowerAdapter;
import khoaluan.vn.flowershop.rating.RatingActivity;
import khoaluan.vn.flowershop.rating.RatingAdapter;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;
import khoaluan.vn.flowershop.utils.OnItemClickUtils;

/**
 * Created by samnguyen on 8/25/16.
 */
public class MutipleDetailItemAdapter extends BaseMultiItemQuickAdapter<MutipleDetailItem> implements Base {
    private final Activity activity;
    private final SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(PRODUCT_DISTANCE);
    private final DetailsContract.Presenter presenter;

    public MutipleDetailItemAdapter(Activity activity, List<MutipleDetailItem> data, DetailsContract.Presenter presenter) {
        super(data);
        this.activity = activity;
        this.presenter = presenter;
        addItemType(MutipleDetailItem.HEADER, R.layout.flower_detail_header);
        addItemType(MutipleDetailItem.SHORT_DESCRIPTION, R.layout.flower_detail_short_description);
        addItemType(MutipleDetailItem.IMAGE, R.layout.flower_detail_image);
        addItemType(MutipleDetailItem.FULL_DESCRIPTION, R.layout.flower_detail_full_description);
        addItemType(MutipleDetailItem.TITLE, R.layout.flower_detail_title);
        addItemType(MutipleDetailItem.SUGGESTION, R.layout.flower_detail_suggesstion);
        addItemType(MutipleDetailItem.ACTION, R.layout.flower_detail_action);
        addItemType(MutipleDetailItem.ADDRESS, R.layout.flower_detail_address);
        addItemType(MutipleDetailItem.RATING, R.layout.flower_detail_rating);
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
                if (item.getFlower().getRating() != null) {
                    ratingBar.setRating(Float.parseFloat(item.getFlower().getRating()));
                }

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

                toolbarFavorite.setFavorite(RealmFlowerUtils.isExistedById(RealmFlag.FLAG, RealmFlag.FAVORITE, item.getFlower().getId()));

                toolbarFavorite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                            Flower flower = item.getFlower();
                            flower.setFlag(RealmFlag.FAVORITE);
                            presenter.addToFavoriteList(flower);
                        } else {
                            presenter.removeFavoriteFlower(item.getFlower());
                        }
                    }
                });
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

            case MutipleDetailItem.RATING:
                RatingAdapter ratingAdapter = new RatingAdapter(item.getRatings());
                LinearLayoutManager linearRating = new LinearLayoutManager(activity);
                RecyclerView recyclerViewRating = (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);
                recyclerViewRating.setHasFixedSize(true);
                recyclerViewRating.setLayoutManager(linearRating);
                recyclerViewRating.removeItemDecoration(spacesItemDecoration);
                recyclerViewRating.addItemDecoration(spacesItemDecoration);
                recyclerViewRating.setAdapter(ratingAdapter);

                View view_empty_rating = activity.getLayoutInflater().inflate(R.layout.empty_for_rating,
                        (ViewGroup) recyclerViewRating.getParent(), false);
                TextView textView = (TextView) view_empty_rating.findViewById(R.id.tv_empty);
                textView.setText("Chưa có nhận xét nào");

                LinearLayout linearLayout = (LinearLayout) holder.getConvertView().findViewById(R.id.ln_more);
                if (item.getRatings().isEmpty())
                    linearLayout.setVisibility(View.GONE);

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, RatingActivity.class);
                        intent.putExtra(FLOWER_PARCELABLE, item.getFlower());
                        intent.putExtra(LIST_FLOWER_PARCELABLE, new FlowerSuggesstion(item.getFlowers()));
                        intent.putExtra(Action.ACTION_FOR_RATING, new ActionDefined(ActionForRating.VIEW_RATINGS));
                        activity.startActivity(intent);
                        activity.finish();

                    }
                });

                ImageView imageViewAddRating = (ImageView) holder.getConvertView().findViewById(R.id.add_rating);
                imageViewAddRating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(activity, RatingActivity.class);
                        intent.putExtra(FLOWER_PARCELABLE, item.getFlower());
                        intent.putExtra(LIST_FLOWER_PARCELABLE, new FlowerSuggesstion(item.getFlowers()));
                        intent.putExtra(Action.ACTION_FOR_RATING, new ActionDefined(ActionForRating.ADD_RATINGS));
                        activity.startActivity(intent);
                        activity.finish();
                        
                    }
                });
                ratingAdapter.setEmptyView(view_empty_rating);
                break;
        }
    }
}
