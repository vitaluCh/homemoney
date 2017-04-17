package vitalu.ua.gmail.com.homemoney.model.factory_method.period;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;

/**
 * Created by Виталий on 12.02.2016.
 */
public class OperationTransfer implements OperationsOfPeriod<List<TransferOperation>, TransferOperation> {

    public static final String TAG = "myLogMainActivity";

    List<TransferOperation> operations;

    public OperationTransfer() {
        operations = new ArrayList<>();
    }

    @Override
    public void addAll(List<Operation> object) {

        for(int i = object.size()-1; i >= 0; ){

            Operation in = object.get(i);
            Operation out = object.get(--i);

            operations.add(new TransferOperation(out, in));
            i--;

            Log.d(TAG, "Запись");
        }

        Collections.sort(operations, new Comparator<TransferOperation>() {

            @Override
            public int compare(TransferOperation lhs, TransferOperation rhs) {
                return (int) (rhs.getOutComeOperation().getDate() - lhs.getOutComeOperation().getDate());
            }
        });

    }

    @Override
    public TransferOperation remove(int position) {
        return operations.remove(position);
    }

    @Override
    public List<TransferOperation> getAll() {
        return operations;
    }

    @Override
    public int size() {
        return operations.size();
    }

    @Override
    public TransferOperation get(int position) {
        return operations.get(position);
    }

    @Override
    public long getDate(int position) {
        return operations.get(position).getInComeOperation().getDate();
    }

    @Override
    public void reverse() {
        Collections.reverse(operations);
    }

    @Override
    public int indexOf(TransferOperation object) {
        return operations.indexOf(object);
    }


}