package vitalu.ua.gmail.com.homemoney.database.database_query;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.database.AppContext;
import vitalu.ua.gmail.com.homemoney.model.ReportCategory;
import vitalu.ua.gmail.com.homemoney.model.ReportInOut;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * Created by Виталий on 22.02.2016.
 */
public class ReportQuery {

    public static final String TAG = "myLogMainActivity";

    public static double getOneMonth(long day, int idOperation){//сумма на один месяц

        String query;
        // day - дата, которую вы вводите в миллисекундах
        Date nownow=new Date(day);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(nownow);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  // здесь часы, минуты, секунды
        calendar.set(Calendar.MINUTE, 0);       //и миллисекунды обнуляем, то есть
        calendar.set(Calendar.SECOND, 0);       //находим начало введенного дня
        calendar.set(Calendar.MILLISECOND, 0);
        /* Определение даты на начало текущего месяца */
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        ///////////
        long begindMonth = calendar.getTimeInMillis(); // переводим полученную календарную
                                                        // дату в миллисекунды
        int dayOfMonth = calendar.getActualMaximum(Calendar.DATE);//кол-во дней в месяце
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth+1);
        long endMonth = calendar.getTimeInMillis();//begindMonth +(1000 * 60 * 60 * 24 * dayOfMonth);
                                                    // получаем конец недели в миллисекундах
                                                    // (прибавляем количество миллисекунд в сутках)

        query = "SELECT sum(operations.[amount_operation]) as summ " +
                "FROM operations, source " +
                "WHERE operations.[sours_id]=source.[_id] " +
                "and source.[tupe_id]=" + idOperation +
                " and operations.[date_operation]>=" + begindMonth +
                " and operations.[date_operation]<" + endMonth;

        return AppContext.getDbAdapter().getSumm(query);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static List<Long> getDateOfDay(/*int idOperation*/){//возвращает стисок дат по дням
        String orderBy;

        orderBy = " ORDER BY operations.[date_operation]";

        String query = "SELECT operations.[date_operation] " +
                "FROM operations , source " +
                "WHERE operations.[sours_id]=source.[_id] " +orderBy;// and source.[tupe_id]=" + idOperation*/// +
        //  orderBy;

        return AppContext.getDbAdapter().getListDay(query);
    }

    private static List<Long> getDateOfDay(int idOperation){//возвращает стисок дат по дням
        String orderBy;

        orderBy = " ORDER BY operations.[date_operation]";

        String query = "SELECT operations.[date_operation] " +
                "FROM operations, source " +
                "WHERE operations.[sours_id]=source.[_id] and source.[tupe_id]=" + idOperation +
                orderBy;

        return AppContext.getDbAdapter().getListDay(query);
    }

