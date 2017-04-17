package vitalu.ua.gmail.com.homemoney.fragment;


import android.support.v4.app.Fragment;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.database.database_query.OperationQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekOperationsFragment extends TimeOfPeriodOperations /*ListFragment*/ {
    @Override
    public List<Operation> getOperations() {
        return OperationQuery.getListWeek(position, idOperation);
    }

/*    private static final String CREATE_LIST_WEEK = "vitalu.ua.gmail.com.homemoney.fragment.create_list";
    private static final String EXTRA_OPERATION = "vitalu.ua.gmail.com.homemoney.fragment.create_operation";

    public static final int INCOM_OPERATION = 1;
    public static final int OUTCOM_OPERATION = 2;
    public static final int TRANSFER_OPERATION = 3;
    private static final int MENU_DELETE = 1;

    OperationsSelector selector;
    OperationsOfPeriod mOperations;
    //List<Operation> listWeekOperations;
    ArrayAdapter adapter;
    int idOperation;

    // View view;

    public WeekOperationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.d(TAG, "onActivityCreated");

        final int position = getArguments().getInt(CREATE_LIST_WEEK);
        idOperation = getArguments().getInt(EXTRA_OPERATION);

        selector = new OperationsSelector();
        mOperations = selector.getOperation(idOperation);
        //((OpeationsListActivity)getActivity()).findViewById(R.id.)

        mOperations.addAll(OperationQuery.getListWeek(position, idOperation));

       *//* if(idOperation == INCOM_OPERATION) {
            listWeekOperations = OperationQuery.getListWeek(position, OperationQuery.OPERATION_INCOM);
        }else if(idOperation == OUTCOM_OPERATION){
            listWeekOperations = OperationQuery.getListWeek(position, OperationQuery.OPERATION_OUTCOM);
        }*//*

        if(idOperation == TRANSFER_OPERATION){
            adapter = new TransferOperationAdapter(getActivity(), (List<TransferOperation>)mOperations.getAll());
        }else if(idOperation == INCOM_OPERATION || idOperation == OUTCOM_OPERATION) {
            adapter = new OperationAdapter(getActivity(), (List<Operation>) mOperations.getAll());
        }

        //adapter = new OperationAdapter(getActivity(), listWeekOperations);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_fragment, null);
        ListView listView = (ListView)view.findViewById(android.R.id.list);
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

             *//*   Log.d(TAG, "MENU_DELETE");
                Log.d(TAG, Utils.getDate(listDaysOperations.get(info.position).getDate()));
                Log.d(TAG, String.valueOf(listDaysOperations.get(info.position).getAmount()));*//*

                try {
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

                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(getActivity(), OperationActivity.class);
        intent.putExtra(OperationActivity.EXTRA_PERIOD_OPERATION, OperationActivity.WEEK_OPERATION);//период день, месяц,...
        intent.putExtra(OperationActivity.EXTRA_POSITION, position);//порзиция в списке
        intent.putExtra(OperationActivity.EXTRA_OPERATION, idOperation);//доход или расход
        if(idOperation == TRANSFER_OPERATION){
            intent.putExtra(OperationActivity.EXTRA_DAY, ((TransferOperation) getListAdapter().getItem(position)).getInComeOperation().getDate());//дата
        }else if(idOperation == INCOM_OPERATION || idOperation == OUTCOM_OPERATION) {
            intent.putExtra(OperationActivity.EXTRA_DAY, ((Operation) getListAdapter().getItem(position)).getDate());//дата
        }
        startActivity(intent);
    }

    public static WeekOperationsFragment newInstance(int position, int idOperation) {//позиция в списке
        Bundle args = new Bundle();
        args.putInt(CREATE_LIST_WEEK, position);
        args.putInt(EXTRA_OPERATION, idOperation);
        WeekOperationsFragment fragment = new WeekOperationsFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

}
