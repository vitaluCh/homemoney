package vitalu.ua.gmail.com.homemoney.fragment.report.pie_category;

import java.util.ArrayList;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.database.database_query.ReportQuery;
import vitalu.ua.gmail.com.homemoney.model.ReportCategory;

/**
 * Created by Виталий on 26.02.2016.
 */
public class PieCategoryMonth extends PieCategoryFragment {
    @Override
    public List<ReportCategory> getList() {
        if(!ReportQuery.getReportCategorysWeek(mOperation).isEmpty()) {
            return ReportQuery.getReportCategorysMonth(mOperation).get(mPosition);
        }else{
            return new ArrayList<>();
        }

    }

/*    PieChart pieChart;

    float[]yData;// = {5,10,15,30,40};
    String[] xData;// = {"LG","Lenovo","HTC","Aple","Asus"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPosition = getArguments().getInt(EXTRA_POSITION);
        mOperation = getArguments().getInt(EXTRA_OPERATION);

        if(!ReportQuery.getReportCategorysMonth(mOperation).isEmpty()) {

            listSum = ReportQuery.getReportCategorysMonth(mOperation).get(mPosition);

            yData = new float[listSum.size()];
            for(int i = 0; i < listSum.size(); i++){
                yData[i] = (float) listSum.get(i).getSumm();
            }
            xData = new String[listSum.size()];
            for(int i = 0; i < listSum.size(); i++){
                xData[i] = listSum.get(i).getCategoryName();
            }
        }
       *//* yData = new float[listSum.size()];
        for(int i = 0; i < listSum.size(); i++){
            yData[i] = (float) listSum.get(i).getSumm();
        }
        xData = new String[listSum.size()];
        for(int i = 0; i < listSum.size(); i++){
            xData[i] = listSum.get(i).getCategoryName();
        }
        Log.d(TAG, "mPosition = " + mPosition);*//*
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pie_chart, null);
        TextView tvEmpty = (TextView)view.findViewById(R.id.tvEmpty);

        if(listSum.isEmpty()){
            tvEmpty.setVisibility(View.VISIBLE);
            return view;
        }

        pieChart = (PieChart)view.findViewById(R.id.chart);

        // linearLayout.addView(pieChart);
        // linearLayout.setBackgroundColor(Color.LTGRAY);

        pieChart.setUsePercentValues(true);
        pieChart.setDescription(getString(R.string.report_category));

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(7);
        pieChart.setTransparentCircleRadius(10);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                if (entry == null)
                    return;
                Toast.makeText(getActivity(),
                        xData[entry.getXIndex()] + " = " + entry.getVal(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        addData();

        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);

        return view;
    }

    private void addData(){
        ArrayList<Entry> yVals1 = new ArrayList<>();

        for(int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<>();

        for(int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        PieDataSet dataSet = new PieDataSet(yVals1, getString(R.string.category));
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        ArrayList<Integer> colors = new ArrayList<>();

        for(int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for(int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for(int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for(int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for(int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);


        pieChart.setData(data);
        pieChart.highlightValue(null);
        pieChart.invalidate();

    }*/
}

