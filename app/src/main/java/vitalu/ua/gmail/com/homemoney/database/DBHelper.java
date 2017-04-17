package vitalu.ua.gmail.com.homemoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vitalu.ua.gmail.com.homemoney.R;

/**
 * Created by Виталий on 28.12.2015.
 */
public class DBHelper extends SQLiteOpenHelper implements DBColumnConstants{

    private static final String DB_NAME = "home_money.db"; // имя БД
    private static final int DB_VERSION = 1; // версия БД

    Context context;
    ContentValues cv;

    // имя таблицы операций
    public static final String OPERATIONS_TABLE = "operations";
    // имя таблицы типа операций
    public static final String TYPE_OPERATION_TABLE = "type_operation";
    // имя таблицы валюта
    public static final String CURRENCY_TABLE = "currency";
    // имя таблицы источник
    public static final String SOURCE_TABLE = "source";
    // имя таблицы хранилище
    public static final String STORAGE_TABLE = "storage";
    // имя таблицы шаблон
    public static final String PATTERN_TABLE = "patterns";
    // имя таблицы задач
    public static final String TASK_TABLE = "task";
    // имя таблицы анализа
    public static final String REVIEW_TABLE = "review";
    public static final String ITEM_REVIEW_TABLE = "item_review";

    //запрос создания таблицы операций
    private static final String OPERATIONS_TABLE_CREATE = "create table "
            + OPERATIONS_TABLE + "("
            + OPERATIONS_COLUMN_ID + " integer primary key AUTOINCREMENT, "
            + OPERATIONS_COLUMN_NAME + " text not null, "
            + OPERATIONS_COLUMN_AMOUNT + " double not null, "
            + OPERATIONS_COLUMN_DATE + " long not null, "
            + OPERATIONS_COLUMN_SOURCE + " integer not null, "
            + OPERATIONS_COLUMN_STORAGE + " integer not null, "
            + OPERATIONS_COLUMN_DESCRIPTION + " text, "
            + OPERATIONS_COLUMN_IMAGE +" text" + ");";

    //запрос создания таблицы типа операций
    private static final String TYPE_OPERATIONS_TABLE_CREATE = "create table "
            + TYPE_OPERATION_TABLE + "("
            + TYPE_OPERATION_COLUMN_ID + " integer primary key AUTOINCREMENT, "
            + TYPE_OPERATION_COLUMN_NAME + " text not null" + ");";

    //запрос создания таблицы валют
    private static final String CURRENCY_TABLE_CREATE = "create table "
            + CURRENCY_TABLE + "("
            + CURRENCY_COLUMN_ID + " integer primary key AUTOINCREMENT, "
            + CURRENCY_COLUMN_FULL_NAME + " text not null, "
            + CURRENCY_COLUMN_SHORT_NAME + " text not null, "
            + CURRENCY_COLUMN_VISIBLE + " integer" + ");";

    //запрос создания таблицы источник
    private static final String SOURCE_TABLE_CREATE = "create table "
            + SOURCE_TABLE + "("
            + SOURCE_COLUMN_ID + " integer primary key AUTOINCREMENT, "
            + SOURCE_COLUMN_NAME + " text not null, "
            + SOURCE_COLUMN_TYPE + " integer not null, "
            + SOURCE_COLUMN_PARENT + " integer, "
            + SOURCE_COLUMN_VISIBLE + " integer" + ");";

    //запрос создания таблицы хранилище
    private static final String STORAGE_TABLE_CREATE = "create table "
            + STORAGE_TABLE + "("
            + STORAGE_COLUMN_ID + " integer primary key AUTOINCREMENT, "
            + STORAGE_COLUMN_NAME + " text not null, "
            + STORAGE_COLUMN_AMOUNT + " double not null, "
            + STORAGE_COLUMN_CURRENCY + " integer not null, "
            + STORAGE_COLUMN_IMAGE + " integer not null, "
            + STORAGE_COLUMN_VISIBLE + " integer" + ");";

    //запрос создания таблицы шаблон
    private static final String PATTERN_TABLE_CREATE = "create table "
            + PATTERN_TABLE + "("
            + PATTERN_COLUMN_ID + " integer primary key AUTOINCREMENT, "
            + PATTERN_COLUMN_NAME + " text not null, "
            + PATTERN_COLUMN_AMOUNT + " double not null, "
            + PATTERN_COLUMN_STORAGE + " integer not null, "
            + PATTERN_COLUMN_SOURCE + " integer not null, "
            + PATTERN_COLUMN_DESCRIPTION + " text" + ");";

