package khoaluan.vn.flowershop.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by samnguyen on 8/29/16.
 */
public class MoneyUtils {
    public static String getMoney(int serverPrice) {
        if (serverPrice == 0)
            return "0 VND";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat();
        df.setDecimalFormatSymbols(symbols);
        df.setGroupingSize(3);
        df.setMaximumFractionDigits(2);
        return df.format(serverPrice) + " VND";
    }
}
