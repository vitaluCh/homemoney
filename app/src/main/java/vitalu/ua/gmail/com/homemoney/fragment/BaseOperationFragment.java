package vitalu.ua.gmail.com.homemoney.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import vitalu.ua.gmail.com.homemoney.activity.OperationActivity;
import vitalu.ua.gmail.com.homemoney.database.database_query.OperationQuery;
import vitalu.ua.gmail.com.homemoney.model.factory_method.period.OperationsOfPeriod;
import vitalu.ua.gmail.com.homemoney.model.factory_method.period.OperationsSelector;

/**
 * Created by Виталий on 12.02.2016.
 */
public class BaseOperationFragment extends Fragment {


    public static final String TAG = "myLogMainActivity";

    public static final int INCOM_OPERATION = 1;
    public static final int OUTCOM_OPERATION = 2;
    public static final int TRANSFER_OPERATION = 3;

    public static final int DAY_OPERATION = 1;
    public static final int MONTH_OPERATION = 3;
    public static final int WEEK_OPERATION = 2;
    public static final int YEAR_OPERATION = 4;

    int mPeriod;//период день, мес, год, неделя
    int mPosition;//позиция в списке
    int mIdOperation;//операция доход, расход
    long mDay;//дата операции

    //T operation;
    OperationsSelector selector;
    OperationsOfPeriod mOperations;

    public BaseOperationFragment() {
        // Required empty public constructor
        Log.d(TAG, "BaseOperationFragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "------------onCreate------------------------ ");
        mPeriod = getArguments().getInt(OperationActivity.EXTRA_PERIOD_OPERATION);
        mPosition = getArguments().getInt(OperationActivity.EXTRA_POSITION);
        mIdOperation = getArguments().getInt(OperationActivity.EXTRA_OPERATION);
        mDay = getArguments().getLong(OperationActivity.EXTRA_DAY);

        selector = new OperationsSelector();
        mOperations = selector.getOperation(mIdOperation);

        switch (mPeriod){//заполняем массив согласно периода
            case DAY_OPERATION:
                mOperations.addAll(OperationQuery.getOperationsDay(mDay, mIdOperation));
                //operation = operationTransfer.get(mPosition);
                break;
            case WEEK_OPERATION:
                mOperations.addAll(OperationQuery.getOperationsWeek(mDay, mIdOperation));
                //operation = OperationQuery.getOperationsWeek(mDay, mIdOperation).get(mPosition);
                break;
            case MONTH_OPERATION:
                mOperations.addAll(OperationQuery.getOperationsMonth(mDay, mIdOperation));
                //operation = OperationQuery.getOperationsMonth(mDay, mIdOperation).get(mPosition);
                break;
            case YEAR_OPERATION:
                mOperations.addAll(OperationQuery.getOperationsYear(mDay, mIdOperation));
                //operation = OperationQuery.getOperationsYear(mDay, mIdOperation).get(mPosition);
                break;
        }


        //((TransferOperation)mOperations.get(mPosition)).getInComeOperation();
/*
        if(mIdOperation == TRANSFER_OPERATION) {
            operation = mOperations.get(mPosition);
        }*/
    }

    public static BaseOperationFragment newInstance(int period, int position, int operation, long date) {//позиция в списке

        Bundle args = new Bundle();

        args.putInt(OperationActivity.EXTRA_PERIOD_OPERATION, period);
        args.putInt(OperationActivity.EXTRA_POSITION, position);
        args.putInt(OperationActivity.EXTRA_OPERATION, operation);
        args.putLong(OperationActivity.EXTRA_DAY, date);

        BaseOperationFragment fragment = null;

        if(operation == TRANSFER_OPERATION){
            fragment = new TransferOperationFragment();
        }else if(operation == INCOM_OPERATION || operation == OUTCOM_OPERATION){
            fragment = new OperationFragment();
        }
        //TransferOperationFragment fragment = new TransferOperationFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
