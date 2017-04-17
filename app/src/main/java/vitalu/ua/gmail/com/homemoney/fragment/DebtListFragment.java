package vitalu.ua.gmail.com.homemoney.fragment;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.activity.SingleFragmentActivity;
import vitalu.ua.gmail.com.homemoney.adapter.DebtAdapter;
import vitalu.ua.gmail.com.homemoney.database.database_query.OperationQuery;
import vitalu.ua.gmail.com.homemoney.dialog.CreateOperationDialog;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebtListFragment extends ListFragment
        implements SingleFragmentActivity.OnChangeIteme<Operation>/*, AdapterView.OnItemClickListener*/{

    private static final int REQUEST_CURRENCY_DATA = 1;
    private static final int REQUEST_DATA = 1;
    private final int MENU_DELETE_CURRENCY = 1;
    private final int MENU_UPDATE_CURRENCY = 2;

    private List<Operation> debtList;
    private DebtAdapter debtAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.d(TAG, "onActivityCreated");

        debtList = OperationQuery.getDebtOperations();
        debtAdapter = new DebtAdapter(getActivity(), debtList);
        setListAdapter(debtAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        debtAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_list_debt);

       /* currencyList = CurrencyQuery.getCurrencys();
        currencyAdapter = new CurrencyAdapter(getActivity(), currencyList);*/
        View view = inflater.inflate(R.layout.list_fragment, null);
        ListView listView = (ListView)view.findViewById(android.R.id.list);
        //listView.setAdapter(currencyAdapter);
        //listView.setOnItemClickListener(this);
        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, MENU_UPDATE_CURRENCY, 0, R.string.itemMenuUpdate);
        menu.add(0, MENU_DELETE_CURRENCY, 0, R.string.itemMenuDelete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case MENU_UPDATE_CURRENCY:
                //showDialog(info.position);
                DialogFragment addingOperationDialogFragment = CreateOperationDialog
                        .newInstance(CreateOperationDialog.DEBT_OPERATION,
                                getString(R.string.dialog_title_add_debt), null, debtList.get(info.position));
                addingOperationDialogFragment.setTargetFragment(DebtListFragment.this, REQUEST_DATA);
                addingOperationDialogFragment.show(getFragmentManager(), "CreateOperationDialog");

                break;
            case MENU_DELETE_CURRENCY:
                Operation operation = debtList.remove(info.position);
                OperationQuery.deleteDebt(operation);
                debtAdapter.notifyDataSetChanged();
                //Log.d(TAG, String.valueOf(info.id));
                break;
        }
        return super.onContextItemSelected(item);
    }

/*    @Override
    public void onChangeItem(Currency currency) {

        //currencyList = CurrencyQuery.getCurrencys();
        //currencyAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        DialogFragment addingOperationDialogFragment = CreateOperationDialog
                .newInstance(CreateOperationDialog.DEBT_OPERATION,
                        getString(R.string.dialog_title_update_debt), null, debtList.get(position));
        addingOperationDialogFragment.setTargetFragment(DebtListFragment.this, REQUEST_DATA);
        addingOperationDialogFragment.show(getFragmentManager(), "CreateOperationDialog");
        //showDialog(position);
        //Toast.makeText(getActivity(), "Нажат " + currencyList.get(position).getFullName(), Toast.LENGTH_SHORT).show();
    }

    private void showDialog(int position){
        //DialogFragment addingCurrencyDialogFragment = CreateCurrencyDialog
          //      .newInstance(CreateCurrencyDialog.CREATE_SOURCE_FRAGMENT, currencyList.get(position));
        //addingCurrencyDialogFragment.setTargetFragment(CurrencyListFragment.this, REQUEST_CURRENCY_DATA);
        //addingCurrencyDialogFragment.show(getFragmentManager(), "CreateCurrencyDialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode){
            case REQUEST_DATA:

                debtAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onChangeItem(Operation object) {
        debtList.add(object);
        Collections.sort(debtList, new Comparator<Operation>() {
            @Override
            public int compare(Operation lhs, Operation rhs) {
                return (int) (rhs.getDate() - lhs.getDate());
            }
        });
        debtAdapter.notifyDataSetChanged();
    }


  /*  @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Нажат " + currencyList.get(position).getFullName(), Toast.LENGTH_SHORT).show();
    }*/
}
