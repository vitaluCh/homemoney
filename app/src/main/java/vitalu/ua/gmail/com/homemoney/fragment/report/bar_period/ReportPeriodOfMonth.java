package vitalu.ua.gmail.com.homemoney.fragment.report.bar_period;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.ReportQuery;

/**
 * Created by Виталий on 22.02.2016.
 */
public class ReportPeriodOfMonth extends ReportOfPeriodFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(EXTRA_POSITION);
        listSumWeeks = ReportQuery.getListOperationsMonth().get(mPosition);
    }

    @Override
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
        for(int i = 0; i < 12; i++){
            BarEntry v1e1 = new BarEntry((float)listSumWeeks.get(i).getSumInCome(), i); // Jan
            valueSet1.add(v1e1);
        }
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        for(int i = 0; i < 12; i++){
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
        xAxis.add("ЯНВ");
        xAxis.add("ФЕВ");
        xAxis.add("МАР");
        xAxis.add("АПР");
        xAxis.add("МАЙ");
        xAxis.add("ИЮН");
        xAxis.add("ИЮЛ");
        xAxis.add("АВГ");
        xAxis.add("СЕН");
        xAxis.add("ОКТ");
        xAxis.add("НОЯ");
        xAxis.add("ДЕК");


        return xAxis;
    }
}
