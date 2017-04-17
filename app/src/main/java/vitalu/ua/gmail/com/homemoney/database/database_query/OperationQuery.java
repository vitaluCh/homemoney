package vitalu.ua.gmail.com.homemoney.database.database_query;

import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.database.AppContext;
import vitalu.ua.gmail.com.homemoney.database.DBHelper;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.Storage;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * Created by Виталий on 02.02.2016.
 */
public class OperationQuery {

    public static final String TAG = "myLogMainActivity";

    public static final int OPERATION_INCOM = 1;
    public static final int OPERATION_OUTCOM = 2;
    public static final int OPERATION_TRANSFER = 3;

    public static final int TO_BORROW = 4;//взять в долг
    public static final int TO_LEND = 5;//дать в долг

    public static long addOperationDebt(Operation operation, int debt){//debt - взять или дать в долг

        ContentValues newContent = new ContentValues();

        Log.d(TAG, "getItem = " + operation.getDate());

        newContent.put(DBHelper.OPERATIONS_COLUMN_NAME, operation.getNameOperation());
        newContent.put(DBHelper.OPERATIONS_COLUMN_AMOUNT, operation.getAmount());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DATE, operation.getDate());
        newContent.put(DBHelper.OPERATIONS_COLUMN_SOURCE, operation.getSoyrce().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_STORAGE, operation.getStorage().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DESCRIPTION, operation.getDescription());
        newContent.put(DBHelper.OPERATIONS_COLUMN_IMAGE, operation.getImagePath());

        if(debt == TO_BORROW){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() + operation.getAmount());

