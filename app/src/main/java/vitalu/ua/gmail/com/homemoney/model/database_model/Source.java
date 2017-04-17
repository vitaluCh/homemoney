package vitalu.ua.gmail.com.homemoney.model.database_model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Виталий on 28.12.2015.
 */
public class Source implements Parcelable{

    private int mId;
    private String mNameSours;
    private TypeOperation mType;
    private int mParentId;
    private boolean mVisible;

    public Source() {
    }

    public Source(int mId, String mNameSours, TypeOperation mType, int mParentId) {
        this.mId = mId;
        this.mNameSours = mNameSours;
        this.mType = mType;
        this.mParentId = mParentId;
    }

    protected Source(Parcel in) {
        mId = in.readInt();
        mNameSours = in.readString();
        mParentId = in.readInt();
        mVisible = in.readByte() != 0;
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };

    public int getId() {
        return mId;
    }

    public Source setId(int mId) {
        this.mId = mId;
        return this;
    }

    public String getNameSours() {
        return mNameSours;
    }

    public Source setNameSours(String mNameSours) {
        this.mNameSours = mNameSours;
        return this;
    }

    public TypeOperation getType() {
        return mType;
    }

    public Source setType(TypeOperation mType) {
        this.mType = mType;
        return this;
    }

    public int getParentId() {
        return mParentId;
    }

    public Source setParentId(int mParentId) {
        this.mParentId = mParentId;
        return this;
    }

    public boolean isVisible() {
        return mVisible;
    }

    public Source setVisible(boolean mVisible) {
        this.mVisible = mVisible;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mNameSours);
        dest.writeInt(mParentId);
        dest.writeByte((byte) (mVisible ? 1 : 0));
    }

    @Override
    public String toString() {
        return mNameSours;
    }
}
