package vitalu.ua.gmail.com.homemoney.model.database_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Виталий on 10.02.2016.
 */
public class TransferOperation implements Parcelable{

    private Operation mOutComeOperation;
    private Operation mInComeOperation;

    public TransferOperation() {
    }

    public TransferOperation(Operation mOutComeOperation, Operation mInComeOperation) {
        this.mOutComeOperation = mOutComeOperation;
        this.mInComeOperation = mInComeOperation;
    }

    public TransferOperation(List<Operation> operations) {
        this.mOutComeOperation = mOutComeOperation;
        this.mInComeOperation = mInComeOperation;
    }

    protected TransferOperation(Parcel in) {
        mOutComeOperation = in.readParcelable(Operation.class.getClassLoader());
        mInComeOperation = in.readParcelable(Operation.class.getClassLoader());
    }

    public static final Creator<TransferOperation> CREATOR = new Creator<TransferOperation>() {
        @Override
        public TransferOperation createFromParcel(Parcel in) {
            return new TransferOperation(in);
        }

        @Override
        public TransferOperation[] newArray(int size) {
            return new TransferOperation[size];
        }
    };

    public Operation getOutComeOperation() {
        return mOutComeOperation;
    }

    public TransferOperation setOutComeOperation(Operation mOutComeOperation) {
        this.mOutComeOperation = mOutComeOperation;
        return this;
    }

    public Operation getInComeOperation() {
        return mInComeOperation;
    }

    public TransferOperation setInComeOperation(Operation mInComeOperation) {
        this.mInComeOperation = mInComeOperation;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mOutComeOperation, flags);
        dest.writeParcelable(mInComeOperation, flags);
    }
}
