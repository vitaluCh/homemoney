package vitalu.ua.gmail.com.homemoney.model.factory_method.all_period;

import java.util.ArrayList;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;

/**
 * Created by Виталий on 12.02.2016.
 */
public class TransferOperationsOfEachOfPeriod implements OperationsOfEachOfPeriods<List<List<Operation>>, TransferOperation> {

    List<List<TransferOperation>> listEachOperations;
    public TransferOperationsOfEachOfPeriod() {
        listEachOperations = new ArrayList<>();
    }

    @Override
    public void addAll(List<List<Operation>> object) {

        List<TransferOperation> transfList;
        int index = 1;

        for(List<Operation> list : object){
            transfList = new ArrayList<>();
            ///////////////////////
           /* for(int i = list.size()-1; i >= 0; ){
                transfList.add(new TransferOperation(list.get(i), list.get(--i)));
                i--;
            }*/
            for(int i = 0; i < list.size(); ){
                transfList.add(new TransferOperation(list.get(i), list.get(++i)));
                i++;
            }
            /*for(Operation oper : list){

                Operation operInCome = null;
                Operation operOutCome = null;
                if(index % 2 == 0){
                    operInCome = oper;
                }else{
                    operOutCome = oper;
                }
                transfList.add(new TransferOperation(operOutCome, operInCome));
            }*/
            listEachOperations.add(transfList);
        }
        //listEachOperations
/*        Collections.sort(listEachOperations, new Comparator<List<TransferOperation>>() {
            @Override
            public int compare(List<TransferOperation> lhs, List<TransferOperation> rhs) {
                return (int)(rhs.get(0).getOutComeOperation().getDate() - lhs.get(0).getOutComeOperation().getDate());
            }
        });*/
       // Collections.reverse(listEachOperations);

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
        return listEachOperations.get(position).get(0).getOutComeOperation().getDate();
    }

    @Override
    public void set(int position, TransferOperation object) {
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
