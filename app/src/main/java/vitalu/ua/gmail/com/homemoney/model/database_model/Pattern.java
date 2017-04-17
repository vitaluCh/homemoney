package vitalu.ua.gmail.com.homemoney.model.database_model;

/**
 * Created by Виталий on 28.12.2015.
 */
public class Pattern {

    int mId;
    String mNamePattern;
    Source mSource;
    double mAmountPattern;
    Storage mStorage;
    String mDescription;

    public Pattern() {
    }

    public Pattern(int mId, String mNamePattern, Source mSource, double mAmountPattern, Storage mStorage, String mDescription) {
        this.mId = mId;
        this.mNamePattern = mNamePattern;
        this.mSource = mSource;
        this.mAmountPattern = mAmountPattern;
        this.mStorage = mStorage;
        this.mDescription = mDescription;
    }

    public int getId() {
        return mId;
    }

    public Pattern setId(int mId) {
        this.mId = mId;
        return this;
    }

    public String getNamePattern() {
        return mNamePattern;
    }

    public Pattern setNamePattern(String mNamePattern) {
        this.mNamePattern = mNamePattern;
        return this;
    }

    public Source getSource() {
        return mSource;
    }

    public Pattern setSource(Source mSource) {
        this.mSource = mSource;
        return this;
    }

    public double getAmountPattern() {
        return mAmountPattern;
    }

    public Pattern setAmountPattern(double mAmountPattern) {
        this.mAmountPattern = mAmountPattern;
        return this;
    }

    public Storage getStorage() {
        return mStorage;
    }

    public Pattern setStorage(Storage mStorage) {
        this.mStorage = mStorage;
        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public Pattern setDescription(String mDescription) {
        this.mDescription = mDescription;
        return this;
    }
}
