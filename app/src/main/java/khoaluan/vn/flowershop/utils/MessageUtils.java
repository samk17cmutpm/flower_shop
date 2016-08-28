package khoaluan.vn.flowershop.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by samnguyen on 8/28/16.
 */
public class MessageUtils {
    public static void showShort(Activity context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Activity context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Activity context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showLong(Activity context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
