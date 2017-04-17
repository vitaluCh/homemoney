package vitalu.ua.gmail.com.homemoney.model.database_model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Виталий on 28.12.2015.
 */
public class Storage implements Parcelable {

    int mId;
    String mNameStorage;
    double mAmountStorage;
    Currency mCurency;
    int mImageStorage;
    boolean mVisible;

    public Storage() {

    }

    public Storage(int mId, String mNameStorage, double mAmountStorage, Currency mCurency, int mImageStorage) {
        this.mId = mId;
        this.mNameStorage = mNameStorage;
        this.mAmountStorage = mAmountStorage;
        this.mCurency = mCurency;
        this.mImageStorage = mImageStorage;
    }

    protected Storage(Parcel in) {
        mId = in.readInt();
        mNameStorage = in.readString();
        mAmountStorage = in.readDouble();
        mImageStorage = in.readInt();
        mVisible = in.readByte() != 0;
    }

    public static final Creator<Storage> CREATOR = new Creator<Storage>() {
        @Override
        public Storage createFromParcel(Parcel in) {
            return new Storage(in);
        }

        @Override
        public Storage[] newArray(int size) {
            return new Storage[size];
        }
    };

    public int getId() {
        return mId;
    }

    public Storage setId(int mId) {
        this.mId = mId;
        return this;
    }

    public String getNameStorage() {
        return mNameStorage;
    }

    public Storage setNameStorage(String mNameStorage) {
        this.mNameStorage = mNameStorage;
        return this;
    }

    public double getAmountStorage() {
        return mAmountStorage;
    }

    public Storage setAmountStorage(double mAmountStorage) {
        this.mAmountStorage = mAmountStorage;
        return this;
    }

    public Currency getCurency() {
        return mCurency;
    }

    public Storage setCurency(Currency mCurency) {
        this.mCurency = mCurency;
        return this;
    }

    public int getImageStorage() {
        return mImageStorage;
    }

    public Storage setImageStorage(int mImageStorage) {
        this.mImageStorage = mImageStorage;
        return this;
    }

    public boolean isVisible() {
        return mVisible;
    }

    public Storage setVisible(boolean mVisible) {
        this.mVisible = mVisible;
        return this;
    }

    @Override
    public String toString() {
        return mNameStorage + " " + mAmountStorage + " " + mCurency.mShortName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mNameStorage);
        dest.writeDouble(mAmountStorage);
        dest.writeInt(mImageStorage);
        dest.writeByte((byte) (mVisible ? 1 : 0));
    }
}
