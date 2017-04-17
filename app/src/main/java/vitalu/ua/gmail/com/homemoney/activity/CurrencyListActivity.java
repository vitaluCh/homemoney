package vitalu.ua.gmail.com.homemoney.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.view.View;

import vitalu.ua.gmail.com.homemoney.database.database_query.CurrencyQuery;
import vitalu.ua.gmail.com.homemoney.dialog.CreateCurrencyDialog;
import vitalu.ua.gmail.com.homemoney.fragment.CurrencyListFragment;
import vitalu.ua.gmail.com.homemoney.model.database_model.Currency;

public class CurrencyListActivity extends SingleFragmentActivity implements CreateCurrencyDialog.AddingCurrencyListener{


    @Override
    protected Fragment createFragment() {
        return new CurrencyListFragment();
    }

    @Override
    public void onAdded(Currency currency) {
        currency.setId((int) CurrencyQuery.addCurrency(currency));
        onChangeIteme.onChangeItem(currency);
        //Toast.makeText(CurrencyListActivity.this, "Записать валюту в базу", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        //Вызвать диалог для создания новой валюты
        DialogFragment addingTaskDialogFragment = CreateCurrencyDialog
                .newInstance(CreateCurrencyDialog.CREATE_SOURCE_ACTIVITY);
        addingTaskDialogFragment.show(getFragmentManager(), "CreateCurrencyDialog");
        //Toast.makeText(CurrencyListActivity.this, "Вызов диалога создания ВАЛЮТЫ", Toast.LENGTH_SHORT).show();
    }


}
