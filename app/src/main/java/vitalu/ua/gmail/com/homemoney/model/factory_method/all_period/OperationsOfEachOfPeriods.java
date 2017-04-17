package vitalu.ua.gmail.com.homemoney.model.factory_method.all_period;

/**
 * Created by Виталий on 12.02.2016.
 */
public interface OperationsOfEachOfPeriods<T, V>{

    void addAll(T object);
    int size();
    boolean isEmpty();
    long getDate(int position);
    void set(int position, V object);
    void replace(T object);
}