    public static List<List<ReportInOut>> getListOperationsWeek(/*int idOperation*/){//список каждой недели в отдельном списке

        long dayGroup = 0;
        ReportInOut rio;

        List<List<ReportInOut>> listOfOperationsWeeks = new ArrayList<>();
        List<ReportInOut> listSumWeeks;// = new ArrayList<>();
        String query;
        long currentDay = 0;

        for(long day : getDateOfDay(/*idOperation*/)){

            // day - дата, которую вы вводите в миллисекундах
           // Log.d(TAG, "getDateOfDay = " + Utils.getDate(day));
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
            // String dateFirstDayOfWeek = formattedDate.format(calendar.getTime()); //Дата на начало текущей недели
            //calendar.add(Calendar.DAY_OF_MONTH, -dayDifference);
            ///////////
            long begindWeek = calendar.getTimeInMillis(); // переводим полученную календарную
           // Log.d(TAG, "начало недели = " + Utils.getDate(begindWeek));
            if(currentDay > begindWeek)
                continue;
            // Log.d(TAG, "Начало недели = " + Utils.getDate(begindWeek));
            // дату в миллисекунды
            long nextDay = begindWeek+(1000*60*60*24);
            currentDay = begindWeek;
            long endWeek = begindWeek +(1000*60*60*24*7); // получаем конец недели в миллисекундах
            // (прибавляем количество миллисекунд в сутках)
            // Log.d(TAG, "Конец недели = " + Utils.getDate(endWeek));
            calendar.setTimeInMillis(endWeek);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            long firstWeek = calendar.getTimeInMillis();
           // Log.d(TAG, "Перед for = " + Utils.getDate(currentDay));
            ////////////////////////////////////////////////////
            listSumWeeks = new ArrayList<>();
            for(int i = 0; i < 7; i++, nextDay+=(1000*60*60*24)) {

              //  Log.d(TAG, "В цикле for = " + Utils.getDate(currentDay));

                query = "SELECT sum(operations.[amount_operation]) as summ " +
                        "FROM operations, source " +
                        "WHERE operations.[sours_id]=source.[_id] " +
                        "and source.[tupe_id]=" + 1 +
                        " and operations.[date_operation]>=" + currentDay +
                        " and operations.[date_operation]<" + nextDay;

                double in = AppContext.getDbAdapter().getSumm(query);

                query = "SELECT sum(operations.[amount_operation]) as summ " +
                        "FROM operations, source " +
                        "WHERE operations.[sours_id]=source.[_id] " +
                        "and source.[tupe_id]=" + 2 +
                        " and operations.[date_operation]>=" + currentDay +
                        " and operations.[date_operation]<" + nextDay;

                double out = AppContext.getDbAdapter().getSumm(query);
                rio = new ReportInOut();
                rio.setDate(currentDay);
                rio.setSumInCome(in);
                rio.setSumOutCome(out);

               /* Log.d(TAG, "доход + дата " + rio.getSumInCome() + " " + Utils.getDate(currentDay));
                Log.d(TAG, "расход + дата " + rio.getSumOutCome() + " " + Utils.getDate(currentDay));*/

                listSumWeeks.add(rio);
                currentDay = nextDay;
            }

            listOfOperationsWeeks.add(listSumWeeks);
        }

        /*for(List<ReportInOut> l : listOfOperationsWeeks){
            for(ReportInOut a : l){
                Log.d(TAG, a.getSumInCome() + "");
                Log.d(TAG, a.getSumOutCome() + "");
                Log.d(TAG, Utils.getDate(a.getDate()));
            }
            Log.d(TAG, "----------------------------------------------------");
        }
        Log.d(TAG, "================================================")*/;
        return listOfOperationsWeeks;
    }

/*    public static List<Operation> getListWeek(int position, int idOperation){//список одной недели

        List<List<Operation>> listOfListOperationsWeek = getListOperationsWeek(idOperation);
        if(listOfListOperationsWeek.isEmpty())return null;
        return listOfListOperationsWeek.get(position);
    }*/

    ////////////////////////////////////

