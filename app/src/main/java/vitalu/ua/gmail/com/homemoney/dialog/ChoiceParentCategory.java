package vitalu.ua.gmail.com.homemoney.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.SourceQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Source;

/**
 * Created by Виталий on 29.01.2016.
 */
public class ChoiceParentCategory extends DialogFragment implements AdapterView.OnItemClickListener{

    public static final int INCOME_PARENT = 1;
    public static final int OUTCOME_PARENT = 2;
    private static final String CREATE_PARENT = "vitalu.ua.gmail.com.homemoney.dialog.create_parent";
    public static final String EXTRA_PARENT_DATA = "vitalu.ua.gmail.com.homemoney.dialog.parent_data";
   // public static final String ETRA_ITEM_LIST = "vitalu.ua.gmail.com.homemoney.dialog.item_list";

    private List<Source> sourceParentList;
    private ArrayAdapter<Source> sourceAdapter;
    private int idParent = 0;
   // private int itemList;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int mSourceCreate = getArguments().getInt(CREATE_PARENT);

        sourceParentList = new ArrayList<>();
        sourceParentList.add(new Source().setId(0).setNameSours(getString(R.string.not_category)));/////////////////////////////////
        if(mSourceCreate == INCOME_PARENT){
            sourceParentList.addAll(SourceQuery.getVisibleInComeSourceParent());
            //sourceParentList = SourceQuery.getVisibleInComeSourceParent();
        }else{
            sourceParentList.addAll(SourceQuery.getVisibleOutComeSourceParent());
            //sourceParentList = SourceQuery.getVisibleOutComeSourceParent();
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_choice_parent);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_choice_parent_category, null);

        ListView listView = (ListView)view.findViewById(android.R.id.list);
        sourceAdapter = new ArrayAdapter<Source>(getActivity(),android.R.layout.simple_list_item_1, sourceParentList);
        listView.setAdapter(sourceAdapter);
        listView.setOnItemClickListener(this);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*Toast.makeText(getActivity(), "position = " + sourceParentList.get(position).getId()
                + " id = " + id, Toast.LENGTH_SHORT).show();*/
        idParent = sourceParentList.get(position).getId();
       // itemList = position;
        sendResult(Activity.RESULT_OK);
        dismiss();
    }

    public static ChoiceParentCategory newInstance(int parentCreate) {
        Bundle args = new Bundle();
        args.putInt(CREATE_PARENT, parentCreate);
        ChoiceParentCategory fragment = new ChoiceParentCategory();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode) {//отправка результата фрагменту
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_PARENT_DATA, idParent);
        //i.putExtra(ETRA_ITEM_LIST, itemList);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
