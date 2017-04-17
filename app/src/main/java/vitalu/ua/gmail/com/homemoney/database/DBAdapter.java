package vitalu.ua.gmail.com.homemoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.model.ReportCategory;
import vitalu.ua.gmail.com.homemoney.model.database_model.Currency;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.Review;
import vitalu.ua.gmail.com.homemoney.model.database_model.Source;
import vitalu.ua.gmail.com.homemoney.model.database_model.Storage;
import vitalu.ua.gmail.com.homemoney.model.database_model.TypeOperation;

/**
 * Created by Виталий on 28.12.2015.
 */
public class DBAdapter implements DBColumnConstants {

    public static final String TAG = "myLogMainActivity";

    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public DBAdapter(Context context)
    {
        this.context = context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getReadableDatabase();
    }

    public List<TypeOperation> getTypeOperation(String selection, String[] selectionArgs, String orderBy){
        List<TypeOperation> typeOperationList = new ArrayList<>();

        Cursor c = db.query(DBHelper.TYPE_OPERATION_TABLE, null, selection, selectionArgs, null, null, orderBy);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DBHelper.TYPE_OPERATION_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(DBHelper.TYPE_OPERATION_COLUMN_NAME));

                TypeOperation typeOperation = new TypeOperation(id, name);
                typeOperationList.add(typeOperation);
            } while (c.moveToNext());
        }
        c.close();

        return typeOperationList;
    }

    public List<Currency> getCurrency(String selection, String[] selectionArgs, String orderBy){
        List<Currency> currencyList = new ArrayList<>();

        Cursor c = db.query(DBHelper.CURRENCY_TABLE, null, selection,
                selectionArgs, null, null, orderBy);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DBHelper.CURRENCY_COLUMN_ID));
                String fullName = c.getString(c.getColumnIndex(DBHelper.CURRENCY_COLUMN_FULL_NAME));
                String shortName = c.getString(c.getColumnIndex(DBHelper.CURRENCY_COLUMN_SHORT_NAME));
                boolean visible =
                        c.getInt(c.getColumnIndex(DBHelper.CURRENCY_COLUMN_VISIBLE))==1?true:false;

                Currency currency = new Currency();
                currency.setId(id).setFullName(fullName).setShortName(shortName).setVisible(visible);
                currencyList.add(currency);
            } while (c.moveToNext());
        }
        c.close();

        return currencyList;
    }

    public long addCurrencyDB(ContentValues newValues){

        /*long l = db.insert(DBHelper.STORAGE_TABLE, null, newValues);
                Log.d(TAG, "long = " + l);*/
        return db.insert(DBHelper.CURRENCY_TABLE, null, newValues);
    }

    public List<Source> getSource(String query){
        /////////////////////////////////////////////////////////
        List<Source> sourceList = new ArrayList<>();
        Cursor cursorSource = db.rawQuery(query, null);

        if (cursorSource.moveToFirst()) {
            do {
                Source source = new Source();
                TypeOperation typeOperation = new TypeOperation();

                source.setId(cursorSource.getInt(cursorSource
                        .getColumnIndex(DBHelper.SOURCE_COLUMN_ID)));
                source.setNameSours(cursorSource.getString(cursorSource
                        .getColumnIndex(DBHelper.SOURCE_COLUMN_NAME)));
                source.setParentId(cursorSource.getInt(cursorSource
                        .getColumnIndex(DBHelper.SOURCE_COLUMN_PARENT)));
                typeOperation.setId(cursorSource.getInt(cursorSource
                        .getColumnIndex(DBHelper.SOURCE_COLUMN_TYPE)));
                source.setVisible(cursorSource.getInt(cursorSource
                        .getColumnIndex(DBHelper.SOURCE_COLUMN_VISIBLE)) == 1 ? true : false);
                typeOperation.setName(cursorSource.getString(cursorSource
                        .getColumnIndex(DBHelper.TYPE_OPERATION_COLUMN_NAME)));

                source.setType(typeOperation);

                sourceList.add(source);
            } while (cursorSource.moveToNext());
        }
        cursorSource.close();

        return sourceList;

    }

    public List<Storage> getStorages(String query){

        List<Storage> storageList = new ArrayList<>();
        Cursor cursorStorage = db.rawQuery(query, null);

        if (cursorStorage.moveToFirst()) {
            do {
                Storage storage = new Storage();
                Currency currency = new Currency();

                storage.setId(cursorStorage.getInt(cursorStorage
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_ID)));
                storage.setNameStorage(cursorStorage.getString(cursorStorage
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_NAME)));
                storage.setAmountStorage(cursorStorage.getDouble(cursorStorage
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_AMOUNT)));
                storage.setImageStorage(cursorStorage.getInt(cursorStorage
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_IMAGE)));
                storage.setVisible(cursorStorage.getInt(cursorStorage
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_VISIBLE)) == 1 ? true : false);
                currency.setId(cursorStorage.getInt(cursorStorage
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_CURRENCY)));
                currency.setFullName(cursorStorage.getString(cursorStorage
                        .getColumnIndex(DBHelper.CURRENCY_COLUMN_FULL_NAME)));
                currency.setShortName(cursorStorage.getString(cursorStorage
                        .getColumnIndex(DBHelper.CURRENCY_COLUMN_SHORT_NAME)));

                storage.setCurency(currency);

                storageList.add(storage);
            } while (cursorStorage.moveToNext());
        }
        cursorStorage.close();

        return storageList;
    }
    ////////////////////////////////////////////////////////////
    public long addStorageDB(ContentValues newValues){

        /*long l = db.insert(DBHelper.STORAGE_TABLE, null, newValues);
                Log.d(TAG, "long = " + l);*/
        return db.insert(DBHelper.STORAGE_TABLE, null, newValues);
    }

    public int updateStorage(int idStorage, ContentValues newValues){
        return db.update(DBHelper.STORAGE_TABLE, newValues,
                "_id = " + idStorage, null);
    }

    public int deleteStorage(int idStorage){
        return db.delete(DBHelper.STORAGE_TABLE, "_id = " + idStorage, null);
    }

    public int getCountRecordStorageForOperation(String queryOperation){//Возвращает кл-во
                                                                        // записей в Operation
        int countRecord = 0;                                            //по определенному кошельку
        Cursor cursorRecord = db.rawQuery(queryOperation, null);

        if (cursorRecord.moveToFirst()) {
            do {
                countRecord = cursorRecord.getInt(cursorRecord
                        .getColumnIndex("countrecord"));

            } while (cursorRecord.moveToNext());
        }
        cursorRecord.close();
        return countRecord;
    }

    ///////////////////////
    public int updateCurrency(int idCurrency, ContentValues newValues){
        return db.update(DBHelper.CURRENCY_TABLE, newValues,
                "_id = " + idCurrency, null);
    }

    public int deleteCurrency(int idCurrency){
        return db.delete(DBHelper.CURRENCY_TABLE, "_id = " + idCurrency, null);
    }

    public int getCountRecordCurrencyForStorage(String queryOperation){//Возвращает кл-во
        // записей
        int countRecord = 0;                                            //по определенному валюте
        Cursor cursorRecord = db.rawQuery(queryOperation, null);

        if (cursorRecord.moveToFirst()) {
            do {
                countRecord = cursorRecord.getInt(cursorRecord
                        .getColumnIndex("countrecord"));

            } while (cursorRecord.moveToNext());
        }
        cursorRecord.close();
        return countRecord;
    }

    ///////////////////////////////////////////////////////////
    public long addSourceDB(ContentValues newValues){

        /*long l = db.insert(DBHelper.STORAGE_TABLE, null, newValues);
                Log.d(TAG, "long = " + l);*/
        return db.insert(DBHelper.SOURCE_TABLE, null, newValues);
    }

    public int updateSource(int idSource, ContentValues newValues){
        return db.update(DBHelper.SOURCE_TABLE, newValues,
                "_id = " + idSource, null);
    }

    public int deleteSource(int idSource){
        return db.delete(DBHelper.SOURCE_TABLE, "_id = " + idSource, null);
    }

    public int getCountRecordSource(String queryOperation){//Возвращает кл-во

        int countRecord = 0;
        Cursor cursorRecord = db.rawQuery(queryOperation, null);

        if (cursorRecord.moveToFirst()) {
            do {
                countRecord = cursorRecord.getInt(cursorRecord
                        .getColumnIndex("countrecord"));

            } while (cursorRecord.moveToNext());
        }
        cursorRecord.close();
        return countRecord;
    }

    public int getCountRecordSourceForOperations(String queryOperation) {
        int countRecord = 0;                                            //по определенному валюте
        Cursor cursorRecord = db.rawQuery(queryOperation, null);

        if (cursorRecord.moveToFirst()) {
            do {
                countRecord = cursorRecord.getInt(cursorRecord
                        .getColumnIndex("countrecord"));

            } while (cursorRecord.moveToNext());
        }
        cursorRecord.close();
        return countRecord;
    }

    //////////////////////////////////////////////////////
    public long addOperationDB(ContentValues newValues){
        return db.insert(DBHelper.OPERATIONS_TABLE, null, newValues);
    }

    public int updateOperationDB(long idOperation, ContentValues newValues){
        return db.update(DBHelper.OPERATIONS_TABLE, newValues,
                "_id = " + idOperation, null);
    }

    public int deleteOperationDB(long idOperation){
        return db.delete(DBHelper.OPERATIONS_TABLE, "_id = " + idOperation, null);
    }

    public List<Long> getListDay(String query){//список дней операции

        List<Long> dayList = new ArrayList<>();

        long day;
        Cursor cursorDay = db.rawQuery(query, null);

        if (cursorDay.moveToFirst()) {
            do {
                day = cursorDay.getLong(cursorDay.getColumnIndex(DBHelper.OPERATIONS_COLUMN_DATE));

                dayList.add(day);
            } while (cursorDay.moveToNext());
        }
        cursorDay.close();

        return dayList;
    }

    public List<Operation> getListOperationsDays(String query){

        List<Operation> operationListDay = new ArrayList<>();
        Cursor cursorOperations = db.rawQuery(query, null);

        if (cursorOperations.moveToFirst()) {
            do {

                Operation operation = new Operation();
                Source source = new Source();
                Storage storage = new Storage();
                Currency currency = new Currency();
                TypeOperation type = new TypeOperation();

                operation.setId(cursorOperations.getInt(cursorOperations
                        .getColumnIndex(DBHelper.OPERATIONS_COLUMN_ID)));
                operation.setNameOperation(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.OPERATIONS_COLUMN_NAME)));
                operation.setAmount(cursorOperations.getDouble(cursorOperations
                        .getColumnIndex(DBHelper.OPERATIONS_COLUMN_AMOUNT)));
                operation.setDate(cursorOperations.getLong(cursorOperations
                        .getColumnIndex(DBHelper.OPERATIONS_COLUMN_DATE)));
                operation.setDescription(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.OPERATIONS_COLUMN_DESCRIPTION)));
                operation.setImagePath(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.OPERATIONS_COLUMN_IMAGE)));

                currency.setId(cursorOperations.getInt(cursorOperations
                        .getColumnIndex("currency_id")));
                currency.setFullName(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.CURRENCY_COLUMN_FULL_NAME)));
                currency.setShortName(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.CURRENCY_COLUMN_SHORT_NAME)));

                storage.setId(cursorOperations.getInt(cursorOperations
                        .getColumnIndex(DBHelper.OPERATIONS_COLUMN_STORAGE)));
                storage.setNameStorage(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_NAME)));
                storage.setAmountStorage(cursorOperations.getDouble(cursorOperations
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_AMOUNT)));
                storage.setImageStorage(cursorOperations.getInt(cursorOperations
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_IMAGE)));

                storage.setCurency(currency);

                type.setId(cursorOperations.getInt(cursorOperations
                        .getColumnIndex(DBHelper.SOURCE_COLUMN_TYPE)));

                source.setId(cursorOperations.getInt(cursorOperations
                        .getColumnIndex(DBHelper.OPERATIONS_COLUMN_SOURCE)));
                source.setNameSours(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.SOURCE_COLUMN_NAME)));

                source.setType(type);

                operation.setSoyrce(source);
                operation.setStorage(storage);

                operationListDay.add(operation);
            } while (cursorOperations.moveToNext());
        }
        cursorOperations.close();

        return operationListDay;
    }

    ////////////////////////////////////////////////////////////

    public long addTemplateDB(ContentValues newValues){
        return db.insert(DBHelper.PATTERN_TABLE, null, newValues);
    }

    public int deleteTemplateDB(long idOperation){
        return db.delete(DBHelper.PATTERN_TABLE, "_id = " + idOperation, null);
    }

    public int updateTemplateDB(long idOperation, ContentValues newValues){
        return db.update(DBHelper.PATTERN_TABLE, newValues,
                "_id = " + idOperation, null);
    }

    public List<Operation> getListTemplate(String query){

        List<Operation> operationListDay = new ArrayList<>();
        Cursor cursorOperations = db.rawQuery(query, null);

        if (cursorOperations.moveToFirst()) {
            do {

                Operation operation = new Operation();
                Source source = new Source();
                Storage storage = new Storage();
                Currency currency = new Currency();
                TypeOperation type = new TypeOperation();

                operation.setId(cursorOperations.getInt(cursorOperations
                        .getColumnIndex(DBHelper.PATTERN_COLUMN_ID)));
                operation.setNameOperation(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.PATTERN_COLUMN_NAME)));
                operation.setAmount(cursorOperations.getDouble(cursorOperations
                        .getColumnIndex(DBHelper.PATTERN_COLUMN_AMOUNT)));
                //operation.setDate(cursorOperations.getLong(cursorOperations
                  //      .getColumnIndex(DBHelper.OPERATIONS_COLUMN_DATE)));
                operation.setDescription(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.PATTERN_COLUMN_DESCRIPTION)));
                //operation.setImagePath(cursorOperations.getString(cursorOperations
                     //
                     //   .getColumnIndex(DBHelper.OPERATIONS_COLUMN_IMAGE)));

                currency.setId(cursorOperations.getInt(cursorOperations
                        .getColumnIndex("currency_id")));
                currency.setFullName(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.CURRENCY_COLUMN_FULL_NAME)));
                currency.setShortName(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.CURRENCY_COLUMN_SHORT_NAME)));

                storage.setId(cursorOperations.getInt(cursorOperations
                        .getColumnIndex(DBHelper.PATTERN_COLUMN_STORAGE)));
                storage.setNameStorage(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_NAME)));
                storage.setAmountStorage(cursorOperations.getDouble(cursorOperations
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_AMOUNT)));
                storage.setImageStorage(cursorOperations.getInt(cursorOperations
                        .getColumnIndex(DBHelper.STORAGE_COLUMN_IMAGE)));

                storage.setCurency(currency);

                type.setId(cursorOperations.getInt(cursorOperations
                        .getColumnIndex(DBHelper.SOURCE_COLUMN_TYPE)));

                source.setId(cursorOperations.getInt(cursorOperations
                        .getColumnIndex(DBHelper.PATTERN_COLUMN_SOURCE)));
                source.setNameSours(cursorOperations.getString(cursorOperations
                        .getColumnIndex(DBHelper.SOURCE_COLUMN_NAME)));

                source.setType(type);

                operation.setSoyrce(source);
                operation.setStorage(storage);

                operationListDay.add(operation);
            } while (cursorOperations.moveToNext());
        }
        cursorOperations.close();

        return operationListDay;
    }

    //////////////////////////////////////////////////////////////////////
    public double getSumm(String queryOperation){//
        // записей в Operation
        double summ = 0;
        Cursor cursorRecord = db.rawQuery(queryOperation, null);

        if (cursorRecord.moveToFirst()) {
            do {
                summ = cursorRecord.getLong(cursorRecord
                        .getColumnIndex("summ"));

            } while (cursorRecord.moveToNext());
        }
        cursorRecord.close();
        return summ;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    public List<ReportCategory> getReportCategory(String query){

        List<ReportCategory> reportList = new ArrayList<>();
        Cursor cursorReport = db.rawQuery(query, null);

        if (cursorReport.moveToFirst()) {
            do {
                ReportCategory reportCategory = new ReportCategory();

                reportCategory.setCategoryName(cursorReport.getString(cursorReport
                        .getColumnIndex(DBHelper.SOURCE_COLUMN_NAME)));

                reportCategory.setDate(cursorReport.getLong(cursorReport
                              .getColumnIndex(DBHelper.OPERATIONS_COLUMN_DATE)));

                reportCategory.setSumm(cursorReport.getLong(cursorReport
                                .getColumnIndex("summ")));

                reportList.add(reportCategory);
            } while (cursorReport.moveToNext());
        }
        cursorReport.close();

        return reportList;
    }

    ////////////////////////////////////////////////////////////////////////////////
    public long addReviewObjectDB(ContentValues newValues){

        /*long l = db.insert(DBHelper.STORAGE_TABLE, null, newValues);
                Log.d(TAG, "long = " + l);*/
        return db.insert(DBHelper.REVIEW_TABLE, null, newValues);
    }

    public List<Review> getParentReview(String query){
        /////////////////////////////////////////////////////////
        List<Review> reviewList = new ArrayList<>();
        Cursor cursorReview = db.rawQuery(query, null);

        if (cursorReview.moveToFirst()) {
            do {
                Review review = new Review();

                review.setId(cursorReview.getInt(cursorReview
                        .getColumnIndex(DBHelper.REVIEW_COLUMN_ID)));
                review.setNameReview(cursorReview.getString(cursorReview
                        .getColumnIndex(DBHelper.REVIEW_COLUMN_NAME)));
               /* review.setParent(cursorReview.getInt(cursorReview
                        .getColumnIndex(DBHelper.REVIEW_COLUMN_PARENT)));*/

                reviewList.add(review);
            } while (cursorReview.moveToNext());
        }
        cursorReview.close();

        return reviewList;

    }

    public List<Review> getReviewDB(String query){
        /////////////////////////////////////////////////////////
        List<Review> reviewList = new ArrayList<>();
        Cursor cursorReview = db.rawQuery(query, null);

        if (cursorReview.moveToFirst()) {
            do {
                Review review = new Review();

                review.setId(cursorReview.getInt(cursorReview
                        .getColumnIndex(DBHelper.ITEM_REVIEW_COLUMN_ID)));
                review.setAmountReview(cursorReview.getDouble(cursorReview
                        .getColumnIndex(DBHelper.ITEM_REVIEW_COLUMN_AMOUNT)));
                review.setDateReview(cursorReview.getLong(cursorReview
                        .getColumnIndex(DBHelper.ITEM_REVIEW_COLUMN_DATE)));
                review.setDescription(cursorReview.getString(cursorReview
                        .getColumnIndex(DBHelper.ITEM_REVIEW_COLUMN_DISCRIPTION)));
                review.setParent(cursorReview.getInt(cursorReview
                        .getColumnIndex(DBHelper.ITEM_REVIEW_COLUMN_ID_REVIEW)));
                review.setNameReview(cursorReview.getString(cursorReview
                        .getColumnIndex(DBHelper.REVIEW_COLUMN_NAME)));

                reviewList.add(review);
            } while (cursorReview.moveToNext());
        }
        cursorReview.close();

        return reviewList;
    }

    public long addReviewDB(ContentValues newValues){

        /*long l = db.insert(DBHelper.STORAGE_TABLE, null, newValues);
                Log.d(TAG, "long = " + l);*/
        return db.insert(DBHelper.ITEM_REVIEW_TABLE, null, newValues);
    }
}
