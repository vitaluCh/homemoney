package vitalu.ua.gmail.com.homemoney.database;

/**
 * Created by Виталий on 28.12.2015.
 */
public interface DBColumnConstants {

    // поля таблицы операций
    public static final String OPERATIONS_COLUMN_ID = "_id";
    public static final String OPERATIONS_COLUMN_NAME = "name_operation";
    public static final String OPERATIONS_COLUMN_AMOUNT = "amount_operation";
    public static final String OPERATIONS_COLUMN_DATE = "date_operation";
    public static final String OPERATIONS_COLUMN_SOURCE = "sours_id";
    public static final String OPERATIONS_COLUMN_STORAGE = "storage_id";
    public static final String OPERATIONS_COLUMN_DESCRIPTION = "description_operation";
    public static final String OPERATIONS_COLUMN_IMAGE = "image_operation";

    //поля таблицы тип_операции
    public static final String TYPE_OPERATION_COLUMN_ID = "_id";
    public static final String TYPE_OPERATION_COLUMN_NAME = "name_type_operation";

    //поля таблицы валюта
    public static final String CURRENCY_COLUMN_ID = "_id";
    public static final String CURRENCY_COLUMN_FULL_NAME = "full_name_currency";
    public static final String CURRENCY_COLUMN_SHORT_NAME = "short_name_currency";
    public static final String CURRENCY_COLUMN_VISIBLE = "visible_currency";

    //поля таблицы источника
    public static final String SOURCE_COLUMN_ID = "_id";
    public static final String SOURCE_COLUMN_NAME = "name_source";
    public static final String SOURCE_COLUMN_TYPE = "tupe_id";
    public static final String SOURCE_COLUMN_PARENT = "parent_id";
    public static final String SOURCE_COLUMN_VISIBLE = "visible_source";

    //поля таблицы хранилеще
    public static final String STORAGE_COLUMN_ID = "_id";
    public static final String STORAGE_COLUMN_NAME = "name_storage";
    public static final String STORAGE_COLUMN_AMOUNT = "amount_storage";
    public static final String STORAGE_COLUMN_CURRENCY = "currency_id";
    public static final String STORAGE_COLUMN_IMAGE = "image_storage";
    public static final String STORAGE_COLUMN_VISIBLE = "visible_storage";

    //поля таблицы шаблон
    public static final String PATTERN_COLUMN_ID = "_id";
    public static final String PATTERN_COLUMN_SOURCE = "sours_id";
    public static final String PATTERN_COLUMN_AMOUNT= "amount_pattern";
    public static final String PATTERN_COLUMN_STORAGE = "storage_id";
    public static final String PATTERN_COLUMN_NAME = "name_pattern";
    public static final String PATTERN_COLUMN_DESCRIPTION = "description_pattern";

    // поля таблицы задач
    public static final String TASK__COLUMN_ID = "_id";
    public static final String TASK_TITLE_COLUMN = "task_title";
    public static final String TASK_DATE_COLUMN = "task_date";
    public static final String TASK_PRIORITY_COLUMN = "task_priority";
    public static final String TASK_STATUS_COLUMN = "task_status";
    public static final String TASK_TIME_STAMP_COLUMN = "task_time_stamp";

    // поля таблицы анализа
    public static final String REVIEW_COLUMN_ID = "_id";
    public static final String REVIEW_COLUMN_NAME = "name_review";
    public static final String REVIEW_COLUMN_PARENT = "parent_id";
   // public static final String REVIEW_COLUMN_DISCRIPTION = "discription";

    // поля таблицы анализа
    public static final String ITEM_REVIEW_COLUMN_ID = "_id";
    public static final String ITEM_REVIEW_COLUMN_ID_REVIEW = "review_id";
    public static final String ITEM_REVIEW_COLUMN_AMOUNT = "amount_review";
    public static final String ITEM_REVIEW_COLUMN_DATE = "date_review";
    public static final String ITEM_REVIEW_COLUMN_DISCRIPTION = "discription";

}
