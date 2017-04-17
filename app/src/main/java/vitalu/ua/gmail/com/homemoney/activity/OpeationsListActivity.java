package vitalu.ua.gmail.com.homemoney.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.OperationQuery;
import vitalu.ua.gmail.com.homemoney.dialog.CreateOperationDialog;
import vitalu.ua.gmail.com.homemoney.dialog.CreateTransferDialog;
import vitalu.ua.gmail.com.homemoney.enums.TabOperation;
import vitalu.ua.gmail.com.homemoney.fragment.TimeOfPeriodOperations;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;
import vitalu.ua.gmail.com.homemoney.model.factory_method.all_period.OperationEachOfPeriodSelector;
import vitalu.ua.gmail.com.homemoney.model.factory_method.all_period.OperationsOfEachOfPeriods;
import vitalu.ua.gmail.com.homemoney.utils.CurrentStateOperation;
import vitalu.ua.gmail.com.homemoney.utils.ParceDateOperation;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

public class OpeationsListActivity extends AppCompatActivity implements View.OnClickListener,
        CreateOperationDialog.OnItemChanged, CreateTransferDialog.OnItemChanged/*, DayOperationsFragment.OnChangeDate*/ {

    public static final String TAG = "myLogMainActivity";

    public static final String EXTRA_OPERATION = "vitalu.ua.gmail.com.homemoney.activity.extra_operation";
    public static final int INCOM_OPERATION = 1;
    public static final int OUTCOM_OPERATION = 2;
    public static final int TRANSFER_OPERATION = 3;
    //private int COUNT_TABS = 4;//количество tab
    ViewPager viewPager;
    FragmentTransaction fTrans;
    //List<List<Operation>>listDayOperations;
    OperationEachOfPeriodSelector operationSelector;
    OperationsOfEachOfPeriods mOperations;

    //private int COUNT_TABS = 4;//количество tab
    private TextView tvTabDay;
    private TextView tvTabWeek;
    private TextView tvTabMonth;
    private TextView tvTabYear;
    private TextView tvDateListOperation;

    int mCurrentPosition = -1;

    /*FragmentManager fm;*/
    CurrentStateOperation stateOperation = CurrentStateOperation.getInstance();

    TabOperation mTabOperation;
    Calendar calendar;

    int idOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_opeations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idOperation = getIntent().getIntExtra(EXTRA_OPERATION, 0);

        operationSelector = new OperationEachOfPeriodSelector();

        if(idOperation == 0) {
            idOperation = stateOperation.getIdOperation();
        }

        viewPager = (ViewPager)findViewById(R.id.pager);

        mTabOperation = TabOperation.DAY_OPERATION;
        //////////////////////////////////////////////////////////////////////
        mOperations = operationSelector.getOperations(idOperation);
        mOperations.addAll(OperationQuery.getDaysOfDay(idOperation));
        //////////////////////////////
        if(idOperation == INCOM_OPERATION) {
            setTitle(R.string.main_incom);
            //listDayOperations = OperationQuery.getDaysOfDay(OperationQuery.OPERATION_INCOM);
        }else if(idOperation == OUTCOM_OPERATION){
            setTitle(R.string.main_outcom);
            //listDayOperations = OperationQuery.getDaysOfDay(OperationQuery.OPERATION_OUTCOM);
        }else if(idOperation == TRANSFER_OPERATION){
            setTitle(R.string.db_transfer);
            //listDayOperations = OperationQuery.getDaysOfDay(OperationQuery.OPERATION_TRANSFER);
        }
        ////////////////////////////////////////////////////////////////////////
        Log.d(TAG, "------------onCreate = OpeationsListActivity " + idOperation);

        FragmentManager fm = getSupportFragmentManager();
/*        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new DayOperationsFragment();
            FragmentTransaction fTrans = fm.beginTransaction();
            fTrans.add(R.id.fragmentContainer, fragment);
            fTrans.addToBackStack(null);
            fTrans.commit();
        }*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment addingOperationDialogFragment = null;

                if (idOperation == INCOM_OPERATION) {
                    addingOperationDialogFragment = CreateOperationDialog
                            .newInstance(CreateOperationDialog.INCOM_OPERATION,
                                    getString(R.string.dialog_title_add_incom), getString(R.string.db_incom));
                } else if (idOperation == OUTCOM_OPERATION) {
                    addingOperationDialogFragment = CreateOperationDialog
                            .newInstance(CreateOperationDialog.OUTCOM_OPERATION,
                                    getString(R.string.dialog_title_add_outcom), getString(R.string.db_outcom));
                }else if(idOperation == TRANSFER_OPERATION){
                    addingOperationDialogFragment = CreateTransferDialog.newInstance(null);
                }

                addingOperationDialogFragment.show(getFragmentManager(), "CreateOperationDialog");
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDateListOperation = (TextView)findViewById(R.id.tvDateListOperation);

        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
               // return DayOperationsFragment.newInstance(position, idOperation);
                return TimeOfPeriodOperations.newInstance(0, idOperation, TimeOfPeriodOperations.DAY_OPERATION);
            }

            @Override
            public int getCount() {
                return mOperations.size();
            }
        });

        viewPager.setCurrentItem(0);

        if(!mOperations.isEmpty()) {
            tvDateListOperation.setText("<<  "
                    + Utils.getDate(mOperations.getDate(mOperations.size() - 1))
                    + "  >>");
        }else{
            tvDateListOperation.setText("<<  " + getString(R.string.empty) + "  >>");
        }
        //tvDateListOperation.setText("<<  " + Utils.getDate(listDayOperations.get(listDayOperations.size() - 1).get(0).getDate()) + "  >>");

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // mCurrentPosition = position;
                tvDateListOperation.setText("<<  " + Utils.getDate(mOperations.getDate(mOperations.size() - 1)) + "  >>");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // initViewPager(mTabOperation);

        initTabs();
    }

    private void initTabs() {

        tvTabDay = (TextView)findViewById(R.id.tvTabDay);
        tvTabWeek = (TextView)findViewById(R.id.tvTabWeek);
        tvTabMonth = (TextView)findViewById(R.id.tvTabMonth);
        tvTabYear = (TextView)findViewById(R.id.tvTabYear);

        tvTabDay.setOnClickListener(this);
        tvTabWeek.setOnClickListener(this);
        tvTabMonth.setOnClickListener(this);
        tvTabYear.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "------------onResume " + idOperation);
        initViewPager(mTabOperation);
        //Toast.makeText(OpeationsListActivity.this, "onResume " + idOperation, Toast.LENGTH_SHORT).show();
        //idOperation = getIntent().getIntExtra(EXTRA_OPERATION, INCOM_OPERATION);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        stateOperation.setIdOperation(idOperation);
        Log.d(TAG, "------------onDestroy " + idOperation);
    }

    @Override
    public void onClick(View v) {

        fTrans = getSupportFragmentManager().beginTransaction();

        mCurrentPosition = -1;

        int id = v.getId();
        if(!mOperations.isEmpty()) {
            switch (id) {
                case R.id.tvTabDay:
                   // fTrans.replace(R.id.fragmentContainer, DayOperationsFragment.newInstance(0, idOperation));
                    fTrans.replace(R.id.fragmentContainer,
                            TimeOfPeriodOperations.newInstance(0, idOperation, TimeOfPeriodOperations.DAY_OPERATION));
                    //создать фрагмент поменять тексты
                    //changeTabs((TextView) v);
                    mTabOperation = TabOperation.DAY_OPERATION;
                    initViewPager(mTabOperation);
                    break;
                case R.id.tvTabWeek:
                    //fTrans.replace(R.id.fragmentContainer, WeekOperationsFragment.newInstance(0, idOperation));
                    fTrans.replace(R.id.fragmentContainer,
                            TimeOfPeriodOperations.newInstance(0, idOperation, TimeOfPeriodOperations.WEEK_OPERATION));
                    //changeTabs((TextView) v);
                    mTabOperation = TabOperation.WEEK_OPERATION;
                    initViewPager(mTabOperation);
                    break;
                case R.id.tvTabMonth:
                    //fTrans.replace(R.id.fragmentContainer, MonthOperationsFragment.newInstance(0, idOperation));
                    fTrans.replace(R.id.fragmentContainer,
                            TimeOfPeriodOperations.newInstance(0, idOperation, TimeOfPeriodOperations.MONTH_OPERATION));
                    //changeTabs((TextView) v);
                    mTabOperation = TabOperation.MONTH_OPERATION;
                    initViewPager(mTabOperation);
                    break;
                case R.id.tvTabYear:
                    //fTrans.replace(R.id.fragmentContainer, YearOperationsFragment.newInstance(0, idOperation));
                    fTrans.replace(R.id.fragmentContainer,
                            TimeOfPeriodOperations.newInstance(0, idOperation, TimeOfPeriodOperations.YEAR_OPERATION));
                    //changeTabs((TextView) v);
                    mTabOperation = TabOperation.YEAR_OPERATION;
                    initViewPager(mTabOperation);

                    break;
            }
        }

        //fTrans.addToBackStack(null);

        changeTabs((TextView) v);
        fTrans.commit();

    }

    private void changeTabs(TextView tv){
        tvTabDay.setTextColor(getResources().getColor(R.color.black));
        tvTabWeek.setTextColor(getResources().getColor(R.color.black));
        tvTabMonth.setTextColor(getResources().getColor(R.color.black));
        tvTabYear.setTextColor(getResources().getColor(R.color.black));

        tvTabDay.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.selector_tab_primary);
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

    private void initViewPager(TabOperation operation){

        //StringDateOperation stringDateOperation = StringDateOperation.getInstance();
        // fTrans = getSupportFragmentManager().beginTransaction();

        switch (operation){
            case DAY_OPERATION:
                mOperations.replace(OperationQuery.getDaysOfDay(idOperation));

               /* if(idOperation == INCOM_OPERATION) {
                    //listDayOperations = OperationQuery.getDaysOfDay(OperationQuery.OPERATION_INCOM);
                }else if(idOperation == OUTCOM_OPERATION){
                    listDayOperations = OperationQuery.getDaysOfDay(OperationQuery.OPERATION_OUTCOM);
                }else if(idOperation == TRANSFER_OPERATION){
                    listDayOperations = OperationQuery.getDaysOfDay(OperationQuery.OPERATION_TRANSFER);
                }*/
                break;
            case WEEK_OPERATION:
                mOperations.replace(OperationQuery.getListOperationsWeek(idOperation));
              /*  if(idOperation == INCOM_OPERATION) {
                    listDayOperations = OperationQuery.getListOperationsWeek(OperationQuery.OPERATION_INCOM);
                }else if(idOperation == OUTCOM_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsWeek(OperationQuery.OPERATION_OUTCOM);
                }else if(idOperation == TRANSFER_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsWeek(OperationQuery.OPERATION_TRANSFER);
                }*/
                break;
            case MONTH_OPERATION:
                mOperations.replace(OperationQuery.getListOperationsMonth(idOperation));
              /*  if(idOperation == INCOM_OPERATION) {
                    listDayOperations = OperationQuery.getListOperationsMonth(OperationQuery.OPERATION_INCOM);
                }else if(idOperation == OUTCOM_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsMonth(OperationQuery.OPERATION_OUTCOM);
                }else if(idOperation == TRANSFER_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsMonth(OperationQuery.OPERATION_TRANSFER);
                }*/
                break;
            case YEAR_OPERATION:
                mOperations.replace(OperationQuery.getListOperationsYear(idOperation));
               /* if(idOperation == INCOM_OPERATION) {
                    listDayOperations = OperationQuery.getListOperationsYear(OperationQuery.OPERATION_INCOM);
                }else if(idOperation == OUTCOM_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsYear(OperationQuery.OPERATION_OUTCOM);
                }else if(idOperation == TRANSFER_OPERATION){
                    listDayOperations = OperationQuery.getListOperationsYear(OperationQuery.OPERATION_TRANSFER);
                }*/
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
                /*fTrans.addToBackStack(null);
                fTrans.commit();*/
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
    }

    @Override
    public void itemChanged() {

    }

    @Override
    public void itemAdding(Operation operation) {
        try {

            mOperations.set(mCurrentPosition, operation);
        }catch(ArrayIndexOutOfBoundsException e){

        }
        initViewPager(mTabOperation);
    }

    @Override
    public void updateTransferOperation() {

    }

    @Override
    public void itemAdding(TransferOperation operation) {
        try {

            mOperations.set(mCurrentPosition, operation);
        }catch(ArrayIndexOutOfBoundsException e){

        }
        initViewPager(mTabOperation);
    }
}

