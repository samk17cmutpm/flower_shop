package khoaluan.vn.flowershop.utils;

import android.app.Activity;
import android.content.Intent;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.category_detail.CategoryDetailActivity;
import khoaluan.vn.flowershop.data.model_parse_and_realm.AdvertisingItem;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.detail.DetailsActivity;

/**
 * Created by samnguyen on 8/24/16.
 */
public class AdvertisingUtils implements Base{
    public static void goDetails(AdvertisingItem advertisingItem, Activity activity) {
        switch (advertisingItem.getType()) {
            case AdvertisingItem.PRODUCT :
                Flower flower = new Flower();
                flower.setId(advertisingItem.getProductId());
                flower.setImage(advertisingItem.getImage());
                // Start activity
                Intent intent = new Intent(activity, DetailsActivity.class);
                intent.putExtra(FLOWER_PARCELABLE, flower);
                activity.startActivity(intent);
                break;
            case AdvertisingItem.CATEGORY:
                Category category = new Category(advertisingItem.getCategoryId());
                Intent intentCategory = new Intent(activity, CategoryDetailActivity.class);
                intentCategory.putExtra(CATEGORY_PARCELABLE, category);
                activity.startActivity(intentCategory);
                break;
        }
    }
}
