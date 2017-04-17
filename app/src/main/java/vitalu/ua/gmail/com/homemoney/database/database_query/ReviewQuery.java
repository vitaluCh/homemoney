package vitalu.ua.gmail.com.homemoney.database.database_query;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vitalu.ua.gmail.com.homemoney.database.AppContext;
import vitalu.ua.gmail.com.homemoney.database.DBHelper;
import vitalu.ua.gmail.com.homemoney.model.database_model.Review;

/**
 * Created by Виталий on 02.03.2016.
 */
public class ReviewQuery {

    public static final String GROUP_NAME = "groupName";
    public static final String CHILD_NAME = "childName";

    public static long addReviewObject(Review review){
        ContentValues newContent = new ContentValues();

        newContent.put(DBHelper.REVIEW_COLUMN_NAME, review.getNameReview());
        if(review.getParent() > 0)
            newContent.put(DBHelper.REVIEW_COLUMN_PARENT, review.getParent());

        return AppContext.getDbAdapter().addReviewObjectDB(newContent);
    }

    public static List<Review> getParent(){
        String query =
                "SELECT review.[_id], review.[name_review], review.[parent_id] " +
                        "FROM review " +
                        "WHERE review.[parent_id] is NULL " +
                        "ORDER BY review.[name_review]";

        return AppContext.getDbAdapter().getParentReview(query);
    }

    public static List<Review> getReviewParent(int id){
        String query =
                "SELECT review.[_id], review.[name_review], review.[parent_id] " +
                        "FROM review " +
                        "WHERE review.[_id]=" + id +
                        " ORDER BY review.[name_review]";

        return AppContext.getDbAdapter().getParentReview(query);
    }

    public static List<Review> getChild(int id){
        String query =
                "SELECT review.[_id], review.[name_review], review.[parent_id] " +
                        "FROM review " +
                        "WHERE review.[parent_id]=" + id +
                        " ORDER BY review.[name_review]";

        return AppContext.getDbAdapter().getParentReview(query);
    }

    public static ArrayList<Map<String, Review>> getParentData(){/////////////////1
        return getGroupData(getParent());
    }

    public static ArrayList<Map<String, Review>> getGroupData(List<Review> reviewList){///////////2

        // коллекция для групп
        ArrayList<Map<String, Review>> groupData = new ArrayList<>();

        // список аттрибутов группы
        Map<String, Review> m;

        // заполняем коллекцию групп из массива с названиями групп
        for (Review group : reviewList) {
            // заполняем список аттрибутов для каждой группы
            m = new HashMap<>();
            m.put(GROUP_NAME, group); // имя группы
            groupData.add(m);
        }

        return groupData;
    }

    public static ArrayList<ArrayList<Map<String, Review>>> getChildData() {//////1c

        return getReviewChild(getParent());
    }

    private static ArrayList<ArrayList<Map<String, Review>>> getReviewChild(List<Review> listParent) {////2c

        // коллекция для элементов одной группы
        ArrayList<Map<String, Review>> childDataItem;

        // общая коллекция для коллекций элементов
        ArrayList<ArrayList<Map<String, Review>>> childData = new ArrayList<>();
        // в итоге получится childData = ArrayList<childDataItem>

        // список аттрибутов элемента
        Map<String, Review> m;

        for(Review review : listParent){
            // создаем коллекцию элементов для первой группы
            childDataItem = new ArrayList<Map<String, Review>>();
            // заполняем список аттрибутов для каждого элемента
            for (Review sourceChild : getChild(review.getId())) {
                m = new HashMap<String, Review>();
                m.put(CHILD_NAME, sourceChild); // название подгруппы
                childDataItem.add(m);
            }
            childData.add(childDataItem);
        }
        return childData;
    }

    public static List<Review> getReview(int id){
        String query =
                "SELECT item_review.[_id], item_review.[amount_review], " +
                        "item_review.[date_review], item_review.[discription], " +
                        "item_review.[review_id], review.[name_review] " +
                        "FROM item_review, review " +
                        "WHERE item_review.[review_id]=review.[_id] and item_review.[review_id]=" + id +
                        " ORDER BY item_review.[date_review] DESC";

        return AppContext.getDbAdapter().getReviewDB(query);
    }

    public static long addReview(Review review){
        ContentValues newContent = new ContentValues();

        newContent.put(DBHelper.ITEM_REVIEW_COLUMN_AMOUNT, review.getAmountReview());
        newContent.put(DBHelper.ITEM_REVIEW_COLUMN_DATE, review.getDateReview());
        newContent.put(DBHelper.ITEM_REVIEW_COLUMN_DISCRIPTION, review.getDescription());
        newContent.put(DBHelper.ITEM_REVIEW_COLUMN_ID_REVIEW, review.getParent());

        return AppContext.getDbAdapter().addReviewDB(newContent);
    }
}
