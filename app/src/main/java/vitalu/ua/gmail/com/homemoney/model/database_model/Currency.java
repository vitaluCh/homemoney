package vitalu.ua.gmail.com.homemoney.model.database_model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Виталий on 28.12.2015.
 */
public class Currency implements Parcelable {

    int mId;
    String mFullName;
    String mShortName;
    boolean mVisible;

    public Currency() {
    }

    public Currency(int mId, String mFullName, String mShortName) {
        this.mId = mId;
        this.mFullName = mFullName;
        this.mShortName = mShortName;
    }

    protected Currency(Parcel in) {
        mId = in.readInt();
        mFullName = in.readString();
        mShortName = in.readString();
        mVisible = in.readByte() != 0;
    }

    public static final Creator<Currency> CREATOR = new Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel in) {
            return new Currency(in);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };

    public int getId() {
        return mId;
    }

    public Currency setId(int mId) {
        this.mId = mId;
        return this;
    }

    public String getFullName() {
        return mFullName;
    }

    public Currency setFullName(String mFullName) {
        this.mFullName = mFullName;
        return this;
    }

    public String getShortName() {
        return mShortName;
    }

    public Currency setShortName(String mShortName) {
        this.mShortName = mShortName;
        return this;
    }

    public boolean isVisible() {
        return mVisible;
    }

    public Currency setVisible(boolean mVisible) {
        this.mVisible = mVisible;
        return this;
    }

    @Override
    public String toString() {
        return mFullName + " " + mShortName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mFullName);
        dest.writeString(mShortName);
        dest.writeByte((byte) (mVisible ? 1 : 0));
    }
}
