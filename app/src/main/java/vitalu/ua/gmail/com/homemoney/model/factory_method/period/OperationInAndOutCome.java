package vitalu.ua.gmail.com.homemoney.model.factory_method.period;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;

/**
 * Created by Виталий on 12.02.2016.
 */
public class OperationInAndOutCome implements OperationsOfPeriod<List<Operation>, Operation> {

    List<Operation> operations;

    public OperationInAndOutCome() {
        operations = new ArrayList<>();
    }

    @Override
    public void addAll(List<Operation> object) {
        operations.addAll(object);
    }

    @Override
    public Operation remove(int position) {
        return operations.remove(position);
    }

    @Override
    public List<Operation> getAll() {
        return operations;
    }

    @Override
    public int size() {
        return operations.size();
    }

    @Override
    public Operation get(int position) {
        return operations.get(position);
    }

    @Override
    public long getDate(int position) {
        return operations.get(position).getDate();
    }

    @Override
    public void reverse() {
        Collections.reverse(operations);
    }

    @Override
    public int indexOf(Operation object) {
        return operations.indexOf(object);
    }
}
