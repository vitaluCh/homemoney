package vitalu.ua.gmail.com.homemoney.activity;

import android.app.DialogFragment;
import android.content.Intent;
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

import java.util.Calendar;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.OperationQuery;
import vitalu.ua.gmail.com.homemoney.database.database_query.PatternQuery;
import vitalu.ua.gmail.com.homemoney.dialog.CreateOperationDialog;
import vitalu.ua.gmail.com.homemoney.dialog.CreateTransferDialog;
import vitalu.ua.gmail.com.homemoney.fragment.BaseTemplateFragment;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;
import vitalu.ua.gmail.com.homemoney.model.factory_method.period.OperationsOfPeriod;
import vitalu.ua.gmail.com.homemoney.model.factory_method.period.OperationsSelector;

public class TemplateActivity extends AppCompatActivity implements View.OnClickListener,
        CreateOperationDialog.OnItemChanged, CreateTransferDialog.OnItemChanged{

    public static final int INCOM_OPERATION = 1;
    public static final int OUTCOM_OPERATION = 2;
    public static final int TRANSFER_OPERATION = 3;

    public static final String EXTRA_OPERATION = "vitalu.ua.gmail.com.homemoney.activity.extra_operation";
    public static final String EXTRA_POSITION = "vitalu.ua.gmail.com.homemoney.activity.extra_position";
    public static final String EXTRA_OPERATION_ID = "vitalu.ua.gmail.com.homemoney.activity.extra_position_id";
    public static final String REQUEST_DATA = "vitalu.ua.gmail.com.homemoney.activity.request";

    private ViewPager viewPager;

    OperationsSelector selector;
    OperationsOfPeriod mOperations;

    int mIdOperation;//операция доход, расход
    int mPosition;//позиция в списке
    int mCurrencyPosition;//текущая позиция

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab_update = (FloatingActionButton) findViewById(R.id.fab_update);
        FloatingActionButton fab_delete = (FloatingActionButton) findViewById(R.id.fab_delete);
        FloatingActionButton fab_use = (FloatingActionButton) findViewById(R.id.fab_use);

        fab_update.setOnClickListener(this);
        fab_delete.setOnClickListener(this);
        fab_use.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();

        mIdOperation = intent.getIntExtra(EXTRA_OPERATION, 1);
        mPosition = intent.getIntExtra(EXTRA_POSITION, 0);
        mCurrencyPosition = mPosition;

        selector = new OperationsSelector();
        mOperations = selector.getOperation(mIdOperation);

        mOperations.addAll(PatternQuery.getTemplate(mIdOperation));

    /*    if(mIdOperation == TRANSFER_OPERATION){
            Toast.makeText(TemplateActivity.this, "TRANSFER_OPERATION", Toast.LENGTH_SHORT).show();
           //mOperations.reverse();
            Collections.reverse((List<TransferOperation>)mOperations.getAll());
        }*/

        viewPager = (ViewPager)findViewById(R.id.pager);

        FragmentManager fm = getSupportFragmentManager();

        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                //return OperationFragment.newInstance(mPeriod, position, mIdOperation, mDay);
                return BaseTemplateFragment.newInstance(position, mIdOperation);
                //BaseOperationFragment.newInstance(mPeriod, position, mIdOperation, mDay);
            }

            @Override
            public int getCount() {
                return mOperations.size();
            }
        });

        viewPager.setCurrentItem(mPosition);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrencyPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        DialogFragment updateOperationDialogFragment = null;

        switch (v.getId()){
            case R.id.fab_use:
                Calendar calendar = Calendar.getInstance();
                if (mIdOperation == INCOM_OPERATION || mIdOperation == OUTCOM_OPERATION) {
                    Operation operation = (Operation) mOperations.get(mCurrencyPosition);
                    operation.setDate(calendar.getTimeInMillis());
                    OperationQuery.addOperation(operation);
                }else if(mIdOperation == TRANSFER_OPERATION){//редактировать шаблон перевода
                    TransferOperation operation = (TransferOperation) mOperations.get(mCurrencyPosition);
                    operation.getInComeOperation().setDate(calendar.getTimeInMillis());
                    operation.getOutComeOperation().setDate(calendar.getTimeInMillis());

                    OperationQuery.addOperationTransfer(operation.getOutComeOperation(), OperationQuery.OPERATION_OUTCOM);
                    OperationQuery.addOperationTransfer(operation.getInComeOperation(), OperationQuery.OPERATION_INCOM);
                }
                Toast.makeText(TemplateActivity.this, getString(R.string.add_operation), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.fab_update:
                if (mIdOperation == INCOM_OPERATION) {

                    updateOperationDialogFragment= CreateOperationDialog
                            .newInstance(CreateOperationDialog.INCOM_OPERATION,
                                    getString(R.string.add_template), getString(R.string.db_incom),
                                    (Operation) mOperations.get(mCurrencyPosition),
                                    CreateOperationDialog.TEMPLATE);

                } else if (mIdOperation == OUTCOM_OPERATION) {

                    updateOperationDialogFragment= CreateOperationDialog
                            .newInstance(CreateOperationDialog.OUTCOM_OPERATION,
                                    getString(R.string.add_template), getString(R.string.db_outcom),
                                    (Operation) mOperations.get(mCurrencyPosition),
                                    CreateOperationDialog.TEMPLATE);

                }else if(mIdOperation == TRANSFER_OPERATION){//редактировать шаблон перевода
                    updateOperationDialogFragment = CreateTransferDialog
                            .newInstance((TransferOperation) mOperations.get(mCurrencyPosition));
                }

                updateOperationDialogFragment.show(getFragmentManager(), "CreateOperationDialog");
                break;
            case R.id.fab_delete:
                if(mIdOperation == INCOM_OPERATION || mIdOperation == OUTCOM_OPERATION) {
                    PatternQuery.deleteTemplate((Operation) mOperations.get(mCurrencyPosition));
                    //OperationQuery.deleteOperation((Operation) mOperations.get(mCurrencyPosition));
                }else if(mIdOperation == TRANSFER_OPERATION){
                    PatternQuery.deleteTemplate(((TransferOperation)mOperations.get(mCurrencyPosition)).getOutComeOperation());
                    PatternQuery.deleteTemplate(((TransferOperation)mOperations.get(mCurrencyPosition)).getInComeOperation());
                   // OperationQuery.deleteOperation(((TransferOperation)mOperations.get(mCurrencyPosition)).getOutComeOperation());
                   // OperationQuery.deleteOperation(((TransferOperation)mOperations.get(mCurrencyPosition)).getInComeOperation());
                }
               mOperations.remove(mCurrencyPosition);
                Toast.makeText(TemplateActivity.this, getString(R.string.operation_delete), Toast.LENGTH_SHORT).show();
                //intent.putExtra(REQUEST_DATA, mCurrencyPosition);
                //setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void itemChanged() {
        Toast.makeText(TemplateActivity.this, getString(R.string.operation_update), Toast.LENGTH_SHORT).show();
        //Toast.makeText(TemplateActivity.this, ((Operation) mOperations.get(mCurrencyPosition)).getSoyrce().getNameSours(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void itemAdding(Operation operation) {

    }

    @Override
    public void updateTransferOperation() {
        Toast.makeText(TemplateActivity.this, getString(R.string.operation_update), Toast.LENGTH_SHORT).show();
        //Toast.makeText(TemplateActivity.this, ((Operation) mOperations.get(mCurrencyPosition)).getSoyrce().getNameSours(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void itemAdding(TransferOperation operation) {

    }

 /*   @Override
    protected void onResume() {
        super.onResume();
    }*/
}
