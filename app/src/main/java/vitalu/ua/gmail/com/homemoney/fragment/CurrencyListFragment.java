package vitalu.ua.gmail.com.homemoney.fragment;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.activity.SingleFragmentActivity;
import vitalu.ua.gmail.com.homemoney.adapter.CurrencyAdapter;
import vitalu.ua.gmail.com.homemoney.database.database_query.CurrencyQuery;
import vitalu.ua.gmail.com.homemoney.dialog.CreateCurrencyDialog;
import vitalu.ua.gmail.com.homemoney.model.database_model.Currency;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyListFragment extends ListFragment
        implements SingleFragmentActivity.OnChangeIteme<Currency>/*, AdapterView.OnItemClickListener*/{

    private static final int REQUEST_CURRENCY_DATA = 1;
    private final int MENU_DELETE_CURRENCY = 1;
    private final int MENU_UPDATE_CURRENCY = 2;

    private List<Currency> currencyList;
    private CurrencyAdapter currencyAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.d(TAG, "onActivityCreated");

        currencyList = CurrencyQuery.getCurrencys();
        currencyAdapter = new CurrencyAdapter(getActivity(), currencyList);
        setListAdapter(currencyAdapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_list_currencye);

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
                showDialog(info.position);
               /* DialogFragment addingStorageDialogFragment = CreateStorageDialog
                        .newInstance(CreateStorageDialog.CREATE_SOURCE_FRAGMENT, listStorage.get(info.position));
                addingStorageDialogFragment.setTargetFragment(StorageListFragment.this, REQUEST_STORAGE_DATA);
                addingStorageDialogFragment.show(getFragmentManager(), "CreateStorageDialog");
                Log.d(TAG, String.valueOf(info.position));*/
                break;
            case MENU_DELETE_CURRENCY:
                final Currency currency = currencyList.remove(info.position);
                CurrencyQuery.removeCurrency(currency);
                currencyAdapter.notifyDataSetChanged();
                //Log.d(TAG, String.valueOf(info.id));
               /* Snackbar.make(getActivity().findViewById(R.id.fragmentContainer), R.string.deleted, Snackbar.LENGTH_LONG)
                        .setAction(R.string.cencel_delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CurrencyQuery.addCurrency(currency);
                                currencyList.add(currency);
                                currencyAdapter.notifyDataSetChanged();
                            }
                        }).show();*/
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onChangeItem(Currency currency) {
        currencyList.add(currency);
        //currencyList = CurrencyQuery.getCurrencys();
        currencyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        showDialog(position);
        //Toast.makeText(getActivity(), "Нажат " + currencyList.get(position).getFullName(), Toast.LENGTH_SHORT).show();
    }

    private void showDialog(int position){
        DialogFragment addingCurrencyDialogFragment = CreateCurrencyDialog
                .newInstance(CreateCurrencyDialog.CREATE_SOURCE_FRAGMENT, currencyList.get(position));
        addingCurrencyDialogFragment.setTargetFragment(CurrencyListFragment.this, REQUEST_CURRENCY_DATA);
        addingCurrencyDialogFragment.show(getFragmentManager(), "CreateCurrencyDialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode){
            case REQUEST_CURRENCY_DATA:
                Currency currency = (Currency)data.getParcelableExtra(CreateCurrencyDialog.EXTRA_CURRENCY_DATA);
                CurrencyQuery.updateCurrency(currency);
                currencyAdapter.notifyDataSetChanged();
                break;
        }
    }

  /*  @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Нажат " + currencyList.get(position).getFullName(), Toast.LENGTH_SHORT).show();
    }*/
}
