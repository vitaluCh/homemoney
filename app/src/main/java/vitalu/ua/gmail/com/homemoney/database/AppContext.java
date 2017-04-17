package vitalu.ua.gmail.com.homemoney.database;

import android.app.Application;

/**
 * Created by Виталий on 29.12.2015.
 */
public class AppContext extends Application {

    private static DBAdapter dbAdapter;

    @Override
    public void onCreate(){
        super.onCreate();
        dbAdapter = new DBAdapter(this);
    }

    public static DBAdapter getDbAdapter(){
        return dbAdapter;
    }
}
