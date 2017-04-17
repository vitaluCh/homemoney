package vitalu.ua.gmail.com.homemoney.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.adapter.TabsPagerFragmentAdapter;
import vitalu.ua.gmail.com.homemoney.dialog.CreateOperationDialog;
import vitalu.ua.gmail.com.homemoney.dialog.CreateTransferDialog;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;

public class TamplateTabActivity extends AppCompatActivity
        implements CreateOperationDialog.OnItemChanged, CreateTransferDialog.OnItemChanged{

    public static final String TAG = "myLogMainActivity";

    private int COUNT_TABS = 3;//количество tab
    ViewPager viewPager;
    int currencyPosition = 0;

    /*public interface ChangeCommunicator<T>{
        void onItemChanged(T operation);
    }

    public ChangeCommunicator changeCommunicator;*/

    public interface OutcomeChangeCommunicator{
        void onOutcomeItemChanged(Operation operation);
    }

    public OutcomeChangeCommunicator outcomeChangeCommunicator;

    public interface IncomeChangeCommunicator{
        void onItemIncomeChanged(Operation operation);
    }

    public IncomeChangeCommunicator incomeChangeCommunicator;

    public interface TransferChangeCommunicator{
        void onTransferItemChanged(TransferOperation operation);
    }

    public TransferChangeCommunicator transferChangeCommunicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamplate_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment addingOperationDialogFragment = null;
                switch (currencyPosition){
                    case 0:
                        addingOperationDialogFragment= CreateOperationDialog
                                .newInstance(CreateOperationDialog.INCOM_OPERATION,
                                        getString(R.string.add_template), getString(R.string.db_incom), CreateOperationDialog.TEMPLATE);
                        addingOperationDialogFragment.show(getFragmentManager(), "CreateOperationDialog");
                        break;
                    case 1:
                        addingOperationDialogFragment = CreateOperationDialog
                                .newInstance(CreateOperationDialog.OUTCOM_OPERATION,
                                        getString(R.string.add_template), getString(R.string.db_outcom), CreateOperationDialog.TEMPLATE);
                        addingOperationDialogFragment.show(getFragmentManager(), "CreateOperationDialog");
                        break;
                    case 2:
                        addingOperationDialogFragment = CreateTransferDialog
                                .newInstance(CreateTransferDialog.TEMPLATE_TRANSFER);
                        addingOperationDialogFragment.show(getFragmentManager(), "CreateTransferDialog");
                        break;
                }
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initTabs();
    }

    TabsPagerFragmentAdapter fragmentAdapter;
    TabLayout tabLayout;

    private void initTabs() {

        tabLayout = (TabLayout)findViewById(R.id.tab_layout_operations);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.main_incom));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.main_outcom));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.db_transfer));
       // tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_year));
        // tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_all));

        fragmentAdapter = new TabsPagerFragmentAdapter(getSupportFragmentManager(), COUNT_TABS);

/*        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tabLayout.set
                Toast.makeText(OpeationsActivity.this, tab.getPosition(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

        viewPager = (ViewPager)findViewById(R.id.pager);

        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currencyPosition = position;
                Log.d(TAG, "onPageSelected = " + currencyPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //currencyPosition = tab.getPosition();

                Log.d(TAG, "currencyPosition = " + currencyPosition);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    @Override
    public void itemChanged() {

    }

    @Override
    public void itemAdding(Operation operation) {
        Log.d(TAG, "itemAdding в TamplateTabActivity");
        //changeCommunicator.onItemChanged(operation);
        switch (currencyPosition){
            case 0:
                incomeChangeCommunicator.onItemIncomeChanged(operation);
                break;
            case 1:
                outcomeChangeCommunicator.onOutcomeItemChanged(operation);
                break;
            case 2:
                Log.d(TAG, "case 2");
                break;
        }
    }


    @Override
    public void updateTransferOperation() {

    }

    @Override
    public void itemAdding(TransferOperation operation) {
        //changeCommunicator.onItemChanged(operation);
        transferChangeCommunicator.onTransferItemChanged(operation);
    }

    ///////////////////////////////////////////////////////////////////
    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "------------onResume ");
        viewPager = (ViewPager)findViewById(R.id.pager);

        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(currencyPosition);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currencyPosition = position;
                Log.d(TAG, "onPageSelected = " + currencyPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //Toast.makeText(OpeationsListActivity.this, "onResume " + idOperation, Toast.LENGTH_SHORT).show();
        //idOperation = getIntent().getIntExtra(EXTRA_OPERATION, INCOM_OPERATION);
    }

}
