package vitalu.ua.gmail.com.homemoney.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.view.View;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.dialog.CreateOperationDialog;
import vitalu.ua.gmail.com.homemoney.fragment.DebtListFragment;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;

public class DebtListActivity extends SingleFragmentActivity implements CreateOperationDialog.OnItemChanged {


    @Override
    protected Fragment createFragment() {
        return new DebtListFragment();
    }

/*    @Override
    public void onAdded(Currency currency) {
        currency.setId((int) CurrencyQuery.addCurrency(currency));
        onChangeIteme.onChangeItem(currency);
        //Toast.makeText(CurrencyListActivity.this, "Записать валюту в базу", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void onClick(View v) {
        //Вызвать диалог для создания долга
        DialogFragment addingOperationDialogFragment = CreateOperationDialog
                .newInstance(CreateOperationDialog.DEBT_OPERATION,
                        getString(R.string.dialog_title_add_debt), null);
        addingOperationDialogFragment.show(getFragmentManager(), "CreateOperationDialog");
       /*
        DialogFragment addingTaskDialogFragment = CreateCurrencyDialog
                .newInstance(CreateCurrencyDialog.CREATE_SOURCE_ACTIVITY);
        addingTaskDialogFragment.show(getFragmentManager(), "CreateCurrencyDialog");*/
        //Toast.makeText(CurrencyListActivity.this, "Вызов диалога создания ВАЛЮТЫ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemChanged() {

    }

    @Override
    public void itemAdding(Operation operation) {
        onChangeIteme.onChangeItem(operation);
    }
}