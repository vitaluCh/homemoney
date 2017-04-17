package vitalu.ua.gmail.com.homemoney.model.factory_method.period;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;

/**
 * Created by Виталий on 12.02.2016.
 */
public interface OperationsOfPeriod <T, V>{

    void addAll(List<Operation> object);
    V remove(int position);
    T getAll();
    int size();
    V get(int position);
    long getDate(int position);
    void reverse();
    int indexOf(V object);
}
