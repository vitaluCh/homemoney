package vitalu.ua.gmail.com.homemoney.fragment;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.activity.SingleFragmentActivity;
import vitalu.ua.gmail.com.homemoney.adapter.SourceExpandableListAdapter;
import vitalu.ua.gmail.com.homemoney.database.database_query.SourceQuery;
import vitalu.ua.gmail.com.homemoney.dialog.UpdateDeleteCategory;
import vitalu.ua.gmail.com.homemoney.model.database_model.Source;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpandListSourceFragment extends Fragment
        implements SourceExpandableListAdapter.OnChoiceSourceListener,
        SingleFragmentActivity.OnChangeIteme<Source>, SourceListFragment.FragmentListener{

    public static final String TAG = "myLogMainActivity";

  /*  private final int MENU_DELETE_SOURCE = 1;
    private final int MENU_UPDATE_SOURCE = 2;
*/
    public static final String TYPE_OPERATION = "type_operation";
    public static final int INCOM_OPERATION = 1;
    public static final int OUTCOM_OPERATION = 2;
    private static final int REQUEST_UPD_DEL_DATA = 1;




    private int operationType;

    private int posParent;
    private int posChild;
    int mParentCat;

    private SourceExpandableListAdapter adapter;

    ArrayList<Map<String, Source>> groupData;
    ArrayList<ArrayList<Map<String, Source>>> childData;
    List<Source> listParent;



    public ExpandListSourceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SourceListFragment.setListener(this);
      /*  TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);*/
       // return textView;
        operationType = getArguments().getInt(TYPE_OPERATION, INCOM_OPERATION);



       /* final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_choice_source);*/

        View view = inflater.inflate(R.layout.list_fragment_source, null);

        if(operationType == INCOM_OPERATION){
            getActivity().setTitle(R.string.title_list_category_income);
            // коллекция для групп
            groupData = SourceQuery.getVisibleInComeParentData();

            // общая коллекция для коллекций элементов
            childData = SourceQuery.getVisibleInComeSourceChild();
            //listParent = SourceQuery.getVisibleInComeSourceParent();
        }else{
            getActivity().setTitle(R.string.title_list_category_outcome);
            // коллекция для групп
            groupData = SourceQuery.getVisibleOutComeParentData();

            // общая коллекция для коллекций элементов
            childData = SourceQuery.getVisibleOutComeSourceChild();
            //listParent = SourceQuery.getVisibleOutComeSourceParent();
        }

        /*Button addButtonChoice = (Button)view.findViewById(R.id.addButtonChoice);
        addButtonChoice.setVisibility(View.INVISIBLE);*/

        // список аттрибутов групп для чтения
        String groupFrom[] = new String[] {SourceQuery.GROUP_NAME};
        // список ID view-элементов, в которые будет помещены аттрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};

        // список аттрибутов элементов для чтения
        String childFrom[] = new String[] {SourceQuery.CHILD_NAME};
        // список ID view-элементов, в которые будет помещены аттрибуты элементов
        int childTo[] = new int[] {android.R.id.text1};

        //Log.d(TAG, "Перед адаптером");

        adapter = new SourceExpandableListAdapter(
                getActivity(), groupData, childData, this);

        ExpandableListView elvSource = (ExpandableListView)view.findViewById(R.id.elvFragment);
        elvSource.setAdapter(adapter);

        ///////////////////////////////////////////////////////////////////////////////////////
        // нажатие на элемент
        elvSource.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
              /*  Log.d(TAG, "onChildClick groupPosition = " + groupPosition +
                        " childPosition = " + childPosition +
                        " id = " + id);*/

                posParent = groupPosition;
                posChild = childPosition;

                mParentCat = childData.get(groupPosition).get(childPosition).get(SourceQuery.CHILD_NAME).getParentId();

                DialogFragment dialogFragment = UpdateDeleteCategory
                        .newInstance(childData.get(groupPosition).get(childPosition).get(SourceQuery.CHILD_NAME),
                                UpdateDeleteCategory.CHILD);
                dialogFragment.setTargetFragment(ExpandListSourceFragment.this, REQUEST_UPD_DEL_DATA);
                dialogFragment.show(getFragmentManager(), "UpdateDeleteCategory");

                return false;
            }
        });
