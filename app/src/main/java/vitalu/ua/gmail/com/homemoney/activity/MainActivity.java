package vitalu.ua.gmail.com.homemoney.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.dialog.CreateOperationDialog;
import vitalu.ua.gmail.com.homemoney.dialog.CreateTransferDialog;
import vitalu.ua.gmail.com.homemoney.fragment.ContentMainFragment;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CreateOperationDialog.OnItemChanged, CreateTransferDialog.OnItemChanged {

    public static final String TAG = "myLogMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ////////////////////////////////////////////////////////////////////////
/*
        for (TypeOperation typeOper :
                AppContext.getDbAdapter().getTypeOperation(DBHelper.TYPE_OPERATION_COLUMN_ID + " = ?",
                        new String[]{String.format("%d", 2),}, null))
        {
            Log.d(TAG, String.valueOf(typeOper.getId()) +" "+ typeOper.getName());
        }

        for (Currency currency : AppContext.getDbAdapter().getCurrency(null, null, null)) {
            Log.d(TAG, String.valueOf(currency.getId()) +" "+ currency.getFullName()+ " "
            + currency.getShortName()+" "+ currency.isVisible());
        }

        for (Source source : AppContext.getDbAdapter().getSource(null, null, null)) {
            Log.d(TAG, String.valueOf(source.getId()) +" "+ source.getNameSours()+ " "
                    + source.getTypeId() + " " + source.getParentId()+" "+ source.isVisible());
        }*/

        ////////////////////////////////////////////////////////////////////////////

        FloatingActionButton fabInCome = (FloatingActionButton) findViewById(R.id.fab_income);
        fabInCome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment addingOperationDialogFragment = CreateOperationDialog
                        .newInstance(CreateOperationDialog.INCOM_OPERATION,
                                getString(R.string.dialog_title_add_incom), getString(R.string.db_incom));
                addingOperationDialogFragment.show(getFragmentManager(), "CreateOperationDialog");
            }
        });

        FloatingActionButton fabOutCome = (FloatingActionButton) findViewById(R.id.fab_outcom);
        fabOutCome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment addingOperationDialogFragment = CreateOperationDialog
                        .newInstance(CreateOperationDialog.OUTCOM_OPERATION,
                                getString(R.string.dialog_title_add_outcom), getString(R.string.db_outcom));
                addingOperationDialogFragment.show(getFragmentManager(), "CreateOperationDialog");
               /* DialogFragment addingTaskDialogFragment = new CreateOperationDialog();
                addingTaskDialogFragment.show(getFragmentManager(), "CreateOperationDialog");*/
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        FloatingActionButton fab_transfer = (FloatingActionButton) findViewById(R.id.fab_transfer);
        fab_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* DialogFragment addingOperationDialogFragment = CreateOperationDialog
                        .newInstance(CreateOperationDialog.OUTCOM_OPERATION,
                                getString(R.string.dialog_title_add_outcom), getString(R.string.db_outcom));
                addingOperationDialogFragment.show(getFragmentManager(), "CreateOperationDialog");*/

                DialogFragment addingTaskDialogFragment = CreateTransferDialog.newInstance(null);
                addingTaskDialogFragment.show(getFragmentManager(), "CreateTransferDialog");
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //////////////////////////

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new ContentMainFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        Log.d(TAG, "main");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void itemChanged() {

    }

    @Override
    public void itemAdding(Operation operation) {

    }

    @Override
    public void updateTransferOperation() {

    }

    @Override
    public void itemAdding(TransferOperation operation) {

    }
}
