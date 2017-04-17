package vitalu.ua.gmail.com.homemoney.activity.report;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.ReportQuery;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int OPERATION_INCOM = 1;
    private static final int OPERATION_OUTCOM = 2;

    PieChart pieChart;
    float[]yData;// = {80,20};
    /*String str1 = getText(R.string.db_incom).toString();
    String str2 = getString(R.string.db_outcom)*/;
    String[] xData;// = {"sadad", "cefcerfvr"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.main_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ///////////////////////////////////////////////
        Calendar calendar = Calendar.getInstance();
        yData = new float[2];
        yData[0] = (float)ReportQuery.getOneMonth(calendar.getTimeInMillis(), OPERATION_INCOM);
        yData[1] = (float)ReportQuery.getOneMonth(calendar.getTimeInMillis(), OPERATION_OUTCOM);

        TextView tvEmpty = (TextView)findViewById(R.id.tvEmpty);
        if(yData[0] == 0.0 && yData[1] == 0.0){
            tvEmpty.setVisibility(View.VISIBLE);
        }

        xData = new String[2];
        xData[0] = getString(R.string.db_incom);
        xData[1] = getString(R.string.db_outcom);
        /////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////
        TextView tvDate = (TextView)findViewById(R.id.tvDate);
        tvDate.setText(Utils.getMonthAndYear(calendar.getTimeInMillis()));

        ImageView imInfo = (ImageView)findViewById(R.id.imInfo);
        TextView tvInfo = (TextView)findViewById(R.id.tvInfo);
        imInfo.setOnClickListener(this);
        tvInfo.setOnClickListener(this);

        TextView tvOutCircle = (TextView)findViewById(R.id.tvOutCircle);
        ImageView imOutCircle = (ImageView)findViewById(R.id.imOutCircle);
        tvOutCircle.setOnClickListener(this);
        imOutCircle.setOnClickListener(this);

        TextView tvInCircle = (TextView)findViewById(R.id.tvInCircle);
        ImageView imInCircle = (ImageView)findViewById(R.id.imInCircle);
        tvInCircle.setOnClickListener(this);
        imInCircle.setOnClickListener(this);

/*        TextView tvOutStolb = (TextView)findViewById(R.id.tvOutStolb);
        ImageView imOutStolb = (ImageView)findViewById(R.id.imOutStolb);
        tvOutStolb.setOnClickListener(this);
        imOutStolb.setOnClickListener(this);

        TextView tvInStolb = (TextView)findViewById(R.id.tvInStolb);
        ImageView imInStolb = (ImageView)findViewById(R.id.imInStolb);
        tvInStolb.setOnClickListener(this);
        imInStolb.setOnClickListener(this);
        ////////////////////////////////////////*///////////////////////////////////////////

        pieChart = (PieChart)findViewById(R.id.chart);


        pieChart.setUsePercentValues(true);
        pieChart.setDescription(getString(R.string.report_in_out));

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
                Toast.makeText(ReportActivity.this,
                        xData[entry.getXIndex()] + " = " + entry.getVal()/* + "%"*/,
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
    }

    private void addData(){
        ArrayList<Entry> yVals1 = new ArrayList<>();

        for(int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<>();

        for(int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);


        pieChart.setData(data);
        pieChart.highlightValue(null);
        pieChart.invalidate();

    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){
            case R.id.imInfo:
            case R.id.tvInfo:
               /* Toast.makeText(ReportActivity.this,"Подробно столбик",
                        Toast.LENGTH_SHORT).show();*/
                intent = new Intent(this, ReportTabActivity.class);
                intent.putExtra(ReportTabActivity.EXTRA_REPORT, ReportTabActivity.PERIOD_OPERATION);
                startActivity(intent);
                break;
            case R.id.tvOutCircle:
            case R.id.imOutCircle:
             /*   Toast.makeText(ReportActivity.this,"Расход круг",
                        Toast.LENGTH_SHORT).show();*/
                intent = new Intent(this, ReportCategoryActivity.class);
                //intent.putExtra(ReportTabActivity.EXTRA_REPORT, ReportTabActivity.PIE_CHART);
                intent.putExtra(ReportCategoryActivity.EXTRA_OPERATION, ReportCategoryActivity.OUTCOM_OPERATION);
                startActivity(intent);
                break;
            case R.id.tvInCircle:
            case R.id.imInCircle:
               /* Toast.makeText(ReportActivity.this,"Доход круг",
                        Toast.LENGTH_SHORT).show();*/
                intent = new Intent(this, ReportCategoryActivity.class);
                //intent.putExtra(ReportTabActivity.EXTRA_REPORT, ReportTabActivity.PIE_CHART);
                intent.putExtra(ReportCategoryActivity.EXTRA_OPERATION, ReportCategoryActivity.INCOM_OPERATION);
                startActivity(intent);
                break;
         /*   case R.id.tvOutStolb:
            case R.id.imOutStolb:
                Toast.makeText(ReportActivity.this,"Расход" +
                                "столбик",
                        Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ReportCategoryActivity.class);
                intent.putExtra(ReportTabActivity.EXTRA_REPORT, ReportTabActivity.BAR_CHART);
                intent.putExtra(ReportTabActivity.EXTRA_OPERATION, ReportTabActivity.OUTCOM_OPERATION);
                startActivity(intent);
                break;
            case R.id.tvInStolb:
            case R.id.imInStolb:
                Toast.makeText(ReportActivity.this,"Доход столбик",
                        Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ReportCategoryActivity.class);
                intent.putExtra(ReportTabActivity.EXTRA_REPORT, ReportTabActivity.BAR_CHART);
                intent.putExtra(ReportTabActivity.EXTRA_OPERATION, ReportTabActivity.INCOM_OPERATION);
                startActivity(intent);
                break;*/
        }
    }
}
