package vitalu.ua.gmail.com.homemoney.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.activity.TamplateTabActivity;
import vitalu.ua.gmail.com.homemoney.activity.TemplateActivity;
import vitalu.ua.gmail.com.homemoney.adapter.OperationAdapter;
import vitalu.ua.gmail.com.homemoney.database.database_query.PatternQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;

/**
 * A simple {@link Fragment} subclass.
 */
public class InComListTemplateFragment extends ListFragment implements TamplateTabActivity.IncomeChangeCommunicator {

    public static final String TAG = "myLogMainActivity";

    public static final int INCOM_OPERATION = 1;
    public static final int RESOULT_CHENGED = 1;

    private List<Operation> listOperation;
    private OperationAdapter templateAdapter;


    public InComListTemplateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.d(TAG, "onActivityCreated");

        listOperation = PatternQuery.getTemplate(INCOM_OPERATION);
        templateAdapter = new OperationAdapter(getActivity(), listOperation);
        setListAdapter(templateAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent = new Intent(getActivity(), TemplateActivity.class);
        intent.putExtra(TemplateActivity.EXTRA_OPERATION, INCOM_OPERATION);
        intent.putExtra(TemplateActivity.EXTRA_POSITION, position);
        startActivity(intent);
        //startActivityForResult(intent, RESOULT_CHENGED);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        /*TextView tv = (TextView)view.findViewById(R.id.tvfragday);
        tv.setText("Income")*/;
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Context context = getActivity();
        ((TamplateTabActivity)getActivity()).incomeChangeCommunicator = this;
    }


    @Override
    public void onItemIncomeChanged(Operation operation) {
        listOperation.add(0, operation);
        templateAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        //listOperation = PatternQuery.getTemplate(INCOM_OPERATION);
        templateAdapter.notifyDataSetChanged();
        //Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();
    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Toast.makeText(getActivity(), "Результат", Toast.LENGTH_SHORT).show();

        //Log.d(TAG, "Получение результата");
        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode){
            case RESOULT_CHENGED:
                //String calcData = (String)data.getStringExtra(DialogCalculator.EXTRA_CALC_DATA);
                int position = data.getIntExtra(TemplateActivity.REQUEST_DATA, -1);

                //Log.d(TAG, "Получение результата REQUEST_CALC_DATA " + calcData);
                listOperation.remove(position);
                templateAdapter.notifyDataSetChanged();
                break;

        }
    }*/
}
