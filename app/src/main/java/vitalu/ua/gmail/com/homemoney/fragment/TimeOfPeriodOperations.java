package vitalu.ua.gmail.com.homemoney.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.activity.OperationActivity;
import vitalu.ua.gmail.com.homemoney.adapter.OperationAdapter;
import vitalu.ua.gmail.com.homemoney.adapter.TransferOperationAdapter;
import vitalu.ua.gmail.com.homemoney.database.database_query.OperationQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;
import vitalu.ua.gmail.com.homemoney.model.factory_method.period.OperationsOfPeriod;
import vitalu.ua.gmail.com.homemoney.model.factory_method.period.OperationsSelector;

/**
 * Created by Виталий on 14.02.2016.
 */
public abstract class TimeOfPeriodOperations extends ListFragment {

    public static final String TAG = "myLogMainActivity";

    private static final String CREATE_LIST_POSITION = "vitalu.ua.gmail.com.homemoney.fragment.create_list";
    private static final String EXTRA_PERIOD = "vitalu.ua.gmail.com.homemoney.fragment.create_period";
    private static final String EXTRA_OPERATION = "vitalu.ua.gmail.com.homemoney.fragment.create_operation";

    public static final int DAY_OPERATION = 1;
    public static final int WEEK_OPERATION = 2;
    public static final int MONTH_OPERATION = 3;
    public static final int YEAR_OPERATION = 4;

    public static final int INCOM_OPERATION = 1;
    public static final int OUTCOM_OPERATION = 2;
    public static final int TRANSFER_OPERATION = 3;
    private static final int MENU_DELETE = 1;

    protected OperationsSelector selector;
    protected OperationsOfPeriod mOperations;
    protected ArrayAdapter adapter;
    protected int idOperation;
    protected int mPeriod;
    protected int position;

    public abstract List<Operation> getOperations();


    public TimeOfPeriodOperations() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        position = getArguments().getInt(CREATE_LIST_POSITION);//////////////////////////////////////////////////////
        idOperation = getArguments().getInt(EXTRA_OPERATION);
        mPeriod = getArguments().getInt(EXTRA_PERIOD);

        selector = new OperationsSelector();
        mOperations = selector.getOperation(idOperation);

        //mOperations.addAll(OperationQuery.getAllOneDay(position, idOperation));//--------------------------------------------
        mOperations.addAll(getOperations());//--------------------------------------------

        if(idOperation == TRANSFER_OPERATION){
            adapter = new TransferOperationAdapter(getActivity(), (List<TransferOperation>)mOperations.getAll());
        }else if(idOperation == INCOM_OPERATION || idOperation == OUTCOM_OPERATION) {
            adapter = new OperationAdapter(getActivity(), (List<Operation>) mOperations.getAll());
        }
        setListAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, null);
        //ListView listView = (ListView)view.findViewById(android.R.id.list);
        //registerForContextMenu(listView);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, MENU_DELETE, 0, R.string.itemMenuDelete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case MENU_DELETE:
                try {    //
                    OperationQuery.deleteOperation((Operation)mOperations.remove(info.position));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), getString(R.string.operation_delete), Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){

                }
            /*    try {    //
                    if(idOperation == TRANSFER_OPERATION){
                        TransferOperation operation = (TransferOperation)mOperations.remove(info.position);
                        //OperationQuery.deleteOperation(operation);
                    }else if(idOperation == INCOM_OPERATION || idOperation == OUTCOM_OPERATION) {
                        Operation operation = (Operation)mOperations.remove(info.position);
                        OperationQuery.deleteOperation(operation);
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), getString(R.string.operation_delete), Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){

                }*/
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(getActivity(), OperationActivity.class);
        intent.putExtra(OperationActivity.EXTRA_PERIOD_OPERATION, mPeriod);//период день, месяц,...==============++++++++
        intent.putExtra(OperationActivity.EXTRA_POSITION, position);//порзиция в списке
        intent.putExtra(OperationActivity.EXTRA_OPERATION, idOperation);//доход или расход
        if(idOperation == TRANSFER_OPERATION){
            intent.putExtra(OperationActivity.EXTRA_DAY, ((TransferOperation) getListAdapter().getItem(position)).getInComeOperation().getDate());//дата
        }else if(idOperation == INCOM_OPERATION || idOperation == OUTCOM_OPERATION) {
            intent.putExtra(OperationActivity.EXTRA_DAY, ((Operation) getListAdapter().getItem(position)).getDate());//дата
        }

        startActivity(intent);
    }

    public static TimeOfPeriodOperations newInstance(int position, int idOperation, int period) {//позиция в списке

        TimeOfPeriodOperations fragment = null;

        Bundle args = new Bundle();
        args.putInt(CREATE_LIST_POSITION, position);/////////////////////////////////////////////////////
        args.putInt(EXTRA_OPERATION, idOperation);
        args.putInt(EXTRA_PERIOD, period);
        if(period == DAY_OPERATION){
            fragment = new DayOperationsFragment();
        }else if(period == WEEK_OPERATION){
            fragment = new WeekOperationsFragment();
        }else if(period == MONTH_OPERATION){
            fragment = new MonthOperationsFragment();
        }else if(period == YEAR_OPERATION){
            fragment = new YearOperationsFragment();
        }

        fragment.setArguments(args);
        return fragment;
    }

}
