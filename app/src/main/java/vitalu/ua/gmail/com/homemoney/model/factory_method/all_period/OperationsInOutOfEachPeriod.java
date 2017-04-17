package vitalu.ua.gmail.com.homemoney.model.factory_method.all_period;

import java.util.ArrayList;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;

/**
 * Created by Виталий on 12.02.2016.
 */
public class OperationsInOutOfEachPeriod implements OperationsOfEachOfPeriods<List<List<Operation>>, Operation> {

    List<List<Operation>>listEachOperations;

    public OperationsInOutOfEachPeriod() {
        listEachOperations = new ArrayList<>();
    }

    @Override
    public void addAll(List<List<Operation>> object) {
        listEachOperations.addAll(object);
    }

    @Override
    public int size() {
        return listEachOperations.size();
    }

    @Override
    public boolean isEmpty() {
        return listEachOperations.isEmpty();
    }

    @Override
    public long getDate(int position) {
        return listEachOperations.get(position).get(0).getDate();
    }

    @Override
    public void set(int position, Operation object) {
        listEachOperations.get(position).set(0, object);
    }

    @Override
    public void replace(List<List<Operation>> object) {

        if(!listEachOperations.isEmpty()){
            listEachOperations.clear();
            listEachOperations = null;
            listEachOperations = new ArrayList<>();
        }
        addAll(object);
    }
}
