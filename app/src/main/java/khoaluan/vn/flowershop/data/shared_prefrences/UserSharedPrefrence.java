package khoaluan.vn.flowershop.data.shared_prefrences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import khoaluan.vn.flowershop.data.model_parse_and_realm.User;

/**
 * Created by samnguyen on 9/10/16.
 */
public class UserSharedPrefrence {
    public static final String isSignedIn = "isSignedIn";
    public static final String Id = "Id";
    public static final String Email = "Email";
    public static final String FullName = "FullName";
    public static final String Address = "Address";
    public static final String Phone = "Phone";
    public static final String DeviceId = "DeviceId";

    public static void setSignedIn(Activity activity, boolean value) {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = mSettings.edit();

        editor.putBoolean(isSignedIn, value);
        editor.apply();
    }

    public static boolean isSignedIn(Activity activity) {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(activity);
        return mSettings.getBoolean(isSignedIn, false);
    }


    public static void saveUser(User user, Activity activity) {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = mSettings.edit();

        editor.putString(Id, user.getId());
        editor.putString(Email, user.getEmail());
        editor.putString(FullName, user.getFullName());
        editor.putString(Address, user.getAddress());
        editor.putString(Phone, user.getPhone());
        editor.putString(DeviceId, user.getDeviceId());

        editor.apply();
    }

    public static User getUser(Activity activity) {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(activity);
        return new User(
                mSettings.getString(Id, null),
                mSettings.getString(Email, null),
                mSettings.getString(FullName, null),
                mSettings.getString(Address, null),
                mSettings.getString(Phone, null),
                mSettings.getString(DeviceId, null)
        );
    }

}
