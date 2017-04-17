package vitalu.ua.gmail.com.homemoney.utils;

/**
 * Created by Виталий on 06.02.2016.
 */
public class CurrentStateOperation {

    private static int mIdOperation = 0;
    private static CurrentStateOperation instance = null;

    public static CurrentStateOperation getInstance(){

        if(instance == null){
            instance = new CurrentStateOperation();
        }

        return instance;
    }

    public static void setIdOperation(int idOperation){
        mIdOperation = idOperation;
    }

    public static int getIdOperation(){
        return mIdOperation;
    }
}
