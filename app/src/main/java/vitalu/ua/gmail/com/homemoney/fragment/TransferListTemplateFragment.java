package vitalu.ua.gmail.com.homemoney.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.activity.TamplateTabActivity;
import vitalu.ua.gmail.com.homemoney.activity.TemplateActivity;
import vitalu.ua.gmail.com.homemoney.adapter.TransferOperationAdapter;
import vitalu.ua.gmail.com.homemoney.database.database_query.PatternQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferListTemplateFragment extends ListFragment implements TamplateTabActivity.TransferChangeCommunicator {

    public static final String TAG = "myLogMainActivity";

    public static final int TRANSFER_OPERATION = 3;

    private List<TransferOperation> listOperation;
    private TransferOperationAdapter templateAdapter;


    public TransferListTemplateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.d(TAG, "onActivityCreated");

        listOperation = new ArrayList<>();
        List<Operation> list = PatternQuery.getTemplate(TRANSFER_OPERATION);

        for(int i = list.size()-1; i >= 0; ){

            Operation in = list.get(i);
            Operation out = list.get(--i);

            listOperation.add(new TransferOperation(out, in));
            i--;

            Log.d(TAG, "Запись");
        }
        //Collections.reverse(listOperation);
        templateAdapter = new TransferOperationAdapter(getActivity(), listOperation);
        setListAdapter(templateAdapter);
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
    public void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent = new Intent(getActivity(), TemplateActivity.class);
        intent.putExtra(TemplateActivity.EXTRA_OPERATION, TRANSFER_OPERATION);
        intent.putExtra(TemplateActivity.EXTRA_POSITION, position);
        startActivity(intent);
        //startActivityForResult(intent, RESOULT_CHENGED);

    }

   @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Context context = getActivity();
        ((TamplateTabActivity)getActivity()).transferChangeCommunicator = this;
    }

    @Override
    public void onTransferItemChanged(TransferOperation operation) {
        listOperation.add(0, operation);
        templateAdapter.notifyDataSetChanged();
    }


}
