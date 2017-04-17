package vitalu.ua.gmail.com.homemoney.model.database_model;

/**
 * Created by Виталий on 28.12.2015.
 */
public class TypeOperation {

    private int mId;
    private String mNameTypeOperation;

    public TypeOperation() {
    }

    public TypeOperation(int mId, String mName) {
        this.mId = mId;
        this.mNameTypeOperation = mName;
    }

    public int getId() {
        return mId;
    }

    public TypeOperation setId(int mId) {
        this.mId = mId;
        return this;
    }

    public String getName() {
        return mNameTypeOperation;
    }

    public TypeOperation setName(String mName) {
        this.mNameTypeOperation = mName;
        return this;
    }
}
