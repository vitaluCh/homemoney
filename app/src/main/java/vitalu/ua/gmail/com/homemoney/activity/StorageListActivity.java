package vitalu.ua.gmail.com.homemoney.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.util.Log;
import android.view.View;

import vitalu.ua.gmail.com.homemoney.database.database_query.StorageQuery;
import vitalu.ua.gmail.com.homemoney.dialog.CreateStorageDialog;
import vitalu.ua.gmail.com.homemoney.fragment.StorageListFragment;
import vitalu.ua.gmail.com.homemoney.model.database_model.Storage;

public class StorageListActivity extends SingleFragmentActivity
        implements CreateStorageDialog.AddingStorageListener {

    public static final String TAG = "myLogMainActivity";

    @Override
    protected Fragment createFragment() {
        return new StorageListFragment();
    }

    @Override
    public void onClick(View v) {
        DialogFragment addingTaskDialogFragment = CreateStorageDialog
                .newInstance(CreateStorageDialog.CREATE_SOURCE_ACTIVITY);
        addingTaskDialogFragment.show(getFragmentManager(), "CreateStorageDialog");
    }

    @Override
    public void onAdded(Storage storage) {

        storage.setId((int)StorageQuery.addStorage(storage));
        onChangeIteme.onChangeItem(storage);
        Log.d(TAG, "StorageListActivity - onAdded");
    }
}
