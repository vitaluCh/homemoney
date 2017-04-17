package vitalu.ua.gmail.com.homemoney.model.database_model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Виталий on 28.12.2015.
 */
public class Operation implements Parcelable{

    long mId;
    String mNameOperation;
    double mAmount;
    long mDate;
    Source mSoyrce;
    Storage mStorage;
    String mDescription;
    String mImagePath;

    public Operation() {
    }

    public Operation(long mId, String mNameOperation, double mAmount, long mDate,
                     Source mSoyrce, Storage mStorage, String mDescription, String mImagePath) {
        this.mId = mId;
        this.mNameOperation = mNameOperation;
        this.mAmount = mAmount;
        this.mDate = mDate;
        this.mSoyrce = mSoyrce;
        this.mStorage = mStorage;
        this.mDescription = mDescription;
        this.mImagePath = mImagePath;
    }

    protected Operation(Parcel in) {
        mId = in.readLong();
        mNameOperation = in.readString();
        mAmount = in.readDouble();
        mDate = in.readLong();
        mSoyrce = in.readParcelable(Source.class.getClassLoader());
        mStorage = in.readParcelable(Storage.class.getClassLoader());
        mDescription = in.readString();
        mImagePath = in.readString();
    }

    public static final Creator<Operation> CREATOR = new Creator<Operation>() {
        @Override
        public Operation createFromParcel(Parcel in) {
            return new Operation(in);
        }

        @Override
        public Operation[] newArray(int size) {
            return new Operation[size];
        }
    };

    public long getId() {
        return mId;
    }

    public Operation setId(int mId) {
        this.mId = mId;
        return this;
    }

    public String getNameOperation() {
        return mNameOperation;
    }

    public Operation setNameOperation(String mNameOperation) {
        this.mNameOperation = mNameOperation;
        return this;
    }

    public double getAmount() {
        return mAmount;
    }

    public Operation setAmount(double mAmount) {
        this.mAmount = mAmount;
        return this;
    }

    public long getDate() {
        return mDate;
    }

    public Operation setDate(long mDate) {
        this.mDate = mDate;
        return this;
    }

    public Source getSoyrce() {
        return mSoyrce;
    }

    public Operation setSoyrce(Source mSoyrce) {
        this.mSoyrce = mSoyrce;
        return this;
    }

    public Storage getStorage() {
        return mStorage;
    }

    public Operation setStorage(Storage mStorage) {
        this.mStorage = mStorage;
        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public Operation setDescription(String mDescription) {
        this.mDescription = mDescription;
        return this;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public Operation setImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mNameOperation);
        dest.writeDouble(mAmount);
        dest.writeLong(mDate);
        dest.writeParcelable(mSoyrce, flags);
        dest.writeParcelable(mStorage, flags);
        dest.writeString(mDescription);
        dest.writeString(mImagePath);
    }
}
