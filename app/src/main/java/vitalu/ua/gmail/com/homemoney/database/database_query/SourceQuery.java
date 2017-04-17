package vitalu.ua.gmail.com.homemoney.database.database_query;

import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vitalu.ua.gmail.com.homemoney.database.AppContext;
import vitalu.ua.gmail.com.homemoney.database.DBHelper;
import vitalu.ua.gmail.com.homemoney.model.database_model.Source;

/**
 * Created by Виталий on 18.01.2016.
 */
public class SourceQuery {
    public static final String TAG = "myLogMainActivity";

    private static final int DONT_VISIBLE_CURRENCY = 0;
    private static final int VISIBLE_CURRENCY = 1;

    public static final String GROUP_NAME = "groupName";
    public static final String CHILD_NAME = "childName";

    public static List<Source> getSourceDebt(){
        String query =
                "select source.[_id], source.[name_source], source.[parent_id], source.[tupe_id], " +
                        "source.[visible_source], type_operation.[name_type_operation] " +
                        "from source, type_operation " +
                        "where source.[tupe_id]=type_operation.[_id] and " +
                        "source.[parent_id] is NULL and source.[visible_source]=1 " +
                        "and source.[tupe_id]=5 or source.[tupe_id]=4" +
                " GROUP BY source.[name_source]";


        return AppContext.getDbAdapter().getSource(query);
    }

    public static List<Source> getSourceTransfer(){
        String query =
                "select source.[_id], source.[name_source], source.[parent_id], source.[tupe_id], " +
                        "source.[visible_source], type_operation.[name_type_operation] " +
                        "from source, type_operation " +
                        "where source.[tupe_id]=type_operation.[_id] and " +
                        "source.[parent_id] is NULL and source.[visible_source]=1 and source.[tupe_id]=3";


        return AppContext.getDbAdapter().getSource(query);
    }

    public static List<Source> getVisibleInComeSourceParent(){///////////////3
        String query =
                "select source.[_id], source.[name_source], source.[parent_id], source.[tupe_id], " +
                        "source.[visible_source], type_operation.[name_type_operation] " +
                        "from source, type_operation " +
                        "where source.[tupe_id]=type_operation.[_id] and " +
                        "source.[parent_id] is NULL and source.[visible_source]=1 and source.[tupe_id]=1" +
                        " ORDER BY source.[name_source]";

        return AppContext.getDbAdapter().getSource(query);
    }

    public static List<Source> getVisibleOutComeSourceParent(){
        String query =
                "select source.[_id], source.[name_source], source.[parent_id], source.[tupe_id], " +
                        "source.[visible_source], type_operation.[name_type_operation] " +
                        "from source, type_operation " +
                        "where source.[tupe_id]=type_operation.[_id] and " +
                        "source.[parent_id] is NULL and source.[visible_source]=1 and source.[tupe_id]=2" +
                        " ORDER BY source.[name_source]";

        return AppContext.getDbAdapter().getSource(query);
    }

    public static List<Source> getVisibleSourceChild(int id){
        String query =
                "select source.[_id], source.[name_source], source.[parent_id], " +
                        "source.[tupe_id], source.[visible_source], " +
                        "type_operation.[name_type_operation]" +
                        "from source, type_operation " +
                        "where source.[tupe_id]=type_operation.[_id] and source.[parent_id]=" + id +
                        " ORDER BY source.[name_source]";

        return AppContext.getDbAdapter().getSource(query);
    }
    public static List<Source> getVisibleSourceParent(int id){
        String query =
                "select source.[_id], source.[name_source], source.[parent_id], source.[tupe_id], source.[visible_source], type_operation.[name_type_operation] " +
                        "from source, type_operation " +
                        "where source.[tupe_id]=type_operation.[_id] and source.[visible_source]=1 and source.[_id]=" + id
                        + " ORDER BY source.[name_source]";

        return AppContext.getDbAdapter().getSource(query);
    }

    public static ArrayList<Map<String, Source>> getVisibleOutComeParentData(){
        return getVisibleGroupData(getVisibleOutComeSourceParent());
    }

    public static ArrayList<Map<String, Source>> getVisibleInComeParentData(){/////////////////1
        return getVisibleGroupData(getVisibleInComeSourceParent());
    }

