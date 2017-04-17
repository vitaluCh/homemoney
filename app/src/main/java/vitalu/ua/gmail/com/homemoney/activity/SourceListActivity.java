package vitalu.ua.gmail.com.homemoney.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.view.View;

import vitalu.ua.gmail.com.homemoney.database.database_query.SourceQuery;
import vitalu.ua.gmail.com.homemoney.dialog.CreateCurrencyDialog;
import vitalu.ua.gmail.com.homemoney.dialog.CreateSourceDialog;
import vitalu.ua.gmail.com.homemoney.fragment.SourceListFragment;
import vitalu.ua.gmail.com.homemoney.model.database_model.Source;

public class SourceListActivity extends SingleFragmentActivity implements View.OnClickListener,
/*SourceListFragment.ChoiceCategoryListener,*/ CreateSourceDialog.AddingSourceListener {

    public static final String TAG = "myLogMainActivity";

    @Override
    protected Fragment createFragment() {
        return new SourceListFragment();
    }

    @Override
    public void onClick(View v) {

     /*   DialogFragment addingTaskDialogFragment = new CreateSourceDialog();
        addingTaskDialogFragment.show(getFragmentManager(), "CreateSourceDialog");*/

        DialogFragment addingTaskDialogFragment = CreateSourceDialog
                .newInstance(CreateCurrencyDialog.CREATE_SOURCE_ACTIVITY);
        addingTaskDialogFragment.show(getFragmentManager(), "CreateSourceDialog");
    }

/*    @Override
    public void categoryListener(int operation) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fTrans = fm.beginTransaction();

        fTrans.replace(R.id.fragmentContainer, ExpandListSourceFragment.newInstance(operation));
        fTrans.addToBackStack(null);
        fTrans.commit();

        Log.d(TAG, "принимаем " + operation);
    }*/

    @Override
    public void onAdded(Source source) {
        source.setId((int) SourceQuery.addSource(source));
        onChangeIteme.onChangeItem(source);
    }
}
