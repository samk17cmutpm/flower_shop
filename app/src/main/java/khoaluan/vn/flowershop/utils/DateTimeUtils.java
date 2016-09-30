package khoaluan.vn.flowershop.utils;

import java.text.Format;
import java.text.ParseException;
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

    public static String getDataDeliveryFull(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        String delivery =  format.format(date);
        String year = delivery.substring(0, 4);
        String month = delivery.substring(5, 7);
        String day = delivery.substring(8, 10);
        String hour = delivery.substring(11, 13);
        String minute = delivery.substring(14, 16);
        return "giao hàng trước " + hour + " giờ ngày " + day + " tháng " + month + " năm " + year;
    }

    public static long string2Milliseconds(String time, SimpleDateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
