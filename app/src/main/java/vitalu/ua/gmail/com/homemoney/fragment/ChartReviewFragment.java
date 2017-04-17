package vitalu.ua.gmail.com.homemoney.fragment;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.activity.SingleFragmentActivity;
import vitalu.ua.gmail.com.homemoney.database.database_query.ReviewQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Review;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartReviewFragment extends Fragment implements SingleFragmentActivity.OnChangeIteme<Review> {

    public static final String EXTRA_CATEGORY = "vitalu.ua.gmail.com.homemoney.fragment.extra_category";

    private LineChart mChart;
    int reviewId;

    List<Review> listReview;

    public ChartReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_review, null);
        getActivity().setTitle(R.string.main_review);

        reviewId = getArguments().getInt(EXTRA_CATEGORY);
        listReview = ReviewQuery.getReview(reviewId);

        mChart = (LineChart) view.findViewById(R.id.chart);

        //mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");

        // add an empty data object
        mChart.setData(new LineData());
//        mChart.getXAxis().setDrawLabels(false);
//        mChart.getXAxis().setDrawGridLines(false);

        mChart.invalidate();
        addDataSet();

        return view;
    }

    private void addDataSet() {

        LineData data = mChart.getData();

        if (data != null) {

            int count = (data.getDataSetCount() + 1);

            ArrayList<Entry> yVals = new ArrayList<Entry>();

            if (data.getXValCount() == 0) {

                if (!listReview.isEmpty())
                    for (int i = 0; i < listReview.size(); i++) {
                        data.addXValue(Utils.getDate(listReview.get(i).getDateReview()));
                    }
            }

            if (!listReview.isEmpty())
                for (int i = 0; i < listReview.size(); i++) {
                    yVals.add(new Entry((float) (listReview.get(i).getAmountReview()), i));
                }


            if (!listReview.isEmpty()) {
                LineDataSet set = new LineDataSet(yVals, listReview.get(0).getNameReview());
                set.setLineWidth(2.5f);
                set.setCircleSize(4.5f);

                set.setLineWidth(3.5f);
                set.setCircleSize(5.5f);
                set.setHighLightColor(Color.rgb(244, 117, 117));
                set.setColor(Color.RED/*ColorTemplate.VORDIPLOM_COLORS[0]*/);
                set.setCircleColor(Color.RED/*ColorTemplate.VORDIPLOM_COLORS[0]*/);
                set.setValueTextSize(12f);

                data.addDataSet(set);
                mChart.notifyDataSetChanged();
                mChart.invalidate();
            }
        }
    }

    public static ChartReviewFragment newInstance(int reviewId) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_CATEGORY, reviewId);
        ChartReviewFragment fragment = new ChartReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onChangeItem(Review object) {
        listReview.add(object);
        mChart.notifyDataSetChanged();
        Toast.makeText(getActivity(), "onChangeItem", Toast.LENGTH_SHORT).show();
    }
}
