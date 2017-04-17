package vitalu.ua.gmail.com.homemoney.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by Виталий on 01.01.2016.
 */
public class Utils {

    public static String getDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }

    public static String getYear(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        return dateFormat.format(date);
    }

    public static String getMonthAndYear(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        return dateFormat.format(date);
    }

    public static String getTime(long time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm");
        return timeFormat.format(time);
    }

    public static String getFullDate(long date) {
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yy  HH.mm");
        return fullDateFormat.format(date);
    }

    public static double getTwoDouble(String str){
        BigDecimal amount = new BigDecimal(str);
        amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        return amount.doubleValue();
    }
}
