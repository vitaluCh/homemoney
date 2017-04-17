package vitalu.ua.gmail.com.homemoney.database.database_query;

import android.content.ContentValues;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.database.AppContext;
import vitalu.ua.gmail.com.homemoney.database.DBHelper;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;

/**
 * Created by Виталий on 16.02.2016.
 */
public class PatternQuery {

    public static long addTemplate(Operation operation){

        ContentValues newContent = new ContentValues();

        //Log.d(TAG, "getItem = " + operation.getDate());

        newContent.put(DBHelper.PATTERN_COLUMN_NAME, operation.getNameOperation());
        newContent.put(DBHelper.PATTERN_COLUMN_AMOUNT, operation.getAmount());
        //newContent.put(DBHelper.OPERATIONS_COLUMN_DATE, operation.getDate());
        newContent.put(DBHelper.PATTERN_COLUMN_SOURCE, operation.getSoyrce().getId());
        newContent.put(DBHelper.PATTERN_COLUMN_STORAGE, operation.getStorage().getId());
        newContent.put(DBHelper.PATTERN_COLUMN_DESCRIPTION, operation.getDescription());
       //newContent.put(DBHelper.OPERATIONS_COLUMN_IMAGE, operation.getImagePath());

        /*if(operation.getSoyrce().getType().getId() == OPERATION_INCOM){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() + operation.getAmount());

            //StorageQuery.updateStorageAmount(operation.getStorage());
        }else if(operation.getSoyrce().getType().getId() == OPERATION_OUTCOM){
            operation.getStorage()
                    .setAmountStorage(operation.getStorage()
                            .getAmountStorage() - operation.getAmount());
        }

        StorageQuery.updateStorageAmount(operation.getStorage());*/

        return AppContext.getDbAdapter().addTemplateDB(newContent);
    }

    public static List<Operation> getTemplate(int idOperation){//список для одного дня

        String query;

        ////////////////////////////////////////////////////
        query = "SELECT patterns.[_id], patterns.[amount_pattern], " +
                "patterns.[description_pattern], " +
                "patterns.[name_pattern], patterns.[sours_id], " +
                "patterns.[storage_id], source.[tupe_id], " +
                "source.[name_source], storage.[image_storage], " +
                "storage.[name_storage], storage.[amount_storage], " +
                "currency.[_id] as [currency_id], currency.[short_name_currency], " +
                "currency.[full_name_currency] " +
                "FROM patterns, source, storage, currency " +
                "WHERE patterns.[sours_id]=source.[_id] " +
                "and currency.[_id]=storage.[currency_id] " +
                "and patterns.[storage_id]=storage.[_id] " +
                "and source.[tupe_id]=" + idOperation +
                " ORDER BY patterns.[_id] DESC";

        return AppContext.getDbAdapter().getListTemplate(query);
    }

    public static long deleteTemplate(Operation operation){
        return AppContext.getDbAdapter().deleteTemplateDB(operation.getId());
    }

    public static long updateTemplate(Operation operation){

        ContentValues newContent = new ContentValues();

        //Log.d(TAG, "getItem = " + operation.getDate());

        newContent.put(DBHelper.PATTERN_COLUMN_NAME, operation.getNameOperation());
        newContent.put(DBHelper.PATTERN_COLUMN_AMOUNT, operation.getAmount());
        //newContent.put(DBHelper.OPERATIONS_COLUMN_DATE, operation.getDate());
        newContent.put(DBHelper.PATTERN_COLUMN_SOURCE, operation.getSoyrce().getId());
        newContent.put(DBHelper.PATTERN_COLUMN_STORAGE, operation.getStorage().getId());
        newContent.put(DBHelper.PATTERN_COLUMN_DESCRIPTION, operation.getDescription());


        return AppContext.getDbAdapter().updateTemplateDB(operation.getId(), newContent);
    }
}
