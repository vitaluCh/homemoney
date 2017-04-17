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
import vitalu.ua.gmail.com.homemoney.fragment.report.bar_period.ReportOfPeriodFragment;
import vitalu.ua.gmail.com.homemoney.model.ReportInOut;
import vitalu.ua.gmail.com.homemoney.utils.CurrentStateOperation;
import vitalu.ua.gmail.com.homemoney.utils.ParceDateOperation;

public class ReportTabActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "myLogMainActivity";

    public static final String EXTRA_REPORT = "vitalu.ua.gmail.com.homemoney.activity.extra_report";
    public static final String EXTRA_OPERATION = "vitalu.ua.gmail.com.homemoney.activity.extra_operation";

    public static final int INCOM_OPERATION = 1;
    public static final int OUTCOM_OPERATION = 2;
    public static final int PERIOD_OPERATION = 3;
    public static final int PIE_CHART = 4;
    public static final int BAR_CHART = 5;

    ViewPager viewPager;
    FragmentTransaction fTrans;
    List<List<ReportInOut>> listReport;


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
        setTitle(R.string.report_in_out_period);

        ///////////////////////////////////////////////////////////////////////////////////////////////
        mOperation = getIntent().getIntExtra(EXTRA_REPORT, 0);
        mChart = getIntent().getIntExtra(EXTRA_OPERATION, 0);

        if(mOperation == PERIOD_OPERATION) {
            listReport = ReportQuery.getListOperationsWeek();
            Log.d(TAG, String.valueOf(listReport.size()));

        }

        Log.d(TAG, String.valueOf(listReport.size()));
        ////////////////////////////////////////////////////////////////////////////////////////////////

     /*   idOperation = getIntent().getIntExtra(EXTRA_OPERATION, 0);

       // operationSelector = new OperationEachOfPeriodSelector();

        if(idOperation == 0) {
            idOperation = stateOperation.getIdOperation();
        }*/

        viewPager = (ViewPager)findViewById(R.id.pager);

        mTabOperation = TabOperation.DAY_OPERATION;
        //////////////////////////////////////////////////////////////////////
        //mOperations = operationSelector.getOperations(idOperation);
       // mOperations.addAll(OperationQuery.getDaysOfDay(idOperation));
        //////////////////////////////
    /*    if(idOperation == INCOM_OPERATION) {
            setTitle(R.string.main_incom);
            //listDayOperations = OperationQuery.getDaysOfDay(OperationQuery.OPERATION_INCOM);
        }else if(idOperation == OUTCOM_OPERATION){
            setTitle(R.string.main_outcom);
            //listDayOperations = OperationQuery.getDaysOfDay(OperationQuery.OPERATION_OUTCOM);
        }*/
        ////////////////////////////////////////////////////////////////////////
        //Log.d(TAG, "------------onCreate = OpeationsListActivity " + idOperation);

        FragmentManager fm = getSupportFragmentManager();
/*        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new DayOperationsFragment();
            FragmentTransaction fTrans = fm.beginTransaction();
            fTrans.add(R.id.fragmentContainer, fragment);
            fTrans.addToBackStack(null);
            fTrans.commit();
        }*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDateListOperation = (TextView)findViewById(R.id.tvDateListOperation);

        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                // return DayOperationsFragment.newInstance(position, idOperation);
                return ReportOfPeriodFragment.newInstance(position, ReportOfPeriodFragment.WEEK);
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

/*        if(!mOperations.isEmpty()) {
            tvDateListOperation.setText("<<  "
                    + Utils.getDate(mOperations.getDate(mOperations.size() - 1))
                    + "  >>");
        }else{
            tvDateListOperation.setText("<<  " + getString(R.string.empty) + "  >>");
        } */

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

/*    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "------------onResume " + idOperation);
        initViewPager(mTabOperation);
        //Toast.makeText(OpeationsListActivity.this, "onResume " + idOperation, Toast.LENGTH_SHORT).show();
        //idOperation = getIntent().getIntExtra(EXTRA_OPERATION, INCOM_OPERATION);
    }*/