/*
        // нажатие на группу
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

        //registerForContextMenu(elvSource);

        return view;
    }

    @Override
    public void choiceSource(Source source, int position) {//////////////////
        DialogFragment dialogFragment = UpdateDeleteCategory
                .newInstance(groupData.get(position).get(SourceQuery.GROUP_NAME),
                        UpdateDeleteCategory.PARENT);
        dialogFragment.setTargetFragment(ExpandListSourceFragment.this, REQUEST_UPD_DEL_DATA);
        dialogFragment.show(getFragmentManager(), "UpdateDeleteCategory");

        posParent = position;
        mParentCat = groupData.get(position).get(SourceQuery.GROUP_NAME).getParentId();
        /*Toast.makeText(getActivity(),
                source.getNameSours(), Toast.LENGTH_SHORT).show();*/
    }

    public static ExpandListSourceFragment newInstance(int operationType) {
        Bundle args = new Bundle();
        args.putInt(TYPE_OPERATION, operationType);
        ExpandListSourceFragment fragment = new ExpandListSourceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onChangeItem(Source object) {//изменить состояние списка ели добавлено через fab
        //return;
    }

    @Override
    public void updateFragment(Source source) {//изменить состояние списка ели добавлено через fab
       // Log.d(TAG, "updateFragment в  ExpandListSourceFragment");
        if (source.getType().getId() == operationType) {
           // Log.d(TAG, "source.getType().getId() == operationType");
            if (source.getParentId() <= 0) {
              //  Log.d(TAG, "source.getParentId() <= 0");
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
               // Log.d(TAG, "source.getParentId() > 0");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_UPD_DEL_DATA) {
            Source source =
                    (Source)data.getParcelableExtra(UpdateDeleteCategory.EXTRA_DATA);

            int action = data.getIntExtra(UpdateDeleteCategory.SOURCE_PARENT_OR_CHILD, 0);

            switch (action){
                case UpdateDeleteCategory.DELETE_CHILD:
                    childData.get(posParent).remove(posChild);
                    break;
                case UpdateDeleteCategory.DELETE_PARENT:
                    groupData.remove(posParent);
                    break;
                case UpdateDeleteCategory.UPDATE_CHILD:
                    if(mParentCat != source.getParentId()){
                        //childData.get(posParent).remove(posChild);
                        Log.d(TAG, "mParentCat != source.getParentId()");
                        Log.d(TAG, String.valueOf(source.getParentId()));

                        if(source.getParentId() > 0) {
                            Log.d(TAG, "mParentCat > 0");
                            ///////////////////////////////////////////Код повторяется!!!!!!!!!!!!!!!!!!!!!!!!!!!!!,,,,,,,,,????????????
                            Map<String, Source> m = new HashMap<String, Source>();

                            m.put(SourceQuery.CHILD_NAME, source); // название подгруппы

                            if (operationType == INCOM_OPERATION) {

                                listParent = SourceQuery.getVisibleInComeSourceParent();
                            } else {

                                listParent = SourceQuery.getVisibleOutComeSourceParent();
                            }

                            int index = 0;
                            for (int i = 0; i < listParent.size(); i++) {
                                if (listParent.get(i).getId() == source.getParentId()) {
                                    //Log.d(TAG, s.getNameSours());
                                    index = i;
                                }
                            }
                            childData.get(index).add(m);

                            childData.get(posParent).remove(posChild);
                        }
                        else if(source.getParentId() == 0){
                            Log.d(TAG, "mParentCat == 0");
                            /*SourceQuery.updateSource(source);*/
                            Map<String, Source> m = new HashMap<>();

                            // заполняем коллекцию групп из массива с названиями групп

                            // заполняем список аттрибутов для каждой группы

                            m.put(SourceQuery.GROUP_NAME, source); // имя группы
                            groupData.add(m);
                            // коллекция для групп

                            ///////////////////////////////
                            ArrayList<Map<String, Source>> childDataItem = new ArrayList<Map<String, Source>>();
                            childData.add(childDataItem);

                            childData.get(posParent).remove(posChild);
                           // SourceQuery.updateSource(source);
                        }
                        ///////////////////////////////////////////
                    }

                    break;
                case UpdateDeleteCategory.UPDATE_PARENT:
                    if(mParentCat != source.getParentId()){
                        Log.d(TAG, "В UPDATE_PARENT  mParentCat != source.getParentId()");
                        groupData.remove(posParent);
                        ///////////////////////////////////////////Код повторяется!!!!!!!!!!!!!!!!!!!!!!!!!!!!!,,,,,,,,,????????????
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
                    break;
            }

            adapter.notifyDataSetChanged();

        }
    }

}
