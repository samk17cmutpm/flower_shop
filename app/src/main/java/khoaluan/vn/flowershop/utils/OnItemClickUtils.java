package khoaluan.vn.flowershop.utils;

import android.app.Activity;
import android.content.Intent;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.detail.DetailsActivity;

/**
 * Created by samnguyen on 8/26/16.
 */
public class OnItemClickUtils implements Base{
    public static void flowerDetail(Activity activity, Flower flower, FlowerSuggesstion flowerSuggesstion, boolean finish) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(FLOWER_PARCELABLE, flower);
        intent.putExtra(LIST_FLOWER_PARCELABLE, flowerSuggesstion);
        activity.startActivity(intent);
        if (finish)
            activity.finish();
    }
}
