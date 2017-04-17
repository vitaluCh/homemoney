package vitalu.ua.gmail.com.homemoney.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.adapter.StorageAdapter;
import vitalu.ua.gmail.com.homemoney.database.database_query.StorageQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Storage;

/**
 * Created by Виталий on 15.01.2016.
 */
public class ChoiceStorageDialog extends DialogFragment
        implements AdapterView.OnItemClickListener,
        View.OnClickListener/*, CreateStorageDialog.AddingStorageListener*/{

    public static final String TAG = "myLogMainActivity";

    public static final String EXTRA_STORAGE_DATA = "vitalu.ua.gmail.com.homemoney.dialog.storage";

    public static final int REQUEST_STORAGE_DATA = 1;

    private List<Storage> listStorage;
    private StorageAdapter adapter;
    private Storage storage;

    //Button addButtonChoice;

 /*   private OnChoiceClickListener onChoiceClickListener;

    public interface OnChoiceClickListener{
        public void onItemChoice(Storage storage);
    }*/

  /*  public ChoiceStorageDialog(){}
    public ChoiceStorageDialog(OnChoiceClickListener onChoiceClickListener){
        this.onChoiceClickListener = onChoiceClickListener;
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        listStorage = StorageQuery.getVisibleStorages();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_choice_storage);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_choice_fragment, null);

        Button addButtonChoice = (Button)view.findViewById(R.id.addButtonChoice);
        addButtonChoice.setOnClickListener(this);

        ListView listView = (ListView)view.findViewById(R.id.listChoiceFragment);
        adapter = new StorageAdapter(getActivity(), listStorage);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d(TAG, String.valueOf(position));
        Log.d(TAG, String.valueOf(listStorage.get(position).getId()));
        Log.d(TAG, listStorage.get(position).getNameStorage());
        Log.d(TAG, String.valueOf(id));
        //Log.d(TAG, String.valueOf(listStorage.get(position).getId()));

        storage = listStorage.get(position);
        sendResult(Activity.RESULT_OK);//////////////////////////////////////////////////////////////
        dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addButtonChoice:
                DialogFragment addingStorageDialogFragment = CreateStorageDialog
                        .newInstance(CreateStorageDialog.CREATE_SOURCE_FRAGMENT);//источник создания диалог или activity
                addingStorageDialogFragment.setTargetFragment(ChoiceStorageDialog.this, REQUEST_STORAGE_DATA);
                addingStorageDialogFragment.show(getFragmentManager(), "CreateStorageDialog");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Получение результата");
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_STORAGE_DATA) {
            Storage storage = (Storage)data.getParcelableExtra(CreateStorageDialog.EXTRA_STORAGE);

            long idNewStorage = StorageQuery.addStorage(storage);
            storage.setId((int)idNewStorage);
            Log.d(TAG, String.valueOf(storage.getId()));
            Log.d(TAG, "listStorage size = " + String.valueOf(listStorage.size()));
            listStorage.add(storage);
            Log.d(TAG, "listStorage size = " + String.valueOf(listStorage.size()));

            adapter.notifyDataSetChanged();
        }
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_STORAGE_DATA, storage);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }


/*    @Override
    public void onAdded(Storage storage) {
        long l = StorageQuery.addStorage(storage);
        storage.setId((int)l);
        Log.d(TAG, String.valueOf(storage.getId()));
        Log.d(TAG, "listStorage size = " + String.valueOf(listStorage.size()));
        listStorage.add(storage);
        Log.d(TAG, "listStorage size = " + String.valueOf(listStorage.size()));
        //listStorage.clear();
        //listStorage = StorageQuery.getVisibleStorages();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddCencel() {

    }*/

}
