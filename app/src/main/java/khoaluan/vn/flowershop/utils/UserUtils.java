package khoaluan.vn.flowershop.utils;

import khoaluan.vn.flowershop.realm_data_local.RealmUserUtils;

/**
 * Created by samnguyen on 9/8/16.
 */
public class UserUtils {
    public static boolean isSignedIn() {
        if (RealmUserUtils.load() == null)
            return false;
        else
            return true;
    }
}
