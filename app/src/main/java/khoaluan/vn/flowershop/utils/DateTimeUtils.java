package khoaluan.vn.flowershop.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by samnguyen on 9/19/16.
 */
public class DateTimeUtils {
    public static String getDataDelivery(long dateDelivery) {
        Date date = new Date(dateDelivery*1000);
        Format format = new SimpleDateFormat("yyyy MM dd");
        String delivery =  format.format(date);
        String year = delivery.substring(0, 4);
        String month = delivery.substring(5, 7);
        String day = delivery.substring(8, 10);
        return "Tạo đơn hàng ngày " + day + " tháng " + month + " năm " + year;
    }
}
