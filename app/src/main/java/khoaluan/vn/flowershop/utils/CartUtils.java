package khoaluan.vn.flowershop.utils;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;

/**
 * Created by samnguyen on 9/12/16.
 */
public class CartUtils {
    public static int getSum(List<Cart> carts) {
        int sum = 0;
        for (Cart cart : carts)
            sum = sum + cart.getProductQuantity();

        return sum;
    }
}
