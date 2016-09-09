package khoaluan.vn.flowershop.utils;

/**
 * Created by samnguyen on 9/8/16.
 */
public class ValidationUtils {
    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
