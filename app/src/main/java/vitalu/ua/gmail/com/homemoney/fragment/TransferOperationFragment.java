package vitalu.ua.gmail.com.homemoney.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * Created by Виталий on 12.02.2016.
 */
public class TransferOperationFragment extends BaseOperationFragment {

/*    public static final String TAG = "myLogMainActivity";

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

    private TransferOperation operation;
    OperationTransfer operationTransfer;*/

    public TransferOperationFragment() {
        super();
        Log.d(TAG, "TransferOperationFragment");
        // Required empty public constructor
    }

/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPeriod = getArguments().getInt(OperationActivity.EXTRA_PERIOD_OPERATION);
        mPosition = getArguments().getInt(OperationActivity.EXTRA_POSITION);
        mIdOperation = getArguments().getInt(OperationActivity.EXTRA_OPERATION);
        mDay = getArguments().getLong(OperationActivity.EXTRA_DAY);

        operationTransfer = new OperationTransfer();

        switch (mPeriod){//заполняем массив согласно периода
            case DAY_OPERATION:
                operationTransfer.addAll(OperationQuery.getOperationsDay(mDay, mIdOperation));
                //operation = operationTransfer.get(mPosition);
                break;
            case WEEK_OPERATION:
                operationTransfer.addAll(OperationQuery.getOperationsWeek(mDay, mIdOperation));
                //operation = OperationQuery.getOperationsWeek(mDay, mIdOperation).get(mPosition);
                break;
            case MONTH_OPERATION:
                operationTransfer.addAll(OperationQuery.getOperationsMonth(mDay, mIdOperation));
                //operation = OperationQuery.getOperationsMonth(mDay, mIdOperation).get(mPosition);
                break;
            case YEAR_OPERATION:
                operationTransfer.addAll(OperationQuery.getOperationsYear(mDay, mIdOperation));
                //operation = OperationQuery.getOperationsYear(mDay, mIdOperation).get(mPosition);
                break;
        }

        operation = operationTransfer.get(mPosition);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "------------onCreateView------------------------ ");
        View view = inflater.inflate(R.layout.transfer_operation_fragment, null);
        getActivity().setTitle(R.string.operation_transfer);
/*
        Log.d(TAG, String.valueOf(mOperations.size()));

        for(int i = 0; i<mOperations.size();i++){
            Log.d(TAG, mOperations.get(i).toString());
        }*/

        TransferOperation transfer = (TransferOperation)mOperations.get(mPosition);

        Log.d(TAG, String.valueOf(mPosition));
        Log.d(TAG, String.valueOf(transfer.getOutComeOperation().getStorage().getImageStorage()));
        Log.d(TAG, transfer.getOutComeOperation().getStorage().getNameStorage());

        ImageView imStorageFrom = (ImageView)view.findViewById(R.id.imStorageFrom);
        imStorageFrom.setImageResource(transfer.getOutComeOperation().getStorage().getImageStorage());

        TextView tvStorageNameFrom = (TextView)view.findViewById(R.id.tvStorageNameFrom);
        tvStorageNameFrom.setText(transfer.getOutComeOperation().getStorage().getNameStorage());

        TextView tvStorageAmountNow = (TextView)view.findViewById(R.id.tvStorageAmountNow);
        tvStorageAmountNow.setText(String.valueOf(transfer.getOutComeOperation().getStorage().getAmountStorage()));

        TextView tvStorageCource = (TextView)view.findViewById(R.id.tvStorageCource);
        tvStorageCource.setText(String.valueOf(transfer.getOutComeOperation().getStorage().getCurency().getShortName()));

        TextView tvStorageAmountFrom = (TextView)view.findViewById(R.id.tvStorageAmountFrom);
        tvStorageAmountFrom.setText("- " + String.valueOf(transfer.getOutComeOperation().getAmount()));

        TextView tvStorageCourceFrom = (TextView)view.findViewById(R.id.tvStorageCourceFrom);
        tvStorageCourceFrom.setText(transfer.getOutComeOperation().getStorage().getCurency().getShortName());

        /*/////////////////////////////////////////////////////////////*/

        ImageView imStorageTo = (ImageView)view.findViewById(R.id.imStorageTo);
        imStorageTo.setImageResource(transfer.getInComeOperation().getStorage().getImageStorage());

        TextView tvStorageNameTo = (TextView)view.findViewById(R.id.tvStorageNameTo);
        tvStorageNameTo.setText(transfer.getInComeOperation().getStorage().getNameStorage());

        TextView tvStorageAmountTo = (TextView)view.findViewById(R.id.tvStorageAmountTo);
        tvStorageAmountTo.setText(String.valueOf(transfer.getInComeOperation().getStorage().getAmountStorage()));

        TextView tvStorageCourceTo = (TextView)view.findViewById(R.id.tvStorageCourceTo);
        tvStorageCourceTo.setText(String.valueOf(transfer.getInComeOperation().getStorage().getCurency().getShortName()));

        TextView tvStorageAmountFromTo = (TextView)view.findViewById(R.id.tvStorageAmountFromTo);
        tvStorageAmountFromTo.setText("+ " + String.valueOf(transfer.getInComeOperation().getAmount()));

        TextView tvStorageCourceFromTo = (TextView)view.findViewById(R.id.tvStorageCourceFromTo);
        tvStorageCourceFromTo.setText(transfer.getInComeOperation().getStorage().getCurency().getShortName());

        /*///////////////////////////////////////////////////////////////////////*/

        TextView tvDate = (TextView)view.findViewById(R.id.tvDate);
        tvDate.setText(Utils.getDate(transfer.getInComeOperation().getDate()));
        tvDate.setTextColor(getResources().getColor(R.color.colorAccent));

        TextView tvDescription = (TextView)view.findViewById(R.id.tvDescription);
        tvDescription.setText(transfer.getInComeOperation().getDescription());

        return view;
    }

    private void setTextColor(){

    }

/*    public static TransferOperationFragment newInstance(int period, int position, int operation, long date) {//позиция в списке

        Bundle args = new Bundle();

        args.putInt(OperationActivity.EXTRA_PERIOD_OPERATION, period);
        args.putInt(OperationActivity.EXTRA_POSITION, position);
        args.putInt(OperationActivity.EXTRA_OPERATION, operation);
        args.putLong(OperationActivity.EXTRA_DAY, date);

        TransferOperationFragment fragment = new TransferOperationFragment();
        fragment.setArguments(args);
        return fragment;
    }*/
}