/*
    @Override
    protected void onDestroy() {
        super.onDestroy();

        stateOperation.setIdOperation(idOperation);
        Log.d(TAG, "------------onDestroy " + idOperation);
    }
*/

    @Override
    public void onClick(View v) {

        fTrans = getSupportFragmentManager().beginTransaction();

        mCurrentPosition = -1;

        int id = v.getId();
     //   if(!mOperations.isEmpty()) {
            switch (id) {
                case R.id.tvTabWeek:
                    fTrans.replace(R.id.fragmentContainer,
                            ReportOfPeriodFragment.newInstance(0, ReportOfPeriodFragment.WEEK));

                    mTabOperation = TabOperation.WEEK_OPERATION;
                    initViewPager(mTabOperation);
                    break;
                case R.id.tvTabMonth:
                    fTrans.replace(R.id.fragmentContainer,
                            ReportOfPeriodFragment.newInstance(0, ReportOfPeriodFragment.MONTH));

                    mTabOperation = TabOperation.MONTH_OPERATION;
                    initViewPager(mTabOperation);

                   /* fTrans.replace(R.id.fragmentContainer,
                            TimeOfPeriodOperations.newInstance(0, idOperation, TimeOfPeriodOperations.MONTH_OPERATION));

                    mTabOperation = TabOperation.MONTH_OPERATION;
                    initViewPager(mTabOperation);*/
                    break;
                case R.id.tvTabYear:
                    fTrans.replace(R.id.fragmentContainer,
                            ReportOfPeriodFragment.newInstance(0, ReportOfPeriodFragment.YEAR));

                    mTabOperation = TabOperation.YEAR_OPERATION;
                    initViewPager(mTabOperation);
                    break;
            }
      //  }

        //fTrans.addToBackStack(null);

        changeTabs((TextView) v);
        fTrans.commit();

    }

    private void initViewPager(TabOperation mTab) {

        mTabOperation = mTab;
        switch (mTabOperation){
            case WEEK_OPERATION:
                listReport = ReportQuery.getListOperationsWeek();

                break;
            case MONTH_OPERATION:
                listReport = ReportQuery.getListOperationsMonth();

                break;
            case YEAR_OPERATION:
                listReport = ReportQuery.getListOperationsYear();

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
                        fragment = ReportOfPeriodFragment.newInstance(position, ReportOfPeriodFragment.WEEK);
                        break;
                    case MONTH_OPERATION:
                        fragment = ReportOfPeriodFragment.newInstance(position, ReportOfPeriodFragment.MONTH);
                        break;
                    case YEAR_OPERATION:
                        fragment = ReportOfPeriodFragment.newInstance(position, ReportOfPeriodFragment.YEAR);
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
                            + ParceDateOperation.getYear(listReport.get(listReport.size() - 1).get(0).getDate())
                            + "  >>");
                    break;
                case YEAR_OPERATION:
                    tvDateListOperation.setText("<<  "
                            + ParceDateOperation.geOnetYear(listReport.get(listReport.size() - 1).get(0).getDate())
                            + "  >>");
                    break;
            }
            /*tvDateListOperation.setText("<<  "
                    + ParceDateOperation.getWeek(listReport.get(listReport.size() - 1).get(0).getDate())
                    + "  >>");*/
        }

/*        if(!mOperations.isEmpty()) {
            tvDateListOperation.setText("<<  "
                    + Utils.getDate(mOperations.getDate(mOperations.size() - 1))
                    + "  >>");
        }else{
            tvDateListOperation.setText("<<  " + getString(R.string.empty) + "  >>");
        } */

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
                                + ParceDateOperation.getYear(listReport.get(position).get(0).getDate())
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


    /*    @Override
        public void changeDate(String date) {
            //tvDateListOperation.setText("<<  " + date + "  >>");
        }*/
    //StringDateOperation stringDateOperation = StringDateOperation.getInstance();