            //StorageQuery.updateStorageAmount(operation.getStorage());
        }else if(debt == TO_LEND){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() - operation.getAmount());
        }

        StorageQuery.updateStorageAmount(operation.getStorage());

        return AppContext.getDbAdapter().addOperationDB(newContent);
    }

    //StringDateOperation stringDateOperation = StringDateOperation.getInstance();
    public static long addOperationTransfer(Operation operation, int transfer){//transfer - от куда и куда

        ContentValues newContent = new ContentValues();

        Log.d(TAG, "getItem = " + operation.getDate());

        newContent.put(DBHelper.OPERATIONS_COLUMN_NAME, operation.getNameOperation());
        newContent.put(DBHelper.OPERATIONS_COLUMN_AMOUNT, operation.getAmount());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DATE, operation.getDate());
        newContent.put(DBHelper.OPERATIONS_COLUMN_SOURCE, operation.getSoyrce().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_STORAGE, operation.getStorage().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DESCRIPTION, operation.getDescription());
        newContent.put(DBHelper.OPERATIONS_COLUMN_IMAGE, operation.getImagePath());

        if(transfer == OPERATION_INCOM){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() + operation.getAmount());

            //StorageQuery.updateStorageAmount(operation.getStorage());
        }else if(transfer == OPERATION_OUTCOM){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() - operation.getAmount());
        }

        StorageQuery.updateStorageAmount(operation.getStorage());

        return AppContext.getDbAdapter().addOperationDB(newContent);
    }
    ///////////////////////////////////////////////////////////////////////

    public static long addOperation(Operation operation){

        ContentValues newContent = new ContentValues();

        Log.d(TAG, "getItem = " + operation.getDate());

        newContent.put(DBHelper.OPERATIONS_COLUMN_NAME, operation.getNameOperation());
        newContent.put(DBHelper.OPERATIONS_COLUMN_AMOUNT, operation.getAmount());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DATE, operation.getDate());
        newContent.put(DBHelper.OPERATIONS_COLUMN_SOURCE, operation.getSoyrce().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_STORAGE, operation.getStorage().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DESCRIPTION, operation.getDescription());
        newContent.put(DBHelper.OPERATIONS_COLUMN_IMAGE, operation.getImagePath());

        if(operation.getSoyrce().getType().getId() == OPERATION_INCOM){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() + operation.getAmount());

            //StorageQuery.updateStorageAmount(operation.getStorage());
        }else if(operation.getSoyrce().getType().getId() == OPERATION_OUTCOM){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() - operation.getAmount());
        }

        StorageQuery.updateStorageAmount(operation.getStorage());

        return AppContext.getDbAdapter().addOperationDB(newContent);
    }

    /////////////////////////////////////////////
    public static long deleteOperation(Operation operation){

        if(operation.getSoyrce().getType().getId() == OPERATION_INCOM){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() - operation.getAmount());

            //StorageQuery.updateStorageAmount(operation.getStorage());
        }else if(operation.getSoyrce().getType().getId() == OPERATION_OUTCOM){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() + operation.getAmount());
        }

        StorageQuery.updateStorageAmount(operation.getStorage());

        return AppContext.getDbAdapter().deleteOperationDB(operation.getId());
    }

    public static int updateTransferOperation(Operation operation, double oldAmount, Storage oldStorage, int transfer){//transfer - от куда и куда
        ContentValues newContent = new ContentValues();

        Log.d(TAG, "getItem = " + operation.getDate());

        newContent.put(DBHelper.OPERATIONS_COLUMN_NAME, operation.getNameOperation());
        newContent.put(DBHelper.OPERATIONS_COLUMN_AMOUNT, operation.getAmount());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DATE, operation.getDate());
        newContent.put(DBHelper.OPERATIONS_COLUMN_SOURCE, operation.getSoyrce().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_STORAGE, operation.getStorage().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DESCRIPTION, operation.getDescription());
        newContent.put(DBHelper.OPERATIONS_COLUMN_IMAGE, operation.getImagePath());

        if(operation.getStorage().getId() != oldStorage.getId()){
            if(transfer == OPERATION_INCOM){
                oldStorage.setAmountStorage(oldStorage
                        .getAmountStorage() - oldAmount);

                operation.getStorage()
                        .setAmountStorage(operation.getStorage()
                                .getAmountStorage() + operation.getAmount());

            }else if(transfer == OPERATION_OUTCOM){
                oldStorage.setAmountStorage(oldStorage
                        .getAmountStorage() + oldAmount);

                operation.getStorage()
                        .setAmountStorage(operation.getStorage()
                                .getAmountStorage() - operation.getAmount());
            }

            StorageQuery.updateStorageAmount(oldStorage);
            StorageQuery.updateStorageAmount(operation.getStorage());
        }else {
            if(transfer == OPERATION_INCOM) {
                if (operation.getAmount() > oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() + (operation.getAmount() - oldAmount));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                } else if (operation.getAmount() < oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() - (oldAmount - operation.getAmount()));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                }
            }else if(transfer == OPERATION_OUTCOM) {
                if (operation.getAmount() > oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() - (operation.getAmount() - oldAmount));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                } else if (operation.getAmount() < oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() + (oldAmount - operation.getAmount()));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                }
            }
        }


        return AppContext.getDbAdapter().updateOperationDB(operation.getId(), newContent);
    }

    public static int updateOperation(Operation operation, double oldAmount, Storage oldStorage){


        ContentValues newContent = new ContentValues();

        Log.d(TAG, "getItem = " + operation.getDate());

        newContent.put(DBHelper.OPERATIONS_COLUMN_NAME, operation.getNameOperation());
        newContent.put(DBHelper.OPERATIONS_COLUMN_AMOUNT, operation.getAmount());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DATE, operation.getDate());
        newContent.put(DBHelper.OPERATIONS_COLUMN_SOURCE, operation.getSoyrce().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_STORAGE, operation.getStorage().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DESCRIPTION, operation.getDescription());
        newContent.put(DBHelper.OPERATIONS_COLUMN_IMAGE, operation.getImagePath());

        if(operation.getStorage().getId() != oldStorage.getId()){
            if(operation.getSoyrce().getType().getId() == OPERATION_INCOM){
                oldStorage.setAmountStorage(oldStorage
                        .getAmountStorage() - oldAmount);

                operation.getStorage()
                        .setAmountStorage(operation.getStorage()
                                .getAmountStorage() + operation.getAmount());

            }else if(operation.getSoyrce().getType().getId() == OPERATION_OUTCOM){
                oldStorage.setAmountStorage(oldStorage
                        .getAmountStorage() + oldAmount);

                operation.getStorage()
                        .setAmountStorage(operation.getStorage()
                                .getAmountStorage() - operation.getAmount());
            }

            StorageQuery.updateStorageAmount(oldStorage);
            StorageQuery.updateStorageAmount(operation.getStorage());
        }else {
            if(operation.getSoyrce().getType().getId() == OPERATION_INCOM) {
                if (operation.getAmount() > oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() + (operation.getAmount() - oldAmount));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                } else if (operation.getAmount() < oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() - (oldAmount - operation.getAmount()));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                }
            }else if(operation.getSoyrce().getType().getId() == OPERATION_OUTCOM) {
                if (operation.getAmount() > oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() - (operation.getAmount() - oldAmount));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                } else if (operation.getAmount() < oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() + (oldAmount - operation.getAmount()));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                }
            }
        }


        return AppContext.getDbAdapter().updateOperationDB(operation.getId(), newContent);
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

    public static List<List<Operation>> getDaysOfDay(int idOperation){//список каждого дня в отдельном списке

        String orderBy;

        orderBy = " ORDER BY operations.[date_operation] DESC";

        long dayGroup = 0;

        List<List<Operation>> listOfListOperationsDays = new ArrayList<>();
        List<Operation> listOperationDays = new ArrayList<>();
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
            long beginday=calendar.getTimeInMillis(); // переводим полученную календарную
                                                        // дату в миллисекунды
            long nextday=beginday+(1000*60*60*24); // получаем следующий день в миллисекундах
                                                    // (прибавляем количество миллисекунд в сутках)

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
                    " and operations.[date_operation]>=" + beginday +
                    " and operations.[date_operation]<" + nextday +
                    orderBy;

            if(dayGroup == 0){
                dayGroup = beginday;
                listOperationDays = AppContext.getDbAdapter().getListOperationsDays(query);

            }else if(dayGroup == beginday){
                continue;
            }else{
                dayGroup = beginday;
                listOperationDays = AppContext.getDbAdapter().getListOperationsDays(query);
            }

            listOfListOperationsDays.add(listOperationDays);
        }

        return listOfListOperationsDays;
    }

    public static List<Operation> getAllOneDay(int position, int idOperation){//список одного дня

        List<List<Operation>> listOfListOperationsDays = getDaysOfDay(idOperation);
        return listOfListOperationsDays.get(position);
    }

    //////////////////////////////////////
    public static List<Operation> getOperationsDay(long day, int idOperation){//список для одного дня

        String orderBy;
        orderBy = " ORDER BY operations.[date_operation] DESC";

        String query;

        Date nownow=new Date(day);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(nownow);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  // здесь часы, минуты, секунды
        calendar.set(Calendar.MINUTE, 0);       //и миллисекунды обнуляем, то есть
        calendar.set(Calendar.SECOND, 0);       //находим начало введенного дня
        calendar.set(Calendar.MILLISECOND, 0);
        long beginday=calendar.getTimeInMillis(); // переводим полученную календарную
        // дату в миллисекунды
        long nextday=beginday+(1000*60*60*24); // получаем следующий день в миллисекундах
        // (прибавляем количество миллисекунд в сутках)

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
                " and operations.[date_operation]>=" + beginday +
                " and operations.[date_operation]<" + nextday +
                orderBy;

        return AppContext.getDbAdapter().getListOperationsDays(query);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////


    public static List<List<Operation>> getListOperationsWeek(int idOperation){//список каждой недели в отдельном списке

        String orderBy;

        orderBy = " ORDER BY operations.[date_operation] DESC";

        long dayGroup = 0;

        List<List<Operation>> listOfListOperationsWeeks = new ArrayList<>();
        List<Operation> listOperationWeeks = new ArrayList<>();
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

            //StringDateOperation stringDateOperation = StringDateOperation.getInstance();

            if(dayGroup == 0){
                dayGroup = begindWeek;
                listOperationWeeks = AppContext.getDbAdapter().getListOperationsDays(query);
               // stringDateOperation.listWeek.add(Utils.getDate(begindWeek) + " - " + Utils.getDate(firstWeek));
            }else if(dayGroup == begindWeek){
                continue;
            }else{
                dayGroup = begindWeek;
                listOperationWeeks = AppContext.getDbAdapter().getListOperationsDays(query);
                //stringDateOperation.listWeek.add(Utils.getDate(begindWeek) + " - " + Utils.getDate(firstWeek));
            }

            listOfListOperationsWeeks.add(listOperationWeeks);
        }


        return listOfListOperationsWeeks;
    }

    public static List<Operation> getListWeek(int position, int idOperation){//список одной недели

        List<List<Operation>> listOfListOperationsWeek = getListOperationsWeek(idOperation);
        if(listOfListOperationsWeek.isEmpty())return null;
        return listOfListOperationsWeek.get(position);
    }

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

    //////////////////////////////////////////////////////////////////////////////

    public static List<List<Operation>> getListOperationsMonth(int idOperation){//список каждого месяца в отдельном списке

        String orderBy;

        orderBy = " ORDER BY operations.[date_operation] DESC";
        long dayGroup = 0;

        List<List<Operation>> listOfListOperationsMonth = new ArrayList<>();
        List<Operation> listOperationMonth = new ArrayList<>();
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
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth+1);
            long endMonth = calendar.getTimeInMillis();//begindMonth +(1000 * 60 * 60 * 24 * dayOfMonth); // получаем конец недели в миллисекундах
            // (прибавляем количество миллисекунд в сутках)
           // Log.d(TAG, "Конец месяца = " + Utils.getDate(endMonth));

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
                    " and operations.[date_operation]>=" + begindMonth +
                    " and operations.[date_operation]<" + endMonth +
                    orderBy;

            //StringDateOperation stringDateOperation = StringDateOperation.getInstance();

            if(dayGroup == 0){
                dayGroup = begindMonth;
                listOperationMonth = AppContext.getDbAdapter().getListOperationsDays(query);
               // stringDateOperation.listMonth.add(Utils.getDate(begindMonth) + " - " + Utils.getDate(endMonth));
            }else if(dayGroup == begindMonth){
                continue;
            }else{
                dayGroup = begindMonth;
                listOperationMonth = AppContext.getDbAdapter().getListOperationsDays(query);
                //stringDateOperation.listMonth.add(Utils.getDate(begindMonth) + " - " + Utils.getDate(endMonth));
            }

            listOfListOperationsMonth.add(listOperationMonth);
        }


        return listOfListOperationsMonth;
    }

    public static List<Operation> getListMonth(int position, int idOperation){//список одноо месяца

        List<List<Operation>> listOfListOperationsMonth = getListOperationsMonth(idOperation);
        return listOfListOperationsMonth.get(position);
    }

    /////////////////////////////////////////////////////////////////////////

    public static List<Operation> getOperationsMonth(long day, int idOperation){//список по одному месяцу

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

            /* Определение даты на начало текущего месяца */
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        ///////////
        long begindMonth = calendar.getTimeInMillis(); // переводим полученную календарную
        // дату в миллисекунды
        // Log.d(TAG, "Начало месяца = " + Utils.getDate(begindMonth));
        int dayOfMonth = calendar.getActualMaximum(Calendar.DATE);//кол-во дней в месяце
        // Log.d(TAG, "Кол-во дней месяца = " + dayOfMonth);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth+1);
        long endMonth = calendar.getTimeInMillis();//begindMonth +(1000 * 60 * 60 * 24 * dayOfMonth); // получаем конец недели в миллисекундах
        // (прибавляем количество миллисекунд в сутках)
        // Log.d(TAG, "Конец месяца = " + Utils.getDate(endMonth));

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
                " and operations.[date_operation]>=" + begindMonth +
                " and operations.[date_operation]<" + endMonth +
                orderBy;

        return AppContext.getDbAdapter().getListOperationsDays(query);
    }
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    public static List<List<Operation>> getListOperationsYear(int idOperation){//список каждого года в отдельном списке

        String orderBy;

        orderBy = " ORDER BY operations.[date_operation] DESC";
        long dayGroup = 0;

        List<List<Operation>> listOfListOperationsYear = new ArrayList<>();
        List<Operation> listOperationYear = new ArrayList<>();
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

            int year = calendar.get(Calendar.YEAR)+1;
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
           /* calendar.set(Calendar.DAY_OF_MONTH, 31);
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);*/
            long endYear = calendar.getTimeInMillis();//begindMonth +(1000 * 60 * 60 * 24 * dayOfMonth); // получаем конец недели в миллисекундах
            // (прибавляем количество миллисекунд в сутках)
            Log.d(TAG, "Конец месяца = " + Utils.getDate(endYear));

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
                    " and operations.[date_operation]>=" + begindYear +
                    " and operations.[date_operation]<" + endYear +
                    orderBy;

            //StringDateOperation stringDateOperation = StringDateOperation.getInstance();

            if(dayGroup == 0){
                dayGroup = begindYear;
                listOperationYear = AppContext.getDbAdapter().getListOperationsDays(query);
                //stringDateOperation.listYear.add(Utils.getDate(begindYear) + " - " + Utils.getDate(endYear));
            }else if(dayGroup == begindYear){
                continue;
            }else{
                dayGroup = begindYear;
                listOperationYear = AppContext.getDbAdapter().getListOperationsDays(query);
                //stringDateOperation.listYear.add(Utils.getDate(begindYear) + " - " + Utils.getDate(endYear));
            }

            listOfListOperationsYear.add(listOperationYear);
        }


        return listOfListOperationsYear;
    }

    public static List<Operation> getListYear(int position, int idOperation){//список одной недели

        List<List<Operation>> listOfListOperationsYear = getListOperationsYear(idOperation);
        if(listOfListOperationsYear.isEmpty())return null;
        return listOfListOperationsYear.get(position);
    }

    public static List<Operation> getOperationsYear(long day, int idOperation){

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
       /* calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);*/
        long endYear = calendar.getTimeInMillis();//begindMonth +(1000 * 60 * 60 * 24 * dayOfMonth); // получаем конец недели в миллисекундах
        // (прибавляем количество миллисекунд в сутках)
        Log.d(TAG, "Конец месяца = " + Utils.getDate(endYear));

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
                " and operations.[date_operation]>=" + begindYear +
                " and operations.[date_operation]<" + endYear +
                orderBy;

        return AppContext.getDbAdapter().getListOperationsDays(query);
    }

    ///////////////////////////////////////////////////////////////////////////////
    public static List<Operation> getDebtOperations(){//

        String query;

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
                "and source.[tupe_id]=4 " +
                "ORDER BY operations.[date_operation] DESC";

        List<Operation> list = AppContext.getDbAdapter().getListOperationsDays(query);

        query = "SELECT operations.[_id], operations.[amount_operation], operations.[date_operation], " +
                "operations.[description_operation], operations.[image_operation], " +
                "operations.[name_operation], operations.[sours_id], operations.[storage_id], source.[tupe_id], " +
                "source.[name_source], storage.[image_storage], storage.[name_storage], storage.[amount_storage], " +
                "currency.[_id] as [currency_id], currency.[short_name_currency], currency.[full_name_currency] " +
                "FROM operations, source, storage, currency " +
                "WHERE operations.[sours_id]=source.[_id] " +
                "and currency.[_id]=storage.[currency_id] " +
                "and operations.[storage_id]=storage.[_id] " +
                "and source.[tupe_id]=5 " +
                "ORDER BY operations.[date_operation] DESC";
       list.addAll(AppContext.getDbAdapter().getListOperationsDays(query));

        Collections.sort(list, new Comparator<Operation>() {
            @Override
            public int compare(Operation lhs, Operation rhs) {
                return (int)(rhs.getDate() - lhs.getDate());
            }
        });
        return list;
    }

    public static long deleteDebt(Operation operation){

        if(operation.getSoyrce().getType().getId() == TO_BORROW){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() - operation.getAmount());

            //StorageQuery.updateStorageAmount(operation.getStorage());
        }else if(operation.getSoyrce().getType().getId() == TO_LEND){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() + operation.getAmount());
        }

        StorageQuery.updateStorageAmount(operation.getStorage());

        return AppContext.getDbAdapter().deleteOperationDB(operation.getId());
    }

    ////////////////////////////////////////////////////////////////////////////////////

    public static int updateDebt(Operation operation, double oldAmount, Storage oldStorage){


        ContentValues newContent = new ContentValues();

        Log.d(TAG, "getItem = " + operation.getDate());

        newContent.put(DBHelper.OPERATIONS_COLUMN_NAME, operation.getNameOperation());
        newContent.put(DBHelper.OPERATIONS_COLUMN_AMOUNT, operation.getAmount());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DATE, operation.getDate());
        newContent.put(DBHelper.OPERATIONS_COLUMN_SOURCE, operation.getSoyrce().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_STORAGE, operation.getStorage().getId());
        newContent.put(DBHelper.OPERATIONS_COLUMN_DESCRIPTION, operation.getDescription());
        newContent.put(DBHelper.OPERATIONS_COLUMN_IMAGE, operation.getImagePath());

        if(operation.getStorage().getId() != oldStorage.getId()){
            if(operation.getSoyrce().getType().getId() == TO_BORROW){
                oldStorage.setAmountStorage(oldStorage
                        .getAmountStorage() - oldAmount);

                operation.getStorage()
                        .setAmountStorage(operation.getStorage()
                                .getAmountStorage() + operation.getAmount());

            }else if(operation.getSoyrce().getType().getId() == TO_LEND){
                oldStorage.setAmountStorage(oldStorage
                        .getAmountStorage() + oldAmount);

                operation.getStorage()
                        .setAmountStorage(operation.getStorage()
                                .getAmountStorage() - operation.getAmount());
            }

            StorageQuery.updateStorageAmount(oldStorage);
            StorageQuery.updateStorageAmount(operation.getStorage());
        }else {
            if(operation.getSoyrce().getType().getId() == TO_BORROW) {
                if (operation.getAmount() > oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() + (operation.getAmount() - oldAmount));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                } else if (operation.getAmount() < oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() - (oldAmount - operation.getAmount()));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                }
            }else if(operation.getSoyrce().getType().getId() == TO_LEND) {
                if (operation.getAmount() > oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() - (operation.getAmount() - oldAmount));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                } else if (operation.getAmount() < oldAmount) {
                    operation.getStorage()
                            .setAmountStorage(operation.getStorage()
                                    .getAmountStorage() + (oldAmount - operation.getAmount()));

                    StorageQuery.updateStorageAmount(operation.getStorage());
                }
            }
        }


        return AppContext.getDbAdapter().updateOperationDB(operation.getId(), newContent);
    }
}
