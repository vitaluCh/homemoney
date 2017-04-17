package vitalu.ua.gmail.com.homemoney.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.activity.ChartReviewActivity;
import vitalu.ua.gmail.com.homemoney.activity.SingleFragmentActivity;
import vitalu.ua.gmail.com.homemoney.adapter.ReviewExpandableListAdapter;
import vitalu.ua.gmail.com.homemoney.database.database_query.ReviewQuery;
import vitalu.ua.gmail.com.homemoney.database.database_query.SourceQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Review;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpandReviewFragment extends Fragment
        implements SingleFragmentActivity.OnChangeIteme<Review>,
        ReviewExpandableListAdapter.OnChoiceSourceListener{

    private ReviewExpandableListAdapter adapter;

    ArrayList<Map<String, Review>> groupData;
    ArrayList<ArrayList<Map<String, Review>>> childData;
    List<Review> listParent;

    public ExpandReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.main_review);
        View view = inflater.inflate(R.layout.list_fragment_source, null);

            // коллекция для групп
            groupData = ReviewQuery.getParentData();

            // общая коллекция для коллекций элементов
            childData = ReviewQuery.getChildData();

        // список аттрибутов групп для чтения
        String groupFrom[] = new String[] {SourceQuery.GROUP_NAME};
        // список ID view-элементов, в которые будет помещены аттрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};

        // список аттрибутов элементов для чтения
        String childFrom[] = new String[] {SourceQuery.CHILD_NAME};
        // список ID view-элементов, в которые будет помещены аттрибуты элементов
        int childTo[] = new int[] {android.R.id.text1};

        //Log.d(TAG, "Перед адаптером");

        adapter = new ReviewExpandableListAdapter(
                getActivity(), groupData, childData, this);

        ExpandableListView elvSource = (ExpandableListView)view.findViewById(R.id.elvFragment);
        elvSource.setAdapter(adapter);

        ///////////////////////////////////////////////////////////////////////////////////////
        // нажатие на элемент
        elvSource.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(getActivity(), ChartReviewActivity.class);
                /*Toast.makeText(getActivity(),
                        childData.get(groupPosition).get(childPosition).get(ReviewQuery.CHILD_NAME).getId()+"",
                        Toast.LENGTH_SHORT).show();*/
                intent.putExtra(ChartReviewActivity.EXTRA_CATEGORY,
                        childData.get(groupPosition).get(childPosition).get(ReviewQuery.CHILD_NAME).getId());
                startActivity(intent);
              /*  Log.d(TAG, "onChildClick groupPosition = " + groupPosition +
                        " childPosition = " + childPosition +
                        " id = " + id);*/
/*
                posParent = groupPosition;
                posChild = childPosition;

                mParentCat = childData.get(groupPosition).get(childPosition).get(ReviewQuery.CHILD_NAME).getParent();

                DialogFragment dialogFragment = UpdateDeleteCategory
                        .newInstance(childData.get(groupPosition).get(childPosition).get(ReviewQuery.CHILD_NAME),
                                UpdateDeleteCategory.CHILD);
                dialogFragment.setTargetFragment(ExpandListSourceFragment.this, REQUEST_UPD_DEL_DATA);
                dialogFragment.show(getFragmentManager(), "UpdateDeleteCategory");*/

                return false;
            }
        });


        /*TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;*/
        return view;
    }

    @Override
    public void onChangeItem(Review object) {

    }

    @Override
    public void choiceSource(Review source, int position) {

    }
}
