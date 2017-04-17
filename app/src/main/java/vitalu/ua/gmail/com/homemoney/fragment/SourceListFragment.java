package vitalu.ua.gmail.com.homemoney.fragment;


import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.activity.SingleFragmentActivity;
import vitalu.ua.gmail.com.homemoney.activity.SourceActivity;
import vitalu.ua.gmail.com.homemoney.model.database_model.Source;

/**
 * A simple {@link Fragment} subclass.
 */
public class SourceListFragment extends ListFragment implements SingleFragmentActivity.OnChangeIteme<Source> {

    public static final String TAG = "myLogMainActivity";

    public static final int INCOME = 1;
    public static final int OUTCOME = 2;

/*    public interface ChoiceCategoryListener{//Сообщить активити что выбран пункт категории дохода или расхода для замены фрагмента
        void categoryListener(int operation);
    }*/

   // private ChoiceCategoryListener categoryListener;

    public interface FragmentListener{
        void updateFragment(Source source);
    }

    private static FragmentListener fragmentListener;

    public static void setListener(FragmentListener fl){
        fragmentListener = fl;
    }

    public SourceListFragment() {
        // Required empty public constructor
    }

/*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            categoryListener = (ChoiceCategoryListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ChoiceCategoryListener");
        }
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String data[] = new String[] { getResources().getString(R.string.incom_category),
                getResources().getString(R.string.outcome_category) };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_list_category);
        View view = inflater.inflate(R.layout.list_fragment, null);

        Log.d(TAG, "onCreateView ");

        return view;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

       /* if(position == 0) {
            Log.d(TAG, "передаем "+ INCOME);
            categoryListener.categoryListener(INCOME);
        }
        else
            categoryListener.categoryListener(OUTCOME);*/

        ////////////////////////////////////////////////////////////////////////////////
        Intent intent = new Intent(getActivity(), SourceActivity.class);
        if(position == 0) {
            intent.putExtra(SourceActivity.EXTRA_CATEGORY, INCOME);
        }else{
            intent.putExtra(SourceActivity.EXTRA_CATEGORY, OUTCOME);
        }

        startActivity(intent);
        ////////////////////////////////////////////////////////////////////////////////

        //Toast.makeText(getActivity(), "position = " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChangeItem(Source object) {
        Log.d(TAG, "onChangeItem в  SourceListFragment");

        fragmentListener.updateFragment(object);
    }
}
