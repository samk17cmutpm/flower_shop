package khoaluan.vn.flowershop.utils;

import android.app.Activity;
import android.content.Intent;

import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.main.MainActivity;
import khoaluan.vn.flowershop.order.OrderActivity;

/**
 * Created by samnguyen on 9/10/16.
 */
public class ActionUtils {
    public static void go(Activity activity, int tab) {
        Intent intent = new Intent(activity, MainActivity.class);
        ActionDefined actionDefined = new ActionDefined(tab);
        intent.putExtra(Action.TAB, actionDefined);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void goOrder(Activity activity, int position) {
        Intent intent = new Intent(activity, OrderActivity.class);
        ActionDefined actionDefined = new ActionDefined(position);
        intent.putExtra(Action.ACTION_FOR_ORDER, actionDefined);
        activity.startActivity(intent);
        activity.finish();
    }
}
