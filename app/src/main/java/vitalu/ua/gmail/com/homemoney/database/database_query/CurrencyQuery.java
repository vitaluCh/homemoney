package vitalu.ua.gmail.com.homemoney.database.database_query;

import android.content.ContentValues;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.database.AppContext;
import vitalu.ua.gmail.com.homemoney.database.DBHelper;
import vitalu.ua.gmail.com.homemoney.model.database_model.Currency;

/**
 * Created by Виталий on 13.01.2016.
 */
public class CurrencyQuery {

    private static final int DONT_VISIBLE_CURRENCY = 0;
    private static final int VISIBLE_CURRENCY = 1;

    public static List<Currency> getCurrencys(){

        String selection = DBHelper.CURRENCY_COLUMN_VISIBLE + "=1";
        return AppContext.getDbAdapter().getCurrency(selection, null, null);
    }

    public static Currency getCurrency(int idCurrency){
        String selection = DBHelper.CURRENCY_COLUMN_ID + "=" + idCurrency;
        return AppContext.getDbAdapter().getCurrency(selection, null, null).get(0);
    }

    public static long addCurrency(Currency currency){
        ContentValues newContent = new ContentValues();

        newContent.put(DBHelper.CURRENCY_COLUMN_FULL_NAME, currency.getFullName());
        newContent.put(DBHelper.CURRENCY_COLUMN_SHORT_NAME, currency.getShortName());
        newContent.put(DBHelper.CURRENCY_COLUMN_VISIBLE, VISIBLE_CURRENCY);

        return AppContext.getDbAdapter().addCurrencyDB(newContent);
    }

    public static int removeCurrency(Currency currency){//Удаление кошелька если в Operation
        //есть записи о кошельке то изменим поле
        String queryOperation =                         //видимости, иначе удалим
                "SELECT COUNT(*) as countrecord " +
                        "FROM storage, currency " +
                        "where storage.[currency_id]=currency.[_id] and currency.[_id]="
                        + currency.getId();
        int countRecord = AppContext.getDbAdapter()
                .getCountRecordCurrencyForStorage(queryOperation);

        if(countRecord == 0){
            return AppContext.getDbAdapter().deleteCurrency(currency.getId());
        }else{
            return updateCurrency(currency, DONT_VISIBLE_CURRENCY);
        }

    }

    public static int updateCurrency(Currency currency){
        return updateCurrency(currency, VISIBLE_CURRENCY);
    }

    private static int updateCurrency(Currency currency, int visible){

        ContentValues newValues = new ContentValues();

        newValues.put(DBHelper.CURRENCY_COLUMN_FULL_NAME, currency.getFullName());
        newValues.put(DBHelper.CURRENCY_COLUMN_SHORT_NAME, currency.getShortName());
        newValues.put(DBHelper.CURRENCY_COLUMN_VISIBLE, visible);

        return AppContext.getDbAdapter().updateCurrency(currency.getId(), newValues);
    }
}
