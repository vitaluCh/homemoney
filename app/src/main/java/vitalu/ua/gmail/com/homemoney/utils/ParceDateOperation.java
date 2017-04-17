package vitalu.ua.gmail.com.homemoney.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Виталий on 12.02.2016.
 */
public class ParceDateOperation {

    public static String getWeek(long day){

        Date nownow=new Date(day);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(nownow);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  // здесь часы, минуты, секунды
        calendar.set(Calendar.MINUTE, 0);       //и миллисекунды обнуляем, то есть
        calendar.set(Calendar.SECOND, 0);       //находим начало введенного дня
        calendar.set(Calendar.MILLISECOND, 0);

             /* Определение даты на начало текущей недели */
        Integer dayDifference = calendar.get(Calendar.DAY_OF_WEEK) == 1 ? -6 : (Calendar.MONDAY - calendar.get(Calendar.DAY_OF_WEEK));
        calendar.add(Calendar.DAY_OF_MONTH, dayDifference);

        long begindWeek = calendar.getTimeInMillis(); // переводим полученную календарную

        // дату в миллисекунды
        long endWeek = begindWeek +(1000*60*60*24*7); // получаем конец недели в миллисекундах
        // (прибавляем количество миллисекунд в сутках)

        calendar.setTimeInMillis(endWeek);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        long firstWeek = calendar.getTimeInMillis();

        return Utils.getDate(begindWeek) + " - " + Utils.getDate(firstWeek);
    }

    public static String getMonth(long day){

        Date nownow=new Date(day);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(nownow);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  // здесь часы, минуты, секунды
        calendar.set(Calendar.MINUTE, 0);       //и миллисекунды обнуляем, то есть
        calendar.set(Calendar.SECOND, 0);       //находим начало введенного дня
        calendar.set(Calendar.MILLISECOND, 0);

            /* Определение даты на начало текущего месяца */
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        long begindMonth = calendar.getTimeInMillis(); // переводим полученную календарную
        // дату в миллисекунды
        int dayOfMonth = calendar.getActualMaximum(Calendar.DATE);//кол-во дней в месяце

        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        long endMonth = calendar.getTimeInMillis();

        return Utils.getDate(begindMonth) + " - " + Utils.getDate(endMonth);
    }

    public static String getYear(long day){
        Date nownow=new Date(day);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(nownow);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  // здесь часы, минуты, секунды
        calendar.set(Calendar.MINUTE, 0);       //и миллисекунды обнуляем, то есть
        calendar.set(Calendar.SECOND, 0);       //находим начало введенного дня
        calendar.set(Calendar.MILLISECOND, 0);

            /* Определение даты на начало текущего месяца */
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        ///////////
        long begindYear = calendar.getTimeInMillis(); // переводим полученную календарную
        // дату в миллисекунды
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        long endYear = calendar.getTimeInMillis();
        // (прибавляем количество миллисекунд в сутках)

        return Utils.getDate(begindYear) + " - " + Utils.getDate(endYear);
    }

    public static String geOnetYear(long day){
        Date nownow=new Date(day);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(nownow);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  // здесь часы, минуты, секунды
        calendar.set(Calendar.MINUTE, 0);       //и миллисекунды обнуляем, то есть
        calendar.set(Calendar.SECOND, 0);       //находим начало введенного дня
        calendar.set(Calendar.MILLISECOND, 0);

            /* Определение даты на начало текущего месяца */
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        ///////////
        long begindYear = calendar.getTimeInMillis(); // переводим полученную календарную


        return Utils.getYear(begindYear);
    }
}