    public static List<Operation> getOperationsWeek(long day, int idOperation){//список по одной недели

        String orderBy;

        orderBy = " ORDER BY operations.[date_operation] DESC";
        String query;
        // day - дата, которую вы вводите в миллисекундах

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
        // String dateFirstDayOfWeek = formattedDate.format(calendar.getTime()); //Дата на начало текущей недели
        //calendar.add(Calendar.DAY_OF_MONTH, -dayDifference);
        ///////////
        long begindWeek = calendar.getTimeInMillis(); // переводим полученную календарную
        // Log.d(TAG, "Начало недели = " + Utils.getDate(begindWeek));
        // дату в миллисекунды
        long endWeek = begindWeek +(1000*60*60*24*7); // получаем конец недели в миллисекундах
        // (прибавляем количество миллисекунд в сутках)
        // Log.d(TAG, "Конец недели = " + Utils.getDate(endWeek));
        calendar.setTimeInMillis(endWeek);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        long firstWeek = calendar.getTimeInMillis();

        ////////////////////////////////////////////////////
        query = "SELECT operations.[_id], operations.[amount_operation], operations.[date_operation], " +
                "operations.[description_operation], operations.[image_operation], " +
                "operations.[name_operation], operations.[sours_id], operations.[storage_id], source.[tupe_id], " +
                "source.[name_source], storage.[image_storage], storage.[name_storage], storage.[amount_storage], " +
                "currency.[_id] as [currency_id], currency.[short_name_currency], currency.[full_name_currency] " +
                "FROM operations, source, storage, currency " +
                "WHERE operations.[sours_id]=source.[_id] " +
                "and currency.[_id]=storage.[currency_id] " +
                "and operations.[storage_id]=storage.[_id] " +
                "and source.[tupe_id]=" + idOperation +
                " and operations.[date_operation]>=" + begindWeek +
                " and operations.[date_operation]<" + endWeek +
                orderBy;

        return AppContext.getDbAdapter().getListOperationsDays(query);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<List<ReportInOut>> getListOperationsMonth(/*int idOperation*/){//список каждого месяца в отдельном списке

        ReportInOut rio;

        List<List<ReportInOut>> listOfOperationsMonth = new ArrayList<>();
        List<ReportInOut> listSumMonth;// = new ArrayList<>();
        String query;
        long currentYear = 0;
        long currentMonth = 0;

        for(long day : getDateOfDay()){

            // day - дата, которую вы вводите в миллисекундах

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

            int dayOfMonth = calendar.getActualMaximum(Calendar.DATE);//кол-во дней в месяце
            // Log.d(TAG, "Кол-во дней месяца = " + dayOfMonth);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth+1);
            long endMonth = calendar.getTimeInMillis();
            /*if(currentYear > begindYear){
                continue;
            }
            currentYear = begindYear;*/
            // дату в миллисекунды
            Log.d(TAG, "Начало месяца = " + Utils.getDate(begindYear));
            // int dayOfMonth = calendar.getActualMaximum(Calendar.DATE);//кол-во дней в месяце
            //Log.d(TAG, "Кол-во дней месяца = " + dayOfMonth);
            calendar.set(Calendar.DAY_OF_MONTH, 31);
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            long endYear = calendar.getTimeInMillis();
            if(currentYear >= begindYear){
                continue;
            }
            currentYear = begindYear;
            currentMonth = currentYear;
            ////////////////////////////////////////////////////
            listSumMonth = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
 /* Определение даты на начало текущего месяца */
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.MONTH, i);
                ///////////
                currentMonth = calendar.getTimeInMillis();

                dayOfMonth = calendar.getActualMaximum(Calendar.DATE);//кол-во дней в месяце
                // Log.d(TAG, "Кол-во дней месяца = " + dayOfMonth);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth+1);
                endMonth = calendar.getTimeInMillis();

                query = "SELECT sum(operations.[amount_operation]) as summ " +
                        "FROM operations, source " +
                        "WHERE operations.[sours_id]=source.[_id] " +
                        "and source.[tupe_id]=" + 1 +
                        " and operations.[date_operation]>=" + currentMonth +
                        " and operations.[date_operation]<" + endMonth;

                double in = AppContext.getDbAdapter().getSumm(query);

                query = "SELECT sum(operations.[amount_operation]) as summ " +
                        "FROM operations, source " +
                        "WHERE operations.[sours_id]=source.[_id] " +
                        "and source.[tupe_id]=" + 2 +
                        " and operations.[date_operation]>=" + currentMonth +
                        " and operations.[date_operation]<" + endMonth;

                double out = AppContext.getDbAdapter().getSumm(query);

                rio = new ReportInOut();
                rio.setDate(currentMonth);
                rio.setSumInCome(in);
                rio.setSumOutCome(out);
                Log.d(TAG, "текущая дата" + Utils.getDate(rio.getDate()));
               /* Log.d(TAG, "доход + дата " + rio.getSumInCome() + " " + Utils.getDate(currentDay));
                Log.d(TAG, "расход + дата " + rio.getSumOutCome() + " " + Utils.getDate(currentDay));*/

                listSumMonth.add(rio);
               // currentMonth = endMonth;
            }

            listOfOperationsMonth.add(listSumMonth);

        }
     /*   for(List<ReportInOut> l : listOfOperationsMonth){
            for(ReportInOut a : l){
                Log.d(TAG, a.getSumInCome() + "");
                Log.d(TAG, a.getSumOutCome() + "");
                Log.d(TAG, Utils.getDate(a.getDate()));
            }
            Log.d(TAG, "----------------------------------------------------");
        }
        Log.d(TAG, "================================================");*/
        return listOfOperationsMonth;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<List<ReportInOut>> getListOperationsYear(){//список каждого года в отдельном списке

        ReportInOut rio;
        long currentYear = 0;

        List<List<ReportInOut>> listOfOperationsYear = new ArrayList<>();
        List<ReportInOut> listSumYear;// = new ArrayList<>();
        String query;

        for(long day : getDateOfDay()){

            // day - дата, которую вы вводите в миллисекундах

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
            Log.d(TAG, "Начало месяца = " + Utils.getDate(begindYear));
            // int dayOfMonth = calendar.getActualMaximum(Calendar.DATE);//кол-во дней в месяце
            //Log.d(TAG, "Кол-во дней месяца = " + dayOfMonth);
            int year = calendar.get(Calendar.YEAR)+1;
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            long endYear = calendar.getTimeInMillis();//begindMonth +(1000 * 60 * 60 * 24 * dayOfMonth); // получаем конец недели в миллисекундах
            // (прибавляем количество миллисекунд в сутках)
            Log.d(TAG, "Конец месяца = " + Utils.getDate(endYear));
            if(currentYear >= begindYear){
                continue;
            }
            currentYear = begindYear;
            listSumYear = new ArrayList<>();
            ////////////////////////////////////////////////////
            query = "SELECT sum(operations.[amount_operation]) as summ " +
                    "FROM operations, source " +
                    "WHERE operations.[sours_id]=source.[_id] " +
                    "and source.[tupe_id]=" + 1 +
                    " and operations.[date_operation]>=" + currentYear +
                    " and operations.[date_operation]<=" + endYear;

            double in = AppContext.getDbAdapter().getSumm(query);

            query = "SELECT sum(operations.[amount_operation]) as summ " +
                    "FROM operations, source " +
                    "WHERE operations.[sours_id]=source.[_id] " +
                    "and source.[tupe_id]=" + 2 +
                    " and operations.[date_operation]>=" + currentYear +
                    " and operations.[date_operation]<=" + endYear;

            double out = AppContext.getDbAdapter().getSumm(query);

            rio = new ReportInOut();
            rio.setDate(currentYear);
            rio.setSumInCome(in);
            rio.setSumOutCome(out);
            Log.d(TAG, "текущая дата" + Utils.getDate(rio.getDate()));
               /* Log.d(TAG, "доход + дата " + rio.getSumInCome() + " " + Utils.getDate(currentDay));
                Log.d(TAG, "расход + дата " + rio.getSumOutCome() + " " + Utils.getDate(currentDay));*/

            listSumYear.add(rio);

            //StringDateOperation stringDateOperation = StringDateOperation.getInstance();



            listOfOperationsYear.add(listSumYear);
        }


        return listOfOperationsYear;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<List<ReportCategory>> getReportCategorysWeek(int idOperation) {//список каждой недели в отдельном списке

        long dayGroup = 0;
        ReportCategory reportCategory;

        List<List<ReportCategory>> listOfcategoryReportWeeks = new ArrayList<>();
        List<ReportCategory> listSumWeeks;// = new ArrayList<>();
        String query;
        long currentDay = 0;

        //////////////////////////////
        for (long day : getDateOfDay(idOperation)) {
            Date nownow = new Date(day);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nownow);
            calendar.set(Calendar.HOUR_OF_DAY, 0);  // здесь часы, минуты, секунды
            calendar.set(Calendar.MINUTE, 0);       //и миллисекунды обнуляем, то есть
            calendar.set(Calendar.SECOND, 0);       //находим начало введенного дня
            calendar.set(Calendar.MILLISECOND, 0);

             /* Определение даты на начало текущей недели */
            Integer dayDifference = calendar.get(Calendar.DAY_OF_WEEK) == 1 ? -6 : (Calendar.MONDAY - calendar.get(Calendar.DAY_OF_WEEK));
            calendar.add(Calendar.DAY_OF_MONTH, dayDifference);
            // String dateFirstDayOfWeek = formattedDate.format(calendar.getTime()); //Дата на начало текущей недели
            //calendar.add(Calendar.DAY_OF_MONTH, -dayDifference);
            ///////////
            long begindWeek = calendar.getTimeInMillis(); // переводим полученную календарную
            // Log.d(TAG, "Начало недели = " + Utils.getDate(begindWeek));
            // дату в миллисекунды
            long endWeek = begindWeek + (1000 * 60 * 60 * 24 * 7); // получаем конец недели в миллисекундах
            // (прибавляем количество миллисекунд в сутках)
            // Log.d(TAG, "Конец недели = " + Utils.getDate(endWeek));
            calendar.setTimeInMillis(endWeek);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            long firstWeek = calendar.getTimeInMillis();

            ////////////////////////////////////////////////////
            query = "SELECT operations.[date_operation], source.[name_source], sum(operations.[amount_operation]) as summ " +
                    "FROM operations, source " +
                    "WHERE operations.[sours_id]=source.[_id] " +
                    "and source.[tupe_id]=" + idOperation +
                    " and operations.[date_operation]>=" + begindWeek +
                    " and operations.[date_operation]<" + endWeek +
                    " group by source.[name_source]";

            //StringDateOperation stringDateOperation = StringDateOperation.getInstance();

            if (dayGroup == 0) {
                dayGroup = begindWeek;
                listSumWeeks = AppContext.getDbAdapter().getReportCategory(query);
                // stringDateOperation.listWeek.add(Utils.getDate(begindWeek) + " - " + Utils.getDate(firstWeek));
            } else if (dayGroup == begindWeek) {
                continue;
            } else {
                dayGroup = begindWeek;
                listSumWeeks = AppContext.getDbAdapter().getReportCategory(query);
                //stringDateOperation.listWeek.add(Utils.getDate(begindWeek) + " - " + Utils.getDate(firstWeek));
            }

            listOfcategoryReportWeeks.add(listSumWeeks);
        }


        return listOfcategoryReportWeeks;
    }

