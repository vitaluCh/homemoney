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
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.adapter.SourceExpandableListAdapter;
import vitalu.ua.gmail.com.homemoney.database.database_query.SourceQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Source;

/**
 * Created by Виталий on 18.01.2016.
 */
public class ChoiceSourceDialog extends DialogFragment
        implements View.OnClickListener, SourceExpandableListAdapter.OnChoiceSourceListener{

    public static final String TAG = "myLogMainActivity";

    public static final String TYPE_OPERATION = "type_operation";
    public static final int INCOM_OPERATION = 1;
    public static final int OUTCOM_OPERATION = 2;

    public static final String EXTRA_SOURCE_DATA = "vitalu.ua.gmail.com.homemoney.dialog.source";
    private static final int REQUEST_SOURCE_DATA = 1;

    Source source;
    int operationType;
    ArrayList<Map<String, Source>> groupData;
    ArrayList<ArrayList<Map<String, Source>>> childData;
    List<Source> listParent;
    SourceExpandableListAdapter adapter;

    //private ExpandableListView elvSource;
    //Button addButtonChoice;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        operationType = getArguments().getInt(TYPE_OPERATION, INCOM_OPERATION);

        /*ArrayList<Map<String, Source>> groupData;
        ArrayList<ArrayList<Map<String, Source>>> childData;*/

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_choice_source);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_choice_source_fragment, null);

        if(operationType == INCOM_OPERATION){
            // коллекция для групп
            groupData = SourceQuery.getVisibleInComeParentData();

            // общая коллекция для коллекций элементов
            childData = SourceQuery.getVisibleInComeSourceChild();
        }else{
            // коллекция для групп
            groupData = SourceQuery.getVisibleOutComeParentData();

            // общая коллекция для коллекций элементов
            childData = SourceQuery.getVisibleOutComeSourceChild();
        }

        Button addButtonChoice = (Button)view.findViewById(R.id.addButtonChoice);
        addButtonChoice.setOnClickListener(this);

        // список аттрибутов групп для чтения
        String groupFrom[] = new String[] {SourceQuery.GROUP_NAME};
        // список ID view-элементов, в которые будет помещены аттрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};

        // список аттрибутов элементов для чтения
        String childFrom[] = new String[] {SourceQuery.CHILD_NAME};
        // список ID view-элементов, в которые будет помещены аттрибуты элементов
        int childTo[] = new int[] {android.R.id.text1};

        Log.d(TAG, "Перед адаптером");

        adapter = new SourceExpandableListAdapter(
                getActivity(), groupData, childData, this);

        ExpandableListView elvSource = (ExpandableListView)view.findViewById(R.id.elvFragment);
        elvSource.setAdapter(adapter);

        ///////////////////////////////////////////////////////////////////////////////////////
        // нажатие на элемент
        elvSource.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.d(TAG, "onChildClick groupPosition = " + groupPosition +
                        " childPosition = " + childPosition +
                        " id = " + id);
                source = childData.get(groupPosition).get(childPosition).get(SourceQuery.CHILD_NAME);
                sendResult(Activity.RESULT_OK);
                dismiss();

                return false;
            }
        });

/*        // нажатие на группу
        elvSource.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                Log.d(TAG, "onGroupClick groupPosition = " + groupPosition +
                        " id = " + id);
                // блокируем дальнейшую обработку события для группы с позицией 1
                //if (groupPosition == 1) return true;

                return false;
            }
        });*/

        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    @Override
    public void onClick(View v) {
        DialogFragment addingTaskDialogFragment = CreateSourceDialog.newInstance(CreateSourceDialog.CREATE_SOURCE_FRAGMENT);
        addingTaskDialogFragment.setTargetFragment(ChoiceSourceDialog.this, REQUEST_SOURCE_DATA);
        addingTaskDialogFragment.show(getFragmentManager(), "CreateSourceDialog");
    }

    public static ChoiceSourceDialog newInstance(int operationType) {
        Bundle args = new Bundle();
        args.putInt(TYPE_OPERATION, operationType);
        ChoiceSourceDialog fragment = new ChoiceSourceDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void choiceSource(Source source, int position) {
        this.source = source;
        sendResult(Activity.RESULT_OK);
        dismiss();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_SOURCE_DATA, source);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_SOURCE_DATA) {
            Source source =
                    (Source)data.getParcelableExtra(CreateSourceDialog.EXTRA_SOURCE_DATA);

            source.setId((int) SourceQuery.addSource(source));

         /*   currencyList.add(source);

            currencyAdapter.notifyDataSetChanged();*/

            /////////////////
            Log.d(TAG, "updateFragment в  ExpandListSourceFragment");
            if (source.getType().getId() == operationType) {
                Log.d(TAG, "source.getType().getId() == operationType");
                if (source.getParentId() <= 0) {
                    Log.d(TAG, "source.getParentId() <= 0");
                    Map<String, Source> m = new HashMap<>();

                    // заполняем коллекцию групп из массива с названиями групп

                    // заполняем список аттрибутов для каждой группы

                    m.put(SourceQuery.GROUP_NAME, source); // имя группы
                    groupData.add(m);
                    // коллекция для групп

                    ///////////////////////////////
                    ArrayList<Map<String, Source>> childDataItem = new ArrayList<Map<String, Source>>();
                    childData.add(childDataItem);

                } else {
                    Log.d(TAG, "source.getParentId() > 0");
                    // коллекция для элементов одной группы
                    //ArrayList<Map<String, Source>> childDataItem = new ArrayList<Map<String, Source>>();

                    Map<String, Source> m = new HashMap<String, Source>();

                    m.put(SourceQuery.CHILD_NAME, source); // название подгруппы

                    if(operationType == INCOM_OPERATION){

                        listParent = SourceQuery.getVisibleInComeSourceParent();
                    }else{

                        listParent = SourceQuery.getVisibleOutComeSourceParent();
                    }

                    int index = 0;
                    for(int i = 0; i<listParent.size(); i++){
                        if(listParent.get(i).getId()==source.getParentId()) {
                            //Log.d(TAG, s.getNameSours());
                            index =i;
                        }
                    }
                    childData.get(index).add(m);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}