    //запрос создания таблицы задач
    private static final String TASK_TABLE_CREATE = "CREATE TABLE "
            + TASK_TABLE + " ("
            + TASK__COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK_TITLE_COLUMN + " TEXT NOT NULL, "
            + TASK_DATE_COLUMN + " LONG, "
            + TASK_PRIORITY_COLUMN + " INTEGER, "
            + TASK_STATUS_COLUMN + " INTEGER, "
            + TASK_TIME_STAMP_COLUMN + " LONG);";

    //запрос создания таблицы анализа
    private static final String REVIEW_TABLE_CREATE = "create table "
            + REVIEW_TABLE + "("
            + REVIEW_COLUMN_ID + " integer primary key AUTOINCREMENT, "
            + REVIEW_COLUMN_NAME + " text not null, "
            + REVIEW_COLUMN_PARENT + " integer"
            + ");";
    ////////////////////////////////////////////////////////////////
    //запрос создания таблицы анализа
    private static final String ITEM_REVIEW_TABLE_CREATE = "create table "
            + ITEM_REVIEW_TABLE + "("
            + ITEM_REVIEW_COLUMN_ID + " integer primary key AUTOINCREMENT, "
            + ITEM_REVIEW_COLUMN_ID_REVIEW + " integer not null, "
            + ITEM_REVIEW_COLUMN_AMOUNT + " double not null, "
            + ITEM_REVIEW_COLUMN_DATE + " long not null, "
            + ITEM_REVIEW_COLUMN_DISCRIPTION + " text" + ");";
    ////////////////////////////////////////////////////////////////

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //создание и заполнение таблицы тип операцин
        db.execSQL(TYPE_OPERATIONS_TABLE_CREATE);
        fillTypeOperations(db);
        //создание и заполнение таблицы валют
        db.execSQL(CURRENCY_TABLE_CREATE);
        fillCurrency(db);
        //создание и заполнение таблицы источник
        db.execSQL(SOURCE_TABLE_CREATE);
        fillSource(db);

        db.execSQL(OPERATIONS_TABLE_CREATE);//создание таблицы операций
        db.execSQL(STORAGE_TABLE_CREATE);//создание таблицы хранилище
        db.execSQL(PATTERN_TABLE_CREATE);//создание таблицы шаблонов

        db.execSQL(TASK_TABLE_CREATE);//создание таблицы задач

