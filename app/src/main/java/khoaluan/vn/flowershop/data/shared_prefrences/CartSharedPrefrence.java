package khoaluan.vn.flowershop.data.shared_prefrences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by samnguyen on 9/11/16.
 */
public class CartSharedPrefrence {
    public static final String CART_ID = "CART_ID";

    public static void saveCartId(String cartId, Activity activity) {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(CART_ID, cartId);
        editor.apply();
    }

    public static String getCartId(Activity activity) {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(activity);
        return mSettings.getString(CART_ID, null);
    }

    public static boolean isCartIdExisted(Activity activity) {
        if (getCartId(activity) != null)
            return true;
        return false;
    }
}