    ///////////////////////////////////////////////////////////////////
    public static List<List<ReportCategory>> getReportCategorysMonth(int idOperation){//список каждого месяца в отдельном списке

        String orderBy;

        orderBy = " ORDER BY operations.[date_operation] DESC";
        long dayGroup = 0;

        List<List<ReportCategory>> listOfcategoryReportMonth = new ArrayList<>();
        List<ReportCategory> listSumMonth;
        String query;

        for(long day : getDateOfDay(idOperation)){

            // day - дата, которую вы вводите в миллисекундах

            Date nownow=new Date(day);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(nownow);
            calendar.set(Calendar.HOUR_OF_DAY, 0);  // здесь часы, минуты, секунды
            calendar.set(Calendar.MINUTE, 0);       //и миллисекунды обнуляем, то есть
            calendar.set(Calendar.SECOND, 0);       //находим начало введенного дня
            calendar.set(Calendar.MILLISECOND, 0);

            /* Определение даты на начало текущего месяца */
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            ///////////
            long begindMonth = calendar.getTimeInMillis(); // переводим полученную календарную
            // дату в миллисекунды
            // Log.d(TAG, "Начало месяца = " + Utils.getDate(begindMonth));
            int dayOfMonth = calendar.getActualMaximum(Calendar.DATE);//кол-во дней в месяце
            // Log.d(TAG, "Кол-во дней месяца = " + dayOfMonth);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth + 1);
            long endMonth = calendar.getTimeInMillis();//begindMonth +(1000 * 60 * 60 * 24 * dayOfMonth); // получаем конец недели в миллисекундах
            // (прибавляем количество миллисекунд в сутках)
            // Log.d(TAG, "Конец месяца = " + Utils.getDate(endMonth));

            ////////////////////////////////////////////////////
            query = "SELECT operations.[date_operation], source.[name_source], sum(operations.[amount_operation]) as summ " +
                    "FROM operations, source " +
                    "WHERE operations.[sours_id]=source.[_id] " +
                    "and source.[tupe_id]=" + idOperation +
                    " and operations.[date_operation]>=" + begindMonth +
                    " and operations.[date_operation]<=" + endMonth +
                    " group by source.[name_source]";

            //StringDateOperation stringDateOperation = StringDateOperation.getInstance();

            if(dayGroup == 0){
                dayGroup = begindMonth;
                listSumMonth = AppContext.getDbAdapter().getReportCategory(query);
                // stringDateOperation.listMonth.add(Utils.getDate(begindMonth) + " - " + Utils.getDate(endMonth));
            }else if(dayGroup == begindMonth){
                continue;
            }else{
                dayGroup = begindMonth;
                listSumMonth = AppContext.getDbAdapter().getReportCategory(query);
                //stringDateOperation.listMonth.add(Utils.getDate(begindMonth) + " - " + Utils.getDate(endMonth));
            }

            listOfcategoryReportMonth.add(listSumMonth);
        }


        return listOfcategoryReportMonth;
    }