        db.execSQL(REVIEW_TABLE_CREATE);//создание таблицы анализ
        db.execSQL(ITEM_REVIEW_TABLE_CREATE);//создание таблицы анализ
    }

    /**
     *
     * Метод заполняет таблицу источник строковыми ресурсами
     */
    private void fillSource(SQLiteDatabase db) {
        cv = new ContentValues();
        //доход зарплата
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_incom_pay));
        cv.put(SOURCE_COLUMN_TYPE, 1);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);
        //доход подарок
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_incom_gift));
        cv.put(SOURCE_COLUMN_TYPE, 1);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);
        //доход другое
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_incom_other));
        cv.put(SOURCE_COLUMN_TYPE, 1);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        //расход машина
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_car));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_car_insurance));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 4);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_car_tax));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 4);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_car_service));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 4);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_car_buy));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 4);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_car_repair));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 4);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_car_fuel));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 4);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Банк
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_bank));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_bank_commission));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 11);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_bank_services));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 11);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_bank_percent));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 11);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Дом
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_home));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_home_furniture));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 15);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_home_cookware));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 15);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_home_renovation));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 15);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_home_electricaldevice));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 15);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Домашние животные
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_homeanimals));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_homeanimals_food));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 20);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Другое
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_other));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Друзья
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_friend));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Еда
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_food));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_food_bread));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 24);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_food_milk));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 24);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_food_butter));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 24);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Коммунальные услуги
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices_water));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 28);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices_garbage));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 28);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices_gas));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 28);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices_groundtax));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 28);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices_tv));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 28);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices_rent));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 28);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices_heating));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 28);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices_insurance));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 28);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices_phone));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 28);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices_electrican));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 28);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_municipalservices_internet));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 28);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Красота и Здоровье
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_beauty_and_health));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_beauty_and_health_dantist));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 40);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_beauty_and_health_cosmetic));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 40);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_beauty_and_health_treatment));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 40);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_beauty_and_health_barbershop));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 40);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_beauty_and_health_sauna));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 40);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_beauty_and_health_lifeinsurance));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 40);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_beauty_and_health_healthinsurance));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 40);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_beauty_and_health_massage));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 40);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Кредиты
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_credit));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_credit_car));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 49);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_credit_house));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 49);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Личное
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_personal));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_personal_charity));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 52);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_personal_gift));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 52);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Образование
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_education));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        //расход Одежда
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_clothes));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        //расход Отдых
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_relax));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_relax_hotel));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 57);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_relax_trip));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 57);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_relax_restaurant));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 57);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_relax_cafe));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 57);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Развлечения
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_amusement));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_amusement_cinema));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 62);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_amusement_club));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 62);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_amusement_concert));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 62);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Семья
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_famely));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        //расход Спорт
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_sport));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_sport_pool));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 67);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_sport_gym));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 67);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Транспорт
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_transport));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_transport_rent));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 70);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_transport_public_transport));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 70);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_transport_train));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 70);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_transport_plane));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 70);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_transport_taxi));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 70);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.clear();
        //расход Услуги
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_amenities));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);

        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_outcom_amenities_mobile_communication));
        cv.put(SOURCE_COLUMN_TYPE, 2);
        cv.put(SOURCE_COLUMN_PARENT, 76);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);
        cv.clear();

        //Перевод
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_remittance));
        cv.put(SOURCE_COLUMN_TYPE, 3);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);
        cv.clear();
        //Взять в долг
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_toborrow));
        cv.put(SOURCE_COLUMN_TYPE, 4);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);
        cv.clear();
        //Дать в долг
        cv.put(SOURCE_COLUMN_NAME, context.getString(R.string.db_tolend));
        cv.put(SOURCE_COLUMN_TYPE, 5);
        cv.put(SOURCE_COLUMN_VISIBLE, 1);
        db.insert(SOURCE_TABLE, null, cv);
        cv.clear();
    }

    /**
     *
     * заполнение таблицы тип операцин
     */
    private void fillTypeOperations(SQLiteDatabase db){
        cv = new ContentValues();
        cv.put(TYPE_OPERATION_COLUMN_NAME, context.getString(R.string.db_incom));
        db.insert(TYPE_OPERATION_TABLE, null, cv);

        cv.put(TYPE_OPERATION_COLUMN_NAME, context.getString(R.string.db_outcom));
        db.insert(TYPE_OPERATION_TABLE, null, cv);

        cv.put(TYPE_OPERATION_COLUMN_NAME, context.getString(R.string.db_remittance));
        db.insert(TYPE_OPERATION_TABLE, null, cv);

        cv.put(TYPE_OPERATION_COLUMN_NAME, context.getString(R.string.db_toborrow));
        db.insert(TYPE_OPERATION_TABLE, null, cv);

        cv.put(TYPE_OPERATION_COLUMN_NAME, context.getString(R.string.db_tolend));
        db.insert(TYPE_OPERATION_TABLE, null, cv);
    }

    /**
     *
     * Метод заполняет таблицу валют строковыми ресурсами
     */
    private void fillCurrency(SQLiteDatabase db) {
        cv = new ContentValues();
        cv.put(CURRENCY_COLUMN_FULL_NAME, context.getString(R.string.db_currency_uah));
        cv.put(CURRENCY_COLUMN_SHORT_NAME, "UAH");
        cv.put(CURRENCY_COLUMN_VISIBLE, 1);
        db.insert(CURRENCY_TABLE, null, cv);

        cv.put(CURRENCY_COLUMN_FULL_NAME, context.getString(R.string.db_currency_usd));
        cv.put(CURRENCY_COLUMN_SHORT_NAME, "USD");
        cv.put(CURRENCY_COLUMN_VISIBLE, 1);
        db.insert(CURRENCY_TABLE, null, cv);

        cv.put(CURRENCY_COLUMN_FULL_NAME, context.getString(R.string.db_currency_eur));
        cv.put(CURRENCY_COLUMN_SHORT_NAME, "EUR");
        cv.put(CURRENCY_COLUMN_VISIBLE, 1);
        db.insert(CURRENCY_TABLE, null, cv);

        cv.put(CURRENCY_COLUMN_FULL_NAME, context.getString(R.string.db_currency_rub));
        cv.put(CURRENCY_COLUMN_SHORT_NAME, "RUB");
        cv.put(CURRENCY_COLUMN_VISIBLE, 1);
        db.insert(CURRENCY_TABLE, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}