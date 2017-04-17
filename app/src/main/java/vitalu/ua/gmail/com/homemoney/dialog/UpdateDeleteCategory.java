package vitalu.ua.gmail.com.homemoney.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.SourceQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Source;

/**
 * Created by Виталий on 01.02.2016.
 */
public class UpdateDeleteCategory extends DialogFragment implements DialogInterface.OnClickListener {

    public static final String TAG = "myLogMainActivity";

    private static final String OPERATION_DEL_UPD = "vitalu.ua.gmail.com.homemoney.dialog.operation_source";
    public static final String SOURCE_PARENT_OR_CHILD = "vitalu.ua.gmail.com.homemoney.dialog.source_object";
    public static final String EXTRA_DATA = "vitalu.ua.gmail.com.homemoney.dialog.source_upd_del_data";
    private static final int REQUEST_UPD_DEL_DATA = 1;
    private final int MENU_DELETE_SOURCE = 1;
    private final int MENU_UPDATE_SOURCE = 0;
    public static final int CHILD = 1;
    public static final int PARENT = 2;

    public static final int UPDATE_CHILD = 1;
    public static final int UPDATE_PARENT = 2;
    public static final int DELETE_CHILD = 3;
    public static final int DELETE_PARENT = 4;

    int mParentORchild;
    int mAction;

/*    public interface ChengeDataExpandList{
        void changeChildUpdate(Source source);
        void changeParentUpdate(Source source);
        void changeChildDelete(Source source);
        void changeParentDelete(Source source);
    }*/

    Source source;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        source = getArguments().getParcelable(OPERATION_DEL_UPD);
        mParentORchild = getArguments().getInt(SOURCE_PARENT_OR_CHILD);
        String[] action  = {getString(R.string.itemMenuUpdate), getString(R.string.itemMenuDelete)};

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity()).setItems(action, this);

        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
            case MENU_DELETE_SOURCE:
                SourceQuery.removeSource(source);
                if(mParentORchild == CHILD){
                    mAction = DELETE_CHILD;
                }else{
                    mAction = DELETE_PARENT;
                }
                sendResult(Activity.RESULT_OK);
                dismiss();
                break;
            case MENU_UPDATE_SOURCE:

                Log.d(TAG, "MENU_UPDATE_SOURCE");

                DialogFragment dialogFragment = CreateSourceDialog
                        .newInstance(source, CreateSourceDialog.CREATE_SOURCE_FRAGMENT);
                dialogFragment.setTargetFragment(UpdateDeleteCategory.this, REQUEST_UPD_DEL_DATA);
                dialogFragment.show(getFragmentManager(), "UpdateDeleteCategory");

                break;
        }
    }

    public static UpdateDeleteCategory newInstance(Source source, int parentORchild) {//Создание диалога с источником
        Bundle args = new Bundle();
        args.putInt(SOURCE_PARENT_OR_CHILD, parentORchild);
        args.putParcelable(OPERATION_DEL_UPD, source);
        UpdateDeleteCategory fragment = new UpdateDeleteCategory();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode) {//отправка результата фрагменту
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_DATA, source);
        i.putExtra(SOURCE_PARENT_OR_CHILD, mAction);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//получает результат после редактирования

        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_UPD_DEL_DATA) {
            source = (Source)data.getParcelableExtra(CreateSourceDialog.EXTRA_SOURCE_DATA);

            Log.d(TAG, "/---------------onActivityResult---------------------/");
            Log.d(TAG, source.getNameSours());
            Log.d(TAG, String.valueOf(source.getId()));
            Log.d(TAG, String.valueOf(source.getParentId()));
            Log.d(TAG, "/------------------------------------/");

            SourceQuery.updateSource(source);

            if(mParentORchild == CHILD){
                mAction = UPDATE_CHILD;
            }else{
                mAction = UPDATE_PARENT;
            }
            sendResult(Activity.RESULT_OK);
            dismiss();
        }
    }

}
