package vitalu.ua.gmail.com.homemoney.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import vitalu.ua.gmail.com.homemoney.R;

/**
 * Created by Виталий on 21.01.2016.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity
        implements View.OnClickListener{

    public static final String TAG = "myLogMainActivity";

    protected abstract Fragment createFragment();
    protected FloatingActionButton fab;

    protected OnChangeIteme onChangeIteme;

    public interface OnChangeIteme<T>{
        void onChangeItem(T object);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            FragmentTransaction fTrans = fm.beginTransaction();
            fTrans.add(R.id.fragmentContainer, fragment);
            fTrans.addToBackStack(null);
            fTrans.commit();
        }

        try {
            onChangeIteme = (OnChangeIteme) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString() + " необходимо реализовать OnChangeIteme");
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
