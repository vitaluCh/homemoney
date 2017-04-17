package vitalu.ua.gmail.com.homemoney.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.OperationQuery;
import vitalu.ua.gmail.com.homemoney.dialog.CreateOperationDialog;
import vitalu.ua.gmail.com.homemoney.dialog.CreateTransferDialog;
import vitalu.ua.gmail.com.homemoney.fragment.BaseOperationFragment;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;
import vitalu.ua.gmail.com.homemoney.model.factory_method.period.OperationsOfPeriod;
import vitalu.ua.gmail.com.homemoney.model.factory_method.period.OperationsSelector;

public class OperationActivity extends AppCompatActivity implements View.OnClickListener,
        CreateOperationDialog.OnItemChanged, CreateTransferDialog.OnItemChanged{

    public static final String TAG = "myLogMainActivity";

    public static final int DAY_OPERATION = 1;
    public static final int WEEK_OPERATION = 2;
    public static final int MONTH_OPERATION = 3;
    public static final int YEAR_OPERATION = 4;

    public static final String EXTRA_PERIOD_OPERATION = "vitalu.ua.gmail.com.homemoney.activity.extra_period_operation";
    public static final String EXTRA_POSITION = "vitalu.ua.gmail.com.homemoney.activity.position_operation";
    public static final String EXTRA_OPERATION = "vitalu.ua.gmail.com.homemoney.activity.extra_operation";
    public static final String EXTRA_DAY = "vitalu.ua.gmail.com.homemoney.activity.extra_day";
    public static final int INCOM_OPERATION = 1;
    public static final int OUTCOM_OPERATION = 2;
    public static final int TRANSFER_OPERATION = 3;

    private ViewPager viewPager;
   // private List<Operation> listOperation;
    OperationsSelector selector;
    OperationsOfPeriod mOperations;

    int mPeriod;//период день, мес, год, неделя
    int mPosition;//позиция в списке
    int mIdOperation;//операция доход, расход
    long mDay;//дата операции
    int mCurrencyPosition;//текущая позиция
/*
    FragmentTransaction fTrans;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPeriod = getIntent().getIntExtra(EXTRA_PERIOD_OPERATION, 1);
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);
        mIdOperation = getIntent().getIntExtra(EXTRA_OPERATION, 1);
        mDay = getIntent().getLongExtra(EXTRA_DAY, 0);

        mCurrencyPosition = mPosition;

    /*    Log.d(TAG, "OperationActivity mPeriod = " + mPeriod);
        Log.d(TAG, "OperationActivity mPosition = " + mPosition);
        Log.d(TAG, "OperationActivity mIdOperation = " + mIdOperation);
        Log.d(TAG, "OperationActivity mDay = " + Utils.getDate(mDay));*/
//////////////////////////
        selector = new OperationsSelector();
        mOperations = selector.getOperation(mIdOperation);
        ///////////////////////////////

        switch (mPeriod){//заполняем массив согласно периода
            case DAY_OPERATION:
                mOperations.addAll(OperationQuery.getOperationsDay(mDay, mIdOperation));
                //listOperation = OperationQuery.getOperationsDay(mDay, mIdOperation);
                break;
            case WEEK_OPERATION:
                mOperations.addAll(OperationQuery.getOperationsWeek(mDay, mIdOperation));
                //listOperation = OperationQuery.getOperationsWeek(mDay, mIdOperation);
                break;
            case MONTH_OPERATION:
                mOperations.addAll(OperationQuery.getOperationsMonth(mDay, mIdOperation));
                //listOperation = OperationQuery.getOperationsMonth(mDay, mIdOperation);
                break;
            case YEAR_OPERATION:
                mOperations.addAll(OperationQuery.getOperationsYear(mDay, mIdOperation));
                //listOperation = OperationQuery.getOperationsYear(mDay, mIdOperation);
                break;
        }

        FloatingActionButton fab_update = (FloatingActionButton) findViewById(R.id.fab_update);
        FloatingActionButton fab_delete = (FloatingActionButton) findViewById(R.id.fab_delete);

        fab_update.setOnClickListener(this);
        fab_delete.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViewPager();
    }

    @Override
    public void onClick(View v) {

        DialogFragment addingOperationDialogFragment = null;

        switch (v.getId()){
            case R.id.fab_update:
                if (mIdOperation == INCOM_OPERATION) {
                    addingOperationDialogFragment = CreateOperationDialog
                            .newInstance(CreateOperationDialog.INCOM_OPERATION,
                                    getString(R.string.dialog_title_update_incom),
                                    getString(R.string.db_incom),
                                    (Operation)mOperations.get(mCurrencyPosition));
                } else if (mIdOperation == OUTCOM_OPERATION) {
                    addingOperationDialogFragment = CreateOperationDialog
                            .newInstance(CreateOperationDialog.OUTCOM_OPERATION,
                                    getString(R.string.dialog_title_update_outcom),
                                    getString(R.string.db_outcom),
                                    (Operation)mOperations.get(mCurrencyPosition));
                }else if(mIdOperation == TRANSFER_OPERATION){
                    addingOperationDialogFragment = CreateTransferDialog
                            .newInstance((TransferOperation) mOperations.get(mCurrencyPosition));
                }

                addingOperationDialogFragment.show(getFragmentManager(), "CreateOperationDialog");

               // Toast.makeText(OperationActivity.this, listOperation.get(mCurrencyPosition).getSoyrce().getNameSours(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_delete:
                if(mIdOperation == INCOM_OPERATION || mIdOperation == OUTCOM_OPERATION) {
                    OperationQuery.deleteOperation((Operation)mOperations.get(mCurrencyPosition));
                }else if(mIdOperation == TRANSFER_OPERATION){
                    OperationQuery.deleteOperation(((TransferOperation)mOperations.get(mCurrencyPosition)).getOutComeOperation());
                    OperationQuery.deleteOperation(((TransferOperation)mOperations.get(mCurrencyPosition)).getInComeOperation());
                }
                mOperations.remove(mCurrencyPosition);
                Toast.makeText(OperationActivity.this, getString(R.string.operation_delete), Toast.LENGTH_SHORT).show();
                //Toast.makeText(OperationActivity.this, "OperatiomActivity", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }

    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        viewPager.getAdapter().notifyDataSetChanged();
    }*/

    @Override
    public void itemChanged() {
        //viewPager.getAdapter().notifyDataSetChanged();
        //Log.d(TAG, "OperationActivity mPeriod = ----------------------------" + mPeriod);
       // viewPager.setCurrentItem(mPosition);
       /* viewPager.getAdapter().notifyDataSetChanged();
       getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer,
               OperationFragment.newInstance(mPeriod, mPosition, mIdOperation, mDay)).commit();*/
        Toast.makeText(OperationActivity.this, getString(R.string.operation_update), Toast.LENGTH_SHORT).show();
        finish();
        //initViewPager();
    }

    @Override
    public void itemAdding(Operation operation) {

    }

    private void initViewPager(){
        viewPager = null;
        viewPager = (ViewPager)findViewById(R.id.pager);

        FragmentManager fm = getSupportFragmentManager();

        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                //return OperationFragment.newInstance(mPeriod, position, mIdOperation, mDay);
                return BaseOperationFragment.newInstance(mPeriod, position, mIdOperation, mDay);
            }

            @Override
            public int getCount() {
                return mOperations.size();
            }
        });

        viewPager.setCurrentItem(mPosition);

        //tvDateListOperation.setText("<<  " + Utils.getDate(listDayOperations.get(listDayOperations.size() - 1).get(0).getDate()) + "  >>");
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrencyPosition = position;//listOperation.get(position).getId();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void updateTransferOperation() {
        Toast.makeText(OperationActivity.this, getString(R.string.operation_update), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void itemAdding(TransferOperation operation) {

    }
}
