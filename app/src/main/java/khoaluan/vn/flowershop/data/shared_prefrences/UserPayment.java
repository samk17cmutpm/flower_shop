package khoaluan.vn.flowershop.data.shared_prefrences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import khoaluan.vn.flowershop.data.model_parse_and_realm.BillingAddressDTO;

/**
 * Created by samnguyen on 9/23/16.
 */
public class UserPayment {
    public static final String FULL_NAME = "FULL_NAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String CITY_NAME = "CITY_NAME";
    public static final String DISTRICT_NAME = "DISTRICT_NAME";
    public static final String ADDRESS = "ADDRESS";

    public static void saveUserPayment(BillingAddressDTO user, Activity activity){

        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = mSettings.edit();

        editor.putString(FULL_NAME, user.getName());
        editor.putString(PHONE_NUMBER, user.getPhone());
        editor.putString(CITY_NAME, user.getCityString());
        editor.putString(DISTRICT_NAME, user.getDistrictString());
        editor.putString(ADDRESS, user.getAddress());

        editor.apply();

    }

    public static BillingAddressDTO getUserPayment(Activity activity) {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(activity);
        return new BillingAddressDTO(
                mSettings.getString(FULL_NAME, null),
                mSettings.getString(PHONE_NUMBER, null),
                mSettings.getString(CITY_NAME, null),
                mSettings.getString(DISTRICT_NAME, null),
                mSettings.getString(ADDRESS, null)
        );
    }
}
