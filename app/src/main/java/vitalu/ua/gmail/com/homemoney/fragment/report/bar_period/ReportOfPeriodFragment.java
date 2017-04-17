package vitalu.ua.gmail.com.homemoney.fragment.report.bar_period;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.model.ReportInOut;

/**
 * Created by Виталий on 22.02.2016.
 */
public class ReportOfPeriodFragment extends Fragment{

    public static final String TAG = "myLogMainActivity";

    BarChart chart;

    protected static final String EXTRA_POSITION = "vitalu.ua.gmail.com.homemoney.fragment.report.extra_position";
    protected static final String EXTRA_PERIOD = "vitalu.ua.gmail.com.homemoney.fragment.report.extra_period";
    public static final int WEEK = 1;
    public static final int MONTH = 2;
    public static final int YEAR = 3;

    List<ReportInOut> listSumWeeks;
    int mPosition;

 /*   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(EXTRA_POSITION);
        listSumWeeks = ReportQuery.getListOperationsWeek().get(mPosition);
    }*/

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bar_chart, null);
        chart = (BarChart) view.findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();

        if(listSumWeeks.isEmpty()){
            TextView tvEmpty = (TextView)view.findViewById(R.id.tvEmpty);
            tvEmpty.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            BarEntry v1e1 = new BarEntry((float)listSumWeeks.get(i).getSumInCome(), i); // Jan
            valueSet1.add(v1e1);
        }
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            BarEntry v2e1 = new BarEntry((float)listSumWeeks.get(i).getSumOutCome(), i); // Jan
            valueSet2.add(v2e1);
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, getString(R.string.db_incom));
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, getString(R.string.db_outcom));
        barDataSet2.setColor(Color.RED);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add(getString(R.string.month_pn));
        xAxis.add(getString(R.string.month_vt));
        xAxis.add(getString(R.string.month_sr));
        xAxis.add(getString(R.string.month_ch));
        xAxis.add(getString(R.string.month_pt));
        xAxis.add(getString(R.string.month_sb));
        xAxis.add(getString(R.string.month_vs));

        return xAxis;
    }*/

    public static ReportOfPeriodFragment newInstance(int position, int period) {//позиция в списке

        ReportOfPeriodFragment fragment = null;

        Bundle args = new Bundle();
        args.putInt(EXTRA_POSITION, position);
        args.putInt(EXTRA_PERIOD, period);

        if(period == WEEK) {
            fragment = new ReportPeriodOfWeek();
        }else if(period == MONTH){
            fragment = new ReportPeriodOfMonth();
        }else if(period == YEAR){
            fragment = new ReportPeriodOfYear();
        }
        fragment.setArguments(args);
        return fragment;
    }

}