    public static ArrayList<Map<String, Source>> getVisibleGroupData(List<Source> sourceList){///////////2

        // коллекция для групп
        ArrayList<Map<String, Source>> groupData = new ArrayList<>();

        // список аттрибутов группы
        Map<String, Source> m;

        // заполняем коллекцию групп из массива с названиями групп
        for (Source group : sourceList) {
            // заполняем список аттрибутов для каждой группы
            m = new HashMap<>();
            m.put(GROUP_NAME, group); // имя группы
            groupData.add(m);
        }

        return groupData;
    }

    public static ArrayList<ArrayList<Map<String, Source>>> getVisibleOutComeSourceChild() {

        return getVisibleSourceChild(getVisibleOutComeSourceParent());
    }

    public static ArrayList<ArrayList<Map<String, Source>>> getVisibleInComeSourceChild() {//////1c

        return getVisibleSourceChild(getVisibleInComeSourceParent());
    }

    private static ArrayList<ArrayList<Map<String, Source>>> getVisibleSourceChild(List<Source> listParent) {////2c

        // коллекция для элементов одной группы
        ArrayList<Map<String, Source>> childDataItem;

        // общая коллекция для коллекций элементов
        ArrayList<ArrayList<Map<String, Source>>> childData = new ArrayList<>();
        // в итоге получится childData = ArrayList<childDataItem>

        // список аттрибутов элемента
        Map<String, Source> m;

        for(Source source : listParent){
            // создаем коллекцию элементов для первой группы
            childDataItem = new ArrayList<Map<String, Source>>();
            // заполняем список аттрибутов для каждого элемента
            for (Source sourceChild : getVisibleSourceChild(source.getId())) {
                m = new HashMap<String, Source>();
                m.put(CHILD_NAME, sourceChild); // название подгруппы
                childDataItem.add(m);
            }
            childData.add(childDataItem);
        }
        return childData;
    }

    public static long addSource(Source source){
        ContentValues newContent = new ContentValues();

        newContent.put(DBHelper.SOURCE_COLUMN_NAME, source.getNameSours());
        newContent.put(DBHelper.SOURCE_COLUMN_TYPE, source.getType().getId());
        if(source.getParentId()>0)
            newContent.put(DBHelper.SOURCE_COLUMN_PARENT, source.getParentId());
        newContent.put(DBHelper.SOURCE_COLUMN_VISIBLE, VISIBLE_CURRENCY);

        return AppContext.getDbAdapter().addSourceDB(newContent);
    }

    //////////////////////////////////////////////////////////

    public static int removeSource(Source source){//Удаление кошелька если в Operation
        //есть записи о кошельке то изменим поле
        String queryOperation =                         //видимости, иначе удалим
                "SELECT COUNT(*) as countrecord " +
                        "FROM operations, source " +
                        "where operations.[sours_id]=source.[_id] and source.[_id]="
                        + source.getId();
        int countRecord = AppContext.getDbAdapter()
                .getCountRecordSourceForOperations(queryOperation);

        if(countRecord == 0){
            return AppContext.getDbAdapter().deleteSource(source.getId());
        }else{
            return updateSource(source, DONT_VISIBLE_CURRENCY);
        }

    }

    public static int updateSource(Source source){
        return updateSource(source, VISIBLE_CURRENCY);
    }

    private static int updateSource(Source source, int visible){

        ContentValues newValues = new ContentValues();

        Log.d(TAG, source.getNameSours());
        Log.d(TAG, String.valueOf(source.getId()));
        Log.d(TAG, "В SourceQuery ------------" + String.valueOf(source.getParentId()));

        newValues.put(DBHelper.SOURCE_COLUMN_NAME, source.getNameSours());
        if(source.getParentId() > 0) {
            newValues.put(DBHelper.SOURCE_COLUMN_PARENT, source.getParentId());
        }else{
            newValues.put(DBHelper.SOURCE_COLUMN_PARENT, (byte[]) null);
        }
        newValues.put(DBHelper.SOURCE_COLUMN_TYPE, source.getType().getId());
        newValues.put(DBHelper.SOURCE_COLUMN_VISIBLE, visible);

        return AppContext.getDbAdapter().updateSource(source.getId(), newValues);
    }
}
