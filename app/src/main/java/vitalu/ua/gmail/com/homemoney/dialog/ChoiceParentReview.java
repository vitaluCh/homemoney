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
import vitalu.ua.gmail.com.homemoney.database.database_query.ReviewQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Review;

/**
 * Created by Виталий on 02.03.2016.
 */
public class ChoiceParentReview extends DialogFragment implements AdapterView.OnItemClickListener{

    public static final String EXTRA_PARENT_DATA = "vitalu.ua.gmail.com.homemoney.dialog.parent_data";


    private List<Review> reviewParentList;
    private ArrayAdapter<Review> reviewAdapter;
    private int idParent = 0;
    // private int itemList;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        reviewParentList = new ArrayList<>();
        reviewParentList.add(new Review().setId(0).setNameReview(getString(R.string.not_category)));/////////////////////////////////

        reviewParentList.addAll(ReviewQuery.getParent());


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_choice_parent);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_choice_parent_category, null);

        ListView listView = (ListView)view.findViewById(android.R.id.list);
        reviewAdapter = new ArrayAdapter<Review>(getActivity(),android.R.layout.simple_list_item_1, reviewParentList);
        listView.setAdapter(reviewAdapter);
        listView.setOnItemClickListener(this);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*Toast.makeText(getActivity(), "position = " + sourceParentList.get(position).getId()
                + " id = " + id, Toast.LENGTH_SHORT).show();*/
        idParent = reviewParentList.get(position).getId();
        // itemList = position;
        sendResult(Activity.RESULT_OK);
        dismiss();
    }

    public static ChoiceParentReview newInstance() {
        ChoiceParentReview fragment = new ChoiceParentReview();
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
