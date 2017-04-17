package vitalu.ua.gmail.com.homemoney.activity.report;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.ReportQuery;
import vitalu.ua.gmail.com.homemoney.enums.TabOperation;
import vitalu.ua.gmail.com.homemoney.fragment.report.pie_category.PieCategoryFragment;
import vitalu.ua.gmail.com.homemoney.model.ReportCategory;
import vitalu.ua.gmail.com.homemoney.utils.CurrentStateOperation;
import vitalu.ua.gmail.com.homemoney.utils.ParceDateOperation;

public class ReportCategoryActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "myLogMainActivity";

    public static final String EXTRA_REPORT = "vitalu.ua.gmail.com.homemoney.activity.extra_report";
    public static final String EXTRA_OPERATION = "vitalu.ua.gmail.com.homemoney.activity.extra_operation";

    public static final int INCOM_OPERATION = 1;
    public static final int OUTCOM_OPERATION = 2;
  //  public static final int PERIOD_OPERATION = 3;
    public static final int PIE_CHART = 4;
    public static final int BAR_CHART = 5;

    ViewPager viewPager;
    FragmentTransaction fTrans;
    List<List<ReportCategory>> listReport;


    //List<List<Operation>>listDayOperations;
  /*  OperationEachOfPeriodSelector operationSelector;
    OperationsOfEachOfPeriods mOperations;*/

    private TextView tvTabWeek;
    private TextView tvTabMonth;
    private TextView tvTabYear;

    private TextView tvDateListOperation;

    int mCurrentPosition = -1;
    int mOperation;
    int mChart;

    /*FragmentManager fm;*/
    CurrentStateOperation stateOperation = CurrentStateOperation.getInstance();

    TabOperation mTabOperation;
    Calendar calendar;

    int idOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ///////////////////////////////////////////////////////////////////////////////////////////////
       // mChart = getIntent().getIntExtra(EXTRA_REPORT, 0);
        mOperation = getIntent().getIntExtra(EXTRA_OPERATION, 0);

        listReport = ReportQuery.getReportCategorysWeek(mOperation);

        if(mOperation == INCOM_OPERATION) {
            setTitle(R.string.report_income_category);
        }else if(mOperation ==  OUTCOM_OPERATION){
            setTitle(R.string.report_outcome_category);
        }

        Log.d(TAG, String.valueOf(listReport.size() + "size onCreate ReportCategoryActivity"));

        viewPager = (ViewPager)findViewById(R.id.pager);

        mTabOperation = TabOperation.WEEK_OPERATION;

        FragmentManager fm = getSupportFragmentManager();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDateListOperation = (TextView)findViewById(R.id.tvDateListOperation);

        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return PieCategoryFragment.newInstance(position, PieCategoryFragment.WEEK, mOperation);
            }

            @Override
            public int getCount() {
                return listReport.size();
            }
        });

        viewPager.setCurrentItem(listReport.size() - 1);

        if(!listReport.isEmpty()) {
            tvDateListOperation.setText("<<  "
                    + ParceDateOperation.getWeek(listReport.get(listReport.size() - 1).get(0).getDate())
                    + "  >>");
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // tvDateListOperation.setText("<<  " + Utils.getDate(listReport.get(0).get(0).getDate()) + "  >>");
                tvDateListOperation.setText("<<  "
                        + ParceDateOperation.getWeek(listReport.get(position).get(0).getDate())
                        + "  >>");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initTabs();
    }

    private void initTabs() {

        tvTabWeek = (TextView)findViewById(R.id.tvTabWeek);
        tvTabMonth = (TextView)findViewById(R.id.tvTabMonth);
        tvTabYear = (TextView)findViewById(R.id.tvTabYear);

        tvTabWeek.setOnClickListener(this);
        tvTabMonth.setOnClickListener(this);
        tvTabYear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        fTrans = getSupportFragmentManager().beginTransaction();

        mCurrentPosition = -1;

        int id = v.getId();
        switch (id) {
            case R.id.tvTabWeek:
                Log.d(TAG, "tvTabWeek");
                fTrans.replace(R.id.fragmentContainer,
                        PieCategoryFragment.newInstance(0, PieCategoryFragment.WEEK, mOperation));

                mTabOperation = TabOperation.WEEK_OPERATION;
                initViewPager(mTabOperation);
                break;
            case R.id.tvTabMonth:
                Log.d(TAG, "tvTabMonth");
                fTrans.replace(R.id.fragmentContainer,
                        PieCategoryFragment.newInstance(0, PieCategoryFragment.MONTH, mOperation));

                mTabOperation = TabOperation.MONTH_OPERATION;
                initViewPager(mTabOperation);
                break;
            case R.id.tvTabYear:
                fTrans.replace(R.id.fragmentContainer,
                        PieCategoryFragment.newInstance(0, PieCategoryFragment.YEAR, mOperation));

                mTabOperation = TabOperation.YEAR_OPERATION;
                initViewPager(mTabOperation);
                break;
        }
        changeTabs((TextView) v);
        fTrans.commit();

    }

    private void initViewPager(TabOperation mTab) {

        mTabOperation = mTab;
        switch (mTabOperation){
            case WEEK_OPERATION:
               // listReport = ReportQuery.getListOperationsWeek();
                listReport = ReportQuery.getReportCategorysWeek(mOperation);
                break;
            case MONTH_OPERATION:
               // listReport = ReportQuery.getListOperationsMonth();
                listReport = ReportQuery.getReportCategorysMonth(mOperation);
                break;
            case YEAR_OPERATION:
                //listReport = ReportQuery.getListOperationsYear();
                listReport = ReportQuery.getReportCategorysYear(mOperation);
                break;
        }

        viewPager = (ViewPager)findViewById(R.id.pager);

        FragmentManager fm = getSupportFragmentManager();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDateListOperation = (TextView)findViewById(R.id.tvDateListOperation);

        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (mTabOperation){
                    case WEEK_OPERATION:
                        fragment = PieCategoryFragment.newInstance(position, PieCategoryFragment.WEEK, mOperation);
                        break;
                    case MONTH_OPERATION:
                        fragment = PieCategoryFragment.newInstance(position, PieCategoryFragment.MONTH, mOperation);
                        break;
                    case YEAR_OPERATION:
                        fragment = PieCategoryFragment.newInstance(position, PieCategoryFragment.YEAR, mOperation);
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return listReport.size();
            }
        });

        viewPager.setCurrentItem(listReport.size() - 1);

        if(!listReport.isEmpty()) {
            switch (mTabOperation){
                case WEEK_OPERATION:
                    tvDateListOperation.setText("<<  "
                            + ParceDateOperation.getWeek(listReport.get(listReport.size() - 1).get(0).getDate())
                            + "  >>");
                    break;
                case MONTH_OPERATION:
                    tvDateListOperation.setText("<<  "
                            + ParceDateOperation.getMonth(listReport.get(listReport.size() - 1).get(0).getDate())
                            + "  >>");
                    break;
                case YEAR_OPERATION:
                    tvDateListOperation.setText("<<  "
                            + ParceDateOperation.geOnetYear(listReport.get(listReport.size() - 1).get(0).getDate())
                            + "  >>");
                    break;
            }
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (mTabOperation){
                    case WEEK_OPERATION:
                        tvDateListOperation.setText("<<  "
                                + ParceDateOperation.getWeek(listReport.get(position).get(0).getDate())
                                + "  >>");
                        break;
                    case MONTH_OPERATION:
                        tvDateListOperation.setText("<<  "
                                + ParceDateOperation.getMonth(listReport.get(position).get(0).getDate())
                                + "  >>");
                        break;
                    case YEAR_OPERATION:
                        tvDateListOperation.setText("<<  "
                                + ParceDateOperation.geOnetYear(listReport.get(position).get(0).getDate())
                                + "  >>");
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void changeTabs(TextView tv){

        tvTabWeek.setTextColor(getResources().getColor(R.color.black));
        tvTabMonth.setTextColor(getResources().getColor(R.color.black));
        tvTabYear.setTextColor(getResources().getColor(R.color.black));

        tvTabWeek.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.selector_tab_primary);
        tvTabMonth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.selector_tab_primary);
        tvTabYear.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.selector_tab_primary);

        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.selector_tab_white);
    }

}

