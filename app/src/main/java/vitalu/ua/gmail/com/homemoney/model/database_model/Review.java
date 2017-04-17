package vitalu.ua.gmail.com.homemoney.model.database_model;

/**
 * Created by Виталий on 28.12.2015.
 */
public class Review {

    int mId;
    String mNameReview;
    double mAmountReview;
    long mDateReview;
    int mParent;
    String mDescription;

    public Review() {
    }

    public Review(int mId, String mNameReview, long mAmountReview, long mDateReview, int mParent, String mDescription) {
        this.mId = mId;
        this.mNameReview = mNameReview;
        this.mAmountReview = mAmountReview;
        this.mDateReview = mDateReview;
        this.mParent = mParent;
        this.mDescription = mDescription;
    }

    public int getId() {
        return mId;
    }

    public Review setId(int mId) {
        this.mId = mId;
        return this;
    }

    public String getNameReview() {
        return mNameReview;
    }

    public Review setNameReview(String mNameReview) {
        this.mNameReview = mNameReview;
        return this;
    }

    public double getAmountReview() {
        return mAmountReview;
    }

    public Review setAmountReview(double mAmountReview) {
        this.mAmountReview = mAmountReview;
        return this;
    }

    public long getDateReview() {
        return mDateReview;
    }

    public Review setDateReview(long mDateReview) {
        this.mDateReview = mDateReview;
        return this;
    }

    public int getParent() {
        return mParent;
    }

    public Review setParent(int mParent) {
        this.mParent = mParent;
        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public Review setDescription(String mDescription) {
        this.mDescription = mDescription;
        return this;
    }

    @Override
    public String toString() {
        return mNameReview;
    }
}
