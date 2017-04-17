package vitalu.ua.gmail.com.homemoney.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.activity.StorageListActivity;
import vitalu.ua.gmail.com.homemoney.adapter.StorageAdapter;
import vitalu.ua.gmail.com.homemoney.database.database_query.StorageQuery;
import vitalu.ua.gmail.com.homemoney.dialog.CreateStorageDialog;
import vitalu.ua.gmail.com.homemoney.model.database_model.Storage;

/**
 * A placeholder fragment containing a simple view.
 */
public class StorageListFragment extends ListFragment
        implements StorageListActivity.OnChangeIteme<Storage>/*, AdapterView.OnItemClickListener*/ {

    public static final String TAG = "myLogMainActivity";
    private static final int REQUEST_STORAGE_DATA = 1;

    private final int MENU_DELETE_STORAGE = 1;
    private final int MENU_UPDATE_STORAGE = 2;

    private List<Storage> listStorage;
    private StorageAdapter storageAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");

        listStorage = StorageQuery.getVisibleStorages();
        storageAdapter = new StorageAdapter(getActivity(), listStorage);
        setListAdapter(storageAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        /*listStorage = StorageQuery.getVisibleStorages();
        storageAdapter = new StorageAdapter(getActivity(), listStorage);*/
        View view = inflater.inflate(R.layout.list_fragment, null);
        ListView listView = (ListView)view.findViewById(android.R.id.list);
        //listView.setAdapter(storageAdapter);
        //listView.setOnItemClickListener(this);
        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.title_list_storage);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, MENU_UPDATE_STORAGE, 0, R.string.itemMenuUpdate);
        menu.add(0, MENU_DELETE_STORAGE, 0, R.string.itemMenuDelete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case MENU_UPDATE_STORAGE:
                showDialog(info.position);
               /* DialogFragment addingStorageDialogFragment = CreateStorageDialog
                        .newInstance(CreateStorageDialog.CREATE_SOURCE_FRAGMENT, listStorage.get(info.position));
                addingStorageDialogFragment.setTargetFragment(StorageListFragment.this, REQUEST_STORAGE_DATA);
                addingStorageDialogFragment.show(getFragmentManager(), "CreateStorageDialog");
                Log.d(TAG, String.valueOf(info.position));*/
                break;
            case MENU_DELETE_STORAGE:
                final Storage storage = listStorage.remove(info.position);
                StorageQuery.removeStorage(storage);
                storageAdapter.notifyDataSetChanged();

                /*Snackbar.make(getActivity().findViewById(R.id.fragmentContainer), R.string.deleted, Snackbar.LENGTH_LONG)
                        .setAction(R.string.cencel_delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StorageQuery.addStorage(storage);
                                listStorage.add(storage);
                                storageAdapter.notifyDataSetChanged();
                            }
                        }).show();*/
               // Log.d(TAG, String.valueOf(info.id));
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onChangeItem(Storage storage) {
        Log.d(TAG, "StorageListFragment - onChangeItem");

        listStorage.add(storage);
        storageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode){
            case REQUEST_STORAGE_DATA:
                Storage storage = (Storage)data.getParcelableExtra(CreateStorageDialog.EXTRA_STORAGE);
                StorageQuery.updateStorage(storage);
                storageAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        showDialog(position);
       /* DialogFragment addingStorageDialogFragment = CreateStorageDialog
                .newInstance(CreateStorageDialog.CREATE_SOURCE_FRAGMENT, listStorage.get(position));
        addingStorageDialogFragment.setTargetFragment(StorageListFragment.this, REQUEST_STORAGE_DATA);
        addingStorageDialogFragment.show(getFragmentManager(), "CreateStorageDialog");*/
    }

    private void showDialog(int position){
        DialogFragment addingStorageDialogFragment = CreateStorageDialog
                .newInstance(CreateStorageDialog.CREATE_SOURCE_FRAGMENT, listStorage.get(position));
        addingStorageDialogFragment.setTargetFragment(StorageListFragment.this, REQUEST_STORAGE_DATA);
        addingStorageDialogFragment.show(getFragmentManager(), "CreateStorageDialog");
    }
 /*   @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DialogFragment addingStorageDialogFragment = CreateStorageDialog
                .newInstance(CreateStorageDialog.CREATE_SOURCE_FRAGMENT, listStorage.get(position));
        addingStorageDialogFragment.setTargetFragment(StorageListFragment.this, REQUEST_STORAGE_DATA);
        addingStorageDialogFragment.show(getFragmentManager(), "CreateStorageDialog");
    }*/
}