    /////////////////////////////////////////////////////////////////////////
    public static List<List<ReportCategory>> getReportCategorysYear(int idOperation){//список каждого года в отдельном списке

        String orderBy;

        orderBy = " ORDER BY operations.[date_operation] DESC";
        long dayGroup = 0;

        List<List<ReportCategory>> listOfcategoryReportYear = new ArrayList<>();
        List<ReportCategory> listSumYear;
        String query;

        for(long day : getDateOfDay(idOperation)){

            // day - дата, которую вы вводите в миллисекундах

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
            Log.d(TAG, "Начало месяца = " + Utils.getDate(begindYear));
            // int dayOfMonth = calendar.getActualMaximum(Calendar.DATE);//кол-во дней в месяце
            //Log.d(TAG, "Кол-во дней месяца = " + dayOfMonth);
            //calendar.set(Calendar.DAY_OF_MONTH, 31);
            ///============================================
            int year = calendar.get(Calendar.YEAR)+1;
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            /////////////////////////////////////////////////////////======================
            /*calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.DAY_OF_MONTH, 31);*/
            /////////////////////////////////////////////////////////////=========================
            long endYear = calendar.getTimeInMillis();//begindMonth +(1000 * 60 * 60 * 24 * dayOfMonth); // получаем конец недели в миллисекундах
            // (прибавляем количество миллисекунд в сутках)
            Log.d(TAG, "Конец месяца = " + Utils.getDate(endYear));

            ////////////////////////////////////////////////////
            query = "SELECT operations.[date_operation], source.[name_source], sum(operations.[amount_operation]) as summ " +
                    "FROM operations, source " +
                    "WHERE operations.[sours_id]=source.[_id] " +
                    "and source.[tupe_id]=" + idOperation +
                    " and operations.[date_operation]>=" + begindYear +
                    " and operations.[date_operation]<" + endYear +
                    " group by source.[name_source]";

            //StringDateOperation stringDateOperation = StringDateOperation.getInstance();

            if(dayGroup == 0){
                dayGroup = begindYear;
                listSumYear = AppContext.getDbAdapter().getReportCategory(query);
                //stringDateOperation.listYear.add(Utils.getDate(begindYear) + " - " + Utils.getDate(endYear));
            }else if(dayGroup == begindYear){
                continue;
            }else{
                dayGroup = begindYear;
                listSumYear = AppContext.getDbAdapter().getReportCategory(query);
                //stringDateOperation.listYear.add(Utils.getDate(begindYear) + " - " + Utils.getDate(endYear));
            }

            listOfcategoryReportYear.add(listSumYear);
        }


        return listOfcategoryReportYear;
    }
}
