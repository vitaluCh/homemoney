package vitalu.ua.gmail.com.homemoney.database.database_query;

import android.content.ContentValues;
import android.util.Log;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.database.AppContext;
import vitalu.ua.gmail.com.homemoney.database.DBHelper;
import vitalu.ua.gmail.com.homemoney.model.database_model.Storage;

/**
 * Created by Виталий on 13.01.2016.
 */
public class StorageQuery {

    public static final String TAG = "myLogMainActivity";

    private static final int DONT_VISIBLE_STORAGE = 0;
    private static final int VISIBLE_STORAGE = 1;

    public static List<Storage> getVisibleStorages(){//Вернуть активные кошельки

        String str =
                "select storage.[_id], storage.[name_storage], storage.[amount_storage], " +
                "storage.[currency_id], storage.[image_storage], storage.[visible_storage], " +
                "currency.[full_name_currency], currency.[short_name_currency] " +
                "from storage, currency " +
                "where storage.[currency_id]=currency.[_id] and storage.[visible_storage]=1";

        return AppContext.getDbAdapter().getStorages(str);
    }

    public static List<Storage> getAllStorages(){//Вернуть все кошельки

        String str =
                "select storage.[_id], storage.[name_storage], storage.[amount_storage], " +
                        "storage.[currency_id], storage.[image_storage], storage.[visible_storage], " +
                        "currency.[full_name_currency], currency.[short_name_currency] " +
                        "from storage, currency " +
                        "where storage.[currency_id]=currency.[_id]";

        return AppContext.getDbAdapter().getStorages(str);
    }

    public static long addStorage(Storage storage){//Создать новый кошелек

        Log.d(TAG, "addStorage");

        ContentValues newValues = new ContentValues();

        newValues.put(DBHelper.STORAGE_COLUMN_NAME, storage.getNameStorage());
        newValues.put(DBHelper.STORAGE_COLUMN_AMOUNT, storage.getAmountStorage());
        newValues.put(DBHelper.STORAGE_COLUMN_CURRENCY, storage.getCurency().getId());
        newValues.put(DBHelper.STORAGE_COLUMN_IMAGE, storage.getImageStorage());
        newValues.put(DBHelper.STORAGE_COLUMN_VISIBLE, VISIBLE_STORAGE);

        return AppContext.getDbAdapter().addStorageDB(newValues);
    }

    public static int removeStorage(Storage storage){//Удаление кошелька если в Operation
                                                        //есть записи о кошельке то изменим поле
        String queryOperation =                         //видимости, иначе удалим
                "SELECT COUNT(*) as countrecord "
                        + "FROM operations, storage "
                        + "where operations.[storage_id]=storage.[_id] and storage.[_id]="
                        + storage.getId();
        int countRecord = AppContext.getDbAdapter()
                .getCountRecordStorageForOperation(queryOperation);

        if(countRecord == 0){
            return AppContext.getDbAdapter().deleteStorage(storage.getId());
        }else{
            return updateStorage(storage, DONT_VISIBLE_STORAGE);
        }

    }

    public static void removeStorage(List<Integer> listIdStorage){

    }

    public static int updateStorage(Storage storage){
        return updateStorage(storage, VISIBLE_STORAGE);
    }

    private static int updateStorage(Storage storage, int visible){

        ContentValues newValues = new ContentValues();

        newValues.put(DBHelper.STORAGE_COLUMN_NAME, storage.getNameStorage());
        newValues.put(DBHelper.STORAGE_COLUMN_AMOUNT, storage.getAmountStorage());
        newValues.put(DBHelper.STORAGE_COLUMN_CURRENCY, storage.getCurency().getId());
        newValues.put(DBHelper.STORAGE_COLUMN_IMAGE, storage.getImageStorage());
        newValues.put(DBHelper.STORAGE_COLUMN_VISIBLE, visible);

        return AppContext.getDbAdapter().updateStorage(storage.getId(), newValues);
    }

    public static int updateStorageAmount(Storage storage){

        ContentValues newValues = new ContentValues();

        newValues.put(DBHelper.STORAGE_COLUMN_AMOUNT, storage.getAmountStorage());

        return AppContext.getDbAdapter().updateStorage(storage.getId(), newValues);
    }

}