/*    private void initViewPager(TabOperation operation){

        //StringDateOperation stringDateOperation = StringDateOperation.getInstance();
        // fTrans = getSupportFragmentManager().beginTransaction();

        switch (operation){
            case DAY_OPERATION:
                mOperations.replace(OperationQuery.getDaysOfDay(idOperation));

               *//* if(idOperation == INCOM_OPERATION) {
                    //listDayOperations = OperationQuery.getDaysOfDay(OperationQuery.OPERATION_INCOM);
                }else if(idOperation == OUTCOM_OPERATION){
                    listDayOperations = OperationQuery.getDaysOfDay(OperationQuery.OPERATION_OUTCOM);
                }else if(idOperation == TRANSFER_OPERATION){
                    listDayOperations = OperationQuery.getDaysOfDay(OperationQuery.OPERATION_TRANSFER);
                }*//*
                break;
            case WEEK_OPERATION:
                mOperations.replace(OperationQuery.getListOperationsWeek(idOperation));
              *//*  if(idOperation == INCOM_OPERATION) {
                    listDayOperations = OperationQuery.getListOperationsWeek(OperationQuery.OPERATION_INCOM);
                }else if(idOperation == OUTCOM_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsWeek(OperationQuery.OPERATION_OUTCOM);
                }else if(idOperation == TRANSFER_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsWeek(OperationQuery.OPERATION_TRANSFER);
                }*//*
                break;
            case MONTH_OPERATION:
                mOperations.replace(OperationQuery.getListOperationsMonth(idOperation));
              *//*  if(idOperation == INCOM_OPERATION) {
                    listDayOperations = OperationQuery.getListOperationsMonth(OperationQuery.OPERATION_INCOM);
                }else if(idOperation == OUTCOM_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsMonth(OperationQuery.OPERATION_OUTCOM);
                }else if(idOperation == TRANSFER_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsMonth(OperationQuery.OPERATION_TRANSFER);
                }*//*
                break;
            case YEAR_OPERATION:
                mOperations.replace(OperationQuery.getListOperationsYear(idOperation));
               *//* if(idOperation == INCOM_OPERATION) {
                    listDayOperations = OperationQuery.getListOperationsYear(OperationQuery.OPERATION_INCOM);
                }else if(idOperation == OUTCOM_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsYear(OperationQuery.OPERATION_OUTCOM);
                }else if(idOperation == TRANSFER_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsYear(OperationQuery.OPERATION_TRANSFER);
                }*//*
                break;
        }



        viewPager = null;
        viewPager = (ViewPager)findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;

                switch (mTabOperation) {
                    case DAY_OPERATION:
                        //fragment = DayOperationsFragment.newInstance(position, idOperation);
                        fragment = TimeOfPeriodOperations.newInstance(position, idOperation, TimeOfPeriodOperations.DAY_OPERATION);

                        //fTrans.replace(R.id.fragmentContainer, fragment);
                        break;
                    case WEEK_OPERATION:
                        //fragment = WeekOperationsFragment.newInstance(position, idOperation);
                        fragment = TimeOfPeriodOperations.newInstance(position, idOperation, TimeOfPeriodOperations.WEEK_OPERATION);
                        //fTrans.replace(R.id.fragmentContainer, fragment);
                        break;
                    case MONTH_OPERATION:
                        //fragment = MonthOperationsFragment.newInstance(position, idOperation);
                        fragment = TimeOfPeriodOperations.newInstance(position, idOperation, TimeOfPeriodOperations.MONTH_OPERATION);
                        break;
                    case YEAR_OPERATION:
                        //fragment = YearOperationsFragment.newInstance(position, idOperation);
                        fragment = TimeOfPeriodOperations.newInstance(position, idOperation, TimeOfPeriodOperations.YEAR_OPERATION);
                        break;
                }

                ////////////////////////////////////
                //  tvDateListOperation.setText("<<  " + Utils.getDate(listDayOperations.get(viewPager.getCurrentItem()).get(0).getDate()) + "  >>");
                ///////////////////////////////////
                *//*fTrans.addToBackStack(null);
                fTrans.commit();*//*
                return fragment;//DayOperationsFragment.newInstance(position, idOperation);
            }

            @Override
            public int getCount() {
                return mOperations.size();
            }
        });

        if( mCurrentPosition < 0) {
            viewPager.setCurrentItem(mOperations.size() - 1);
        }else{
            viewPager.setCurrentItem(mCurrentPosition);
        }

        if(!mOperations.isEmpty()) {

            if(mCurrentPosition < 0) {
                switch (mTabOperation) {
                    case DAY_OPERATION:
                        tvDateListOperation.setText("<<  " + Utils.getDate(mOperations.getDate(mOperations.size() - 1)) + "  >>");
                        //fTrans.replace(R.id.fragmentContainer, fragment);
                        break;
                    case WEEK_OPERATION:
                        tvDateListOperation.setText("<<  " + ParceDateOperation.getWeek(mOperations.getDate(mOperations.size() - 1)) + "  >>");

                        //fTrans.replace(R.id.fragmentContainer, fragment);
                        break;
                    case MONTH_OPERATION:
                        tvDateListOperation.setText("<<  " + ParceDateOperation.getMonth(mOperations.getDate(mOperations.size() - 1)) + "  >>");
                        break;
                    case YEAR_OPERATION:
                        tvDateListOperation.setText("<<  " + ParceDateOperation.getYear(mOperations.getDate(mOperations.size() - 1)) + "  >>");
                        //tvDateListOperation.setText("<<  " + stringDateOperation.listYear.get(mOperations.size() - 1) + "  >>");
                        break;
                }
            }else{
                switch (mTabOperation) {
                    case DAY_OPERATION:
                        tvDateListOperation.setText("<<  " + Utils.getDate(mOperations.getDate(mCurrentPosition)) + "  >>");
                        //fTrans.replace(R.id.fragmentContainer, fragment);
                        break;
                    case WEEK_OPERATION:
                        tvDateListOperation.setText("<<  " + ParceDateOperation.getWeek(mOperations.getDate(mCurrentPosition)) + "  >>");

                        //fTrans.replace(R.id.fragmentContainer, fragment);
                        break;
                    case MONTH_OPERATION:
                        tvDateListOperation.setText("<<  " + ParceDateOperation.getMonth(mOperations.getDate(mCurrentPosition)) + "  >>");
                        break;
                    case YEAR_OPERATION:
                        tvDateListOperation.setText("<<  " + ParceDateOperation.getYear(mOperations.getDate(mCurrentPosition)) + "  >>");

                        //tvDateListOperation.setText("<<  " + stringDateOperation.listYear.get(mCurrentPosition) + "  >>");
                        break;
                }
            }
        }else{
            tvDateListOperation.setText("<<  " + getString(R.string.empty) + "  >>");
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mCurrentPosition = position;

                switch (mTabOperation) {
                    case DAY_OPERATION:
                        tvDateListOperation.setText("<<  " + Utils.getDate(mOperations.getDate(position)) + "  >>");
                        //fTrans.replace(R.id.fragmentContainer, fragment);
                        break;
                    case WEEK_OPERATION:
                        tvDateListOperation.setText("<<  " + ParceDateOperation.getWeek(mOperations.getDate(position)) + "  >>");
                        //tvDateListOperation.setText("<<  " + stringDateOperation.listWeek.get(position) + "  >>");

                        //fTrans.replace(R.id.fragmentContainer, fragment);
                        break;
                    case MONTH_OPERATION:
                        tvDateListOperation.setText("<<  " + ParceDateOperation.getMonth(mOperations.getDate(position)) + "  >>");
                        break;
                    case YEAR_OPERATION:
                        tvDateListOperation.setText("<<  " + ParceDateOperation.getYear(mOperations.getDate(position)) + "  >>");
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }*/

}
